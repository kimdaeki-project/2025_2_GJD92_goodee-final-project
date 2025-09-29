package com.goodee.finals.staff;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<StaffDTO, Integer> {

	@NativeQuery(value = "SELECT staff_code FROM staff ORDER BY staff_code DESC LIMIT 1")
	Integer findLastStaffCode();
	@NativeQuery(value = "SELECT * FROM staff s INNER JOIN dept d USING(dept_code) INNER JOIN job j USING(job_code) WHERE (s.staff_name LIKE %:search% OR d.dept_detail LIKE %:search% OR j.job_detail LIKE %:search% OR s.staff_phone LIKE %:search% OR s.staff_code LIKE %:search%) AND s.staff_enabled = 1")
	Page<StaffDTO> findAllBySearch(String search, Pageable pageable);
	@Query(value = "SELECT * FROM staff s INNER JOIN dept d USING(dept_code) INNER JOIN job j USING(job_code) WHERE s.dept_code = :deptCode AND (s.staff_name LIKE %:search% OR d.dept_detail LIKE %:search% OR j.job_detail LIKE %:search% OR s.staff_phone LIKE %:search% OR s.staff_code LIKE %:search%) AND s.staff_enabled = 1", nativeQuery = true)
	Page<StaffDTO> findAllByDeptCodeAndSearch(Integer deptCode, String search, Pageable pageable);
	@Query("SELECT s FROM StaffDTO s JOIN FETCH s.deptDTO JOIN FETCH s.jobDTO")
	List<StaffDTO> findAllWithDeptAndJob();
	@NativeQuery(value = "SELECT * FROM staff s INNER JOIN dept d USING(dept_code) INNER JOIN job j USING(job_code) WHERE (s.staff_name LIKE %:search% OR d.dept_detail LIKE %:search% OR j.job_detail LIKE %:search% OR s.staff_phone LIKE %:search% OR s.staff_code LIKE %:search%) AND s.staff_enabled = 0")
	Page<StaffDTO> findAllQuitBySearch(String search, Pageable pageable);
	
	List<StaffDTO> findByStaffCodeNot(Integer loggedStaff);
	
	// deptDTO.deptCode 시설부서의 코드 꺼내옴
    List<StaffDTO> findByDeptDTO_DeptCode(Integer deptCode);
}
