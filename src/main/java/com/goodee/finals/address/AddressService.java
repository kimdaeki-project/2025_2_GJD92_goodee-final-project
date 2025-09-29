package com.goodee.finals.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.DeptRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class AddressService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private DeptRepository deptRepository;
	
	public Page<StaffDTO> getAddressSearchList(String search, Pageable pageable){
		return staffRepository.findAllBySearch(search, pageable);
	}
	
	public Page<StaffDTO> getAddressDeptCodeSearchList(Integer deptCode, String search, Pageable pageable){
		return staffRepository.findAllByDeptCodeAndSearch(deptCode, search, pageable);
	}

	public long getTotalAddress() {
		return staffRepository.count();
	}
	
	public List<DeptDTO> findAllDept(){
		return deptRepository.findAll();
	}
}
