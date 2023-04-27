package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.AccountCredentials;
import com.packt.cardatabase.service.JwtService;


// 로그인을 위한 controller 클래스를 구현**
// 이 컨트롤러를 이용하여 로그인하려면 요청 본문에 사용자 이름과 암호를 넣고 POST 방식으로 /login 엔드포인트를 호출해야함**

@RestController
public class LoginController {
	
	@Autowired
	private JwtService jwtService; // 이 클래스에는 로그인 성공 시 서명된 JWT 를 생성하는 데 필요한 JwtService 인스턴스를 주입해야함
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	// 로그인 기능을 처리하는 getToken 메서드는 다음과 같이 구현함 **
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
		//토큰을 생성하고 응답의 Authorization 헤더로 보냄		
		UsernamePasswordAuthenticationToken creds =
				new UsernamePasswordAuthenticationToken(
				credentials.getUsername(),
				credentials.getPassword());
				
		Authentication auth = authenticationManager.authenticate(creds);
		
		// 토큰 생성
		String jwts = jwtService.getToken(auth.getName());
		
		// 생성된 토큰으로 응답을 생성
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.build();
		
	}
}
