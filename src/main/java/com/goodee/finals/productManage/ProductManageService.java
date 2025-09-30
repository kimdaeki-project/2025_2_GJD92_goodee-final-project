package com.goodee.finals.productManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.product.ProductRepository;
import com.goodee.finals.product.ProductTypeDTO;
import com.goodee.finals.product.ProductTypeRepository;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductManageService {

	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private ProductRepository pRepository;
	@Autowired
	private ProductManageRepository pmRepository;
	@Autowired
	private ProductTypeRepository ptRepository;

	public Page<ProductManageDTO> getProductManageSearchList(String search, Pageable pageable) {
		return pmRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalProduct() {
		return pmRepository.countByPmDeleteFalse();
	}
	
	public ProductManageDTO getProductManage(Long pmNum) {
		return pmRepository.findById(pmNum).orElseThrow();
	}
	
	public ProductManageDTO Write(ProductManageDTO productManageDTO, ProductDTO productDTO) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		productManageDTO.setStaffDTO(staffDTO);
		
		ProductDTO productDB = pRepository.findById(productDTO.getProductCode()).orElseThrow();

		Long pmAmount = productManageDTO.getPmAmount();
		Long pAmount = productDB.getProductAmount();
		
		// 출고 90 코드일 때, 음수로 변환
		if (productManageDTO.getPmType() == 90) productManageDTO.setPmRemainAmount(pAmount + (-pmAmount));
		else productManageDTO.setPmRemainAmount(pAmount + pmAmount);
		
		productDB.setProductAmount(productManageDTO.getPmRemainAmount());
		
		productManageDTO.setProductDTO(productDB);
		ProductManageDTO result = pmRepository.save(productManageDTO);
		
		pRepository.save(productDB);
		
		if (result != null) return productManageDTO;
		else return null;
	}
	
	public boolean updateProductManage(ProductDTO pDTO, ProductManageDTO pmDTO) {
		StaffDTO staffDTO = staffRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
		
		ProductManageDTO pmDB = pmRepository.findById(pmDTO.getPmNum()).orElseThrow();

		Long beforePmAmount = pmDB.getPmAmount();
		Long currentPmAmount = pmDTO.getPmAmount();
		
		ProductDTO pDB=  pmDB.getProductDTO();
		Long pAmountDB = pDB.getProductAmount();
		
		// 출고 90 코드일 때, 음수로 변환
		if (pmDB.getPmType() == 90) beforePmAmount *= (-1); 
		else if (pmDTO.getPmType() == 90) currentPmAmount *= (-1);  
//		else productManageDTO.setPmRemainAmount(pAmount + currentPmAmount);
		
		Long modCount = null;
		// 더크면 그 차이만큼 뺴줘야 함
		if (beforePmAmount > currentPmAmount) {
			modCount = beforePmAmount - currentPmAmount;
		} else { // db에서 그 차이만큼 더해줘야 함
			modCount = currentPmAmount - beforePmAmount;
		}
		
		pDB.setProductAmount(modCount);
		
		ProductTypeDTO ptDTO = ptRepository.findById(pDTO.getProductTypeDTO().getProductTypeCode()).orElseThrow();
		pDTO.setProductTypeDTO(ptDTO);
		
		pmDB.setProductDTO(pDTO);
		pmDB.setPmType(pmDTO.getPmType());
		pmDB.setPmAmount(pmDTO.getPmAmount());
		pmDB.setPmRemainAmount(modCount);
		
		ProductManageDTO result = pmRepository.save(pmDB);
		
		if (result != null) return true;
		else return false;
	}
	
	public ProductManageDTO delete(ProductDTO pDTO, ProductManageDTO pmDTO) {
		pmDTO = pmRepository.findById(pmDTO.getPmNum()).orElseThrow();

		Long pmAmount = pmDTO.getPmAmount();
		
		if (pmDTO.getPmType() == 90) pmDTO.getProductDTO().setProductAmount(pmDTO.getProductDTO().getProductAmount() + pmAmount);
		else pmDTO.getProductDTO().setProductAmount(pmDTO.getProductDTO().getProductAmount() - pmAmount);
			
		pmDTO.setPmDelete(true);
		
		ProductManageDTO result = pmRepository.save(pmDTO);
		return result;
	}
	
}
