package com.goodee.finals.drive;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.home.HomeController;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;
import com.goodee.finals.staff.JobDTO;
import com.goodee.finals.staff.JobRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;
import com.goodee.finals.staff.StaffResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DriveService {

    private final HomeController homeController;
	
	@Value("${goodee.file.upload.base-directory}")
	private String baseDir;
	
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private DriveRepository driveRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private DriveShareRepository driveShareRepository;
	@Autowired
	private FileService fileService;


    DriveService(HomeController homeController) {
        this.homeController = homeController;
    }
	
	
	public List<DriveDTO> myDrive(StaffDTO staffDTO) {
		return driveRepository.findAllByStaffDTO_StaffCode(staffDTO.getStaffCode()); // 내 드라이브
	}
	
	public List<DriveShareDTO> shareDrive(StaffDTO staffDTO) {
		return driveShareRepository.findAllByStaffDTO_StaffCode(staffDTO.getStaffCode()); // 공용 드라이브
	}
	
	public List<StaffResponseDTO> staffList() {
		return staffRepository.findAllWithDeptAndJob().stream().map(StaffResponseDTO:: new).collect(Collectors.toList());
	}
	
	public List<DeptDTO> getDeptList() {
		return deptRepository.findAll();
	}
	
	public List<JobDTO> getJobList() {
		return jobRepository.findAll();
	}
	
	public List<DocumentDTO> getDocListByDriveNum(DriveDTO driveDTO) {
		return documentRepository.findAllByDriveDTO_DriveNum(driveDTO.getDriveNum());
	}
	
	public DriveDTO getDefaultDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = driveRepository.findByStaffDTO_StaffCodeAndIsPersonalTrueAndDriveDefaultNumIsNotNull(staffDTO.getStaffCode());
		if(driveDTO != null) return driveDTO;
		else return null;
	}
	
	public DriveDTO getDrive(Long driveNum) {
		return driveRepository.findById(driveNum).orElseThrow();
	}
	
	public DriveDTO createDrive(DriveDTO driveDTO) {
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());    // 드라이브 이름 중복 조회
		if(existDriveName != null) {
			System.out.println("DriveService createDrive : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		// 드라이브 분기
		// 1. 개인용 드라이브
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().size() < 1) {
			driveDTO.setIsPersonal(true);
			driveDTO.setDriveEnabled(true);
			driveDTO.setDriveDefaultNum(null);
			driveDTO.setDriveName(driveDTO.getDriveName().trim());
			driveDTO = driveRepository.save(driveDTO);
			
			return driveDTO;
		}

		// 2. 공용 드라이브
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(driveDTO);
		}		
		driveDTO.setIsPersonal(false);
		driveDTO.setDriveEnabled(true);
		driveDTO.setDriveDefaultNum(null);
		driveDTO.setDriveName(driveDTO.getDriveName().trim());
		driveDTO = driveRepository.save(driveDTO);
		
		return driveDTO; 
	}
	
	public DriveDTO updateDrive(DriveDTO driveDTO) {
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());    
		if(existDriveName != null && !existDriveName.getDriveNum().equals(driveDTO.getDriveNum())) { // 중복이름과 PK가 같지 않은경우 return;
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		DriveDTO originDrive = driveRepository.findById(driveDTO.getDriveNum()).orElseThrow();
		originDrive.getDriveShareDTOs().clear(); // 기존 드라이브 연간관계 끊기
		
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().isEmpty()) {
			originDrive.setIsPersonal(true);
			originDrive.setDriveName(driveDTO.getDriveName().trim());
			return driveRepository.save(originDrive);
		}
		
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			if(driveShare.getStaffDTO() == null) continue;	// TODO JSP에서 인덱스 꼬임 방어용 나중에 수정 
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(originDrive);
			originDrive.setIsPersonal(false);
			originDrive.setDriveName(driveDTO.getDriveName().trim());
			originDrive.getDriveShareDTOs().add(driveShare);
		}
		
		return driveRepository.save(originDrive);
	}
	
	public DriveDTO deleteDrive(DriveDTO driveDTO) {
		driveDTO = driveRepository.findById(driveDTO.getDriveNum()).orElseThrow();
		if(driveDTO == null) {
			return null;
		}
		driveDTO.setDriveEnabled(false);
		driveDTO = driveRepository.save(driveDTO);
		
		return driveDTO;
	}
	
	// 사원 등록시 기본드라이브 생성
	public DriveDTO createDefaultDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = new DriveDTO();
		driveDTO.setDriveName(staffDTO.getStaffName() + "님");
		driveDTO.setIsPersonal(true);
		driveDTO.setStaffDTO(staffDTO);
		driveDTO.setDriveEnabled(true);
		
		driveDTO = driveRepository.save(driveDTO);
		driveDTO.setDriveDefaultNum(driveDTO.getDriveNum());
		
		driveDTO = driveRepository.save(driveDTO);
		
		return driveDTO;
	}
	
	public DocumentDTO uploadDocument(Long driveNum, JobDTO jobDTO, MultipartFile attach, StaffDTO staffDTO) {
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		if(attach != null && attach.getSize() != 0) {
			try {
				String path = FileService.DRIVE + "/" + driveNum;
				String fileName = fileService.saveFile(path, attach);
				
				attachmentDTO.setSavedName(fileName);
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LocalDate currentDate = LocalDate.now();
		
		DriveDTO driveDTO = new DriveDTO();
		driveDTO.setDriveNum(driveNum);
		
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setJobDTO(jobDTO);
		documentDTO.setStaffDTO(staffDTO);
		documentDTO.setDriveDTO(driveDTO);
		documentDTO.setDocStatus("ACTIVE");
		documentDTO.setDocDate(currentDate);
		documentDTO.setDocExpire(currentDate.plusDays(6));
		documentDTO.setDocContentType(attach.getContentType());
		documentDTO.setAttachmentDTO(attachmentDTO);
		
		return documentRepository.save(documentDTO);
	}
	
	public boolean deleteDocByAttachNum(StaffDTO staffDTO, Long[] attachNums) {
		boolean result = true;
		
		try {
			for(Long attachNum : attachNums) {		// 삭제 실패시 EmptyResultDataAccessException 발생 
				DocumentDTO documentDTO = documentRepository.findByattachmentDTO_AttachNum(attachNum);
				
				Integer regStaff = documentDTO.getStaffDTO().getStaffCode();
				Integer currentStaff = staffDTO.getStaffCode();
				
				if(!regStaff.equals(currentStaff)) return false;
				documentDTO.setDocStatus("DELETED");
				
				documentRepository.save(documentDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public AttachmentDTO getAttachByAttachNum(Long attachNum) {
		return attachmentRepository.findById(attachNum).orElseThrow();
	}
	
	public List<AttachmentDTO> getAttachListByAttachNum(List<Long> attachNums) {
		return attachmentRepository.findAllById(attachNums);
	}
	
}
