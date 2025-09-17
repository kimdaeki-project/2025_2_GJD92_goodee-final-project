package com.goodee.finals.common.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.goodee.finals.staff.StaffService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	private StaffService staffService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		String resultMsg = "로그인 중 오류가 발생했습니다.";
		String resultIcon = "warning";
		String resultUrl = "/staff/login";
		
		String staffCode = request.getParameter("staffCode");
		String staffPw = request.getParameter("staffPw");
		
		if (staffCode == null || staffCode.equals("")) {
			resultMsg = "사원코드를 입력해주세요.";
		} else if (staffPw == null || staffPw.equals("")) {
			resultMsg = "비밀번호를 입력해주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			resultMsg = "존재하지 않는 계정입니다.";
		} else if (exception instanceof DisabledException) {
			resultMsg = "비활성화된 계정입니다.";
		} else if (exception instanceof LockedException) {
			resultMsg = "차단된 계정입니다.";
		} else if (exception instanceof BadCredentialsException) {
			Integer loginFailureCount = (Integer) session.getAttribute("loginFailureCount");
			resultMsg = "비밀번호를 잘못 입력하였습니다.";
			
			if (loginFailureCount == null) {
				session.setAttribute("loginFailureCount", 1);
			} else if (loginFailureCount < 4) {
				session.setAttribute("loginFailureCount", loginFailureCount + 1);
			} else {
				resultMsg = "로그인을 5번 실패해 계정이 차단되었습니다.";
				session.removeAttribute("loginFailureCount");
				
				staffService.lockStaff(Integer.valueOf(staffCode));
			}
		}
		
		request.setAttribute("resultMsg", resultMsg);
		request.setAttribute("resultIcon", resultIcon);
		request.setAttribute("resultUrl", resultUrl);
		
		request.getRequestDispatcher("/WEB-INF/views/common/result.jsp").forward(request, response);
	}

}
