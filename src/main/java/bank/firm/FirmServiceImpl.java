package bank.firm;

import org.springframework.beans.factory.annotation.Autowired;


public class FirmServiceImpl implements FirmService {
	
	@Autowired
	public FirmRepository repository;

	@Override
	public Firm findOnde(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Firm save(Firm certificate) {
		return repository.save(certificate);
	}
	
	

}
