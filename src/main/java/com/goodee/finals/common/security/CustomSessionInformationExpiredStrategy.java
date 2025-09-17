package com.goodee.finals.common.security;

import java.io.IOException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		HttpServletRequest request = event.getRequest();
		
		request.setAttribute("resultMsg", "다른 세션으로부터의 접속이 감지되어 로그아웃합니다.");
		request.setAttribute("resultIcon", "warning");
		request.setAttribute("resultUrl", "/");
		
		request.getRequestDispatcher("/WEB-INF/views/common/result.jsp").forward(request, event.getResponse());
	}

}
