package com.goodee.finals.inspection;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

// 어트랙션 점검 기록 목록
@Getter
@Setter
public class InspectionPager {
	int blockSize = 10;  // 10묶음씩
	int currentPage;  // 현재 페이지
	int totalPages;  // 총 페이지
	int startPage;  // 시작 페이지(1, 6, 11...)
	int endPage;  // 끝 페이지(5, 10, 15...)
	String keyword;  // 검색어
	
	public void calc(Page<InspectionDTO> result) throws Exception {
		this.currentPage = result.getNumber();
		this.totalPages = result.getTotalPages();
		this.startPage = (currentPage / blockSize) * blockSize;
		this.endPage = startPage + blockSize -1;
		if (endPage >= totalPages) endPage = totalPages -1;
	}

}




