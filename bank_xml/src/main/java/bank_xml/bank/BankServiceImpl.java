package bank_xml.bank;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BankServiceImpl implements BankService {
	private final BankRepository repository;

	@Autowired
	public BankServiceImpl(final BankRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Bank> findAll() {
		return (List<Bank>) repository.findAll();
	}

	@Override
	public Bank save(Bank bank) {
		return repository.save(bank);
	}

	@Override
	public Bank findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
		
	}
}