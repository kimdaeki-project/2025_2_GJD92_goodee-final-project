package com.goodee.finals.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product_type")
public class ProductTypeDTO {

	@Id
	private Integer productTypeCode;
	private String productTypeName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "productTypeDTO", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProductDTO> productDTOs;
}
