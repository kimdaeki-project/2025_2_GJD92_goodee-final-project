package com.goodee.finals.productManage;

import java.time.LocalDate;

import org.springframework.format.annotation.NumberFormat;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_manage")
public class ProductManageDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pmNum;
	private Integer pmType;
	@NotNull(message = "* 수량은 필수입니다.")
//	@Pattern(regexp = "^[0-9]+$", message = "숫자만 입력 가능합니다.")
	private Long pmAmount;
	private Long pmRemainAmount;
	private LocalDate pmDate = LocalDate.now();
	@NotBlank(message = "* 비고는 필수입니다.")
	private String pmNote = "";
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@ManyToOne
	@JoinColumn(name = "productCode")
	private ProductDTO productDTO;
	
}
