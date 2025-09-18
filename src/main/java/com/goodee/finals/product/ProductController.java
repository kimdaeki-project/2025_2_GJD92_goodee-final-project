package com.goodee.finals.product;

import com.goodee.finals.GoodeeFinalProjectApplication;
import com.goodee.finals.lost.LostDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product/**")
public class ProductController {
			
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public String getProductlist(@PageableDefault(size = 10, sort = "product_code", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductDTO> productList = productService.getProductSearchList(search,pageable);
		
		model.addAttribute("productList", productList);
		model.addAttribute("search", search);
		
		long totalProduct = productService.getTotalProduct();
		model.addAttribute("totalProduct", totalProduct);
		
		
		return "product/list";
	}
	
	@GetMapping("{productCode}")
	public String getProductDetail(@PathVariable Integer productCode, Model model) {
		ProductDTO productDTO = productService.getProduct(productCode);
		model.addAttribute("productDTO", productDTO);
		
		return "product/detail";
	}
	
	@GetMapping("write")
	public String write() {
		return "product/write";
	}
	
	@PostMapping("write")
	public String write(ProductDTO productDTO, MultipartFile attach, Model model) {
		ProductDTO result = productService.write(productDTO, attach);
		
		String resultMsg = "물품 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "물품을 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/product";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
		
	}
	
}
