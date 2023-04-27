package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

// 소유자 CRUD 작업을 테스트할 단위 테스트를 만들어 봄
@DataJpaTest // H2 하이버네이트 데이터베이스와 스프링 데이터가 자동으로 테스트에 맞게 구성 됨
public class OwnerRepositoryTest {
	@Autowired
	private OwnerRepository repository;
	
	@Test // 새소유자를 추가하는 기능을 테스트하는 테스트 케이스를 만들어봄
	void saveOwner() { // 새 Owner 객채를 생성하고 save 메서드를 이용해 데이터베이스에 저장함 그런다음 소유자가 검색되는지 확인
		repository.save(new Owner("Lucy", "Smith"));//
		assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();// OwnerRepository.java 쿼리를 추가함 
	}
	
	@Test // 소유자를 삭제하는 기능을 테스트함
	void deleteOwner() {// 새 owner 객체를 생성하고 데이터 베이스에 저장함 
		repository.save(new Owner("Lisa", "Morrison"));
		repository.deleteAll(); // 그런 다음 데이터베이스에서 모든 소유자를 삭제하면 count()메서드가 0을 반환함
		assertThat(repository.count()).isEqualTo(0);
	}

}
