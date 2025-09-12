package com.goodee.finals.product;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private Long productCode;
	private String productType;
	private String productName;
	private Long productAmount;
	private LocalDate productDate;
	
	
	
}
