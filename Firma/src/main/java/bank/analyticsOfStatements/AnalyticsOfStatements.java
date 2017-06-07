package bank.analyticsOfStatements;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * analitika izvoda
 */

@Entity
public class AnalyticsOfStatements {
	
	@Id
	@Column
	private Long itemNumber;
	
	@Column(length = 256)
	private String debtor_originator; //duznik-nalogodavac

	@Column(length = 256)
	private String purposeOfPayment; //svrha placanja
	
	@Column(length = 256)
	private String creditor_recipient; //poverilac-primalac
	
	@Column
	@NotNull
	private Date dateOfReceipt; //datum prijema
	
	@Column
	@NotNull
	private Date currencyDate; //datum valute
	
	
	@Column(length = 18)
	private String debtorAccount; //racun duznika
	
	@Column
	@Max(value = 2)
	private int modelAssigments; //model zaduzenja
	
	@Column(length = 20)
	private String referenceNumberAssigments; //poziv na broj zaduzenja
	
	@Column(length = 18)
	private String accountCreditor; //racun poverioca
	
	@Column
	@Max(value = 2)
	private int modelApproval; //model odobrenja
	
	@Column(length = 20)
	private String referenceNumberCreditor; //poziv na broj odobrenja
	
	@Column
	@NotNull
	private boolean emergency;
	
	@Column(length = 15, precision = 2)
	private float sum;
	
	@Column
	@Max(value = 1)
	private int typeOfMistake; 
	
	@Column(length = 1)
	private String status;

	public Long getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Long itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getDebtor_originator() {
		return debtor_originator;
	}

	public void setDebtor_originator(String debtor_originator) {
		this.debtor_originator = debtor_originator;
	}

	public String getPurposeOfPayment() {
		return purposeOfPayment;
	}

	public void setPurposeOfPayment(String purposeOfPayment) {
		this.purposeOfPayment = purposeOfPayment;
	}

	public String getCreditor_recipient() {
		return creditor_recipient;
	}

	public void setCreditor_recipient(String creditor_recipient) {
		this.creditor_recipient = creditor_recipient;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public Date getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(Date currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getDebtorAccount() {
		return debtorAccount;
	}

	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}

	public int getModelAssigments() {
		return modelAssigments;
	}

	public void setModelAssigments(int modelAssigments) {
		this.modelAssigments = modelAssigments;
	}

	public String getReferenceNumberAssigments() {
		return referenceNumberAssigments;
	}

	public void setReferenceNumberAssigments(String referenceNumberAssigments) {
		this.referenceNumberAssigments = referenceNumberAssigments;
	}

	public String getAccountCreditor() {
		return accountCreditor;
	}

	public void setAccountCreditor(String accountCreditor) {
		this.accountCreditor = accountCreditor;
	}

	public int getModelApproval() {
		return modelApproval;
	}

	public void setModelApproval(int modelApproval) {
		this.modelApproval = modelApproval;
	}

	public String getReferenceNumberCreditor() {
		return referenceNumberCreditor;
	}

	public void setReferenceNumberCreditor(String referenceNumberCreditor) {
		this.referenceNumberCreditor = referenceNumberCreditor;
	}

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public int getTypeOfMistake() {
		return typeOfMistake;
	}

	public void setTypeOfMistake(int typeOfMistake) {
		this.typeOfMistake = typeOfMistake;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
