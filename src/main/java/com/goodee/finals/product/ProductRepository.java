package com.goodee.finals.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDTO, Integer>{
	
	@NativeQuery(value = "SELECT * FROM product p INNER JOIN staff s USING(staff_code) WHERE p.product_name LIKE %:search%")
	Page<ProductDTO> findAllBySearch(String search, Pageable pageable);
	
}
