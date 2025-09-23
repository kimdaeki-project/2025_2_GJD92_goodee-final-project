package com.goodee.finals.productManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.product.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/productManage/**")
@Slf4j
public class ProductManageController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductManageService productManageService;
	
	@GetMapping("")
	public String getProductManageList(@PageableDefault(size = 10, sort = "pm_num", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		return "productManage/manageList";
	}
	
	@GetMapping("write")
	public String write(@PageableDefault(size = 10, sort = "product_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductDTO> productPage  = productService.getProductSearchList(search,pageable);
		List<ProductDTO> productList = productPage.getContent();
		
		model.addAttribute("productList", productList);
		model.addAttribute("search", search);
		
		return "productManage/manageWrite";
	}
	
	@PostMapping("write")
	public String manageWrite(ProductDTO productDTO, ProductManageDTO productManageDTO, Model model) {
		ProductManageDTO result = productManageService.manageWrite(productManageDTO, productDTO);
		
		String resultMsg = "입출고 내역 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "입출고 내역을 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/productManage";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
}
