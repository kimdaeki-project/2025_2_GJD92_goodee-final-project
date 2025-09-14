package com.goodee.finals.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notice/**") @Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("")
	public String list(@PageableDefault(size = 10, sort = "noticeNum", direction= Sort.Direction.DESC) Pageable pageable, Model model) {
		Page<NoticeDTO> result = noticeService.list(pageable);
		model.addAttribute("notice", result);
		return "notice/list";
	}
	
	@GetMapping("write")
	public String write() {
		return "notice/write";
	}
	
	@PostMapping("write")
	public String write(NoticeDTO noticeDTO) {
		NoticeDTO result = noticeService.write(noticeDTO);
		if (result != null) {			
			return "redirect:/notice";
		} else {
			return null;
		}
	}
	
	@GetMapping("{noticeNum}")
	public String detail(@PathVariable("noticeNum") NoticeDTO noticeDTO, Model model) {
		NoticeDTO result = noticeService.detail(noticeDTO);
		model.addAttribute("notice", result);
		return "notice/detail";
	}
	
	@GetMapping("{noticeNum}/edit")
	public String edit(@PathVariable("noticeNum") NoticeDTO noticeDTO, Model model) {
		NoticeDTO result = noticeService.detail(noticeDTO);
		model.addAttribute("notice", result);
		return "notice/write";
	}
	
}
