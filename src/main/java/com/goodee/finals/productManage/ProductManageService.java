package com.goodee.finals.productManage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class ProductManageService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private ProductManageRepository productManageRepository;
	
	public ProductManageDTO manageWrite(ProductManageDTO productManageDTO, ProductDTO productDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
		productManageDTO.setStaffDTO(staffDTO.get());
		
//		Optional<ProductDTO> productDTO = productRepository.findById(productDTO.getProductCode());
//		productManageDTO.setProductDTO(productDTO);
		
		// 출고 90 코드일 때, 음수로 변환
		if (productManageDTO.getPmType() == 90) productManageDTO.setPmAmount(productManageDTO.getPmAmount()*-1);
		
		
		// productDTO 수량 계산해서 넣고 DB 저장 = productDTO.getProductAmount() + productManageDTO.getpmAmount()
		
		ProductManageDTO result = productManageRepository.save(productManageDTO);

		if (result != null) return productManageDTO;
		else return null;
	}
	
}
