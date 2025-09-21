package com.goodee.finals.attend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.goodee.finals.common.security.CustomSessionInformationExpiredStrategy;
import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttendService {

    private final CustomSessionInformationExpiredStrategy customSessionInformationExpiredStrategy;

	@Autowired
	AttendRepository attendRepository;
	
	@Autowired
	StaffRepository staffRepository;

    AttendService(CustomSessionInformationExpiredStrategy customSessionInformationExpiredStrategy) {
        this.customSessionInformationExpiredStrategy = customSessionInformationExpiredStrategy;
    }

	public AttendDTO attendIn(AttendDTO attendDTO) {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        // 오늘 이미 출근했는지 확인
        if (attendRepository.existsByStaffDTOStaffCodeAndAttendDate(staffCode, today)) {
            throw new IllegalStateException("오늘은 이미 출근 기록이 있습니다.");
        }

        StaffDTO staff = staffRepository.findById(staffCode)
                .orElseThrow(() -> new IllegalStateException("직원 정보를 찾을 수 없습니다."));

        attendDTO.setStaffDTO(staff);
        attendDTO.setAttendDate(today);
        attendDTO.setAttendIn(LocalTime.now());

        return attendRepository.save(attendDTO);
	}
	
	public AttendDTO attendOut(AttendDTO attendDTO) {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        AttendDTO attend = attendRepository.findByStaffDTOStaffCodeAndAttendDate(staffCode, today)
                .orElseThrow(() -> new IllegalStateException("출근 기록이 없습니다."));

        if (attend.getAttendOut() != null) {
            throw new IllegalStateException("이미 퇴근 기록이 있습니다. 인사 담당자에게 문의하세요.");
        }

        attend.setAttendOut(LocalTime.now());
        return attendRepository.save(attend);
		
	}
	
	public AttendDTO findAttend() {
		Integer staffCode = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        LocalDate today = LocalDate.now();

        return attendRepository.findByStaffDTOStaffCodeAndAttendDate(staffCode, today)
                .orElse(null); // 있으면 AttendDTO, 없으면 null
	}
	
	public List<AttendDTO> getMonthlyAttendances(Integer staffCode, int year, int month) {
        return attendRepository.findMonthlyAttendances(year, month, staffCode);
    }
	
}
