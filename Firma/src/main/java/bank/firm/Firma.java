package bank.firm;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bank.bank.Bank;
import bank.faktura.Faktura;

@Entity
public class Firma {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@NotNull
	@Column(name = "name", unique = true)
	private String name;

	@NotNull
	@Column(name = "address")
	private String address;

	@NotNull
	@Column(name = "city")
	private String city;

	@NotNull
	@Column(name = "country")
	private String country;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "web")
	private String web;
	
	@Column(unique = true, columnDefinition = "CHAR(11)")
	private String pibFirm;

	private Integer stanjeRacuna;
	
	@Column(columnDefinition = "CHAR(18)",unique = true)	
	private String brojRacuna;

	@ManyToOne
	private Bank bank;
	

	
	@JsonIgnore
	@OneToMany(mappedBy = "firma", cascade = CascadeType.ALL)
	private List<Faktura> fakture;

	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<Faktura> getFakture() {
		return fakture;
	}

	public void setFakture(List<Faktura> fakture) {
		this.fakture = fakture;
	}

	public String getPibFirm() {
		return pibFirm;
	}

	public void setPibFirm(String pibFirm) {
		this.pibFirm = pibFirm;
	}


	public Integer getStanjeRacuna() {
		return stanjeRacuna;
	}

	public void setStanjeRacuna(Integer stanjeRacuna) {
		this.stanjeRacuna = stanjeRacuna;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	
	

}
