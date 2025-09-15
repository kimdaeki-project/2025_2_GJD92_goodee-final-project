package com.goodee.finals.product;

import java.time.LocalDate;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String productType;
	private String productName;
	private Long productAmount;
	private LocalDate productDate;
	
	// 물품 작성자
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;

	// 물품 사진파일
	
}
