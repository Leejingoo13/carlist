package com.packt.cardatabase.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.packt.cardatabase.domain.User;
import com.packt.cardatabase.domain.UserRepository;

//이클래스는 스프링 시큐리티의 사용자 인증과 권한 부여에 사용됨***

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repository; // 스프링 시큐리티가 인증을 처리할 떄 데이터베이스에서 사용자를 검색하는 데 필요한 UserRepository 클래스 를 UserDetailsServiceImpl 클래스에 주입 해야함
	
	
	@Override
	public UserDetails loadUserByUsername(String username) // 이 메서드는 인증이 필요한 UserDetails 객체를 반환함
		throws UsernameNotFoundException {
		Optional<User> user = repository.findByUsername(username); // findByUsername 메서드는 Optional을 반환 하므로 isPresent()메서드로 사용자가 존재하는지 확인할 수 있음
		UserBuilder builder = null; // 인증할 사용자를 만드는 데는 UserBuilser 클래스가 사용됨 
		if (user.isPresent()) {
				User currentUser = user.get();
				builder =
						org.springframework.security.core.userdetails.
						User.withUsername(username);
				builder.password(currentUser.getPassword());
				builder.roles(currentUser.getRole());			
		} else {
			throw new UsernameNotFoundException("User not found.");// 사용자가 존재하지 않으면 이 메서드가 예외를 발생시킴 
		}
		
		return builder.build();
	}
}
