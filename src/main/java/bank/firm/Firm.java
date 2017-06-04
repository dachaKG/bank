package bank.firm;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class Firm {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    @NotNull
	    @Column(name = "name")
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
	    
}
