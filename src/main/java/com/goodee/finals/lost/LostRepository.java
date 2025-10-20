package com.goodee.finals.lost;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LostRepository extends JpaRepository<LostDTO, Long>{

	@Query(
		    value = "SELECT * FROM lost l " +
		            "INNER JOIN staff s USING(staff_code) " +
		            "WHERE (l.lost_name LIKE %:search% " +
		            "OR s.staff_name LIKE %:search%) " +  // ← 괄호로 OR 조건 묶기
		            "AND l.lost_delete = false " +
		            "AND (:startDate IS NULL OR l.lost_date >= :startDate) " +
		            "AND (:endDate IS NULL OR l.lost_date <= :endDate) ",
		    countQuery = "SELECT COUNT(*) FROM lost l " +
		                 "INNER JOIN staff s USING(staff_code) " +
		                 "WHERE (l.lost_name LIKE %:search% " +
		                 "OR s.staff_name LIKE %:search%) " +
		                 "AND l.lost_delete = false " +
		                 "AND (:startDate IS NULL OR l.lost_date >= :startDate) " +
		                 "AND (:endDate IS NULL OR l.lost_date <= :endDate) ",
		    nativeQuery = true
		)
	Page<LostDTO> findAllBySearch(LocalDate startDate, LocalDate endDate, String search, Pageable pageable);
	
	long countByLostDeleteFalse();
}
