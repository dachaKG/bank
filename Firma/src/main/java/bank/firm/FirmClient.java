package bank.firm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.ObjectFactory;

import bank.faktura.Faktura;



@Component
public class FirmClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;


	public void sendNalog(Faktura f, int s) {
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest nalogZaPlacanjeRequest = factory.createGetNalogZaPlacanjeRequest();
		nalogZaPlacanjeRequest.setNalogZaPlacanje(factory.createNalogZaPlacanje());
		nalogZaPlacanjeRequest.getNalogZaPlacanje().setDuznikNalogodavac(f.getNazivKupca());
		
		/*String sub = webServiceTemplate.getDefaultUri().substring(0, 20);
		webServiceTemplate.setDefaultUri(sub+"5/ws");*/
		
		GetNalogZaPlacanjeResponse getNalogZaPlacanjeResponse = (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);

		}

}
