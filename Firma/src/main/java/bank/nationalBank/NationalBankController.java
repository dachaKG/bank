package bank.nationalBank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.validation.Valid;

import org.apache.sshd.common.util.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bank.certificate.Certificate;
import bank.certificate.CertificateService;
import bank.selfCertificate.SelfCertificate;

@RestController
@RequestMapping("/nationalBank")
public class NationalBankController {

	private final NationalBankService nationalBankService;
	//private final SelfCertificateService certificateService;
	private final CertificateService certificateService;
	@Autowired
	public NationalBankController(final NationalBankService nationalBankService,final CertificateService certificateService) {
		Security.addProvider(new BouncyCastleProvider());
		this.nationalBankService = nationalBankService;
		this.certificateService = certificateService;
	}

	@GetMapping
	public List<NationalBank> findAll() {
		return nationalBankService.findAll();
	}

	
	@RequestMapping(value = "/commonName", method = RequestMethod.GET)
	public List<String> getAllCommonNames()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		ArrayList<String> result = new ArrayList<>();
		File dir = new File("ksCentralBank");
		File[] listing = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".jks");
			}
		});

		for (int i = 0; i < listing.length; i++) {

			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream readStream = new FileInputStream(listing[i].getPath());
			ks.load(readStream, "123".toCharArray());
			Enumeration<String> aliases = ks.aliases();
			if (checkCN(ks, aliases)) {
				String s = listing[i].getPath().replaceAll("ksCentralBank", "");
				String cName = s.replaceAll(".jks", "");
				result.add(cName.substring(1));
			}
		}
		return result;
	}

	private boolean checkCN(KeyStore ks, Enumeration<String> aliases) {
		boolean result = false;
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (isCA(alias, ks)) {
				result = true;
				break;
			}
		}
		return result;
	}

	@RequestMapping(value = "/commonName/{cn}/aliases", method = RequestMethod.GET)
	public List<String> getAliases(@PathVariable String cn)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		
		KeyStore ks = KeyStore.getInstance("JKS");
		
		InputStream readStream = new FileInputStream("ksCentralBank\\"+cn + ".jks");
		ks.load(readStream, "123".toCharArray());
		Enumeration<String> aliases = ks.aliases();
		ArrayList<String> result = new ArrayList<>();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (isCA(alias, ks))
				result.add(alias);
		}
		readStream.close();
		return result;
	}

	private boolean isCA(String alias, KeyStore ks) {
		try {
			X509Certificate c = (X509Certificate) ks.getCertificate(alias);

			c.checkValidity();
			// ovo mi treba da bih proverio da li je povucen
			bank.certificate.Certificate cert = certificateService.findBySerialNumber(c.getSerialNumber().toString());
			return c.getBasicConstraints() != -1 && !cert.isPovucen();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateExpiredException e) {
			return false;// ako je istekao vraa false
		} catch (CertificateNotYetValidException e) {
			return false;
		}
		return false;
	}	
	
	
	
	
	@PreAuthorize("hasAuthority('addCertificate')")
	@PostMapping
	public void addCertificate(@Valid @RequestBody SelfCertificate selfCertificate) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException {
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		selfCertificate.setSerialNumber(""+unique_id+"");
		KeyStore keyStore;
		KeyPair keyPair = generateKeyPair();

		SelfSignedGenerator ssc = new SelfSignedGenerator();
		X500Name x500Name = generateIssuerData(keyPair.getPrivate(), selfCertificate);
		
		X509Certificate x509cert = ssc.generateCertificate(keyPair, selfCertificate, x500Name);

		//store key and certificate
		X509Certificate[] chain = new X509Certificate[1];
		chain[0] = x509cert;
			keyStore = KeyStore.getInstance("jks", "SUN");
			File file = new File("ksCentralBank\\" + selfCertificate.getCommonName()+".jks");
			
			if(!file.exists()){
				file.createNewFile();
				keyStore.load(null, "123".toCharArray());
			} else {
				keyStore.load(new FileInputStream(file), null);
			}
			
			
			//getExistingCertificate("1222");
			keyStore.setKeyEntry(selfCertificate.getAlias(), keyPair.getPrivate(), selfCertificate.getPassword().toCharArray(), chain);
			keyStore.store(new FileOutputStream(file), "123".toCharArray());
			
			File cerFile = new File("certificates\\"+x509cert.getSerialNumber()+".cer");
			final FileOutputStream os = new FileOutputStream(cerFile);
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.encodeBase64(x509cert.getEncoded(), true));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();			
			
			certificateService.save(new Certificate(selfCertificate.getSerialNumber(),false));

	}
	
	

	private X500Name generateIssuerData(PrivateKey issuerKey, SelfCertificate selfCertificate) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, selfCertificate.getCommonName());
	    
	    builder.addRDN(BCStyle.O, selfCertificate.getOrganization());
	    builder.addRDN(BCStyle.OU, selfCertificate.getOrganizationUnit());
	    builder.addRDN(BCStyle.C, selfCertificate.getCountry());
	    builder.addRDN(BCStyle.E, selfCertificate.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, selfCertificate.getSerialNumber());
	    
	    return builder.build();
		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		//return new IssuerData(issuerKey, builder.build());
	}
	

	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

}
