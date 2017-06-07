package bank.currencyRate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

import bank.currency.Currency;

/**
 * Kurs u valuti
 */

@Entity
public class CurrencyRate {

	@Id
	@Column(length = 9)
	private float serialNumber;
	
	@Column(length = 9, precision = 4)
	@NotBlank
	private float buyingExchangeRate;
	
	@Column(length = 9, precision = 4)
	@NotBlank
	private float middleExchangeRate;
	
	@Column(length = 9, precision = 4)
	@NotBlank
	private float sellingExchangeRate;
	
	@ManyToOne
	@JoinTable(name = "by_currency", joinColumns = @JoinColumn(name = "currency_id"), inverseJoinColumns = @JoinColumn(name = "serial_number"))
	private Currency baseCurrencyRates;

	public float getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(float serialNumber) {
		this.serialNumber = serialNumber;
	}

	public float getBuyingExchangeRate() {
		return buyingExchangeRate;
	}

	public void setBuyingExchangeRate(float buyingExchangeRate) {
		this.buyingExchangeRate = buyingExchangeRate;
	}

	public float getMiddleExchangeRate() {
		return middleExchangeRate;
	}

	public void setMiddleExchangeRate(float middleExchangeRate) {
		this.middleExchangeRate = middleExchangeRate;
	}

	public float getSellingExchangeRate() {
		return sellingExchangeRate;
	}

	public void setSellingExchangeRate(float sellingExchangeRate) {
		this.sellingExchangeRate = sellingExchangeRate;
	}
	
	
	
}
