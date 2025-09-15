package com.goodee.finals.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.goodee.finals.common.security.CustomAuthenticationFailureHandler;
import com.goodee.finals.common.security.CustomAuthenticationSuccessHandler;
import com.goodee.finals.common.security.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security
		
		// CORS
		.cors((option) -> option.disable())
		
		// CSRF
		.csrf((option) -> option.disable())
		
		// Authorization
		.authorizeHttpRequests((option) -> { option
			// static resources
			.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
			.requestMatchers("/staff/info").authenticated()
			// 임시
			.anyRequest().permitAll()
			;
		})
		
		// Login
		.formLogin((option) -> { option
			.loginPage("/staff/login")
			.usernameParameter("staffCode")
			.passwordParameter("staffPw")
			.successHandler(customAuthenticationSuccessHandler)
			.failureHandler(customAuthenticationFailureHandler)
			;
		})
		
		// Logout
		.logout((option) -> { option
			.logoutUrl("/staff/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessHandler(customLogoutSuccessHandler)
			;
		})
		
		;
		
		return security.build();
	}
}
