package com.goodee.finals.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffDTO, Integer> {

	@NativeQuery(value = "SELECT staff_code FROM staff ORDER BY staff_code DESC LIMIT 1")
	Integer findLastStaffCode();
	
}
