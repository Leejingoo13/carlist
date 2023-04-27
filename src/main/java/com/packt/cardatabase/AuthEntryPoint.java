package com.packt.cardatabase;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// 틀린 암호로 로그인하려고 하면 추가 정보 없이 403 Forbidden 상태가 반환되도록
// 스프링 시큐리티에는 예외를 처리하는 데 이용할 수 있은 AuthenticationEntryPoint 인터페이스가 있음

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint{
	@Override
	public void commence( // 이 클래스에 예외를 메게변수로 받는 commence 메서드를 구현함
			// 예외가 발생하면 응답 상태를 401 Unauthorized 로 설정하고 응답 본문에 예외 메시지를 기록함
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException)
	
			throws IOException, ServletException{
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		writer.println("Error: " + authException.getMessage());
		
	}
}
