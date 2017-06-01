package bank.user;

import java.security.SecureRandom;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import bank.privilege.Privilege;
import bank.security.UserDetailServiceImpl;
import bank.userBadPassword.UserBadPasswordService;

@RestController
@RequestMapping
public class UserController {

	private final UserService userService;
	private final UserDetailsService userDetailsService;

	@Autowired
	UserDetailServiceImpl userDetails;

	@Autowired
	private UserBadPasswordService userBadPasswordService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5, new SecureRandom());

	@Autowired
	public UserController(UserService userService, final UserDetailsService userDetailsService) {
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	// @PreAuthorize("hasAuthority('registerUser')")
	@PostMapping("/registerUser")
	public void registerUser(@RequestBody User user) {
		boolean checkPrivilege = checkPrivilege(getUserDetails());
		// if(){
		if(checkPrivilege){
			User saveUser = user;
	
			saveUser.setPassword(encoder.encode(user.getPassword()));
			userService.save(user);
		} else {
			throw new RuntimeException("You don't have permission for that");
		}
		// }
	}

	private boolean checkPrivilege(CustomUserDetails customUserDetails) {
		User user = userService.findByUsername(customUserDetails.getUsername());
		for(Role role : user.getRoles()){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getId() == 3L)
					return true;
			}
		}
		return false;
	}

	@GetMapping("/logout")
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// return "/login?logout";
	}

	@PutMapping("/changePassword")
	public boolean changePassword(@RequestBody ChangePassword changePassword) {
		CustomUserDetails userDetails = getUserDetails();
		User user = userService.findByUsername(userDetails.getUsername());
		boolean checkOldPassword = encoder.matches(changePassword.getOldPassword(), user.getPassword());
		if (checkOldPassword) {
			user.setPassword(encoder.encode(changePassword.getNewPassword()));
			userService.save(user);
			return true;
		}

		return false;

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
