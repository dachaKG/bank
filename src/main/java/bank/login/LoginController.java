package bank.login;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.user.User;
import bank.user.UserService;

@RestController
@RequestMapping
public class LoginController {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	UserService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5, new SecureRandom());


	@GetMapping("/forgotPasswordMail/{credentials}")
	public void forgotPasswordMail(@PathVariable String credentials) {
		// UserDetails user =
		// userDetailsService.loadUserByUsername(credentials.getUsername());
		User user = userService.findByUsername(credentials);

		SecureRandom random = new SecureRandom();

		String newPassword = nextSessionId(random);
		System.out.println("Nova sifra " + newPassword);
		user.setPassword(encoder.encode(newPassword));
		userService.save(user);

		//return user;
	}

	public String nextSessionId(SecureRandom random) {
		return new BigInteger(130, random).toString(16);
	}

}
