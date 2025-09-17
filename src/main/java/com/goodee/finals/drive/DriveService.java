package com.goodee.finals.drive;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class DriveService {

	@Value("${goodee.file.upload.base-directory}")
	private String baseDir;
	
	@Autowired
	private DriveRepository driveRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private FileService fileService;
	
	public List<StaffDTO> staffList() {
		return staffRepository.findAll();
	}
	
	public void createDrive(DriveDTO driveDTO) {
		// 드라이브 이름 중복 조회
		DriveDTO existDriveName = driveRepository.findByDriveName(driveDTO.getDriveName());
		if(existDriveName != null) {
			System.out.println("DriveService : 중복된이름 존재 메서드 종료");
			return;
		}
		
		// 개인용 드라이브
		if(driveDTO.getDriveShareDTOs() == null || driveDTO.getDriveShareDTOs().size() < 1) {
			driveDTO.setIsPersonal(true);
			driveDTO = driveRepository.save(driveDTO); // DB에 드라이브 저장
			makeDriveDir(baseDir + FileService.DRIVE + "/" + driveDTO.getDriveNum());
			return;
		}

		// 공용 드라이브
		driveDTO.setIsPersonal(false);
		driveDTO = driveRepository.save(driveDTO);//
		System.out.println("생성된 날짜 불러옴 : " + driveDTO.getDriveDate());
		
		// 생성된 PK로 Dir생성
		
		
	}
	
	public void makeDriveDir(String path) {
		File file = new File(path);
		System.out.println("makeDriveDir : " + path);
		boolean result = false;
		if(!file.exists()) {
			result = file.mkdirs();
		}
		System.out.println(result);
	}
	
	
}
