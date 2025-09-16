package com.goodee.finals.common.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String rememberId = request.getParameter("rememberId");
		log.info("{}", rememberId);
		
		if (rememberId != null) {
			Cookie cookie = new Cookie("rememberId", request.getParameter("staffCode"));
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 365);
			cookie.setHttpOnly(true);
			log.info("난 쿠키 줬음");
			
			response.addCookie(cookie);
		} 
		
		if (rememberId == null) {
			Cookie cookie = new Cookie("rememberId", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			
			response.addCookie(cookie);
		}
		
		response.sendRedirect("/");
	}

}
