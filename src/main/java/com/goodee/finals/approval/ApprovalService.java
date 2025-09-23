package com.goodee.finals.approval;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.ApprovalAttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ApprovalService {
	private static final Integer NORMAL = 999; // 기안
	private static final Integer VACATION = 901; // 휴가
	private static final Integer OVERTIME = 902; // 야근
	private static final Integer EARLY = 903; // 조퇴
	
	private static final Integer SAVE = 700; // 임시저장
	private static final Integer RUN = 701; // 결재중
	private static final Integer FINISH = 702; // 결재완료
	private static final Integer DENY = 703; // 결재반려

	private static final Integer RECEIVER = 710; // 수신
	private static final Integer CHECKER = 711; // 검토
	private static final Integer CONFIRMER = 712; // 승인
	private static final Integer AGREER = 713; // 합의
	
	private static final Integer WAIT = 720; // 처리대기
	private static final Integer READY = 721; // 처리요청
	private static final Integer DONE = 722; // 처리완료
	
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private ApprovalRepository approvalRepository;
	@Autowired
	private StaffService staffService;
	@Autowired
	private FileService fileService;
	@Autowired
	private AttachmentRepository attachmentRepository;

	public List<DeptDTO> getDeptList() {
		return deptRepository.findAll();
	}
	
	public Integer findLastAprvCode() {
		return approvalRepository.findLastAprvCode();
	}
	
	public Page<ApprovalListDTO> getApprovalList(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllApproval(staffCode, search, pageable);
	}
	
	public Page<ApprovalListDTO> getApprovalRequestList(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllApprovalRequest(staffCode, search, pageable);
	}

	public Page<ApprovalListDTO> getApprovalReadyList(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllApprovalReady(staffCode, search, pageable);
	}

	public Page<ApprovalResultDTO> getApprovalMineList(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllApprovalMine(staffCode, search, pageable);
	}

	public Page<ApprovalResultDTO> getApprovalFinishList(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllApprovalFinish(staffCode, search, pageable);
	}
	
	public ApprovalDTO getApprovalDetail(Integer aprvCode) {
		return approvalRepository.findById(aprvCode).orElseThrow();
	}

	public boolean sendNormalDraft(InputApprovalDTO inputApprovalDTO, MultipartFile[] attach) throws IOException {
		ApprovalDTO draft = setDraftDefault(inputApprovalDTO, NORMAL);
		setApprover(draft, inputApprovalDTO.getApprover());
		
		if (inputApprovalDTO.getReceiver() != null && inputApprovalDTO.getReceiver().size() != 0) setReceiver(draft, inputApprovalDTO.getReceiver());
		if (inputApprovalDTO.getAgreer() != null && inputApprovalDTO.getAgreer().size() != 0) setAgreer(draft, inputApprovalDTO.getAgreer());
		if (attach != null && attach.length != 0) setAttach(draft, attach);

		ApprovalDTO result = approvalRepository.saveAndFlush(draft);
		
		if (result != null) return true;
		else return false;
	}

	private ApprovalDTO setDraftDefault(InputApprovalDTO inputApprovalDTO, Integer aprvType) {
		ApprovalDTO approvalDTO = new ApprovalDTO();
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		approvalDTO.setAprvCode(Integer.valueOf(inputApprovalDTO.getAprvCode()));
		approvalDTO.setAprvType(aprvType);
		approvalDTO.setAprvTitle(inputApprovalDTO.getAprvTitle());
		approvalDTO.setAprvContent(inputApprovalDTO.getAprvContent());
		approvalDTO.setAprvDate(LocalDate.now());
		approvalDTO.setAprvExe(inputApprovalDTO.getAprvExe());
		approvalDTO.setAprvState(RUN);
		approvalDTO.setStaffDTO(staffDTO);
		approvalDTO.setAprvTotal(inputApprovalDTO.getApprover().size() + 1);
		approvalDTO.setAprvCrnt(2);
		
		return approvalDTO;
	}
	
	private void setApprover(ApprovalDTO draft, List<String> approver) {
		List<ApproverDTO> approverDTOs = new ArrayList<>();
		
		for (int i = 0; i < approver.size(); i++) {
			StaffDTO staffDTO = staffService.getStaff(Integer.valueOf(approver.get(i)));
			
			ApproverDTO approverDTO = new ApproverDTO();
			approverDTO.setApprovalDTO(draft);
			approverDTO.setStaffDTO(staffDTO);
			
			if (i == 0) approverDTO.setApvrType(CONFIRMER);
			else approverDTO.setApvrType(CHECKER);
			
			approverDTO.setApvrSeq(approver.size() - i);
			
			if (i == approver.size() - 1) approverDTO.setApvrState(READY);
			else approverDTO.setApvrState(WAIT);
			
			approverDTOs.add(approverDTO);
		}
		
		draft.setApproverDTOs(approverDTOs);
	}
	
	private void setReceiver(ApprovalDTO draft, List<String> receiver) {
		for (int i = 0; i < receiver.size(); i++) {
			StaffDTO staffDTO = staffService.getStaff(Integer.valueOf(receiver.get(i)));
			
			ApproverDTO approverDTO = new ApproverDTO();
			approverDTO.setApprovalDTO(draft);
			approverDTO.setStaffDTO(staffDTO);
			approverDTO.setApvrType(RECEIVER);
			approverDTO.setApvrSeq(0);
			
			draft.getApproverDTOs().add(approverDTO);
		}
	}
	
	private void setAgreer(ApprovalDTO draft, List<String> agreer) {
		for (int i = 0; i < agreer.size(); i++) {
			StaffDTO staffDTO = staffService.getStaff(Integer.valueOf(agreer.get(i)));
			
			ApproverDTO approverDTO = new ApproverDTO();
			approverDTO.setApprovalDTO(draft);
			approverDTO.setStaffDTO(staffDTO);
			approverDTO.setApvrType(AGREER);
			approverDTO.setApvrSeq(0);
			
			draft.getApproverDTOs().add(approverDTO);
		}
	}
	
	private void setAttach(ApprovalDTO draft, MultipartFile[] attach) throws IOException {
		List<ApprovalAttachmentDTO> attachList = new ArrayList<>();
		
		for (MultipartFile file : attach) {
			if (file != null && file.getSize() != 0) {
				String fileName = fileService.saveFile(FileService.APPROVAL, file);
				
				AttachmentDTO attachmentDTO = new AttachmentDTO();
				attachmentDTO.setOriginName(file.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				attachmentDTO.setAttachSize(file.getSize());
				
				attachmentRepository.save(attachmentDTO);
				
				ApprovalAttachmentDTO approvalAttachmentDTO = new ApprovalAttachmentDTO();
				approvalAttachmentDTO.setAttachmentDTO(attachmentDTO);
				approvalAttachmentDTO.setApprovalDTO(draft);
				
				attachList.add(approvalAttachmentDTO);
			}
		}
		
		if (attachList.size() != 0) draft.setApprovalAttachmentDTOs(attachList);
	}

}
