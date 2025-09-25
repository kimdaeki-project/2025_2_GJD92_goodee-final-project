package com.goodee.finals.alert;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

@Service
public class AlertService {
	
	@Autowired
	AlertRepository alertRepository;
	
	@Autowired
	StaffRepository staffRepository;

	public AlertDTO saveAlert(AlertDTO alertDTO) {
		Optional<StaffDTO> staffDTO = staffRepository.findById(alertDTO.getStaffCodeToDb());
		alertDTO.setStaffDTO(staffDTO.get());
		AlertDTO result = alertRepository.save(alertDTO);
		return result;
	}

	public List<AlertDTO> getAlerts(AlertDTO alertDTO) {
		List<AlertDTO> result = alertRepository.findAllByStaffDTOStaffCodeOrderByAlertNumAsc(alertDTO.getStaffCodeToDb());
		return result;
	}

	public AlertDTO deleteAlert(AlertDTO alertDTO) {
		Optional<AlertDTO> alertFromDb = alertRepository.findById(alertDTO.getAlertNumToDelete());
		alertFromDb.get().setAlertDelete(true);
		AlertDTO result = alertRepository.save(alertFromDb.get());
		return result;
	}

	public AlertDTO getNewAlert(AlertDTO alertDTO) {
		AlertDTO result = alertRepository.findFirstByStaffDTOStaffCodeOrderByAlertNumDesc(alertDTO.getStaffCodeToDb());
		return result;
	}
	
}
