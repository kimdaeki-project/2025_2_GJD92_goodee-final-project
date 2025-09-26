package com.goodee.finals.productManage;

import java.time.LocalDate;

import com.goodee.finals.product.ProductDTO;
import com.goodee.finals.staff.StaffDTO;

import jakarta.persistence.Column;
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
	private Long pmAmount;
	private Long pmRemainAmount;
	private LocalDate pmDate = LocalDate.now();
	@Column(columnDefinition = "boolean default false")
	private boolean pmDelete;
	
	@ManyToOne
	@JoinColumn(name = "staffCode")
	private StaffDTO staffDTO;
	
	@ManyToOne
	@JoinColumn(name = "productCode")
	private ProductDTO productDTO;
	
}
