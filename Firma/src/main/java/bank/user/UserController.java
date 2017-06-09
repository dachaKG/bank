package bank.user;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.security.UserDetailServiceImpl;
import bank.userBadPassword.UserBadPasswordService;

@RestController
@RequestMapping
public class UserController {

	private final UserService userService;
	private final UserDetailsService userDetailsService;
	private final RoleService roleService;

	@Autowired
	UserDetailServiceImpl userDetails;
	
    @Autowired
    private JavaMailSender javaMailSender;

	@Autowired
	private UserBadPasswordService userBadPasswordService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	public UserController(UserService userService, final UserDetailsService userDetailsService, final RoleService roleService) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.roleService = roleService;
	}

	@PreAuthorize("hasAuthority('registerUser')")
	@PostMapping("/registerUser")
	public void registerUser(@RequestBody User user) {

		String password = nextSessionId(new SecureRandom());
		System.out.println("Nova sifra " + password);
		
		user.setPassword(encoder.encode(password));
		Role role =roleService.findByEnumRole(EnumRole.ROLE_USER);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		userService.save(user);
		try{
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(user.getEmail());// umesto ovoga guest.mail..ako neces da testiras
			email.setFrom("isarestorani2@gmail.com");
			email.setSubject("Registration successfull");
			email.setText("Your password is " + password);
	
			javaMailSender.send(email);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String nextSessionId(SecureRandom random) {
		return new BigInteger(130, random).toString(16);
	}


	@GetMapping("/logout")
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	@PutMapping("/changePassword")
	public String changePassword(@RequestBody ChangePassword changePassword) {
		if (changePassword.getNewPassword().equals(changePassword.getCheckNewPassword())) {
			CustomUserDetails userDetails = getUserDetails();
			User user = userService.findByUsername(userDetails.getUsername());
			boolean checkOldPassword = encoder.matches(changePassword.getOldPassword(), user.getPassword());
			if (checkOldPassword) {
				user.setPassword(encoder.encode(changePassword.getNewPassword()));
				userService.save(user);
				return "changed";
			}

			return "badOldPassword";
		}

		return "password didn't match";
	}

	@GetMapping("/userDetails")
	public CustomUserDetails userDetails() {

		return getUserDetails();
	}

	private CustomUserDetails getUserDetails() {

		SecurityContext context = SecurityContextHolder.getContext();

		Authentication authentication = context.getAuthentication();

		// User user = userService.findByUsername(authentication.getName());

		// return user;
		org.springframework.security.core.userdetails.UserDetails currentUser = userDetailsService
				.loadUserByUsername(authentication.getName());

		userBadPasswordService.delete(userDetails.getBadPassword().getId());

		return (CustomUserDetails) currentUser;

	}

}