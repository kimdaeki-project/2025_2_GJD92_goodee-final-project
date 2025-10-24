package com.goodee.finals.productManage;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

	public Page<ProductManageDTO> getProductManageSearchList(LocalDate startDate, LocalDate endDate, Integer pmType, String search, Pageable pageable) {
		return pmRepository.findAllBySearch(startDate, endDate, pmType, search, pageable);
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
	
	// 엑셀용 전체 리스트
    public List<ProductManageDTO> getProductManageSearchListForExcel(LocalDate startDate, LocalDate endDate, Integer pmType, String search) {
        return pmRepository.findBySearchKeyword(startDate, endDate, pmType, search);
    }

    // 엑셀 작성
    public void writeProductManageExcel(List<ProductManageDTO> list, OutputStream os) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("물품관리대장");

        // 헤더 작성
        Row header = sheet.createRow(0);
        String[] headers = {"No.", "입출고일자", "물품명", "구분", "등록수량", "잔여수량", "작성자", "비고"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 데이터 작성
        int rowIdx = 1;
        for (ProductManageDTO pm : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(pm.getPmNum());
            row.createCell(1).setCellValue(pm.getPmDate().toString());
            row.createCell(2).setCellValue(pm.getProductDTO().getProductName());
            row.createCell(3).setCellValue(pm.getPmType() == 90 ? "출고" : "입고");
            row.createCell(4).setCellValue(pm.getPmAmount());
            row.createCell(5).setCellValue(pm.getPmRemainAmount());
            row.createCell(6).setCellValue(pm.getStaffDTO().getStaffName());
            row.createCell(7).setCellValue(pm.getPmNote() == null ? "" : pm.getPmNote());
        }

        // 열 너비 자동 조정
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(os);
        workbook.close();
    }
	
    // 조건 검색 (기간, 카테고리, 입출고, 키워드)
//    public List<ProductManageDTO> findByConditions(LocalDate startDate,
//                                                   LocalDate endDate,
//                                                   Integer productTypeCode,
//                                                   String inoutType,
//                                                   String keyword) {
//
//        if (keyword != null && keyword.trim().isEmpty()) keyword = null;
//        if (inoutType != null && inoutType.trim().isEmpty()) inoutType = null;
//
//        return productManageRepository.findByConditions(startDate, endDate, productTypeCode, inoutType, keyword);
//    }
//
//    // 통계용 합계 계산
//    public ProductSummary calculateSummary(List<ProductManageDTO> list) {
//        int totalIn = 0;
//        int totalOut = 0;
//        int totalStock = 0;
//        int totalItems = 0;
//
//        if (list != null && !list.isEmpty()) {
//            totalItems = (int) list.stream()
//                    .map(pm -> pm.getProductDTO().getProductName())
//                    .distinct()
//                    .count();
//
//            for (ProductManageDTO dto : list) {
//                if ("입고".equals(dto.getPmTypeName()) || dto.getPmType() == 80) {
//                    totalIn += dto.getPmAmount();
//                } else if ("출고".equals(dto.getPmTypeName()) || dto.getPmType() == 90) {
//                    totalOut += dto.getPmAmount();
//                }
//                totalStock += dto.getPmRemainAmount();
//            }
//        }
//
//        return new ProductSummary(totalItems, totalIn, totalOut, totalStock);
//    }
}
