package com.goodee.finals.drive;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.file.FileService;
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
	
	@Value("${goodee.file.upload.base-directory}")
	private String baseDir;
	
	@Autowired
	private DriveRepository driveRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private DriveShareRepository driveShareRepository;
	@Autowired
	private FileService fileService;
	
	
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
			driveDTO.setDriveDefaultNum(null);
			driveDTO.setDriveEnabled(true);
			driveDTO = driveRepository.save(driveDTO);
			makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
			return driveDTO;
		}

		// 2. 공용 드라이브
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(driveDTO);
		}		
		driveDTO.setDriveDefaultNum(null);
		driveDTO.setIsPersonal(false);
		driveDTO.setDriveEnabled(true);
		driveDTO = driveRepository.save(driveDTO);
		makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
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
			originDrive.setDriveEnabled(true);
			return driveRepository.save(originDrive);
		}
		
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			if(driveShare.getStaffDTO() == null) continue;	// TODO JSP에서 인덱스 꼬임 방어용 나중에 수정 
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveDTO.setDriveDefaultNum(null);
			driveDTO.setDriveEnabled(true);
			driveDTO.setIsPersonal(false);
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(originDrive);
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
	
	public boolean makeDriveDir(String path) {
		File file = new File(path);
		System.out.println("makeDriveDir : " + path);
		boolean result = false;
		if(!file.exists()) {
			result = file.mkdirs();
		}
		return result;
	}
	
	public DocumentDTO uploadDocument(Long driveNum, JobDTO jobDTO, MultipartFile[] attaches) {
		
		
		return null;
	}
	
}
