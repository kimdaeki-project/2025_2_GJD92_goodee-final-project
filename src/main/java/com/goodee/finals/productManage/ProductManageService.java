package com.goodee.finals.productManage;

import java.util.List;

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
	
	public List<ProductDTO> list(){
		return pRepository.findAll();
	}
	
	public long getTotalProduct() {
		return pmRepository.count();
	}
	
	public ProductManageDTO getProductManage(Long pmNum) {
		return pmRepository.findById(pmNum).orElseThrow();
	}
	
	public ProductManageDTO write(ProductDTO productDTO, ProductManageDTO productManageDTO) {
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
		// 물품, 유형, 수량, 비고(수정되었음 표시)
		// 1. 삭제 pm 등록
		delete(productDTO, productManageDTO);
		
		// 2. 새로 입력된 pm 새로 등록 + 수정 메세지 비고란에
		ProductManageDTO newPmDTO = new ProductManageDTO();
		
		ProductManageDTO pmDB = pmRepository.findById(productManageDTO.getPmNum()).orElseThrow();
		
		newPmDTO.setStaffDTO(pmDB.getStaffDTO());
		
		ProductDTO productDB = pRepository.findById(productDTO.getProductCode()).orElseThrow();
			
		Long pmAmount = productManageDTO.getPmAmount(); // 새로입력한 수량
		Long pAmount = productDB.getProductAmount(); // 물품 현재수량
		
		Long pmNum = productManageDTO.getPmNum(); // 기존 pmDTO 번호
		
		// 출고 90 코드일 때, 음수로 변환
		if (productManageDTO.getPmType() == 90) {
			newPmDTO.setPmType(90);
			newPmDTO.setPmAmount(pmAmount);
			newPmDTO.setPmRemainAmount(pAmount + (-pmAmount));
			newPmDTO.setPmNote(productManageDTO.getPmNote() + " (No."+pmNum+"  내용 정정)");
		}else {
			newPmDTO.setPmType(80);
			newPmDTO.setPmAmount(pmAmount);
			newPmDTO.setPmRemainAmount(pAmount + pmAmount);
			newPmDTO.setPmNote(productManageDTO.getPmNote() + " (No."+pmNum+"  내용 정정)");
		}
		
		// product 현재수량 저장
		productDB.setProductAmount(newPmDTO.getPmRemainAmount());
		newPmDTO.setProductDTO(productDB);
		
		ProductManageDTO result1 = pmRepository.save(newPmDTO);
		ProductDTO result2 = pRepository.save(productDB);
		
		if (result2 != null) return true;
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
			newDeletePm.setPmNote("No."+pmNum+"  입고 취소"); // 기존 pmDTO 수량과 반대수량 입력
			remainAmountUpdated = productDTO.getProductAmount() + newDeletePm.getPmAmount();
		} else if (newDeletePm.getPmType() == 90){ // 출고시
			newDeletePm.setPmNote("No."+pmNum+"  출고 취소"); // 기존 pmDTO 수량과 반대수량 입력
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
