package com.goodee.finals.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security
		
		// Authorization
		.authorizeHttpRequests(auth -> {
			auth
					// static resources
					.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
					// 임시
					.anyRequest().permitAll()
					;
		})
		
		;
		
		return security.build();
	}
}
