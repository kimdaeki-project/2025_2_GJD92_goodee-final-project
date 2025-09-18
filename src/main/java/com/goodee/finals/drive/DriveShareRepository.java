package com.goodee.finals.drive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveShareRepository extends JpaRepository<DriveShareDTO, DriveShareDTO.PK> {
	public List<DriveShareDTO> findAllByStaffDTO_StaffCode(Integer staffCode);
}
