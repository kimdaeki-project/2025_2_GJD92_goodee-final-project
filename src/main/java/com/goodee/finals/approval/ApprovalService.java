package com.goodee.finals.approval;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.goodee.finals.common.attachment.StaffSignDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;
import com.goodee.finals.staff.StaffService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
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
	private static final Integer DISABLED = 723; // 처리불능
	
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private ApprovalRepository approvalRepository;
	@Autowired
	private StaffService staffService;
	@Autowired
	private StaffRepository staffRepository;
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
	
	public Page<ApprovalResultDTO> getApprovalRecept(Integer staffCode, String search, Pageable pageable) {
		return approvalRepository.findAllReceive(staffCode, search, pageable);
	}
	
	public ApprovalDTO getApprovalDetail(Integer aprvCode) {
		return approvalRepository.findById(aprvCode).orElseThrow();
	}
	
	public AttachmentDTO getAttach(Long attachNum) {
		return attachmentRepository.findById(attachNum).orElseThrow();
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
	
	public boolean sendVacationDraft(InputApprovalDTO inputApprovalDTO, VacationDTO vacationDTO) {
		inputApprovalDTO.setAprvContent("상기의 사유로 휴가를 신청합니다.");
		inputApprovalDTO.setAprvExe(vacationDTO.getVacStart());
		
		ApprovalDTO draft = setDraftDefault(inputApprovalDTO, VACATION);
		setApprover(draft, inputApprovalDTO.getApprover());
		
		if (inputApprovalDTO.getReceiver() != null && inputApprovalDTO.getReceiver().size() != 0) setReceiver(draft, inputApprovalDTO.getReceiver());
		if (inputApprovalDTO.getAgreer() != null && inputApprovalDTO.getAgreer().size() != 0) setAgreer(draft, inputApprovalDTO.getAgreer());
		
		draft.setVacationDTO(vacationDTO);
		vacationDTO.setApprovalDTO(draft);
		ApprovalDTO result = approvalRepository.saveAndFlush(draft);
		
		if (result != null) return true;
		else return false;
	}
	
	public boolean sendOvertimeDraft(InputApprovalDTO inputApprovalDTO, OvertimeDTO overtimeDTO) {
		inputApprovalDTO.setAprvContent("상기의 사유로 연장 근무를 신청합니다.");
		inputApprovalDTO.setAprvExe(overtimeDTO.getOverStart().toLocalDate());
		
		ApprovalDTO draft = setDraftDefault(inputApprovalDTO, OVERTIME);
		setApprover(draft, inputApprovalDTO.getApprover());
		
		if (inputApprovalDTO.getReceiver() != null && inputApprovalDTO.getReceiver().size() != 0) setReceiver(draft, inputApprovalDTO.getReceiver());
		if (inputApprovalDTO.getAgreer() != null && inputApprovalDTO.getAgreer().size() != 0) setAgreer(draft, inputApprovalDTO.getAgreer());
		
		draft.setOvertimeDTO(overtimeDTO);
		overtimeDTO.setApprovalDTO(draft);
		ApprovalDTO result = approvalRepository.saveAndFlush(draft);
		
		if (result != null) return true;
		else return false;
	}
	
	public boolean sendEarlyDraft(InputApprovalDTO inputApprovalDTO, EarlyDTO earlyDTO) {
		inputApprovalDTO.setAprvContent("상기의 사유로 조퇴를 신청합니다.");
		inputApprovalDTO.setAprvExe(earlyDTO.getEarlyDtm().toLocalDate());
		
		ApprovalDTO draft = setDraftDefault(inputApprovalDTO, EARLY);
		setApprover(draft, inputApprovalDTO.getApprover());
		
		if (inputApprovalDTO.getReceiver() != null && inputApprovalDTO.getReceiver().size() != 0) setReceiver(draft, inputApprovalDTO.getReceiver());
		if (inputApprovalDTO.getAgreer() != null && inputApprovalDTO.getAgreer().size() != 0) setAgreer(draft, inputApprovalDTO.getAgreer());
		
		draft.setEarlyDTO(earlyDTO);
		earlyDTO.setApprovalDTO(draft);
		ApprovalDTO result = approvalRepository.saveAndFlush(draft);
		
		if (result != null) return true;
		else return false;
	}

	public boolean checkApprovalTrue(Integer aprvCode, String apvrComment) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApprovalDTO approval = approvalRepository.findById(aprvCode).orElseThrow();
		
		int nextSeq = 0;
		for (ApproverDTO approver : approval.getApproverDTOs()) {
			if (approver.getStaffDTO().getStaffCode().equals(staffDTO.getStaffCode())) {
				if (approver.getApvrType().equals(AGREER)) {
					approver.setApvrResult(true);
					approver.setApvrState(DONE);
					approver.setApvrDtm(LocalDateTime.now());
					approver.setApvrComment(apvrComment);
					
					ApprovalDTO result = approvalRepository.saveAndFlush(approval);
					
					if (result != null) return true;
					else return false;
					
				} else if (approver.getApvrType().equals(CONFIRMER)) {
					approver.setApvrResult(true);
					approver.setApvrState(DONE);
					approver.setApvrDtm(LocalDateTime.now());
					approver.setApvrComment(apvrComment);
					
					approval.setAprvState(FINISH);
					ApprovalDTO result = approvalRepository.saveAndFlush(approval);
					
					if (result != null) return true;
					else return false;
					
				} else {
					approver.setApvrResult(true);
					approver.setApvrState(DONE);
					approver.setApvrDtm(LocalDateTime.now());
					approver.setApvrComment(apvrComment);
					
					nextSeq = approver.getApvrSeq() + 1;
					for (ApproverDTO nextApprover : approval.getApproverDTOs()) {
						if (nextApprover.getApvrSeq() == nextSeq) {
							nextApprover.setApvrState(READY);
						}
					}
					
					approval.setAprvCrnt(approval.getAprvCrnt() + 1);
					ApprovalDTO result = approvalRepository.saveAndFlush(approval);
					
					if (result != null) return true;
					else return false;
					
				}
			}
		}
		
		return false;
	}
	
	public boolean checkApprovalFalse(Integer aprvCode, String apvrComment) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApprovalDTO approval = approvalRepository.findById(aprvCode).orElseThrow();
		
		for (ApproverDTO approver : approval.getApproverDTOs()) {
			if (approver.getStaffDTO().getStaffCode().equals(staffDTO.getStaffCode())) {
				approver.setApvrResult(false);
				approver.setApvrState(DONE);
				approver.setApvrDtm(LocalDateTime.now());
				approver.setApvrComment(apvrComment);
			} else if (approver.getApvrState().equals(WAIT) || approver.getApvrState().equals(READY)) {
				approver.setApvrState(DISABLED);
			}
		}
		
		approval.setAprvState(DENY);
		ApprovalDTO result = approvalRepository.saveAndFlush(approval);
		
		if (result != null) return true;
		else return false;
	}
	
	public boolean setApprovalSign(StaffDTO staffDTO, MultipartFile attach) throws IOException {
		staffDTO = staffService.getStaff(staffDTO.getStaffCode());
		
		if (attach != null && attach.getSize() > 0) {
			if (staffDTO.getStaffSignDTO() != null) {
				fileService.fileDelete(FileService.SIGN, staffDTO.getStaffSignDTO().getAttachmentDTO().getSavedName());
				attachmentRepository.deleteById(staffDTO.getStaffSignDTO().getAttachmentDTO().getAttachNum());				
			}
			
			String fileName = fileService.saveFile(FileService.SIGN, attach);
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			attachmentDTO.setOriginName(attach.getOriginalFilename());
			attachmentDTO.setSavedName(fileName);
			attachmentDTO.setAttachSize(attach.getSize());
			
			attachmentRepository.save(attachmentDTO);
			
			StaffSignDTO staffSignDTO = new StaffSignDTO();
			staffSignDTO.setAttachmentDTO(attachmentDTO);
			staffSignDTO.setStaffDTO(staffDTO);
			
			staffDTO.setStaffSignDTO(staffSignDTO);
			
			StaffDTO result = staffRepository.saveAndFlush(staffDTO);
			
			if (result != null) return true;
			else return false;
		} else {
			return false;
		}
	}
	
	public boolean changeApprovalLine(InputApprovalDTO inputApprovalDTO) {
		ApprovalDTO draft = approvalRepository.findById(Integer.parseInt(inputApprovalDTO.getAprvCode())).orElseThrow();
		List<ApproverDTO> beforeApproverDTOs = draft.getApproverDTOs();
		
		int doneCount = 1;
		setApprover(draft, inputApprovalDTO.getApprover());
		
		if (inputApprovalDTO.getReceiver() != null && inputApprovalDTO.getReceiver().size() != 0) setReceiver(draft, inputApprovalDTO.getReceiver());
		if (inputApprovalDTO.getAgreer() != null && inputApprovalDTO.getAgreer().size() != 0) setAgreer(draft, inputApprovalDTO.getAgreer());
		
		for (ApproverDTO approver : draft.getApproverDTOs()) {
			for (ApproverDTO beforeApprover : beforeApproverDTOs) {
				if (approver.getStaffDTO().getStaffCode().equals(beforeApprover.getStaffDTO().getStaffCode())) {
					
					if (approver.getApvrType().equals(AGREER) && approver.getApvrType().equals(beforeApprover.getApvrType()) && beforeApprover.getApvrResult() != null) {
						approver.setApvrState(beforeApprover.getApvrState());
						approver.setApvrResult(beforeApprover.getApvrResult());
						approver.setApvrComment(beforeApprover.getApvrComment());
						approver.setApvrDtm(beforeApprover.getApvrDtm());
					}
					
					if (approver.getApvrType().equals(CHECKER) && approver.getApvrType().equals(beforeApprover.getApvrType()) && beforeApprover.getApvrResult() != null) {
						if (!approver.getApvrSeq().equals(beforeApprover.getApvrSeq())) {
							return false;
						} else {
							approver.setApvrState(beforeApprover.getApvrState());
							approver.setApvrResult(beforeApprover.getApvrResult());
							approver.setApvrComment(beforeApprover.getApvrComment());
							approver.setApvrDtm(beforeApprover.getApvrDtm());
							
							doneCount++;
						}
					}
				}
			}
		}
		
		for (ApproverDTO approver : draft.getApproverDTOs()) {
			if ((approver.getApvrType().equals(CHECKER) || approver.getApvrType().equals(CONFIRMER)) && approver.getApvrSeq() == doneCount) {
				approver.setApvrState(READY);
			}
		}
		
		for (ApproverDTO beforeApprover : beforeApproverDTOs) {
			approvalRepository.removeApproverByApvrNum(beforeApprover.getApvrNum());
		}
		
		draft.setAprvCrnt(doneCount + 1);
		draft.setAprvTotal(inputApprovalDTO.getApprover().size() + 1);
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
			approverDTO.setApvrState(READY);
			
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
