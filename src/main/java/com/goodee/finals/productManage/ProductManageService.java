package com.goodee.finals.productManage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.product.ProductRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductManageService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductManageRepository productManageRepository;
	
	public Page<ProductManageDTO> getProductManageSearchList(String search, Pageable pageable) {
		return productManageRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalProduct() {
		return productManageRepository.count();
	}
	
	public ProductManageDTO manageWrite(ProductManageDTO productManageDTO, ProductDTO productDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		productManageDTO.setStaffDTO(staffDTO.get());
		
//		Optional<ProductDTO> productDTO = productRepository.findById(productDTO.getProductCode());
//		productManageDTO.setProductDTO(productDTO);
		
		// 출고 90 코드일 때, 음수로 변환
		if (productManageDTO.getPmType() == 90) productManageDTO.setPmAmount(productManageDTO.getPmAmount()*-1);
		
		Long oriAmount = productManageDTO.getProductDTO().getProductAmount();
		Long pmAmount = productManageDTO.getPmAmount();
		
		productManageDTO.getProductDTO().setProductAmount(oriAmount + pmAmount);
		
		ProductManageDTO result = productManageRepository.save(productManageDTO);

		if (result != null) return productManageDTO;
		else return null;
	}
	
}
