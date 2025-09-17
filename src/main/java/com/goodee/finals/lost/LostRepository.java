package com.goodee.finals.lost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface LostRepository extends JpaRepository<LostDTO, Long>{

	@NativeQuery(value = "SELECT * FROM lost l INNER JOIN staff s USING(staff_code) WHERE l.lost_name LIKE %:search%")
	Page<LostDTO> findAllBySearch(String search, Pageable pageable);
	
}
