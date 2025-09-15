package com.goodee.finals.notice;

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
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/notice/**") @Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("")
	public String list(@PageableDefault(size = 10, sort = "noticeNum", direction= Sort.Direction.DESC) Pageable pageable, NoticePager noticePager, Model model) {
		String keyword = noticePager.getKeyword();
		if (keyword == null) keyword = "";
		Page<NoticeDTO> result = noticeService.list(pageable, keyword);
		noticePager.calc(result);
		model.addAttribute("notice", result);
		model.addAttribute("pager", noticePager);
		return "notice/list";
	}
	
	@GetMapping("write")
	public String write() {
		return "notice/write";
	}
	
	@PostMapping("write")
	public String write(NoticeDTO noticeDTO, MultipartFile[] files) {
		NoticeDTO result = noticeService.write(noticeDTO, files);
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
	
	@PostMapping("{noticeNum}/edit")
	public String edit(NoticeDTO noticeDTO) {
		NoticeDTO result = noticeService.edit(noticeDTO);
		if (result != null) {			
			return "redirect:/notice/" + noticeDTO.getNoticeNum();
		} else {
			return null;
		}
	}
	
	@PostMapping("{noticeNum}/delete")
	public String delete(@PathVariable("noticeNum") NoticeDTO noticeDTO, Model model) {
		NoticeDTO result = noticeService.delete(noticeDTO);
		if (result != null) {
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("url", "/notice");
			return "notice/result";
		} else {
			return null;
		}
	}
	
}
