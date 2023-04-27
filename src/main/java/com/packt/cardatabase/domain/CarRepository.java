package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="cars") // 엔드 포인트 경로 변경 
public interface CarRepository extends CrudRepository<Car, Long> { //CRUD 작업을 위한 인터페이스 
	// 브랜드로 자동차를 검색
	List<Car> findByBrand(@Param("brand")String brand);
	
	// 색상으로 자동차를 검색
	List<Car> findByColor(@Param("color")String color);
	
	
	// CrudRepository 를 확장 하는 PagingAndSortingRepository 도 있으며 이를 통해 페이지 매김과 정렬을 적용하고 엔티티를 검색하는 메서드를 제공함
	// 대규모 결과 집합에서 모든 데이터를 반환할 필요가 없기 때문에 대규모 데이터를 처리할 때 좋음
	// 또한 이용하기 편한 방식으로 데이터를 정렬 할 수 있음 
//	public interface CarRepository extends PagingAndSortingRepository<Car, Long> { 
	
	
	//스프링 데이터 리포지터리에 쿼리를 정의 할 수 있음
	//쿼리는 접두사(findby) 로 시작해야 하고, 그다음에는 쿼리에 이용할 엔티티 클래스 필드가 나와야함
	// 브랜드로 자동차를 검색
//	List<Car> findbyBrand(String brand);
	
	// 색상으로 자동차를 검색
//	List<Car> findByColor(String color);
	
	// 연도로 자동차를 검색
//	List<Car> findByYear(String year);
	
	//브랜드와 모델로 자동차를 검색 , By 키워드 다음에 And 및 Or 키워드를 붙여 여러 필드를 지정할 수 있음
//	List<Car> findByBrandAndModel(String brand, String model);
	//브랜드나 색상으로 자동차를 검색
//	List<Car> findByBrandOrColor(String brand);
	
	//쿼리를 정렬하려면 쿼리 메서드에서 OrderBy 키워드를 이용함 
	//브랜드로 자동차를 검색하고 연도로 정렬 
//	List<Car> findByBrandOrderByYearAsc(String brand);
	
	// @Query 어노테이션을 이용하면 SQL 문으로 쿼리를 만들 수도 있음
	// SQL 문을 이용해 브랜드로 자동차를 검색 
//	@Query("select c from Car c where c.brand = ?1")
//	List<Car> findByBrand(String brand);
	
	// @Query 어노테이션에는 like 같은 고급 식을 지정할 수 있음
	// SQL 문을 이용해 브랜드로 자동차를 검색
//	@Query("select c from Car c where c.brand like %?1")
//	List<Car> findByBrandEndWith(String brand);

}
