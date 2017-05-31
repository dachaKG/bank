package bank.nationalBank;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RestController;

import bank.certificate.Certificate;
import bank.certificate.CertificateService;
import bank.selfCertificate.SelfCertificate;

@RestController
@RequestMapping
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

	@GetMapping("/nationalBank")
	public List<NationalBank> findAll() {
		return nationalBankService.findAll();
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/addCertificate")
	public void addCertificate(@Valid @RequestBody SelfCertificate selfCertificate) throws KeyStoreException, NoSuchProviderException {
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		selfCertificate.setSerialNumber(""+unique_id+"");
		KeyStore keyStore;
		KeyPair keyPair = generateKeyPair();

		SelfSignedGenerator ssc = new SelfSignedGenerator();
		
		//NationalBank nationalBank = nationalBankService.findAll().get(0);
		X500Name x500Name = generateIssuerData(keyPair.getPrivate(), selfCertificate);
		
		X509Certificate x509cert = ssc.generateCertificate(keyPair, selfCertificate, x500Name);
		

		
		//store key and certificate
		X509Certificate[] chain = new X509Certificate[1];
		chain[0] = x509cert;
			//keyStore = KeyStore.getInstance("jks");
		
		try {
			keyStore = KeyStore.getInstance("jks", "SUN");
			
			File file = new File(selfCertificate.getCommonName()+".jks");
			
			if(!file.exists()){
				file.createNewFile();
				keyStore.load(null, "123".toCharArray());
			} else {
				keyStore.load(new FileInputStream(selfCertificate.getCommonName()+".jks"), null);
			}
			
			
			//getExistingCertificate("1222");
			keyStore.setKeyEntry(selfCertificate.getAlias(), keyPair.getPrivate(), selfCertificate.getPassword().toCharArray(), chain);
			keyStore.store(new FileOutputStream(selfCertificate.getCommonName()+".jks"), "123".toCharArray());
			File cerFile = new File("certificates\\"+x509cert.getSerialNumber()+".cer");
			final FileOutputStream os = new FileOutputStream(cerFile);
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.encodeBase64(x509cert.getEncoded(), true));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();			
			certificateService.save(new Certificate(selfCertificate.getSerialNumber(),false));
			/*//------------
			X509CertSelector targetConstraints = new X509CertSelector();

			targetConstraints.setCertificate(x509cert);

			try {
				PKIXBuilderParameters params = new PKIXBuilderParameters(keyStore, targetConstraints);
				params.setRevocationEnabled(true);
				System.out.println(params.isRevocationEnabled());
			} catch (InvalidAlgorithmParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			//-----------*/
			

			//Certificate readCertificate = readCertificateFromKeyStore("selfSignedCertificate.jks", "123", "mika");
			
			
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
		
	
		
	}
	
	
	@GetMapping("/getExistingCertificate/{serialNumber}")
	public List<X509Certificate> getExistingCertificate(@PathVariable String serialNumber) throws FileNotFoundException {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			File file = new File("selfSignedCertificate.jks");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
			ks.load(in, "123".toCharArray());
			
			
			List<X509Certificate> x509CertificateList = new ArrayList<X509Certificate>();
			Enumeration<String> enumeration = ks.aliases();
	        while(enumeration.hasMoreElements()) {
	            String alias = (String)enumeration.nextElement();
	            System.out.println("alias name: " + alias);
	            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);
	            
	            if(certificate.getSerialNumber().equals(new BigInteger(serialNumber))){
	            	System.out.println("pronasao sertifikat");
	            	x509CertificateList.add(certificate);
	            }

	        }
	        System.out.println(x509CertificateList.toString());
	        return x509CertificateList;
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
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
