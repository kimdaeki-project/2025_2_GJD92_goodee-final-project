package com.goodee.finals.attend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<AttendDTO, Long>{

	AttendDTO findByStaffCode(Integer staffCode);
	
	
	
}
