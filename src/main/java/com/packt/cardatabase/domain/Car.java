package com.packt.cardatabase.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Car {
	@Id // 기본키 정의 , 여러 특성을 지정하면 복합 기본 키를 만들 수 있음
	@GeneratedValue(strategy = GenerationType. AUTO) // 데이터베이스가 자동으로 ID를 생성하도록 지정함 
	private long id;
	private String brand, model, color, registerNumber;
	@Column(name="`year`")
	private int year;
	private int price;
	
	public Car() {}
	
	public Car(String brand, String model, String color, 
			String registerNumber, int year, int price, Owner owner) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.year = year;
		this.price = price;
		this.owner = owner; // CommandLineRunner로 데이터베이스에 여러 소유자를 추가할 수 있음 Car 엔티티 클래스 생성자도 수정하고 여기에 owner 객체를 추가
	}
	
	// 일대다 관계를 추가하려면 @ManyToOne 및 @OneToMany 어노테이션을 이용함
	// 기본 키가 있는 Car 엔티티 클래스에서는 @ManyToOne 어노테이션으로 관계를 정의해야함
	// 소유자 필드에 대한 getter setter도 추가해야함
	//모든 연관관계에는 FetchThype.LAZY를 이용하는 것이 좋음 (대다(toMany) 관계에는 FetchType.LAZY 가 기본값이므로 정의할 필요가없지만 대일(toOne) 관계에는 정의해야함)
	
	@ManyToOne(fetch=FetchType.LAZY) // fetchType 은 데이터베이스에서 데이터를 검색하는 전략을 정의 , 지정 가능한 값은 즉시검색을 의미하는 EAGER, 지연검색은 LAZY
	@JoinColumn(name="owner") // 지연검색은 데이터베이스에서 소유자를 검색하면 필요할 떄에 해당 소유자와 연관된 모든 자동차를 검색한다는 뜻 
	private Owner owner;

//	@ManyToMany(mappedBy = "cars")
//	private Set<Owner> owners = new HashSet<Owner>();
	
	

//	public Set<Owner> getOwners() {
//		return owners;
//	}

//	public void setOwners(Set<Owner> owners) {
//		this.owners = owners;
//	}
	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	// 엔티티클래스를 만들고 JPA가 엔티티 클래스를 참조해 데이터베이스 테이블을 생성하는 방법 
}
	

