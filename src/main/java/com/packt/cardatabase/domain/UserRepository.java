package com.packt.cardatabase.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username); // 이메서드는 인증 프로세스 중에 데이터베이스에서 user를 찾는 데 사용된다. 메서드는 null 예외를 방지하기 위해 Optional을 반환 함

}
