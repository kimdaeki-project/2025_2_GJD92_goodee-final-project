package com.goodee.finals.drive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodee.finals.staff.StaffDTO;

@Repository
public interface DriveRepository extends JpaRepository<DriveDTO, Long> {

	public DriveDTO findByDriveName(String driveName);
	public List<DriveDTO> findAllByStaffDTO_StaffCode(Integer staffCode);
	
}
