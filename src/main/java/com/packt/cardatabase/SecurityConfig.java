package com.packt.cardatabase;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.packt.cardatabase.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity //이 클래스에서 기본 웹 보안 구성을 비활성화 하고 자체구성을 정의할 수 있음
public class SecurityConfig extends WebSecurityConfigurerAdapter { //보안 구성 클래스에서 스프링 시큐리티가 인메모리 사용자 대신 데이터베이스의 사용자를 사용하도록 정의해야함
	 
	 @Autowired
	 private AuthenticationFilter authenticationFilter; // 필터 클래스를 스프링 시큐리티 구성에 추가 
	
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	 
	 @Autowired
	 private AuthEntryPoint exceptionHandler; // 다음으로 예외 처리를 위해 스프링 시큐리티를 구성할 차례 ***
	 
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { //데이터베이스에서 사용자를 활성화하기 위해 configureGlobal 메서드를 추가해야함
		 auth.userDetailsService(userDetailsService)// 암호는 일반 텍스트로 데이터베이스에 저장하면 안됨 따라서, configureGlobal 메서드에 암호 해싱 알고리즘을 정의해함
		 	.passwordEncoder(new BCryptPasswordEncoder());// 인증 프로세스를 수행하는동안 해시된 암호를 인코딩하는 스프링 시큐리티의 BCryptPasswordEncoder클래스를 이용하면 이기능을 쉽게 구현 가능
	 }// 암호를 데이터베이스에 저장하기전에 BCrypt로 암호화하는 작업
	 
	 
	 // 스프링 시큐리티 기능 구성해야함
	 // 백엔드 보호!! ****
	 @Override
	 protected void configure(HttpSecurity http) throws Exception { // configure 메서드는 보호되는 경로와 그렇지 않은 경로를 정의함 // 이 메서드에서 /login 엔드포인트에대한 POST 요청은 인증 없이도 허용되지만 다른 모든 엔드포인트에 대한 POST 요청은 인증이 필요하도록 지정함
		 http.csrf().disable().cors().and()//.authorizeRequests().anyRequest().permitAll(); // 모든 사용자가 모든 엔드포인트로 접근할 수 있게 함.
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		 .authorizeRequests()// /login 엔드포인트에 대한 POST 요청은 보호되지 안흠
		 .antMatchers(HttpMethod.POST,"/login").permitAll()// 다른 모든 요청은 보호됨
		 .anyRequest().authenticated().and()
		 .exceptionHandling()
		 .authenticationEntryPoint(exceptionHandler).and()
		 .addFilterBefore(authenticationFilter,
				 UsernamePasswordAuthenticationFilter.class);// 전체 워크 플로를 테스트할 준비 끝!!
				
	 }
	 
	 
	 // 또한 AuthenticationManager 를 LoginController 클래스의에 주입했으므로 다음 코드를 SecurityConfig 클래스에 추가해야함
	 @Bean
	 public AuthenticationManager getAuthenticationManager() throws Exception {
		 return authenticationManager();
	 }
	 // 로그인 단계 완료!!@!
	 
	 // 클래스에 전역 CORS 필터 추가
	 // 보안 구성 클래스에 교차 출처 리소스 공유를 위한 CORS(Cross- Origin Resource Sharing) 필터도 추가
	 // 이는 다른 출처에서 요청을 보내는 프런트엔드에 필요함
	 // CORS 필터는 요청을 가로채고 해당 요청이 교차 출처에서 환인되면 적절한 헤더를 요청에 추가함
	 // 이를 위해 스프링 시큐리티의 CorsConfigurationSource 인터페이스를 이용함 
	 // 여기서는 모든 출처의 HTTP 방식과 헤더를 허용함 허용되는 출처, 방식 , 헤더 목록을 정의하면 정의를 더 세분화할 수 있음
	 @Bean
	 CorsConfigurationSource corsConfigurationSource() {
		 UrlBasedCorsConfigurationSource source = 
				 new UrlBasedCorsConfigurationSource();
		 
		 CorsConfiguration config = new CorsConfiguration();
		 config.setAllowedOrigins(Arrays.asList("*"));
		 config.setAllowedMethods(Arrays.asList("*"));
		 config.setAllowedHeaders(Arrays.asList("*"));
		 config.setAllowCredentials(false);
		 config.applyPermitDefaultValues();
		 
		 source.registerCorsConfiguration("/**", config);
		 return source;
	 }
}
