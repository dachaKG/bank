package bank.certificate;

import java.util.List;

public interface CertificateService {
	
	public List<Certificate> findAll();

	public Certificate save(Certificate certificate);
	
	public Certificate findOne(Long id);
	
	public void delete(Long id);
	
}
