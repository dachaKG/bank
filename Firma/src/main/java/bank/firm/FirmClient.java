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

	// private static final Logger LOGGER =
	// LoggerFactory.getLogger(BankClient.class);

	public void sendNalog(Faktura f) {
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest nalogZaPlacanjeRequest = factory.createGetNalogZaPlacanjeRequest();
		nalogZaPlacanjeRequest.setNalogZaPlacanje(factory.createNalogZaPlacanje());
		nalogZaPlacanjeRequest.getNalogZaPlacanje().setDuznikNalogodavac(f.getNazivKupca());
		/*nalogZaPlacanjeRequest.getNalogZaPlacanje().setIznos(new BigDecimal(f.getIznosZaUplatu()));
*/	
		//GetNalogZaPlacanjeResponse nalogZaPlacanjeResponse= (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);
		/*final SoapActionCallback soapActionCallback = new SoapActionCallback("getNalogZaPlacanje");
		nalogZaPlacanjeResponse = (GetNalogZaPlacanjeResponse) webServiceTemplate
		    .marshalSendAndReceive(nalogZaPlacanjeRequest, soapActionCallback );
		//responseString = response.getAnswer().toString();
		System.out.println(nalogZaPlacanjeResponse.getNalogZaPlacanje().getDuznikNalogodavac());*/
		
		/*io.spring.guides.gs_producing_web_service.ObjectFactory factory = new io.spring.guides.gs_producing_web_service.ObjectFactory();
		GetCountryRequest countryRequest = factory.createGetCountryRequest();
		countryRequest.setName("NAME");
		GetCountryResponse getCountryResponse = (GetCountryResponse) webServiceTemplate.marshalSendAndReceive(countryRequest);
		*/
		GetNalogZaPlacanjeResponse getNalogZaPlacanjeResponse = (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);

		}

}
