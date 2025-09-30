package com.goodee.finals.product;

import java.time.LocalDate;
import java.util.List;

import com.goodee.finals.common.attachment.ProductAttachmentDTO;
import com.goodee.finals.productManage.ProductManageDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class ProductDTO {

	@Id
	private Integer productCode;
	private String productName;
	private Long productAmount = 0L;
	private LocalDate productDate = LocalDate.now();
	@Column(columnDefinition = "boolean default false")
	private boolean productDelete;
	
	// 물품 작성자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	// 물품 카테고리
	@ManyToOne
	@JoinColumn(name = "productTypeCode")
	private ProductTypeDTO productTypeDTO;

	// 물품관리대장
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productDTO", cascade = CascadeType.ALL)
	private List<ProductManageDTO> productManageDTO;
	
	// 물품 사진파일
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "productDTO", cascade = CascadeType.ALL)
	private ProductAttachmentDTO productAttachmentDTO;
}
