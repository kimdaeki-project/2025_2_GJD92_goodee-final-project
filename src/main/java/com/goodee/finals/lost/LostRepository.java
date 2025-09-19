package com.goodee.finals.lost;

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
			          "WHERE l.lost_name LIKE %:search% " +
			          "AND l.lost_delete = false",
			  countQuery = "SELECT COUNT(*) FROM lost l " +
			               "INNER JOIN staff s USING(staff_code) " +
			               "WHERE l.lost_name LIKE %:search% " +
			               "AND l.lost_delete = false",
			  nativeQuery = true
			)
	Page<LostDTO> findAllBySearch(String search, Pageable pageable);
	
	long countByLostDeleteFalse();
}
