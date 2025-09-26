package com.goodee.finals.attend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendRepository extends JpaRepository<AttendDTO, Long>{

	Optional<AttendDTO> findByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	boolean existsByStaffDTOStaffCodeAndAttendDate(Integer staffCode, LocalDate attendDate);
	
	@Query("SELECT COUNT(a) FROM AttendDTO a " +
		       "WHERE a.attendIn > :time " +
		       "AND a.staffDTO.staffCode = :staffCode " +
		       "AND a.attendDate BETWEEN :startDate AND :endDate")
		long countLateByStaffCodeInMonth(@Param("staffCode") int staffCode,
		                                 @Param("time") LocalTime time,
		                                 @Param("startDate") LocalDate startDate,
		                                 @Param("endDate") LocalDate endDate);

		@Query("SELECT COUNT(a) FROM AttendDTO a " +
		       "WHERE a.attendOut < :time " +
		       "AND a.staffDTO.staffCode = :staffCode " +
		       "AND a.attendDate BETWEEN :startDate AND :endDate")
		long countEarlyLeaveByStaffCodeInMonth(@Param("staffCode") int staffCode,
		                                       @Param("time") LocalTime time,
		                                       @Param("startDate") LocalDate startDate,
		                                       @Param("endDate") LocalDate endDate);
	@Query("SELECT a FROM AttendDTO a " +
	           "WHERE FUNCTION('YEAR', a.attendDate) = :year " +
	           "AND FUNCTION('MONTH', a.attendDate) = :month " +
	           "AND FUNCTION('DAY', a.attendDate) NOT IN :holiday " +
	           "AND a.staffDTO.staffCode = :staffCode")
	Page<AttendDTO> findMonthlyAttendances(@Param("year") int year,
                                           @Param("month") int month,
                                           @Param("staffCode") Integer staffCode,
                                           Pageable pageable, List<Integer> holiday);
	
	@Query("SELECT h FROM HolidayDTO h WHERE to_char(h.date, 'yyyymm') = :monthStr")
	List<HolidayDTO> findByMonth(String monthStr);
}
