package com.packt.cardatabase.domain;

// 인증을 위한 자격 증명을 포함할 간단한 POJO(Palin Old Java Object) 클래스를 추가할 차례 ***

// 이 클래스에는 username 과 password 라는 필드 두개가 있음
// 자격 증명을 데이터베이스에 저장하지는 않으므로 이클래스에는 @Entity 어노테이션은 지정하지 않음

public class AccountCredentials {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
