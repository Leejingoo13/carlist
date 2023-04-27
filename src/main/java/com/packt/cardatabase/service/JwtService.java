package com.packt.cardatabase.service;


import java.security.Key;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// 서명된 JWT를 생성하고 검증하는 클래스 ***

@Component
public class JwtService {
	
	static final long EXPIRATIONTIME = 86400000; // 1일을 밀리초로 계산한값 , 토큰의 만료 시간을 밀리초 단위로 정읜함 
	static final String PREFIX = "Bearer"; // 토큰의 접두사를 정의 하며, 일반적으로 Bearer 스키마를 이용함
	// 비밀 키 생성 , 시연 용도로만 이용해야함 
	// 애플리케이션 구성에서 읽을 수 있음 
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 비밀키는 jjwt 라이브러리의 secretKeyFor 메서드로 생성함
	// 시연 용도로는 이것으로 충분하지만 운영 환경에서는 애플리케이션의 구성에서 비밀키를 읽어야 함
	//서명된 JWT 토큰생성
	public String getToken(String username) {// getToken 메서드는 토큰을 생성하고 반환함
		String token = Jwts.builder()// parserBuilder 메서드를 이용해 JwtParserBuilder 인스턴스를 생성 
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis()
						+ EXPIRATIONTIME))
				.signWith(key)
				.compact();

		return token;
	}
	
	public String getAuthUser(HttpServletRequest request) {// 응답의 Authoriztion 헤더에서 토큰을 가져옴 그다음 
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		
		if (token != null) {
			String user = Jwts.parserBuilder()//jjwt 라이브러리의 parserBuilder 메서드를 이용해 JwtParserBuilder 인스턴스를 생성함
					.setSigningKey(key)// setSigningkey 메서드는 토큰 검증을 위한 비밀 키를 지정함 
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody()
					.getSubject(); // 마지막으로 getSubject 메서드로 사용자 이름을 얻음
			
			if(user != null)
				return user;
		}
		return null;
	}
}
