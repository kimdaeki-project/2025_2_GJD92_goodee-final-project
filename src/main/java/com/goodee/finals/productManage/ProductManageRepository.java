package com.goodee.finals.productManage;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManageRepository extends JpaRepository<ProductManageDTO, Long>{

	@Query(
			  value = "SELECT pm.* FROM product_manage pm " +
			          "INNER JOIN staff s USING(staff_code) " +
			          "INNER JOIN product p USING(product_code) " +
			          "INNER JOIN product_type pt USING(product_type_code) " +
			          "WHERE (s.staff_name LIKE %:search% " + 
			          "OR pm.pm_note LIKE %:search% " +
			          "OR p.product_name LIKE %:search%) " +
			          "AND (:pmType IS NULL OR pm.pm_type = :pmType) "+
			          "AND (:startDate IS NULL OR pm.pm_date >= :startDate) "+
			          "AND (:endDate IS NULL OR pm.pm_date <= :endDate) ",
	          countQuery = "SELECT COUNT(*) FROM product_manage pm " +
	                  "INNER JOIN staff s USING(staff_code) " +
	                  "INNER JOIN product p USING(product_code) " +
	                  "INNER JOIN product_type pt USING(product_type_code) " +
	                  "WHERE (s.staff_name LIKE %:search% " +
	                  "OR pm.pm_note LIKE %:search% " +
	                  "OR p.product_name LIKE %:search%) " +
	                  "AND (:pmType IS NULL OR pm.pm_type = :pmType) " +
	                  "AND (:startDate IS NULL OR pm.pm_date >= :startDate) " +
	                  "AND (:endDate IS NULL OR pm.pm_date <= :endDate)" ,
			  nativeQuery = true
			)
	Page<ProductManageDTO> findAllBySearch(LocalDate startDate, LocalDate endDate, Integer pmType, String search, Pageable pageable);
	
	@Query("SELECT pm FROM ProductManageDTO pm " +
		       "JOIN pm.staffDTO s " +
		       "JOIN pm.productDTO p " +
		       "JOIN p.productTypeDTO pt " +
		       "WHERE (" +
		       "LOWER(s.staffName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(pm.pmNote) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
		       "LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))" +
		       ") " +
		       "AND (:pmType IS NULL OR pm.pmType = :pmType) " +
		       "AND (:startDate IS NULL OR pm.pmDate >= :startDate) " +
		       "AND (:endDate IS NULL OR pm.pmDate <= :endDate) " +
		       "ORDER BY pm.pmNum DESC")
		List<ProductManageDTO> findBySearchKeyword(@Param("startDate") LocalDate startDate,
		                                           @Param("endDate") LocalDate endDate,
		                                           @Param("pmType") Integer pmType,
		                                           @Param("search") String search);
}
