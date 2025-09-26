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
import org.springframework.web.bind.annotation.PathVariable;
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
	private ProductService pService;
	
	@Autowired
	private ProductManageService pmService;
	
	@GetMapping("")
	public String getProductManageList(@PageableDefault(size = 10, sort = "pm_num", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductManageDTO> productManageList = pmService.getProductManageSearchList(search, pageable);
		
		model.addAttribute("productManageList", productManageList);
		model.addAttribute("search", search);
		
		long totalProductManage = pmService.getTotalProduct();
		model.addAttribute("totalProductManage", totalProductManage);
		
		return "productManage/manageList";
	}
	
	@GetMapping("{pmNum}")
	public String getProductManageDetail(@PathVariable Long pmNum, Model model) {
		ProductManageDTO productManageDTO = pmService.getProductManage(pmNum);
		model.addAttribute("productManageDTO", productManageDTO);
		
		return "productManage/manageDetail";
	}
	
	@GetMapping("write")
	public String write(@PageableDefault(size = 10, sort = "product_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductDTO> productPage  = pService.getProductSearchList(search,pageable);
		List<ProductDTO> productList = productPage.getContent();
		
		model.addAttribute("productList", productList);
		model.addAttribute("search", search);
		
		return "productManage/manageWrite";
	}
	
	@PostMapping("write")
	public String Write(ProductDTO pDTO, ProductManageDTO pmDTO, Model model) {
		log.info("{}", pDTO.getProductTypeDTO().getProductTypeCode());
		ProductManageDTO result = pmService.Write(pmDTO, pDTO);
		
		String resultMsg = "입출고 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "입출고를 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/productManage";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{pmNum}/update")
	public String getProductManageUpdate(@PathVariable Long pmNum, @PageableDefault(size = 10, sort = "product_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<ProductDTO> productPage  = pService.getProductSearchList(search,pageable);
		List<ProductDTO> productList = productPage.getContent();
		
		model.addAttribute("productList", productList);
		model.addAttribute("search", search);
		
		ProductManageDTO productManageDTO = pmService.getProductManage(pmNum);
		
		model.addAttribute("productManageDTO", productManageDTO);
		return "productManage/manageWrite";
	}
	
	@PostMapping("{pmNum}/update")
	public String postProductManageUpdate(ProductDTO pDTO, ProductManageDTO pmDTO, Model model) {
		
		
		boolean result = pmService.updateProductManage(pDTO, pmDTO);
		
		String resultMsg = "입출고내역 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result) {
			resultMsg = "입출고내역을 수정했습니다.";
			resultIcon = "success";
			String resultUrl = "/productManage/" + pmDTO.getPmNum();
			model.addAttribute("resultUrl", resultUrl);
		}
			
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
		
	}
	
	@PostMapping("{pmNum}/delete")
	public String delete(ProductDTO pDTO, ProductManageDTO pmDTO, Model model) {
		ProductManageDTO result = pmService.delete(pDTO, pmDTO);
		
		String resultMsg = "입출고내역 삭제 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "입출고내역을 삭제했습니다.";
			resultIcon = "success";
			String resultUrl = "/productManage";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
}
