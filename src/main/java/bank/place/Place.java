package bank.place;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

import bank.country.Country;

@Entity
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	Long id;
	
	@Column(columnDefinition = "CHAR(5)")
	@NotBlank(message = "PTT number is mandatory")
	String pttNumber;
	
	@Column(length = 50)
	@NotBlank(message = "Name is mandatory")
	String name;
	
	@ManyToOne
	private Country country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPttNumber() {
		return pttNumber;
	}

	public void setPttNumber(String pttNumber) {
		this.pttNumber = pttNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	

}
