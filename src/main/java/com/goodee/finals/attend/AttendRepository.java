package com.goodee.finals.attend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<AttendDTO, Long>{

	Optional<AttendDTO> findByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	boolean existsByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	
	@Query("SELECT a FROM AttendDTO a " +
	           "WHERE FUNCTION('YEAR', a.attendDate) = :year " +
	           "AND FUNCTION('MONTH', a.attendDate) = :month " +
	           "AND a.staffDTO.staffCode = :staffCode")
	    List<AttendDTO> findMonthlyAttendances(@Param("year") int year,
	                                           @Param("month") int month,
	                                           @Param("staffCode") Integer staffCode);
}
