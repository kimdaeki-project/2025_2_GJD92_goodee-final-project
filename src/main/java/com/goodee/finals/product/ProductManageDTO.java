package com.goodee.finals.product;

import java.time.LocalDate;

import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "product_manage")
public class ProductManageDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pmNum;
	private Integer pmType;
	private Integer pmAmount;
	private LocalDate pmDate;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@ManyToOne
	@JoinColumn(name = "productCode")
	private ProductDTO productDTO;
	
}
