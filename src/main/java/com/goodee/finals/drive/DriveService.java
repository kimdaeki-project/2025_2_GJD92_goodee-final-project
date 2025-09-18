package com.goodee.finals.drive;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

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
	
	public List<StaffDTO> staffList() {
//		List<StaffDTO> list = staffRepository.findAll();
//		List<StaffDTO> newList = new ArrayList<>();
//		
//		for(StaffDTO staff : list) {
//			StaffDTO staffDTO = new StaffDTO();
//			staffDTO.setDeptDTO(staff.getDeptDTO());
//			staffDTO.setJobDTO(staff.getJobDTO());
//			staffDTO.setStaffCode(staff.getStaffCode());
//			staffDTO.setStaffName(staff.getStaffName());
//			newList.add(staffDTO);
//		}
//		return newList;
		List<StaffDTO> list = staffRepository.findAllWithDeptAndJob();
		return list;
		
	}
	
	public DriveDTO createDrive(DriveDTO driveDTO) {
		boolean result = false;
		
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());    // 드라이브 이름 중복 조회
		if(existDriveName != null) {
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return null;
		}
		
		// 드라이브 분기
		// 1. 개인용 드라이브
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().size() < 1) {
			driveDTO.setIsPersonal(true);
			driveDTO = driveRepository.save(driveDTO); // DB에 저장
			result = makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
			return driveDTO;
		}

		// 2. 공용 드라이브
		driveDTO.setIsPersonal(false);
		for (DriveShareDTO driveShare : driveDTO.getDriveShareDTOs()) {
			StaffDTO staffDTO = staffRepository.findById(driveShare.getStaffDTO().getStaffCode()).orElseThrow();
			driveShare.setStaffDTO(staffDTO);
			driveShare.setDriveDTO(driveDTO);
		}		
		driveDTO = driveRepository.save(driveDTO);
		makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
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
