package com.goodee.finals.attend;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<AttendDTO, Long>{

	Optional<AttendDTO> findByStaffDTOStaffCode(Integer staffCode);

	
	
}
