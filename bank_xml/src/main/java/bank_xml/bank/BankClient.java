package bank_xml.bank;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import localhost._8080.ws.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import localhost._8080.ws.nalogzaplacanje.NalogZaPlacanje;
import localhost._8080.ws.nalogzaplacanje.ObjectFactory;

@Component
public class BankClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	 // private static final Logger LOGGER = LoggerFactory.getLogger(BankClient.class);

	
	public void s(){
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest request = factory.createGetNalogZaPlacanjeRequest();
		
		
		//NalogZaPlacanje result = (NalogZaPlacanje) webServiceTemplate.marshalSendAndReceive(nalog);

	}
}
