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

import javax.servlet.http.HttpServletResponse;

import org.apache.sshd.common.util.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

	private final CertificateService certificateService;

	@Autowired
	public CertificateController(final CertificateService certificateService) {
		Security.addProvider(new BouncyCastleProvider());
		this.certificateService = certificateService;
	}

	@RequestMapping(value = "/commonName", method = RequestMethod.GET)
	public List<String> getAllCommonNames() {
		ArrayList<String> result = new ArrayList<>();
		File dir = new File(".");
		File[] listing = dir.listFiles(new FilenameFilter() { // ovo ce mi
																// trebati kada
																// budem listao
																// sve
																// sertifikate
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".jks");
			}
		});
		for (int i = 0; i < listing.length; i++) {

			try {
				KeyStore ks = KeyStore.getInstance("JKS");
				InputStream readStream = new FileInputStream(listing[i].getPath());
				ks.load(readStream, "123".toCharArray());
				Enumeration<String> aliases = ks.aliases();
				if (checkCN(ks, aliases)) {
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
	public List<String> getAliases(@PathVariable String cn) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream readStream = new FileInputStream(cn + ".jks");
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

	@RequestMapping(method = RequestMethod.POST)
	public void addCertificate(@RequestBody BankCertificate bc) throws IOException, KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException {

		int unique_id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		bc.setSerialNumber("" + unique_id + "");

		KeyStore ks = KeyStore.getInstance("JKS", "SUN");
		KeyPair keyPairSubject = generateKeyPair();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(bc.getIssuerCommonName() + ".jks"));
		ks.load(in, "123".toCharArray());

		if (ks.isKeyEntry(bc.getIssuerAlias())) {

			PrivateKey issuerPrivateKey = (PrivateKey) ks.getKey(bc.getIssuerAlias(),
					bc.getIssuerPassword().toCharArray());// privatni kljuc
															// issuer-a
			Certificate issuerCert = ks.getCertificate(bc.getIssuerAlias());
			X500Name issuerX500Name = new JcaX509CertificateHolder((X509Certificate) issuerCert).getSubject();
			Certificate[] issuerChain = ks.getCertificateChain(bc.getIssuerAlias());// certificate chain if issuer , kako bi odredio ceo chain za  subject

			// sada imam potrebne podatke vezane za issuer-a za potpis
			// sertifikata
			System.out.println(issuerX500Name.toString());
			System.out.println(issuerPrivateKey);
			System.out.println(bc.getSerialNumber());

			SubjectData subject = generateSubjectData(bc, keyPairSubject);
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate certificate = cg.generateCertificate(subject, issuerPrivateKey, issuerX500Name,true);
			boolean ca = certificate.getBasicConstraints() != -1;// proveravam
																	// da li
																	// sam
																	// dobro
																	// podesio
																	// CA
			System.out.println("CA: " + ca);
			Certificate[] chain = new Certificate[issuerChain.length + 1];
			chain[0] = certificate;
			for (int i = 0; i < issuerChain.length; i++) {
				chain[i + 1] = issuerChain[i];
			}
			File file = new File(bc.getCommonName() + ".jks");
			// keyStore.load(null, null);

			if (!file.exists()) {
				file.createNewFile();
				ks.load(null, "123".toCharArray());
			} else {
				ks.load(new FileInputStream(bc.getCommonName() + ".jks"), null);
			}
			ks.setKeyEntry(bc.getAlias(), keyPairSubject.getPrivate(), bc.getPassword().toCharArray(), chain);
			ks.store(new FileOutputStream(bc.getCommonName() + ".jks"), "123".toCharArray());

			// cuvanje u bazu serial number-revoke
			certificateService.save(new bank.certificate.Certificate(bc.getSerialNumber(), false));

			// export to .cer fajl
			File cerFile = new File("certificates\\"+certificate.getSerialNumber() + ".cer");
			final FileOutputStream os = new FileOutputStream(cerFile);
			os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.write(Base64.encodeBase64(certificate.getEncoded(), true));
			os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
			os.close();
		}

	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{serialNumber}")
	public bank.certificate.Certificate revokeCertificate(@PathVariable("serialNumber") String serialNumber) {
		return certificateService.revoke(serialNumber);
	}

	@GetMapping("/{serialNumber}")
	public bank.certificate.Certificate findBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
		return certificateService.findBySerialNumber(serialNumber);
	}

	@RequestMapping(value = "/download/{serialNumber}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable String serialNumber, HttpServletResponse response) throws IOException {

		response.setContentType("application/x-x509-ca-cert");

		File file = new File(serialNumber + ".cer");

		InputStream inputStream = new FileInputStream(file);
		IOUtils.copy(inputStream, response.getOutputStream());

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

	private SubjectData generateSubjectData(BankCertificate bc, KeyPair keyPairSubject) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, bc.getCommonName());

		builder.addRDN(BCStyle.O, bc.getOrganization());
		builder.addRDN(BCStyle.OU, bc.getOrganizationUnit());
		builder.addRDN(BCStyle.C, bc.getCountry());
		builder.addRDN(BCStyle.E, bc.getEmail());
		// UID (USER ID) je ID korisnika
		builder.addRDN(BCStyle.UID, bc.getSerialNumber());
		return new SubjectData(keyPairSubject.getPublic(), builder.build(), bc.getSerialNumber(), bc.getStartDate());
	}
}
