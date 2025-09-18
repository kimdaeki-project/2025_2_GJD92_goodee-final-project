package com.goodee.finals.attend;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<AttendDTO, Long>{

	Optional<AttendDTO> findByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	boolean existsByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	
	
}
