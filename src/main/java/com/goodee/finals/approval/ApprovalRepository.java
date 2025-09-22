package com.goodee.finals.approval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalDTO, Integer> {
	
	@NativeQuery(value = "SELECT aprv_code FROM approval ORDER BY aprv_code DESC LIMIT 1")
	Integer findLastAprvCode();
	
}
