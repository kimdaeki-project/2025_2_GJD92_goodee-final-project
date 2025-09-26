package com.goodee.finals.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class AddressService {

	@Autowired
	private StaffRepository staffRepository;
	
	public Page<StaffDTO> getAddressSearchList(String search, Pageable pageable){
		return staffRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalAddress() {
		return staffRepository.count();
	}
}
