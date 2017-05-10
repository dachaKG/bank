package bank.nationalBank;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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
import java.security.cert.X509Certificate;
import java.util.List;

import javax.validation.Valid;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.selfCertificate.SelfCertificate;
import bank.selfCertificate.SelfCertificateService;

@RestController
@RequestMapping
public class NationalBankController {

	private final NationalBankService nationalBankService;
	private final SelfCertificateService certificateService;

	@Autowired
	public NationalBankController(final NationalBankService nationalBankService,
			final SelfCertificateService certificateService) {
		Security.addProvider(new BouncyCastleProvider());
		this.nationalBankService = nationalBankService;
		this.certificateService = certificateService;
	}

	@GetMapping("/nationalBank")
	public List<NationalBank> findAll() {
		return nationalBankService.findAll();
	}

	@PostMapping("/addCertificate")
	public void addCertificate(@Valid @RequestBody SelfCertificate certificate) throws KeyStoreException, NoSuchProviderException {
		System.out.println(certificate.getSerialNumber());
		certificateService.save(certificate);
		KeyStore keyStore;
		KeyPair keyPair = generateKeyPair();
		SelfSignedCertificate ssc = new SelfSignedCertificate();
		
		NationalBank nationalBank = nationalBankService.findAll().get(0);
		X500Name x500Name = generateIssuerData(keyPair.getPrivate(), nationalBank);
		
		X509Certificate x509cert = ssc.generateCertificate(keyPair, nationalBank, certificate, x500Name);
		

		
		//store key and certificate
		X509Certificate[] chain = new X509Certificate[1];
		chain[0] = x509cert;
			//keyStore = KeyStore.getInstance("jks");
		
		try {
			keyStore = KeyStore.getInstance("jks");
			
			File file = new File("selfSignedCertificate.jks");
			//keyStore.load(null, null);
			
			if(!file.exists()){
				file.createNewFile();
				keyStore.load(null, "123".toCharArray());
			}else {
				keyStore.load(new FileInputStream("selfSignedCertificate.jks"), null);
			}
			keyStore.setKeyEntry(certificate.getCommonName(), keyPair.getPrivate(), certificate.getPassword().toCharArray(), chain);
			keyStore.store(new FileOutputStream("selfSignedCertificate.jks"), "123".toCharArray());
			
			//keyStore.store(new FileInputStream("selfSignedCertificate.jks"), "pass".toCharArray());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//	generateIssuerData(keyPair.getPrivate(), nationalBank, certificate);
		
	
		
	}

	private X500Name generateIssuerData(PrivateKey issuerKey, NationalBank nationalBank) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, nationalBank.getCommonName());
	    
	    builder.addRDN(BCStyle.O, nationalBank.getOrganization());
	    builder.addRDN(BCStyle.OU, nationalBank.getOrganizationUnit());
	    builder.addRDN(BCStyle.C, nationalBank.getCountry());
	    builder.addRDN(BCStyle.E, nationalBank.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, "123456");
	    
	    return builder.build();
		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		//return new IssuerData(issuerKey, builder.build());
	}
	
	@PostMapping("/addNationalBank")
	public void addNationalBank(@RequestBody NationalBank nationalBank) throws Exception {
		System.out.println(nationalBank.getCommonName());
		/*KeyPair keyPair = generateKeyPair();
		
		KeyFactory fact = KeyFactory.getInstance("RSA");
		
		RSAPublicKeySpec pub = fact.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		
		saveToFile("nationalBankPublicKey.cfg")*/
		// nationalBank.setPrivateKey(new
		// BigInteger(keyPair.getPrivate().getEncoded()));
		// nationalBank.setPublicKey(new
		// BigInteger(keyPair.getPublic().getEncoded()));
		nationalBankService.save(nationalBank);
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

	private static void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws Exception {
		ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			oout.close();
		}
	}

}
