package com.packt.cardatabase.domain;

import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.mapping.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})//직렬화 프로세스 중에 cars 필드를 무시하게 하는것
public class Owner {

	//Car 테이블과 인대가 관계에 있는 Owner라는 새테이블을 추가
	//일대다 관계라는 것은 소유자 한명이 자동차 여러대를 가질 수 있지만, 한 차의 소유자는 한면이라는 뜻임
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long ownerid;
	private String firstname, lastname;
	
	public Owner(){}
	
	public Owner(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	@JsonIgnore
	// Owner 엔티티 클래스에서는 @OneToMany 어노테이션으로 관계를 정의 함
	// 소유자는 자동차 여러 대를 가질 수 있으므로 필드의 형식은 List<Car> 임
	//@OneTomany 
	// cascade 특성은 삭제 또는 업데이트 시 연속 효과가 적용되는 방법을 지정 ALL로 설정 하면 모든 작업이 연속 적용됨
	// 소유자를 삭제하면 그 소유자와 연결된 모든 자동차도 함꼐 삭제됨
	// mappedBy="owner" 특성 설정은 Car 클래스에 있는 owner 필드가 이 관계의 기본 키임을 지정함
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner") 
	private List<Car> cars;
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(name="car_owner", 
//			joinColumns = { @JoinColumn(name="ownerid")},
//			inverseJoinColumns = { @JoinColumn(name="`id`")})
//	private Set<Car> cars = new HashSet<Car>();
	
	
	
	
	
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

//	public Set<Car> getCars() {
//		return cars;
//	}

//	public void setCars(Set<Car> cars) {
//		this.cars = cars;
//	}

	public long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(long ownerid) {
		this.ownerid = ownerid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	

}
