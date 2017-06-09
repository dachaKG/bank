package bank.firm;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import localhost._8080.ws.nalogzaplacanje.NalogZaPlacanje;
import localhost._8080.ws.nalogzaplacanje.ObjectFactory;

@Component
public class FirmClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BankClient.class);

	public void sendNalog() {
		ObjectFactory factory = new ObjectFactory();
		NalogZaPlacanje nalog = factory.createNalogZaPlacanje();
		nalog.setIznos(new BigDecimal("1234"));

		webServiceTemplate.marshalSendAndReceive(nalog);

	}

}
