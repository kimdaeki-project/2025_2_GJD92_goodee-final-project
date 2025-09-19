package com.goodee.finals.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.goodee.finals.staff.StaffDTO;
import com.goodee.finals.staff.StaffService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CheckStaffEnableInterceptor implements HandlerInterceptor {
	@Autowired
	private StaffService staffService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getName().equals("anonymousUser")) {
			StaffDTO staffDTO = (StaffDTO) authentication.getPrincipal();
			staffDTO = staffService.getStaff(staffDTO.getStaffCode());
			
			if (!staffDTO.getStaffEnabled()) {
				request.setAttribute("resultMsg", "계정이 비활성화되어 로그아웃합니다.");
				request.setAttribute("resultIcon", "warning");
				request.setAttribute("resultUrl", "/staff/logout");
				
				request.getRequestDispatcher("/WEB-INF/views/common/result.jsp").forward(request, response);
				return false;
			}
		}
		
		return true;
	}

}
