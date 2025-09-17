package com.goodee.finals.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.goodee.finals.staff.StaffDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NoticeCheckAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private NoticeRepository noticeRepository;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;

		Long noticeNum = Long.parseLong(request.getRequestURI().split("/")[2]);
		int noticeId = noticeRepository.findById(noticeNum).get().getStaffDTO().getStaffCode();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		StaffDTO loginUser = (StaffDTO)auth.getPrincipal();
		int loginId = loginUser.getStaffCode();
		
		if (noticeId == loginId) {
			flag = true;
		} else {
			request.setAttribute("resultMsg", "작성자만 접근가능합니다.");
			request.setAttribute("resultIcon", "warning");
			request.setAttribute("resultUrl", "/notice");
			request.getRequestDispatcher("/WEB-INF/views/common/result.jsp").forward(request, response);
			return flag;
		}
		
		return flag;
	}
	
}
