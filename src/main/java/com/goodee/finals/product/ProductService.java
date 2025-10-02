package com.goodee.finals.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;
import com.goodee.finals.common.attachment.AttachmentRepository;
import com.goodee.finals.common.attachment.ProductAttachmentDTO;
import com.goodee.finals.common.file.FileService;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductService {

	@Autowired
	private FileService fileService;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private AttachmentRepository attachmentRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	public Page<ProductDTO> getProductSearchList(String search, Pageable pageable) {
		return productRepository.findAllBySearch(search, pageable);
	}
	
	public long getTotalProduct() {
		return productRepository.countByProductDeleteFalse();
	}
	
	public ProductDTO getProduct(Integer productCode) {
		return productRepository.findById(productCode).orElseThrow();
	}
	
	public List<ProductTypeDTO> getProductTypeList() {
		return productTypeRepository.findAll();
	}
	
	public ProductDTO write(ProductDTO productDTO, MultipartFile attach) {
		StaffDTO staffDTO = new StaffDTO();
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
		staffDTO.setStaffCode(staffCode);
		
		productDTO.setStaffDTO(staffDTO);
		productDTO = setProductDefault(productDTO);
		
		String fileName = null;
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		
		if (attach != null && attach.getSize() > 0) {
			try {
				fileName = fileService.saveFile(FileService.PRODUCT, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			attachmentDTO.setAttachSize(0L);
			attachmentDTO.setOriginName("default.png");
			attachmentDTO.setSavedName("default.png");
			
			attachmentRepository.save(attachmentDTO);
			
		}
		
		ProductAttachmentDTO productAttachmentDTO = new ProductAttachmentDTO();
		productAttachmentDTO.setProductDTO(productDTO);
		productAttachmentDTO.setAttachmentDTO(attachmentDTO);
		
		productDTO.setProductAttachmentDTO(productAttachmentDTO);
		ProductDTO result = productRepository.save(productDTO);
		
		if (result != null) return productDTO;
		else return null;
	}
	
	private ProductDTO setProductDefault(ProductDTO productDTO) {
		Integer productTypeCode = productDTO.getProductTypeDTO().getProductTypeCode();
		Integer lastProductCode = productRepository.findTopProductCodeByProductType(productTypeCode);
		
		if(lastProductCode == null) {
			productDTO.setProductCode((productTypeCode * 10000) + 1);
		} else {
			productDTO.setProductCode(lastProductCode + 1);
		}
		
		return productDTO;
	}
	
	public boolean updateProduct(ProductDTO productDTO, MultipartFile attach) {
		setProductUpdate(productDTO);
		
		if(attach != null && attach.getSize() > 0) {
			ProductDTO before = productRepository.findById(productDTO.getProductCode()).orElseThrow();
			AttachmentDTO beforeAttach = before.getProductAttachmentDTO().getAttachmentDTO();
			
			String savedName = attachmentRepository.findById(beforeAttach.getAttachNum()).get().getSavedName();
			boolean deleteResult = fileService.fileDelete(FileService.PRODUCT, savedName);
			
			if(deleteResult) {
				productRepository.deleteBeforeAttach(beforeAttach.getAttachNum());
				attachmentRepository.deleteAttach(beforeAttach.getAttachNum());
			}
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			
			try {
				String fileName = fileService.saveFile(FileService.PRODUCT, attach);
				
				attachmentDTO.setAttachSize(attach.getSize());
				attachmentDTO.setOriginName(attach.getOriginalFilename());
				attachmentDTO.setSavedName(fileName);
				
				attachmentRepository.save(attachmentDTO);
				
				ProductAttachmentDTO productAttachmentDTO = new ProductAttachmentDTO();
				productAttachmentDTO.setProductDTO(productDTO);
				productAttachmentDTO.setAttachmentDTO(attachmentDTO);
				
				productDTO.setProductAttachmentDTO(productAttachmentDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		ProductDTO result = productRepository.saveAndFlush(productDTO);
		
		if (result != null) return true;
		else return false;
	}
	
	private void setProductUpdate(ProductDTO after) {
		ProductDTO before = productRepository.findById(after.getProductCode()).orElseThrow();
		after.setStaffDTO(before.getStaffDTO());
	}
	
	public ProductDTO delete(ProductDTO productDTO) {
		productDTO = productRepository.findById(productDTO.getProductCode()).orElseThrow();
		productDTO.setProductDelete(true);
		ProductDTO result = productRepository.save(productDTO);
		return result;
	}
	
}
