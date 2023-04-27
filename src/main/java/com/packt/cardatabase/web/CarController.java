package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
//허용할 HTTP 방식을 지정 하려면 @RequestMapping(value="/cars", method=GET)형식을 이용하면 됨

//이 경우 메서드는 /cars 엔드포인트에서 GET 요청만 치러함 또는  @GetMapping 어노테이션을 이용하면 GET 요청만 getCars() 메서드로 매핑됨

//이처럼 각기 다른 HTTP 방식을 위한 @PostMapping, @DeleteMapping 등의 어노테이션이 있음

//@Restcontroller 덕분에 데이터는 응답에 JSON 형식으로 직렬화 됨
@RestController  // 이 클래스가 RESTful 웹 서비스의 컨트롤러가 되도록 지정, 모든 HTTP 방식의 요청(GET, PUT, POST 등)을 처리함
public class CarController {
	@Autowired
	private CarRepository repository; // 데이터 베이스에서 자동차를 반환 할 수 있게 하려면 CarRepository를 컨트롤러에 주입해야 함 그러면 리포지터리가 제공하는 findAll()메서드로 모든자동차를 검색 할 수 있음
	
	@RequestMapping("/cars") // 메서드가 매핑되는 엔드포인트를 지정함 , 사용자가 /cars 엔드포인트로 이동하면 getCars() 메서드가 실행 됨 
	public Iterable<Car> getCars() {// getCars() 메서드는 모든 자동차 객체를 반환 하며 이는 jackson 라이브러리에의해 JSON 객체로 마샬링 됨
		// 자동차를 검색하고 반환
		return repository.findAll();
	}
}
