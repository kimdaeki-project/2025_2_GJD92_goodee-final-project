package com.goodee.finals.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address/**")
public class AddressController {

	@GetMapping("")
	public String list() {
		return "address/list";
	}
	
	@GetMapping("create")
	public String create() {
		return "address/create";
	}
}
