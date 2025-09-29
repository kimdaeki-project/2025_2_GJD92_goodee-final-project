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
		return pmRepository.count();
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
	
	public boolean updateProductManage(ProductDTO productDTO, ProductManageDTO productManageDTO) {
		StaffDTO staffDTO = (StaffDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		productManageDTO.setStaffDTO(staffDTO);
		
		ProductManageDTO pmDB = pmRepository.findById(productManageDTO.getPmNum()).orElseThrow();

		Long beforePmAmount = pmDB.getPmAmount();
		Long currentPmAmount = productManageDTO.getPmAmount();
		
		ProductDTO pDB=  pmDB.getProductDTO();
		Long pAmountDB = pDB.getProductAmount();
		
		// 출고 90 코드일 때, 음수로 변환
		if (pmDB.getPmType() == 90) beforePmAmount *= (-1); 
		else if (productManageDTO.getPmType() == 90) currentPmAmount *= (-1);  
//		else productManageDTO.setPmRemainAmount(pAmount + currentPmAmount);
		
		Long modCount = null;
		// 더크면 그 차이만큼 뺴줘야 함
		if (beforePmAmount > currentPmAmount) {
			modCount = beforePmAmount - currentPmAmount;
		} else { // db에서 그 차이만큼 더해줘야 함
			modCount = currentPmAmount - beforePmAmount;
		}
		
		pDB.setProductAmount(modCount);
		
		ProductTypeDTO ptDTO = ptRepository.findById(productDTO.getProductTypeDTO().getProductTypeCode()).orElseThrow();
		productDTO.setProductTypeDTO(ptDTO);
		
		pmDB.setProductDTO(productDTO);
		pmDB.setPmType(productManageDTO.getPmType());
		pmDB.setPmAmount(productManageDTO.getPmAmount());
		pmDB.setPmRemainAmount(modCount);
		
		ProductManageDTO result = pmRepository.save(pmDB);
		
		if (result != null) return true;
		else return false;
	}
	
	public ProductManageDTO delete(ProductDTO productDTO, ProductManageDTO productManageDTO) {
		productManageDTO = pmRepository.findById(productManageDTO.getPmNum()).orElseThrow();

		Long pmNum = productManageDTO.getPmNum(); // 기존 pmDTO 번호
		Integer pmType = productManageDTO.getPmType(); // 기존 pmDTO 유형
		Long pmAmount = productManageDTO.getPmAmount(); // 기존 pmDTO 입출고수량
		
		ProductManageDTO newDeletePm = new ProductManageDTO(); // 취소내용 생성할 pmDTO
		newDeletePm.setStaffDTO(productManageDTO.getStaffDTO()); // 로그인한 사용자 = 기존 pmDTO 작성자
		newDeletePm.setPmType(pmType); // 기존 pmDTO 유형과 같음
		newDeletePm.setPmAmount(-pmAmount); // 기존 pmDTO 수량과 반대수량 입력
		
		productDTO = productManageDTO.getProductDTO();
		Long remainAmountUpdated = 0L;
		
		if(newDeletePm.getPmType() == 80) { // 입고시
			newDeletePm.setPmNote("번호"+pmNum+"번 - 입고 취소(재고 복원"); // 기존 pmDTO 수량과 반대수량 입력
			remainAmountUpdated = productDTO.getProductAmount() + newDeletePm.getPmAmount();
		} else if (newDeletePm.getPmType() == 90){ // 출고시
			newDeletePm.setPmNote("번호"+pmNum+"번 - 출고 취소(재고 복원"); // 기존 pmDTO 수량과 반대수량 입력
			remainAmountUpdated = productDTO.getProductAmount() - newDeletePm.getPmAmount();
		}
		
		newDeletePm.setPmRemainAmount(remainAmountUpdated); // pmDTO 잔여수량에 pDTO수량 + 마이너스한 취소수량 더해주기
		productDTO.setProductAmount(remainAmountUpdated);
		
		newDeletePm.setProductDTO(productDTO);
		
		ProductManageDTO result = pmRepository.save(newDeletePm);
		
		// 상품 재고수량 복원시키기
		pRepository.save(productDTO);
		
		return result;
	}
	
}
