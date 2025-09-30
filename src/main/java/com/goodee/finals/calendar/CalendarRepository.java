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
		            (c.calType IN :calTypes AND c.calType <> 2003)
		         OR (c.calType = 2003 AND c.deptDTO.deptCode = :deptCode AND c.calType IN :calTypes)
		           )
		    """)
	List<CalendarDTO> getCalendarList(@Param("deptCode") Integer deptCode, @Param("calTypes") List<Integer> calTypes);

	@Query("SELECT new com.goodee.finals.calendar.CalTypeDTO(c.calType, c.calTypeName) " +
	       "FROM CalendarDTO c " +
	       "WHERE c.deptDTO.deptCode = :deptCode " +
	       "   OR c.calType IN (2000,2001,2002) " +
	       "GROUP BY c.calType, c.calTypeName")
	List<CalTypeDTO> getCalTypesByDept(@Param("deptCode") Integer deptCode);
	
}
