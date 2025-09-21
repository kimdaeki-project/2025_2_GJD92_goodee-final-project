package com.goodee.finals.product;

import lombok.extern.slf4j.Slf4j;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product/**")
@Slf4j
public class ProductController {
			
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public String getProductList(@PageableDefault(size = 10, sort = "product_code", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductDTO> productList = productService.getProductSearchList(search,pageable);
		
		model.addAttribute("productList", productList);
		model.addAttribute("search", search);
		
		long totalProduct = productService.getTotalProduct();
		model.addAttribute("totalProduct", totalProduct);
		
		return "product/list";
	}
	
	@GetMapping("manage")
	public String getProductManageList(@PageableDefault(size = 10, sort = "pm_num", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		return "product/manageList";
	}
	
	@GetMapping("{productCode}")
	public String getProductDetail(@PathVariable Integer productCode, Model model) {
		ProductDTO productDTO = productService.getProduct(productCode);
		model.addAttribute("productDTO", productDTO);
		
		return "product/detail";
	}
	
	@GetMapping("write")
	public String write(Model model) {
		// 품목타입리스트 가져오기
		List<ProductTypeDTO> productTypeList = productService.getProductTypeList();
		model.addAttribute("productTypeList", productTypeList);
		
		return "product/write";
	}
	
	@PostMapping("write")
	public String write(ProductDTO productDTO, MultipartFile attach, Model model) {
		log.info("{}", productDTO.getProductTypeDTO().getProductTypeCode());
		
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
	
	@GetMapping("{productCode}/update")
	public String getProductUpdate(@PathVariable Integer productCode, Model model) {
		ProductDTO productDTO = productService.getProduct(productCode);
		
		model.addAttribute("productDTO", productDTO);
		// 품목타입리스트 가져오기
		List<ProductTypeDTO> productTypeList = productService.getProductTypeList();
		model.addAttribute("productTypeList", productTypeList);
		
		return "product/write";
	}
	
	@PostMapping("{productCode}/update")
	public String postProductUpdate(ProductDTO productDTO, MultipartFile attach, Model model) {
		boolean result = productService.updateProduct(productDTO, attach);
		
		String resultMsg = "물품 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "물품 정보를 수정했습니다.";
			resultIcon = "success";
			String resultUrl = "/product/" + productDTO.getProductCode();
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	public String delete(ProductDTO productDTO, Model model) {
		ProductDTO result = productService.delete(productDTO);
		
		String resultMsg = "물품 삭제 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "물품 정보를 삭제했습니다.";
			resultIcon = "success";
			String resultUrl = "/product";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	
	
	
}
