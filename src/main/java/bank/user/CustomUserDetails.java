package bank.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails{
	 
	private static final long serialVersionUID = 1L; 
	
	private final User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
		if(user == null){
			throw new RuntimeException("Bad credentials");
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(Role role : user.getRoles()){
        	authorities.add(new GrantedAuthority() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public String getAuthority() {
					return role.getEnumRole().toString();
				}
			});
        }
       
        return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}