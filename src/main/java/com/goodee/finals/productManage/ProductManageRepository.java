package com.goodee.finals.productManage;

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
			          "WHERE s.staff_name LIKE %:search% " + 
			          "OR pm.pm_note LIKE %:search% " +
			          "OR p.product_name LIKE %:search% " ,
			  countQuery = "SELECT COUNT(*) FROM product_manage pm " +
						  "INNER JOIN staff s USING(staff_code) " +
						  "INNER JOIN product p USING(product_code) " +
						  "INNER JOIN product_type pt USING(product_type_code) " +
				          "WHERE s.staff_name LIKE %:search% " +
				          "OR pm.pm_note LIKE %:search% " +
				          "OR p.product_name LIKE %:search% " ,
			  nativeQuery = true
			)
	Page<ProductManageDTO> findAllBySearch(@Param("search") String search, Pageable pageable);
	
}
