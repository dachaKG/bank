package bank.certificate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

	public CertificateController(){
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void addCertificate(BankCertificate bc){
/*		File dir = new File(".");
		File[] listing = dir.listFiles(new FilenameFilter() { //ovo ce mi trebati kada budem listao sve sertifikate
	            public boolean accept(File dir, String filename)
	            	{ return filename.endsWith(".jks"); }
				});*/
		bc = new BankCertificate();
		bc.setCommonName("PROBA2");
		bc.setAlias("alias2");
		bc.setCountry("srb");
		bc.setEmail("adaa@a.com");
		bc.setStartDate(new Date());
		bc.setEndDate(new Date());
		bc.setOrganization("ORG");
		bc.setOrganizationUnit("unit");
		bc.setPassword("abc");
		bc.setSerialNumber("123457777");
	
		String commonName = "selfSignedCertificate";//bc.getIssuerCommonName();
		String tempFile = commonName+".jks";
		String tempAlias = "nbs8";//bc.getIssuerAlias();
		String tempKeyPass = "p";//treba resiti cuvanje ovih password-a
		
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			KeyPair keyPairSubject = generateKeyPair();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(tempFile));
			ks.load(in, "123".toCharArray());
			
			if(ks.isKeyEntry(tempAlias)) {
				
				PrivateKey issuerPrivateKey = (PrivateKey) ks.getKey(tempAlias, tempKeyPass.toCharArray());//privatni kljuc issuer-a
				Certificate cert = ks.getCertificate(tempAlias);
				X500Name issuerX500Name = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
				Certificate[] issuerChain = ks.getCertificateChain(tempAlias);//certificate chain if issuer , kako bi odredio ceo chain za subject
				//sada imam potrebne podatke vezane za issuer-a za potpis sertifikata
				System.out.println(issuerX500Name.toString());
				System.out.println(issuerPrivateKey);
				
				SubjectData subject = generateSubjectData(bc,keyPairSubject);
				CertificateGenerator cg = new CertificateGenerator();
				X509Certificate certificate = cg.generateCertificate(subject, issuerPrivateKey, issuerX500Name);
				
				Certificate[] chain = new Certificate[issuerChain.length+1];
				chain[0] = certificate;
				for(int i = 0 ; i < issuerChain.length;i++){
					chain[i+1] = issuerChain[i];
				}
				File file = new File(bc.getCommonName()+".jks");
				//keyStore.load(null, null);
				
				if(!file.exists()){
					file.createNewFile();
					ks.load(null, "123".toCharArray());
				}else {
					ks.load(new FileInputStream(bc.getCommonName()+".jks"), null);
				}
				ks.setKeyEntry(bc.getCommonName()+bc.getSerialNumber(), keyPairSubject.getPrivate(), bc.getPassword().toCharArray(), chain);
				ks.store(new FileOutputStream(bc.getCommonName()+".jks"), "123".toCharArray());
				
				
			}			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
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
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	private SubjectData generateSubjectData(BankCertificate bc, KeyPair keyPairSubject) {
		X500NameBuilder builder =new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, bc.getCommonName());
	    
	    builder.addRDN(BCStyle.O, bc.getOrganization());
	    builder.addRDN(BCStyle.OU, bc.getOrganizationUnit());
	    builder.addRDN(BCStyle.C, bc.getCountry());
	    builder.addRDN(BCStyle.E, bc.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, bc.getSerialNumber());
	    
	    return new SubjectData(keyPairSubject.getPublic(), builder.build(), bc.getSerialNumber(), bc.getStartDate(), bc.getEndDate());

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
