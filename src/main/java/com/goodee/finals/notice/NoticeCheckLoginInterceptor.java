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
//			resultMsg : 결과창에 띄울 메시지
//			resultIcon : 결과창에 뜨는 아이콘 (success, warning, info, error, question)
//			resultUrl : 결과창이 뜬 후에 이동할 URL (절대경로)
			request.setAttribute("resultMsg", "먼저 로그인을 진행해주세요.");
			request.setAttribute("resultIcon", "warning");
			request.setAttribute("resultUrl", "/staff/login");
			request.getRequestDispatcher("/WEB-INF/views/common/result.jsp").forward(request, response);
			return flag;
		}
		return flag;
	}

}
