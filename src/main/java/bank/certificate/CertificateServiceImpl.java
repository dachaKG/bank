package bank.certificate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CertificateServiceImpl implements CertificateService{
	
	private final CertificateRepo certificateRepository;
	
	@Autowired
	public CertificateServiceImpl(final CertificateRepo certificateRepository) {
		this.certificateRepository = certificateRepository;
	}

	@Override
	public List<Certificate> findAll() {
		return certificateRepository.findAll();
	}

	@Override
	public Certificate save(Certificate certificate) {
		return certificateRepository.save(certificate);
	}

	@Override
	public Certificate findOne(Long id) {
		return certificateRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		certificateRepository.delete(id);
	}

}
