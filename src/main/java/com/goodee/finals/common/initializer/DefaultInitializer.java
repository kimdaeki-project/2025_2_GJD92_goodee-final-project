package com.goodee.finals.common.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.goodee.finals.drive.DriveDTO;
import com.goodee.finals.drive.DriveRepository;
import com.goodee.finals.drive.DriveService;
import com.goodee.finals.product.ProductTypeDTO;
import com.goodee.finals.product.ProductTypeRepository;
import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;
import com.goodee.finals.staff.JobDTO;
import com.goodee.finals.staff.JobRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;
import com.goodee.finals.staff.StaffService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Transactional
@Slf4j
public class DefaultInitializer implements ApplicationRunner {
	@Autowired
	private DeptRepository deptRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DriveRepository driveRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private StaffService staffService;
	@Autowired
	private DriveService driveService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		setDeptDefault();
		setJobDefault();
		setAdmin();
		setDeptDrive();
		setProductType();
	}

	private void setDeptDefault() {
		if (deptRepository.count() == 0) {
			Integer[] deptCodeArr = {1000, 1001, 1002, 1003};
			String[] deptNameArr = {"ROLE_HQ", "ROLE_HR", "ROLE_OP", "ROLE_FA"};
			String[] deptDetailArr = {"임원", "인사", "운영", "시설"};
			
			for (int i = 0; i < deptCodeArr.length; i++) {
				DeptDTO deptDTO = new DeptDTO();
				deptDTO.setDeptCode(deptCodeArr[i]);
				deptDTO.setDeptName(deptNameArr[i]);
				deptDTO.setDeptDetail(deptDetailArr[i]);
				
				deptRepository.save(deptDTO);
			}
		}
	}

	private void setJobDefault() {
		if (jobRepository.count() == 0) {
			Integer[] jobCodeArr = {1100, 1101, 1102, 1200, 1201, 1202};
			String[] jobNameArr = {"ROLE_CEO", "ROLE_EVP", "ROLE_SVP", "ROLE_GM", "ROLE_SM", "ROLE_EM"};
			String[] jobDetailArr = {"사장", "전무", "상무", "부장", "과장", "사원"};
			
			for (int i = 0; i < jobCodeArr.length; i++) {
				JobDTO jobDTO = new JobDTO();
				jobDTO.setJobCode(jobCodeArr[i]);
				jobDTO.setJobName(jobNameArr[i]);
				jobDTO.setJobDetail(jobDetailArr[i]);
				
				jobRepository.save(jobDTO);
			}
		}
	}
	
	private void setAdmin() {
		if (staffRepository.count() == 0) {
			StaffDTO staffDTO = new StaffDTO();
			staffDTO.setInputDeptCode(1000);
			staffDTO.setInputJobCode(1100);
			staffDTO.setStaffName("정도현");
			
			staffService.registStaff(staffDTO, null);
		}
	}
	
	private void setDeptDrive() {
		if(driveRepository.count() == 1) {
			String[] driveNameArr = {"임원 드라이브", "인사팀 드라이브", "운영팀 드라이브", "시설팀 드라이브"};		
			StaffDTO staffDTO = staffService.getStaff(20250001);
			
			for(int i = 0; i < driveNameArr.length; i++) {
				DriveDTO driveDTO = new DriveDTO();
				driveDTO.setDriveName(driveNameArr[i]);
				driveDTO.setStaffDTO(staffDTO);
				driveService.createDrive(driveDTO);
			}
		}
	}
	
	private void setProductType() {
		if (productTypeRepository.count() == 0) {
			Integer[] typeNumArr = {801, 802, 803, 804, 805, 806, 807, 808, 809};
			String[] typeNameArr = {"식음료 소모품", "증정품", "운영 자재", "시설 유지 보수 자재", "청소 / 위생용품", "포장 / 물류 소모품", "보안 / 안전용품", "행사용 / 이벤트 소품", "IT / 사무 장비"};
			
			for (int i = 0; i < typeNameArr.length; i++) {
				ProductTypeDTO productTypeDTO = new ProductTypeDTO();
				productTypeDTO.setProductTypeCode(typeNumArr[i]);
				productTypeDTO.setProductTypeName(typeNameArr[i]);
				
				productTypeRepository.save(productTypeDTO);
			}
		}
	}
}
