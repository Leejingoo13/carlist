package com.packt.cardatabase;



import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner ;
import com.packt.cardatabase.domain.OwnerRepository;
import com.packt.cardatabase.domain.User;
import com.packt.cardatabase.domain.UserRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner { // 인터페이스를 이용하면 애플리케이션이 완전히 시작되기전에 추가 코드를 실행 수 있어 데이터를 준비하기에 적합
	// 스프링 부트 애플리케이션의 main클래스는 CommandLineRunner 인터페이스를 구현
	private static final Logger logger =
			LoggerFactory.getLogger(CardatabaseApplication.class);
	// 새로운 자동차 객체를 데이터베이스에 저장할 수 있도록 자동차 리포지터리를 main 클래스에 주입할 차례
	
	@Autowired
	private UserRepository urepository; // CommandLineRunner 인터페이스를 이용해 두 테스트 사용자를 데이터베이스에 저장함
	
	@Autowired // 의존성 주입을 솰성화하는 데는 @AutoWired 어노테이션을 이용함 , 그러면 의존성을 객체로 전달할 수 있게 됨 
	// 리포지터리 클래스를 주입한 다음에는 run 메서드에서 CRUD 메서드를 이용할 수 있게 됨
	private CarRepository repository;
	
	@Autowired
	private OwnerRepository oreRepository;
	
	public static void main(String[] args) {
		
		SpringApplication.run(CardatabaseApplication.class, args);
	}
	
	@Override // save 메서드로 여러 자동차 레코드를 데이터베이스에 추가함
	public void run(String... args) throws Exception {
		// 소유자를 저장하고 소유자와 자동차를 연결하도록 run 메서드를 수정해야함 
		// Add owner objects and save these to db 
		Owner owner1 = new Owner("John", "Johnson");
		Owner owner2 = new Owner("Mary", "Robinson");
		oreRepository.saveAll(Arrays.asList(owner1, owner2));
		
		
		// Add car object and link to owners and save these to db
		// 자동차 객체를 추가하고 소유자와 연결한 후 데이터베이스에 저장
		Car car1 = new Car("Ford", "Mustang", "Red", 
				"ADF-1121", 2021, 59000, owner1);
		Car car2 = new Car("Nissan", "Leaf", "White", 
				"SSJ-3002", 2019, 29000, owner2);
		Car car3 = new Car("Toyota", "Prius", "Silver", 
				"KKO-0212", 2020, 39000, owner2);
		repository.saveAll(Arrays.asList(car1, car2, car3));
           
		
		// 모든 자동차를 가져와 콘솔에 로깅 
		// findAll() 메서드로 데이터베이스에서 모든 자동차 레코드를 검색하고 로거를 통해 콘솔에 출력함 
		for (Car car : repository.findAll()) {
			logger.info(car.getBrand()+ " " + car.getModel());
		}
		
	      urepository.save(new User("user", 
	              "$2a$10$Gim0vtvhdtA72jc.rdFPa.uie8AfgmmH3LcXVszNsDOt4tCmfF2jS","USER"));
	        urepository.save(new User("admin", 
	              "$2a$10$Uy9CFWetVu7K1GLy5TVJeOZez34.g3uZtyZD/Yh1OVK9n0ruUvEuG", "ADMIN"));
	}
	

}
