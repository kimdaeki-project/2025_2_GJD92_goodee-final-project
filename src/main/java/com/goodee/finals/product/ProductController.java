package com.goodee.finals.product;

import com.goodee.finals.GoodeeFinalProjectApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product/**")
public class ProductController {

    private final GoodeeFinalProjectApplication goodeeFinalProjectApplication;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	

    ProductController(GoodeeFinalProjectApplication goodeeFinalProjectApplication) {
        this.goodeeFinalProjectApplication = goodeeFinalProjectApplication;
    }
			
	@GetMapping("")
	public String list() {
		System.out.println(passwordEncoder.encode("0000"));
		return "product/list";
	}
	
	@GetMapping("write")
	public String write() {
		return "product/write";
	}
	
	@PostMapping("write")
	public void write(ProductDTO productDTO) {
		
		System.out.println(productDTO.toString());
		
	}
	
}
