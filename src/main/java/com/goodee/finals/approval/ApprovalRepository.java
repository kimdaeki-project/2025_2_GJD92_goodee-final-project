package com.goodee.finals.approval;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalDTO, Integer> {
	
	@NativeQuery(value = "SELECT aprv_code FROM approval ORDER BY aprv_code DESC LIMIT 1")
	Integer findLastAprvCode();
	@Query("SELECT new com.goodee.finals.approval.ApprovalListDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, ar.apvrState, al.aprvDate) "
			+ "	FROM ApprovalDTO al JOIN al.approverDTOs ar JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE ar.staffDTO.staffCode = :staffCode AND al.aprvState = 701 AND ar.apvrState IN (720, 721)"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY ar.apvrState DESC, al.aprvCode ASC")
	Page<ApprovalListDTO> findAllApproval(Integer staffCode, String search, Pageable pageable);
}
