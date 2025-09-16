package com.goodee.finals.staff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffDTO, Integer> {

	@NativeQuery(value = "SELECT staff_code FROM staff ORDER BY staff_code DESC LIMIT 1")
	Integer findLastStaffCode();
	@NativeQuery(value = "SELECT * FROM staff s INNER JOIN dept d USING(dept_code) INNER JOIN job j USING(job_code) WHERE s.staff_name LIKE %:search% OR d.dept_detail LIKE %:search% OR j.job_detail LIKE %:search% OR s.staff_phone LIKE %:search% OR s.staff_code LIKE %:search%")
	Page<StaffDTO> findAllBySearch(String search, Pageable pageable);
	
}
