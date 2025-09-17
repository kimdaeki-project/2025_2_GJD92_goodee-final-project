package com.goodee.finals.lost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/lost/**")
@Slf4j
public class LostController {

	@Autowired
	private LostService lostService;
	
	@GetMapping("")
	public String getLostList(@PageableDefault(size = 10, sort = "lost_num", direction = Direction.DESC) Pageable pageable, String search, Model model) {
		if (search == null) search = "";
		
		Page<LostDTO> lostList = lostService.getLostSearchList(search, pageable);
		
		model.addAttribute("lostList", lostList);
		model.addAttribute("search", search);
		
		long totalLost = lostService.getTotalLost();
		model.addAttribute("totalLost", totalLost);
		
		return "lost/list";
	}
	
	@GetMapping("{lostNum}")
	public String getLostDetail(@PathVariable Long lostNum, Model model) {
		LostDTO lostDTO = lostService.getLost(lostNum);
		model.addAttribute("lostDTO", lostDTO);
		
		return "lost/detail";
	}
	
	@GetMapping("write")
	public String write() throws Exception {
		return "lost/write";
	}
	
	@PostMapping("write")
	public String write(LostDTO lostDTO, MultipartFile attach, Model model) throws Exception {
		LostDTO result = lostService.write(lostDTO, attach);
		
		String resultMsg = "분실물 등록 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		if (result != null) {
			resultMsg = "분실물을 성공적으로 등록했습니다.";
			resultIcon = "success";
			String resultUrl = "/lost";
			model.addAttribute("resultUrl", resultUrl);
		}
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
	@GetMapping("{lostNum}/update")
	public String getLostUpdate(@PathVariable Long lostNum, Model model) {
		LostDTO lostDTO = lostService.getLost(lostNum);
		model.addAttribute("lostDTO", lostDTO);
		
		return "lost/write";
	}
	
	@PostMapping("{lostNum}/update")
	public String postLostUpdate(LostDTO lostDTO, MultipartFile attach, Model model) {
//		boolean result = lostService.updateLost(lostDTO, attach);
		
		String resultMsg = "분실물 수정 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		
		
		
		model.addAttribute("resultMsg", resultMsg);
		model.addAttribute("resultIcon", resultIcon);
		
		return "common/result";
	}
	
}
