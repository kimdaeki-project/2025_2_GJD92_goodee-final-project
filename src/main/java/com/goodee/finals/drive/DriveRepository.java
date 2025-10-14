package com.goodee.finals.drive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveRepository extends JpaRepository<DriveDTO, Long> {

	public List<DriveDTO> findAllByDriveEnabledFalse();
	public List<DriveDTO> findAllByStaffDTO_StaffCode(Integer staffCode);
	public DriveDTO findByDriveName(String driveName);
	public DriveDTO findByStaffDTO_StaffCodeAndIsPersonalTrueAndDriveDefaultNumIsNotNull(Integer staffCode);
}
