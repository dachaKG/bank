package bank.suspension;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * ukidanje
 */

public class Suspension {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	@NotNull
	private Date date;
	
	@Column(length = 20)
	@NotBlank
	private String transferToAccount; //sredstva se prenose na racun
	
}
