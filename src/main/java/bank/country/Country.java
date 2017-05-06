package bank.country;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import bank.currency.Currency;

@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "country_id")
	private Long id;

	@Column(unique = true, columnDefinition = "CHAR(3)")
	@NotBlank(message = "Country code is mandatory")
	private int country_code;

	@Column(length = 40, unique = true)
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@OneToMany
	@JoinTable(name = "national_currency", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "currency_id"))
	private List<Currency> currencies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCountry_code() {
		return country_code;
	}

	public void setCountry_code(int country_code) {
		this.country_code = country_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	
	

}
