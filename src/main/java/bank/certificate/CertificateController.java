package bank.certificate;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.sshd.common.util.Base64;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

	public CertificateController(){
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@RequestMapping(value = "/commonName", method = RequestMethod.GET)
	public List<String> getAllCommonNames(){
		ArrayList<String> result = new ArrayList<>();
		File dir = new File(".");
		File[] listing = dir.listFiles(new FilenameFilter() { //ovo ce mi trebati kada budem listao sve sertifikate
	            public boolean accept(File dir, String filename)
	            	{ return filename.endsWith(".jks"); }
				});
		for(int i = 0 ; i < listing.length;i++){

			try {
				KeyStore ks = KeyStore.getInstance("JKS");
				InputStream readStream = new FileInputStream(listing[i].getPath());			
				ks.load(readStream, "123".toCharArray());
				Enumeration<String> aliases = ks.aliases();
				if(checkCN(ks,aliases)){
					String cName = listing[i].getPath().replaceAll(".jks", "");
					result.add(cName.substring(2));					
				}
			} catch (KeyStoreException e) {
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
			}
			
		}
		return result;
	}
	
	private boolean checkCN(KeyStore ks, Enumeration<String> aliases) {
		boolean result = false;
		while(aliases.hasMoreElements()){
			String alias = aliases.nextElement();
			if(isCA(alias,ks)){
				result = true;
				break;
			}
		}		
		return result;
	}

	@RequestMapping(value = "/commonName/{cn}/aliases",method = RequestMethod.GET)
	public List<String> getAliases(@PathVariable String cn){
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream readStream = new FileInputStream(cn+".jks");
			ks.load(readStream, "123".toCharArray());
			Enumeration<String> aliases = ks.aliases();
			ArrayList<String> result = new ArrayList<>();
			while(aliases.hasMoreElements()){
				String alias = aliases.nextElement();
				if(isCA(alias,ks))
					result.add(alias);
			}
			readStream.close();	
			return result;
		} catch (KeyStoreException e) {
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
		}
		
		return null;
	}
	private boolean isCA(String alias, KeyStore ks) {
		try {
			X509Certificate c = (X509Certificate) ks.getCertificate(alias);
			c.checkValidity();
			return c.getBasicConstraints() != -1;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateExpiredException e) {
			return false;//ako je istekao vraa false
		} catch (CertificateNotYetValidException e) {
			return false;
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void addCertificate(@RequestBody BankCertificate bc){
		
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		bc.setSerialNumber(""+unique_id+"");
		String commonName = bc.getIssuerCommonName();
		String tempFile = commonName+".jks";
		String tempAlias = bc.getIssuerAlias();
		
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			KeyPair keyPairSubject = generateKeyPair();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(tempFile));
			ks.load(in, "123".toCharArray());
			
			if(ks.isKeyEntry(tempAlias)) {
				
				PrivateKey issuerPrivateKey = (PrivateKey) ks.getKey(tempAlias, bc.getIssuerPassword().toCharArray());//privatni kljuc issuer-a
				Certificate cert = ks.getCertificate(tempAlias);
				X500Name issuerX500Name = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
				Certificate[] issuerChain = ks.getCertificateChain(tempAlias);//certificate chain if issuer , kako bi odredio ceo chain za subject

				//sada imam potrebne podatke vezane za issuer-a za potpis sertifikata		
				System.out.println(issuerX500Name.toString());
				System.out.println(issuerPrivateKey);
				System.out.println(bc.getSerialNumber());
				
				SubjectData subject = generateSubjectData(bc,keyPairSubject);
				CertificateGenerator cg = new CertificateGenerator();
				X509Certificate certificate = cg.generateCertificate(subject, issuerPrivateKey, issuerX500Name,bc.isCertificateAuthority());
				boolean  ca = certificate.getBasicConstraints() != -1;// proveravam da li sam dobro podesio CA
				System.out.println("CA: " + ca);
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
				ks.setKeyEntry(bc.getAlias(), keyPairSubject.getPrivate(), bc.getPassword().toCharArray(), chain);
				ks.store(new FileOutputStream(bc.getCommonName()+".jks"), "123".toCharArray());
				final FileOutputStream os = new FileOutputStream("cert.cer");
				os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
				os.write(Base64.encodeBase64(cert.getEncoded(), true));
				os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
				os.close();
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
