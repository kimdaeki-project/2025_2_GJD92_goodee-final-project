package com.goodee.finals.notice;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoticePager {
	int blockSize = 10;
	int currentPage;
	int totalPages;
	int startPage;
	int endPage;
	String keyword;
	
	public void calc(Page<NoticeDTO> result) {
		this.currentPage = result.getNumber();
		this.totalPages = result.getTotalPages();
		this.startPage = (currentPage / blockSize) * blockSize;
		this.endPage = startPage + blockSize - 1;
		if (endPage >= totalPages) endPage = totalPages - 1;
	}
}
