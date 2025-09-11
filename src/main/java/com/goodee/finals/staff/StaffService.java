package com.goodee.finals.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StaffService implements UserDetailsService {
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Integer staffCode;
		
		try {
			staffCode = Integer.parseInt(username);
		} catch (NumberFormatException e) {
			throw new UsernameNotFoundException("올바른 사원번호를 입력해주세요.");
		}
		
		StaffDTO staffDTO = staffRepository.findById(staffCode).orElseThrow();
		
		return staffDTO;
	}

}
