package com.goodee.finals.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarDTO, Long>{
	@Query("""
	SELECT c
	  FROM CalendarDTO c
	  JOIN FETCH c.staffDTO s
	  LEFT JOIN FETCH c.deptDTO d
	 WHERE c.calEnabled = true 
	   AND (
			c.calType IN (2000, 2001, 2002)
	    OR (c.calType = 2003 AND d.deptCode = :deptCode)
			)""")
	List<CalendarDTO> getCalendarList(@Param("deptCode") Integer deptCode);
	
}
