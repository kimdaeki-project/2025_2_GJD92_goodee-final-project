package com.goodee.finals.staff;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobDTO, Integer>{
	List<JobDTO> findAllByOrderByJobCodeDesc();
}
