package com.goodee.finals.product;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductDTO {

	@Id
	private Integer productCode;
	
	@NotBlank(message = "물품명은 필수입니다.")
	private String productName;
	@NotBlank(message = "물품 규격은 필수입니다.")
	private String productSpec;
	private Long productAmount = 0L;
	private LocalDate productDate = LocalDate.now();
	@Column(columnDefinition = "boolean default false")
	private boolean productDelete;
	
	// 물품 작성자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	@JsonIgnore
	private StaffDTO staffDTO;
	
	// 물품 카테고리
	@ManyToOne
	@JoinColumn(name = "productTypeCode")
	private ProductTypeDTO productTypeDTO;

	// 물품관리대장
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProductManageDTO> productManageDTO;
	
	// 물품 사진파일
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "productDTO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private ProductAttachmentDTO productAttachmentDTO;
	
	public void setProductName(String productName) {
	    this.productName = productName == null ? null : productName.trim();
	}
	
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec == null ? null : productSpec.trim();
	}
	
}
