package bank.interbankTransfer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * medjubankarski prenos
 */

@Entity
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long idMessage;

	@Column(columnDefinition = "CHAR(5)")
	@NotBlank
	private String typeOfMessage;

	@Column
	@NotNull
	private Date date;

	@Column
	@NotBlank
	private float sum;

	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}

	public String getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

}
