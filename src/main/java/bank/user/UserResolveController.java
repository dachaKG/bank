package bank.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.privilege.Privilege;
import bank.privilege.PrivilegeService;
import bank.security.UserDetailServiceImpl;
import bank.userBadPassword.UserBadPasswordService;

@RestController
public class UserResolveController {
	
	private final UserService userService;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	private UserBadPasswordService userBadPasswordService;
	
	@Autowired
	UserDetailServiceImpl userDetails;
	
	@Autowired
	public UserResolveController(UserService userService, UserDetailsService userDetailsService) {
		super();
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/userPermissionAddCertificate")
	public boolean userPermissionAddCertificate(){
		UserDetails userDetails = getUserDetails();
		User user = userService.findByUsername(userDetails.getUsername());
		for(Role role : user.getRoles()){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getPrivilege().equals(privilegeService.findOne(1L).getPrivilege()))
					return true;
			}
		}
		return false;
	}
	
	@GetMapping("/userPermissionAddCaSignedCertificate")
	public boolean userPermissionAddCaSignedCertificate(){
		UserDetails userDetails = getUserDetails();
		User user = userService.findByUsername(userDetails.getUsername());
		for(Role role : user.getRoles()){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getPrivilege().equals(privilegeService.findOne(4L).getPrivilege()))
					return true;
			}
		}
		return false;
	}
	
	@GetMapping("/userPermissionRegisterUser")
	private boolean userPermissionRegisterUser(){
		UserDetails userDetails = getUserDetails();
		User user = userService.findByUsername(userDetails.getUsername());
		for(Role role : user.getRoles()){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getPrivilege().equals(privilegeService.findOne(3L).getPrivilege()))
					return true;
			}
		}
		return false;
	}
	
	
	
	private CustomUserDetails getUserDetails(){

		
		SecurityContext context = SecurityContextHolder.getContext();

		Authentication authentication = context.getAuthentication();

		org.springframework.security.core.userdetails.UserDetails currentUser = userDetailsService.loadUserByUsername(authentication.getName());
		
		userBadPasswordService.delete(userDetails.getBadPassword().getId());

		return (CustomUserDetails) currentUser;
	
	}

}
