package com.goodee.finals.drive;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.finals.common.file.FileService;
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
	
	public DriveDTO getDefaultDrive(StaffDTO staffDTO) {
		DriveDTO driveDTO = driveRepository.findByStaffDTO_StaffCodeAndIsPersonalTrueAndDefaultDriveNumIsNotNull(staffDTO.getStaffCode());
		if(driveDTO != null) return driveDTO;
		else return null;
	}
	
	public DriveDTO getDrive(Long driveNum) {
		return driveRepository.findById(driveNum).orElseThrow();
	}
	
	public DriveDTO createDrive(DriveDTO driveDTO) {
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());    // 드라이브 이름 중복 조회
		if(existDriveName != null) {
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		// 드라이브 분기
		// 1. 개인용 드라이브
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().size() < 1) {
			driveDTO.setIsPersonal(true);
			driveDTO.setDefaultDriveNum(null);
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
		driveDTO.setDefaultDriveNum(null);
		driveDTO.setIsPersonal(false);
		driveDTO.setDriveEnabled(true);
		driveDTO = driveRepository.save(driveDTO);
		makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
		return driveDTO; 
	}
	
	public DriveDTO updateDrive(DriveDTO driveDTO) {
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());    // 드라이브 이름 중복 조회
		if(existDriveName != null && !existDriveName.getDriveNum().equals(driveDTO.getDriveNum())) { // 중복이름과 PK가 같지 않은경우 return;
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().size() < 1) {
			driveDTO.setIsPersonal(true);
			driveDTO.setDriveEnabled(true);
			return driveRepository.save(driveDTO);
		}
		
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveDTO.setDefaultDriveNum(null);
			driveDTO.setDriveEnabled(true);
			driveDTO.setIsPersonal(false);
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(driveDTO);
		}
		return driveRepository.save(driveDTO);
	}
	
	public DriveDTO deleteDrive(DriveDTO driveDTO) {
		driveDTO = driveRepository.findById(driveDTO.getDriveNum()).orElseThrow();
		if(driveDTO == null) {
			return null;
		}
		driveDTO.setDriveEnabled(false);
		driveDTO = driveRepository.save(driveDTO);
		
		System.out.println(driveDTO.getDriveNum());
		System.out.println(driveDTO.getDriveName());
		System.out.println(driveDTO.getDriveEnabled());
		System.out.println(driveDTO.getDriveDate());
		
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
		driveDTO.setDefaultDriveNum(driveDTO.getDriveNum());
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
	
	
}
