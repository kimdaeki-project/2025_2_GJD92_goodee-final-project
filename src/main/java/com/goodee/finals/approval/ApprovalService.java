package com.goodee.finals.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;

@Service
public class ApprovalService {
	@Autowired
	private DeptRepository deptRepository;

	public List<DeptDTO> getDeptList() {
		return deptRepository.findAll();
	}

}
