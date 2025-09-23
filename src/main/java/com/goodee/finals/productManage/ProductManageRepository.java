package com.goodee.finals.productManage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManageRepository extends JpaRepository<ProductManageDTO, Long>{

	
	
}
