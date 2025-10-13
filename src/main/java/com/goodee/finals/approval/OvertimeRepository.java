package com.goodee.finals.approval;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OvertimeRepository extends JpaRepository<OvertimeDTO, Long>{

	@Query(value = "SELECT o.* FROM overtime o " +
            "JOIN approval a ON o.aprv_code = a.aprv_code " +
            "WHERE a.staff_code = :staffCode " +
            "AND TO_CHAR(o.over_start, 'yyyymm') = :monthStr",
            nativeQuery = true)
	List<OvertimeDTO> findAllOvertimeByStaffCodeAndByMonth(
     @Param("staffCode") Integer staffCode, 
     @Param("monthStr") String monthStr);
	
}
