package bank.currency;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import bank.currencyRate.CurrencyRate;

/**
 * valuta
 */
@Entity
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "currency_id")
	private Long id;

	@Column(columnDefinition = "CHAR(3)", unique = true)
	@NotBlank(message = "Official code is mandatory")
	private String official_code;

	@Column(unique = true, length = 30)
	@NotBlank(message = "Name is mandatory")
	private String name;

	@Column
	@NotNull
	private boolean dom; // sta ovde treba da pise?

	/*@OneToMany
	@JoinTable(name = "base_currency", joinColumns = @JoinColumn(name = "currency_id"), inverseJoinColumns = @JoinColumn(name = "serial_number"))
	private List<CurrencyRate> baseCurrencyRates; // osnovna valuta*/
	@OneToMany(mappedBy = "baseCurrencyRates")
	private List<CurrencyRate> baseCurrencyRates;

	@OneToMany
	@JoinTable(name = "by_currency", joinColumns = @JoinColumn(name = "currency_id"), inverseJoinColumns = @JoinColumn(name = "serial_number"))
	private List<CurrencyRate> byCurrencyRates;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfficial_code() {
		return official_code;
	}

	public void setOfficial_code(String official_code) {
		this.official_code = official_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDom() {
		return dom;
	}

	public void setDom(boolean dom) {
		this.dom = dom;
	}

	public List<CurrencyRate> getBaseCurrencyRates() {
		return baseCurrencyRates;
	}

	public void setBaseCurrencyRates(List<CurrencyRate> baseCurrencyRates) {
		this.baseCurrencyRates = baseCurrencyRates;
	}

	public List<CurrencyRate> getByCurrencyRates() {
		return byCurrencyRates;
	}

	public void setByCurrencyRates(List<CurrencyRate> byCurrencyRates) {
		this.byCurrencyRates = byCurrencyRates;
	}

}
