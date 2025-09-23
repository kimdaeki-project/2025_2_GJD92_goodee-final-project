package com.goodee.finals.common.attachment;

import com.goodee.finals.product.ProductDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "product_attach")
@IdClass(ProductAttachmentDTO.PK.class)
public class ProductAttachmentDTO {

	@Id
	@OneToOne
	@JoinColumn(name = "productCode", insertable = false, updatable = false)
	private ProductDTO productDTO;
	
	@Id
	@OneToOne
	@JoinColumn(name = "attachNum", insertable = false, updatable = false)
	private AttachmentDTO attachmentDTO;
	
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class PK {
		private Integer productDTO;
		private Long attachmentDTO;
	}
	
	
}
