package com.goodee.finals.notice;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NoticeCheckLoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean flag = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
			flag = true;
		}
		else {
			request.setAttribute("msg", "먼저 로그인을 진행해주세요.");
			request.setAttribute("url", "/staff/login");
			request.getRequestDispatcher("/WEB-INF/views/notice/result.jsp").forward(request, response);
			return flag;
		}
		return flag;
	}

}
