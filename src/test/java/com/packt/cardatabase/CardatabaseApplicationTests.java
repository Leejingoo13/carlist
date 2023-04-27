package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.packt.cardatabase.web.CarController;

@SpringBootTest
class CardatabaseApplicationTests {
	@Autowired
	private CarController controller;
	
	@Test
	@DisplayName("First example test case") // 테스트 케이스에 자세한 이름을 지정할 수 있음 
	void contextLoads() { // 테스트를 추가할 위치 
		assertThat(controller).isNotNull(); // 컨트롤러의 인스턴스가 정상적으로 생성되고 주입됐는지 확인 
	}

}
