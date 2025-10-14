package com.goodee.finals.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.finals.common.attachment.AttachmentDTO;

import jakarta.validation.Valid;

@RequestMapping("/notice/**") @Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("")
	public String list(@PageableDefault(size = 10, sort = "noticeNum", direction= Sort.Direction.DESC) Pageable pageable, NoticePager noticePager, Model model) {
		String keyword = noticePager.getKeyword();
		if (keyword == null) keyword = "";
		
		Page<NoticeDTO> resultNotice = noticeService.list(pageable, keyword);
		List<NoticeDTO> resultPinned = noticeService.pinned(keyword);
		noticePager.calc(resultNotice);
		model.addAttribute("notice", resultNotice);
		model.addAttribute("pinned", resultPinned);
		model.addAttribute("pager", noticePager);
		model.addAttribute("totalNotice", resultNotice.getContent().size() + resultPinned.size());
		return "notice/list";
	}
	
	@GetMapping("temp")
	public String temp(@PageableDefault(size = 10, sort = "noticeNum", direction= Sort.Direction.DESC) Pageable pageable, NoticePager noticePager, Model model) {
		String keyword = noticePager.getKeyword();
		if (keyword == null) keyword = "";
		
		Page<NoticeDTO> resultNotice = noticeService.tempList(pageable, keyword);
		noticePager.calc(resultNotice);
		model.addAttribute("notice", resultNotice);
		model.addAttribute("pager", noticePager);
		model.addAttribute("totalNotice", resultNotice.getContent().size());
		return "notice/temp";
	}
	
	@GetMapping("write")
	public String write() {
		return "notice/write";
	}
	
	@PostMapping("write")
	public String write(@Valid NoticeDTO noticeDTO, BindingResult bindingResult, MultipartFile[] files, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "notice/write";
		}
		
		NoticeDTO result = noticeService.write(noticeDTO, files);
		if (result != null) {
			model.addAttribute("resultMsg", "게시글이 등록되었습니다.");
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/notice");
			return "common/result";
		} else {
			return null;
		}
	}
	
	@PostMapping("temp")
	public String saveTemp(@Valid NoticeDTO noticeDTO, BindingResult bindingResult, MultipartFile[] files, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "notice/write";
		}
		
		noticeDTO.setNoticeTmp(true);
		
		NoticeDTO result = noticeService.write(noticeDTO, files);
		if (result != null) {
			model.addAttribute("resultMsg", "게시글이 임시저장되었습니다.");
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/notice/temp");
			return "common/result";
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
	public String edit(NoticeDTO noticeDTO, @RequestParam(required = false) List<Long> deleteFiles, MultipartFile[] files, Model model) {
		
		Long toDelete = noticeDTO.getNoticeNum();
		
		if (noticeDTO.isNoticeTmp()) {
			noticeDTO.setNoticeNum(null);
			noticeDTO.setNoticeTmp(false);
			noticeDTO.setNoticeTmpChecker(true);
		}
		NoticeDTO result = noticeService.edit(noticeDTO, files, deleteFiles);
		if (noticeDTO.isNoticeTmpChecker()) {
			result = noticeService.deleteTemp(toDelete);
		}
		if (result != null) {
			if (noticeDTO.isNoticeTmpChecker()) {
				model.addAttribute("resultMsg", "게시글이 등록되었습니다.");				
			} else {				
				model.addAttribute("resultMsg", "게시글이 수정되었습니다.");
			}
			
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/notice/" + noticeDTO.getNoticeNum());
			return "common/result";
		} else {
			return null;
		}
	}
	
	@PostMapping("{noticeNum}/delete")
	public String delete(@PathVariable("noticeNum") NoticeDTO noticeDTO, Model model) {
		NoticeDTO result = noticeService.delete(noticeDTO);
		if (result != null) {			
			model.addAttribute("resultMsg", "게시글이 삭제되었습니다.");
			model.addAttribute("resultIcon", "success");
			model.addAttribute("resultUrl", "/notice");
			return "common/result";
		} else {
			return null;
		}
	}
	
	@GetMapping("{attachNum}/download")
	public String download(@PathVariable("attachNum") AttachmentDTO attachmentDTO, Model model) {
		AttachmentDTO result = noticeService.download(attachmentDTO);
		model.addAttribute("file", result);
		model.addAttribute("type", "notice");
		return "fileDownView";
	}
	
	@PostMapping("pinned") @ResponseBody
	public boolean pinned() {
		boolean result = false;
		List<NoticeDTO> resultPinned = noticeService.pinned("");
		if (resultPinned.size() >= 5) {
			result = true;
		}
		return result;
	}
	
}
