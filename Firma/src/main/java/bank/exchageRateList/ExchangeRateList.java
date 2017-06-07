package bank.exchageRateList;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * kursna lista
 */
@Entity
public class ExchangeRateList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	@NotNull
	private Date date;
	
	@Column
	private int numberOfExchangeRateList; // broj kursne liste
	
	@Column
	private Date appliedBy; //primenjuje se od

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberOfExchangeRateList() {
		return numberOfExchangeRateList;
	}

	public void setNumberOfExchangeRateList(int numberOfExchangeRateList) {
		this.numberOfExchangeRateList = numberOfExchangeRateList;
	}

	public Date getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(Date appliedBy) {
		this.appliedBy = appliedBy;
	}
	
	
	
}
