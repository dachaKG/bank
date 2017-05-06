package bank.dailyAccountBalance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * dnevno stanje racuna
 */

@Entity
public class DailyAccountBalance {

	@Id
	@Column
	private Long numberOfStatements; //broj izvoda
	
	@Column
	@NotNull
	private Date trafficDate; //datum prometa
	
	@Column(length = 15, precision = 2)
	@NotBlank
	private float previousState;  // prethodno stanje
	
	@Column(length = 15, precision = 2)
	@NotBlank
	private float trafficToBenefit; //promet u korist
	
	@Column(length = 15, precision = 2)
	@NotBlank
	private float trafficToTheBurden; //promet na teret
	
	@Column(length = 15, precision = 2)
	@NotBlank
	private float newState;

	public Long getNumberOfStatements() {
		return numberOfStatements;
	}

	public void setNumberOfStatements(Long numberOfStatements) {
		this.numberOfStatements = numberOfStatements;
	}

	public Date getTrafficDate() {
		return trafficDate;
	}

	public void setTrafficDate(Date trafficDate) {
		this.trafficDate = trafficDate;
	}

	public float getPreviousState() {
		return previousState;
	}

	public void setPreviousState(float previousState) {
		this.previousState = previousState;
	}

	public float getTrafficToBenefit() {
		return trafficToBenefit;
	}

	public void setTrafficToBenefit(float trafficToBenefit) {
		this.trafficToBenefit = trafficToBenefit;
	}

	public float getTrafficToTheBurden() {
		return trafficToTheBurden;
	}

	public void setTrafficToTheBurden(float trafficToTheBurden) {
		this.trafficToTheBurden = trafficToTheBurden;
	}

	public float getNewState() {
		return newState;
	}

	public void setNewState(float newState) {
		this.newState = newState;
	}
	
	
	
}
