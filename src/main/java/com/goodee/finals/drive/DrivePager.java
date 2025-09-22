package com.goodee.finals.drive;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrivePager {

	int blockSize = 15;
	int currentPage;
	int totalPages;
	int startPage;
	int endPage;
	String keyword;
	
	public void cacl(Page<DocumentDTO> result) {
		this.currentPage = result.getNumber();
		this.totalPages = result.getTotalPages();
		this.startPage = (currentPage / blockSize) * blockSize;
		this.endPage = startPage + blockSize - 1;
		if (endPage >= totalPages) endPage = totalPages - 1;
	}
	
}
