package com.goodee.finals.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goodee.finals.staff.DeptDTO;
import com.goodee.finals.staff.StaffDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/address/**")
@Slf4j
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	// 전체 직원
	@GetMapping("")
	public String list(@PageableDefault(size = 10, sort = "staff_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		List<DeptDTO> deptList = addressService.findAllDept();
		model.addAttribute("deptList", deptList);
		
		Page<StaffDTO> addressList = addressService.getAddressSearchList(search, pageable);
		model.addAttribute("addressList", addressList);
		model.addAttribute("search", search);
		
		long totalAddress = addressService.getTotalAddress();
		model.addAttribute("totalAddress", totalAddress);
		return "address/list";
	}
	
	@GetMapping("{dept_code}")
	public String deptList(@PathVariable(name = "dept_code") Integer deptCode , @PageableDefault(size = 10, sort = "staff_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		List<DeptDTO> deptList = addressService.findAllDept();
		model.addAttribute("deptList", deptList);
		
		Page<StaffDTO> addressList = addressService.getAddressDeptCodeSearchList(deptCode, search, pageable);
		model.addAttribute("addressList", addressList);
		model.addAttribute("search", search);
		
		long totalAddress = addressService.getTotalAddress();
		model.addAttribute("totalAddress", totalAddress);
		
		switch (deptCode) {
		case 1000: model.addAttribute("deptSelected", "임원");
				break;
		case 1001: model.addAttribute("deptSelected", "인사");
				break;
		case 1002: model.addAttribute("deptSelected", "운영");
				break;
		case 1003: model.addAttribute("deptSelected", "시설");
				break;
		}
		
		return "address/list";
	}
	
}
