package bank.certificate;

import java.util.List;

public interface SelfCertificateService {
	
	public List<SelfCertificate> findAll();

	public SelfCertificate save(SelfCertificate certificate);
	
	public SelfCertificate findOne(Long id);
	
	public void delete(Long id);
	
}
