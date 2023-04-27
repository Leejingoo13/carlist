package com.packt.cardatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// 사용자를 데이터베이스에 저장하려면 사용자 엔티티 클래스와 리포지터리를 작성해야함
// 암호는 데이터베이스에 일반 텍스트 형식으로 정장해서는 안 된다.
// 스프링 시큐리티는 암호를 해시하는 데 이용할 수 있는 여러 해싱 알고리즘을 제공함 
@Table(name="user")
@Entity
public class User {
	// 모든 필드를 nullable로 설정하고 username에 @Column 어노테이션으로 고유해야 한다는 것을 지정함
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false , updatable=false)
	private Long id; 
	
	@Column(nullable=false , unique=true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String role;
	
	public User() {}
	
	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
