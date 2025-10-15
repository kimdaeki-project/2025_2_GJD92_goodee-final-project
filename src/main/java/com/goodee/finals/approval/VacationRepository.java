package com.goodee.finals.approval;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRepository extends JpaRepository<VacationDTO, Long>{

	@Query(value = "SELECT v.* FROM vacation v " +
            "JOIN approval a ON v.aprv_code = a.aprv_code " +
            "WHERE a.staff_code = :staffCode " +
            "AND TO_CHAR(v.vac_start, 'yyyymm') = :monthStr " +
            "AND TO_CHAR(v.vac_end, 'yyyymm') = :monthStr",
            nativeQuery = true)
	List<VacationDTO> findAllVacationByStaffCodeAndByMonth(
     @Param("staffCode") Integer staffCode, 
     @Param("monthStr") String monthStr);
	
}
