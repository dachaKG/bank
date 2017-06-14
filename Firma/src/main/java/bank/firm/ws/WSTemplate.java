package bank.firm.ws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.crypto.SecretKey;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.support.MarshallingUtils;
import org.w3c.dom.Document;

import encryption.KeyStoreReader;
import encryption.XMLEncryptionUtility;
import encryption.XMLSigningUtility;


public class WSTemplate extends WebServiceTemplate {
	
	
	//koristi se ovaj WebServiceTemplate da bi mogao sam da menjam izlaznu poruku, tj da sifrujem
	
	@Override
	public Object marshalSendAndReceive(String uri,
										final Object requestPayload,
										final WebServiceMessageCallback requestCallback) {
		return sendAndReceive(uri, new WebServiceMessageCallback() {

			public void doWithMessage(WebServiceMessage request) throws IOException, TransformerException {
				if (requestPayload != null) {
					Marshaller marshaller = getMarshaller();
					if (marshaller == null) {
						throw new IllegalStateException(
								"No marshaller registered. Check configuration of WebServiceTemplate.");
					}
					MarshallingUtils.marshal(marshaller, requestPayload, request);
					if (requestCallback != null) {
						requestCallback.doWithMessage(request);
					}
				}
				System.out.println("-----------------SOURCE--------------");
				//------print na izlaz
				DOMSource source = (DOMSource) request.getPayloadSource();
				Document doc = source.getNode().getOwnerDocument();
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(source, result);
				System.out.println("XML IN String format is: \n" + writer.toString());
				//----------------------------------------
				
				//---------sifrovanje test ----------
				KeyStoreReader ksReader = new KeyStoreReader();
				XMLEncryptionUtility encUtility = new XMLEncryptionUtility();
				XMLSigningUtility sigUtility = new XMLSigningUtility();
				SecretKey secretKey = encUtility.generateDataEncryptionKey();
				Certificate cert = ksReader.readCertificate("C:\\Users\\Nebojsa\\Desktop\\primer.jks", "primer", "primer");
				//doc = encUtility.encrypt(doc, secretKey, cert);
				PrivateKey privateKey = ksReader.readPrivateKey("C:\\Users\\Nebojsa\\Desktop\\primer.jks", "primer", "primer", "primer");
				//doc = sigUtility.signDocument(doc, privateKey, cert);
				saveDocument(doc,"C:\\Users\\Nebojsa\\Desktop\\dokument_sifrovan.xml");
				//-----------------------------------------------------------------------------------------------------------------
				//------------------desifrovanje--------------------
				
				/*System.out.println("\n===== Provera validnosti digitalnog potpisa =====");
				boolean res = sigUtility.verifySignature(doc);
				if(res) {
					System.out.println("\n===== Potpis je validan, dokument se desifruje =====");
					doc = encUtility.decrypt(doc, privateKey);
					System.out.println("\n===== Desifrovanje zavrseno, tacka B je primila dokument =====");
					//Ukoliko je proces uspesan garantovana je poverljivost i integritet poruke, kao i autentifikacija i neporecivost postupka slanja
				} else {
					System.out.println("\n===== Potpis nije validan, dokument se odbacuje =====");
				}
				
				saveDocument(doc,"C:\\Users\\Nebojsa\\Desktop\\dokument_desifrovan.xml");*/
				//--------------------------------------------------------------------------------------------------------------
				
		}
		}, new WebServiceMessageExtractor<Object>() {

			public Object extractData(WebServiceMessage response) throws IOException {
				Unmarshaller unmarshaller = getUnmarshaller();
				if (unmarshaller == null) {
					throw new IllegalStateException(
							"No unmarshaller registered. Check configuration of WebServiceTemplate.");
				}
				return MarshallingUtils.unmarshal(unmarshaller, response);
			}
		});
	}
	
	
	
	private void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			
			transformer.transform(source, result);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
