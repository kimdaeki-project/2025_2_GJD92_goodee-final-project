package com.goodee.finals.drive;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.ride.RideRepository;
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

    private final RideRepository rideRepository;
	private static final Long ROLE_HQ_DRIVE = 2L;
	private static final Long ROLE_HR_DRIVE = 3L;
	private static final Long ROLE_OP_DRIVE = 4L;
	private static final Long ROLE_FA_DRIVE = 5L;
	
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

    DriveService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

	public List<DeptDTO> getDeptList() {
		return deptRepository.findAll();
	}
	
	public List<JobDTO> getJobList() {
		return jobRepository.findAllByOrderByJobCodeDesc();
	}
	
	public List<DriveDTO> getAllMyDrive(StaffDTO staffDTO) {
		return driveRepository.findAllByStaffDTO_StaffCode(staffDTO.getStaffCode()); // 내 드라이브
	}
	
	public List<DriveDTO> getDisabledDriveList() {
		return driveRepository.findAllByDriveEnabledFalse();
	}
	
	public List<DriveShareDTO> getShareDriveByStaffCode(StaffDTO staffDTO) {
		return driveShareRepository.findAllByStaffDTO_StaffCode(staffDTO.getStaffCode()); // 공유 드라이브
	}
	
	public List<StaffResponseDTO> staffList() { // 순환참조 방지를 위해 응답 DTO 만듬
		return staffRepository.findAllEnabledStaffWithDeptAndJob().stream().map(StaffResponseDTO:: new).collect(Collectors.toList());
	}
	
	public Page<DocumentDTO> getDocListByDriveNum(DriveDTO driveDTO, DrivePager drivePager, StaffDTO staffDTO, Pageable pageable) {
		Long driveNum = driveDTO.getDriveNum();
		String keyword = drivePager.getKeyword();
		String fileTypeSelect = drivePager.getFileType();
		Integer staffJobCode = staffDTO.getJobDTO().getJobCode();
		List<String> fileTypeList = new ArrayList<>();
		
		if(keyword == null) keyword = "";
		if(fileTypeSelect == null) fileTypeSelect = "all"; 
		
		switch (fileTypeSelect) {
			case "all": fileTypeList = null; break;
			case "audio":  fileTypeList = Arrays.asList("MP3", "WAV", "OGG", "AAC", "FLAC"); break;
			case "video":  fileTypeList = Arrays.asList("MP4", "AVI", "MOV", "WMV", "MKV", "WEBM"); break;
			case "image":  fileTypeList = Arrays.asList("JPG", "JPEG", "PNG", "GIF", "BMP", "SVG", "WEBP"); break;
			case "doc":  fileTypeList = Arrays.asList( "PDF", "DOC", "DOCX", "XLS", "XLSX", "PPT", "PPTX", "TXT", "HWP", "JSON", "CSV"); break;
		}
		
		Page<DocumentDTO> result = documentRepository.findByDriveAndKeyword(driveNum, keyword, staffJobCode ,pageable, fileTypeList);
		drivePager.calc(result);
		
		return result;
	}
	
	public DriveDTO getDefaultDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = driveRepository.findByStaffDTO_StaffCodeAndIsPersonalTrueAndDriveDefaultNumIsNotNull(staffDTO.getStaffCode());
		if(driveDTO != null) return driveDTO;
		else return null;
	}
	
	public DriveDTO getDrive(Long driveNum) {
		return driveRepository.findById(driveNum).orElseThrow();
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
		
		if(driveRepository.count() > 1)	addInDeptDrive(staffDTO); // 최초 서버 실행시 부서드라이브 없음
		
		return driveDTO;
	}

	// 사원 등록시 부서 공유드라이브에 추가
	public boolean addInDeptDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = findDeptDrive(staffDTO);
		
		List<DriveShareDTO> dsList = driveDTO.getDriveShareDTOs();
		if(dsList == null) { // || isEmpty() 조건 추가시 영속성 추적 불가 에러 발생
			dsList = new ArrayList<DriveShareDTO>();
		}
		int checkSize = dsList.size();
		
		DriveShareDTO driveShareDTO = new DriveShareDTO();
		driveShareDTO.setDriveDTO(driveDTO);
		driveShareDTO.setStaffDTO(staffDTO);
		dsList.add(driveShareDTO);
		driveDTO.setIsPersonal(false);
		driveDTO.setDriveShareDTOs(dsList);
		
		driveDTO = driveRepository.save(driveDTO);
		if(driveDTO.getDriveShareDTOs().size() > checkSize) return true;
		else return false;
	}
	
	public boolean deleteFromDeptDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = findDeptDrive(staffDTO);
		Integer disabledStaffCode = staffDTO.getStaffCode();
		
		List<DriveShareDTO> dsList = driveDTO.getDriveShareDTOs();
		for(int i = 0; i < dsList.size(); i++) {
			Integer dsStaffCode = dsList.get(i).getStaffDTO().getStaffCode();
			if(disabledStaffCode.equals(dsStaffCode)) {
				dsList.remove(i);
				driveDTO.setDriveShareDTOs(dsList);
				driveRepository.saveAndFlush(driveDTO);
				return true;
			}
		}
		return false;
	}
	
	public DriveDTO createDrive(DriveDTO driveDTO) {
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName().trim()); // 드라이브 이름 중복 조회
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

		// 2. 공유 드라이브
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
		DriveDTO existDrive = driveRepository.findByDriveName(driveDTO.getDriveName());    
		if(existDrive != null && !existDrive.getDriveNum().equals(driveDTO.getDriveNum())) { // 중복이름과 PK가 같지 않은경우 return;
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		DriveDTO originDrive = driveRepository.findById(driveDTO.getDriveNum()).orElseThrow();
		originDrive.getDriveShareDTOs().clear(); // 기존 드라이브 연간관계 끊기
		originDrive.setDriveName(driveDTO.getDriveName().trim());
		
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().isEmpty()) {
			originDrive.setIsPersonal(true);
			return driveRepository.save(originDrive);
		}
		
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			if(driveShare.getStaffDTO() == null) continue;	// TODO JSP에서 인덱스 꼬임 방어용 나중에 수정 
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(originDrive);
			originDrive.setIsPersonal(false);
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
	
	public DocumentDTO uploadDocument(Long driveNum, JobDTO jobDTO, MultipartFile attach, StaffDTO staffDTO) {
		if(attach == null) return null;
		if(attach.getOriginalFilename().length() > 255) return null;
		
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		if(attach != null && attach.getSize() != 0) {
			try {
				String path = FileService.DRIVE + "/" + driveNum;
				
				String fileName = fileService.saveFile(path, attach);
				
				attachmentDTO.setSavedName(fileName);
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setAttachSizeDetail(fileService.readableFileSize(attach.getSize()));
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		LocalDate currentDate = LocalDate.now();
		String contentType = attach.getOriginalFilename();
		String newContentType = "";
		
		if(contentType.lastIndexOf(".") > 0) {
			newContentType = contentType.substring(contentType.lastIndexOf(".") + 1).toUpperCase();
		} else {
			newContentType = contentType.substring(contentType.lastIndexOf("/") + 1).toUpperCase();
		}
		
		if(jobDTO == null) {
			jobDTO = new JobDTO();
			jobDTO.setJobCode(1202);
		}
		
		DriveDTO driveDTO = new DriveDTO();
		driveDTO.setDriveNum(driveNum);
		
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setJobDTO(jobDTO);
		documentDTO.setStaffDTO(staffDTO);
		documentDTO.setDriveDTO(driveDTO);
		documentDTO.setDocStatus("ACTIVE");
		documentDTO.setDocDate(currentDate);
		documentDTO.setDocExpire(currentDate.plusDays(90));
		documentDTO.setDocContentType(newContentType);
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
	
	public DriveDTO findDeptDrive(StaffDTO staffDTO) {
		Integer deptCode = staffDTO.getDeptDTO().getDeptCode();
		DriveDTO driveDTO = null;	
		
		switch (deptCode) {
			case 1000: driveDTO = driveRepository.findById(ROLE_HQ_DRIVE).orElseThrow(); break;
			case 1001: driveDTO = driveRepository.findById(ROLE_HR_DRIVE).orElseThrow(); break;
			case 1002: driveDTO = driveRepository.findById(ROLE_OP_DRIVE).orElseThrow(); break;
			case 1003: driveDTO = driveRepository.findById(ROLE_FA_DRIVE).orElseThrow(); break;
		}
		
		return driveDTO;
	}
	
}
