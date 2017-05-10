package bank.selfCertificate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SelfCertificate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String commonName;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private String serialNumber;
	
	//private String alias;
	
	@Column
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

	/*public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}*/

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	

	/*public X500Name getX500Name() {
		return x500Name;
	}

	public void setX500Name(X500Name x500Name) {
		this.x500Name = x500Name;
	}*/
	
	
	
	
	
	
}
