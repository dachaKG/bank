package bank.paymentType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

/**
 * vrste placanja
 */

@Entity
public class PaymentType {

	//treba promeniti u number, ali ne znam koji tip tu da koristimo
	@Id
	@Column(columnDefinition = "CHAR(3)")
	private String code;
	
	@Column(length = 120)
	@NotBlank
	private String nameOfPaymentType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameOfPaymentType() {
		return nameOfPaymentType;
	}

	public void setNameOfPaymentType(String nameOfPaymentType) {
		this.nameOfPaymentType = nameOfPaymentType;
	}
	
	
	
}
