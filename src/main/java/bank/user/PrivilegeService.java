package bank.user;

import java.util.List;

public interface PrivilegeService {
	
	public List<Privilege> findAll();
	
	public Privilege findOne(Long id);
	

}
