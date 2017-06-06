package bank_xml.bank;

import java.util.List;

public interface BankService {
	public List<Bank> findAll();

	public Bank save(Bank bank);

	public Bank findOne(Long id);
	
	public void delete(Long id);
	
}
