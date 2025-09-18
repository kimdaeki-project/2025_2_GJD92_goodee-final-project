package com.goodee.finals.messenger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodee.finals.staff.StaffDTO;

@RequestMapping("/msg/**") @Controller
public class MessengerController {
	
	@Autowired
	MessengerService messengerService;
	
	@GetMapping("")
	public String pop() {
		return "messenger/list";
	}
	
	@GetMapping("create")
	public String create(Model model) {
		List<StaffDTO> result = messengerService.getStaff();
		model.addAttribute("staff", result);
		return "messenger/create";
	}
	
	@PostMapping("create")
	public String create(@RequestParam(required = false) List<Integer> addedStaff, ChatRoomDTO chatRoomDTO) {
		ChatRoomDTO result = messengerService.createRoom(addedStaff, chatRoomDTO);
		return null;
	}
	
}
