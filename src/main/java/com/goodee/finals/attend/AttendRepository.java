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
	
	// 휴무일 리스트 가져오기	
	@Query("SELECT h FROM HolidayDTO h WHERE to_char(h.date, 'yyyymm') = :monthStr")
	List<HolidayDTO> findByMonth(String monthStr);
	
	// 휴무일 제외한 출퇴근 내역 가져오기
	@Query("SELECT a FROM AttendDTO a " +
	           "WHERE FUNCTION('YEAR', a.attendDate) = :year " +
	           "AND FUNCTION('MONTH', a.attendDate) = :month " +
	           "AND FUNCTION('DAY', a.attendDate) NOT IN :holiday " +
	           "AND a.staffDTO.staffCode = :staffCode " + 
			   "AND a.attendDate <= :today")
	Page<AttendDTO> findMonthlyAttendancesUntilToday(@Param("year") int year,
		                                             @Param("month") int month,
		                                             @Param("staffCode") Integer staffCode,
		                                             @Param("today") LocalDate today,
		                                             Pageable pageable, List<Integer> holiday);
	
	// 휴무일 제외한 출퇴근 내역 중 결근 갯수
	@Query("SELECT COUNT(a) FROM AttendDTO a " +
		       "WHERE FUNCTION('YEAR', a.attendDate) = :year " +
		       "AND FUNCTION('MONTH', a.attendDate) = :month " +
		       "AND FUNCTION('DAY', a.attendDate) NOT IN :holiday " +
		       "AND a.staffDTO.staffCode = :staffCode " +
		       "AND a.attendDate <= :today " +
		       "AND a.attendIn IS NULL AND a.attendOut IS NULL")
	Long countAbsentDays(@Param("year") int year,
	                     @Param("month") int month,
	                     @Param("staffCode") Integer staffCode,
	                     @Param("today") LocalDate today,
	                     @Param("holiday") List<Integer> holiday);
	
}
