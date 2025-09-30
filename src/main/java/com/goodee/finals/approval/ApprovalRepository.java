package com.goodee.finals.approval;

import java.util.List;

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
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalListDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, ar.apvrState, al.aprvDate) "
			+ "	FROM ApprovalDTO al JOIN al.approverDTOs ar JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE ar.staffDTO.staffCode = :staffCode AND al.aprvState = 701 AND ar.apvrState = 721"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY ar.apvrState DESC, al.aprvCode ASC")
	Page<ApprovalListDTO> findAllApprovalRequest(Integer staffCode, String search, Pageable pageable);
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalListDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, ar.apvrState, al.aprvDate) "
			+ "	FROM ApprovalDTO al JOIN al.approverDTOs ar JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE ar.staffDTO.staffCode = :staffCode AND al.aprvState = 701 AND ar.apvrState = 720"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY ar.apvrState DESC, al.aprvCode ASC")
	Page<ApprovalListDTO> findAllApprovalReady(Integer staffCode, String search, Pageable pageable);
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalResultDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, al.aprvState, al.aprvDate, al.aprvExe) "
			+ "	FROM ApprovalDTO al JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE st.staffCode = :staffCode AND al.aprvState IN (701, 702, 703)"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY al.aprvCode DESC")
	Page<ApprovalResultDTO> findAllApprovalMine(Integer staffCode, String search, Pageable pageable);
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalResultDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, al.aprvState, al.aprvDate, al.aprvExe) "
			+ "	FROM ApprovalDTO al JOIN al.approverDTOs ar JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE ar.staffDTO.staffCode = :staffCode AND al.aprvState IN (701, 702, 703) AND ar.apvrState IN (722, 723)"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY ar.apvrState DESC, al.aprvCode ASC")
	Page<ApprovalResultDTO> findAllApprovalFinish(Integer staffCode, String search, Pageable pageable);
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalResultDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, al.aprvState, al.aprvDate, al.aprvExe) "
			+ "	FROM ApprovalDTO al JOIN al.approverDTOs ar JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE ar.staffDTO.staffCode = :staffCode AND al.aprvState = 702 AND ar.apvrType = 710"
			+ " AND (al.aprvTitle LIKE %:search%)"
			+ " ORDER BY al.aprvCode DESC")
	Page<ApprovalResultDTO> findAllReceive(Integer staffCode, String search, Pageable pageable);
	
	@NativeQuery(value = "DELETE FROM approver WHERE apvr_num = :apvrNum")
	void removeApproverByApvrNum(Long apvrNum);
	
	@NativeQuery(value = "DELETE FROM approval_attach WHERE attach_num = :attachNum")
	void deleteAttach(Long attachNum);
	
	@Query("SELECT new com.goodee.finals.approval.ApprovalResultDTO(al.aprvCode, al.aprvTitle, al.aprvTotal, al.aprvCrnt, st.staffName, dp.deptDetail, al.aprvState, al.aprvDate, al.aprvExe) "
			+ "	FROM ApprovalDTO al JOIN al.staffDTO st JOIN st.deptDTO dp"
			+ " WHERE st.staffCode = :staffCode AND al.aprvState = 700"
			+ " ORDER BY al.aprvCode DESC")
	List<ApprovalResultDTO> findAllApprovalSaved(Integer staffCode);
	
	@NativeQuery(value = "DELETE FROM approver WHERE aprv_code = :aprvCode")
	void deleteAllApprover(Integer aprvCode);

	@NativeQuery(value = "DELETE FROM approval WHERE aprv_code = :aprvCode")
	void deleteApproval(Integer aprvCode);
	
	@NativeQuery(value = "DELETE FROM vacation WHERE aprv_code = :aprvCode")
	void deleteVacation(Integer aprvCode);
	
	@NativeQuery(value = "DELETE FROM overtime WHERE aprv_code = :aprvCode")
	void deleteOvertime(Integer aprvCode);
	
	@NativeQuery(value = "DELETE FROM early WHERE aprv_code = :aprvCode")
	void deleteEarly(Integer aprvCode);
}
