package com.goodee.finals.alert;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller @RequestMapping("/alert/**")
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	
	@PostMapping("save") @ResponseBody
	public AlertDTO saveAlert(@RequestBody AlertDTO alertDTO) {
		AlertDTO result = alertService.saveAlert(alertDTO);
		if (result != null) return result;
		else return null;
	}
	
	@PostMapping("list") @ResponseBody
	public List<AlertDTO> getAlerts(@RequestBody AlertDTO alertDTO) {
		List<AlertDTO> result = alertService.getAlerts(alertDTO);
		return result;
	}
	
	@PostMapping("delete") @ResponseBody
	public boolean deleteAlert(@RequestBody AlertDTO alertDTO) {
		AlertDTO result = alertService.deleteAlert(alertDTO);
		if (result != null) return true;
		else return false;
	}
	
	@PostMapping("new") @ResponseBody
	public AlertDTO getNewAlert(@RequestBody AlertDTO alertDTO) {
		AlertDTO result = alertService.getNewAlert(alertDTO);
		if (result != null) return result;
		else return null;
	}	
	
}
