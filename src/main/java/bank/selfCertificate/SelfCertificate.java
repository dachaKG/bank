package bank.selfCertificate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class SelfCertificate implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public SelfCertificate(Long id, String alias, Date startDate, Date endDate, String serialNumber, String password) {
		super();
		this.id = id;
		this.alias = alias;
		this.startDate = startDate;
		this.endDate = endDate;
		this.serialNumber = serialNumber;
		this.password = password;
	}
	
	public SelfCertificate() {
		super();
		// TODO Auto-generated constructor stub
	}



	private Long id;

	private String alias;

	private Date startDate;
	
	private Date endDate;
	
	private String serialNumber;

	private String password;
	
	/*@Column
	private X500Name x500Name;*/

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	
	

	/*public X500Name getX500Name() {
		return x500Name;
	}

	public void setX500Name(X500Name x500Name) {
		this.x500Name = x500Name;
	}*/
	
	
	
	
	
	
}
