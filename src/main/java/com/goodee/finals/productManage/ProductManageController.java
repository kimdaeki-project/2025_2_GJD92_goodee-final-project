package com.goodee.finals.productManage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.product.ProductService;
import com.goodee.finals.product.ProductTypeDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
		
		String searchValue = search;
		
		// 실제 검색용으로는 숫자 변환
	    if ("입고".equals(search)) {
	        search = "80";
	    } else if ("출고".equals(search)) {
	        search = "90";
	    }
			
		Page<ProductManageDTO> productManageList = pmService.getProductManageSearchList(search, pageable);
		
		model.addAttribute("productManageList", productManageList);
		model.addAttribute("search", search);
		
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("staffDTO", staffDTO);
		
		long totalProductManage = pmService.getTotalProduct();
		model.addAttribute("totalProductManage", totalProductManage);
		
		// 검색창에 보일 원래 검색어를 별도로 넘긴다
	    model.addAttribute("searchKeyword", searchValue);
	    
		return "productManage/manageList";
	}
	
	@GetMapping("/excel")
	public void downloadExcel(@RequestParam(required = false) String search, HttpServletResponse response) throws IOException {
	    if (search == null) search = "";

	    // 입출고 변환 (기존과 동일한 로직)
	    if ("입고".equals(search)) {
	        search = "80";
	    } else if ("출고".equals(search)) {
	        search = "90";
	    }

	    // 전체 데이터 조회
	    List<ProductManageDTO> list = pmService.getProductManageSearchListForExcel(search);

	    // 파일명 지정
	    String fileName = URLEncoder.encode("물품관리대장.xlsx", StandardCharsets.UTF_8);

	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

	    // 서비스에서 엑셀 파일 생성
	    pmService.writeProductManageExcel(list, response.getOutputStream());
	}
	
	@GetMapping("{pmNum}")
	@ResponseBody
	public ProductManageDTO getProductManageDetail(@PathVariable Long pmNum, Model model) {
		ProductManageDTO productManageDTO = pmService.getProductManage(pmNum);
		model.addAttribute("productManageDTO", productManageDTO);
		
		return productManageDTO;
	}
	
	@GetMapping("write")
	public String write(@ModelAttribute ProductManageDTO productManageDTO, @PageableDefault(size = 10, sort = "product_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
//		if (search == null) search = "";
//		
//		Page<ProductDTO> productPage  = pService.getProductSearchList(search, pageable);
//		List<ProductDTO> productList = productPage.getContent();
//		
//		model.addAttribute("productList", productList);
//		model.addAttribute("search", search);
		
		return "productManage/manageWrite";
	}
	
	
	@GetMapping("loadProducts")
	@ResponseBody
	public List<ProductDTO> loadloadProducts() {
//		if (search == null) search = "";
//		
//		Page<ProductDTO> productPage  = pService.getProductSearchList(search, pageable);
//		List<ProductDTO> productList = productPage.getContent();
//		
//		model.addAttribute("productList", productList);
//		model.addAttribute("search", search);
		
		List<ProductDTO> productList =  pmService.list();
		
		return productList;
	}
	
	@PostMapping("write")
	public String Write(@Valid ProductManageDTO productManageDTO, BindingResult bindingResult, ProductDTO pDTO, Model model) {
		if(bindingResult.hasErrors()) {
			return "productManage/manageWrite";
		}
		
		if (pDTO.getProductCode() == null) {
			String resultMsg = "입출고 대상을 지정해주세요.";
			String resultIcon = "warning";
			String resultUrl = "/productManage/write";
			
			model.addAttribute("resultUrl", resultUrl);
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("resultIcon", resultIcon);
			
			return "common/result";
		}
		
		ProductManageDTO result = pmService.write(pDTO, productManageDTO);
		
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
	
//	@GetMapping("{pmNum}/update")
//	public String getProductManageUpdate(@PathVariable Long pmNum, @PageableDefault(size = 10, sort = "product_code", direction = Direction.ASC) Pageable pageable, String search, Model model) {
//		if (search == null) search = "";
//		
//		Page<ProductDTO> productPage  = pService.getProductSearchList(search,pageable);
//		List<ProductDTO> productList = productPage.getContent();
//		
//		model.addAttribute("productList", productList);
//		model.addAttribute("search", search);
//		
//		ProductManageDTO productManageDTO = pmService.getProductManage(pmNum);
//		
//		model.addAttribute("productManageDTO", productManageDTO);
//		return "productManage/manageWrite";
//	}
//	
//	@PostMapping("{pmNum}/update")
//	public String postProductManageUpdate(ProductDTO pDTO, ProductManageDTO pmDTO, Model model) {
//		
//		boolean result = pmService.updateProductManage(pDTO, pmDTO);
//		
//		String resultMsg = "입출고내역 수정 중 오류가 발생했습니다.";
//		String resultIcon = "warning";
//		
//		if (result) {
//			resultMsg = "입출고내역을 수정했습니다.";
//			resultIcon = "success";
//			String resultUrl = "/productManage";
//			model.addAttribute("resultUrl", resultUrl);
//		}
//			
//		model.addAttribute("resultMsg", resultMsg);
//		model.addAttribute("resultIcon", resultIcon);
//		
//		return "common/result";
//		
//	}
	
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
	
	@GetMapping("stockReport")
	public String getStockReport() {
		return "productManage/stockReport";
	}
}
