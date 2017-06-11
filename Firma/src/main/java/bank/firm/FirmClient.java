package bank.firm;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.NalogZaPlacanje;
import com.nalogzaplacanje.ObjectFactory;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import bank.faktura.Faktura;



@Component
public class FirmClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;


	public void sendNalog(Faktura f, int s) {
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest nalogZaPlacanjeRequest = factory.createGetNalogZaPlacanjeRequest();
		
		NalogZaPlacanje nalogZaPlacanje = factory.createNalogZaPlacanje();
		nalogZaPlacanje.setDatumNaloga(new XMLGregorianCalendarImpl());
		nalogZaPlacanje.setDatumValute(new XMLGregorianCalendarImpl());
		nalogZaPlacanje.setDuznikNalogodavac("levi");
		nalogZaPlacanje.setHitno(false);
		nalogZaPlacanje.setIdPoruke("Nalog za prenos");
		nalogZaPlacanje.setIznos(new BigDecimal("250200"));
		nalogZaPlacanje.setModelOdobrenja(97);
		nalogZaPlacanje.setModelZaduzenja(92);
		nalogZaPlacanje.setOznakaValute("RSD");
		nalogZaPlacanje.setPozivNaBrojOdobrenja("147541214");
		nalogZaPlacanje.setPozivNaBrojZaduzenja("41247412");
		nalogZaPlacanje.setPrimalacPoverilac("ftn");
		nalogZaPlacanje.setRacunDuznika("200636547896321456");
		nalogZaPlacanje.setRacunPoverioca("400254547845612156");
		nalogZaPlacanje.setSvrhaPlacanja("Svrha placanja");
		
		nalogZaPlacanjeRequest.setNalogZaPlacanje(nalogZaPlacanje);
		/*String sub = webServiceTemplate.getDefaultUri().substring(0, 20);
		webServiceTemplate.setDefaultUri(sub+"5/ws");*/
		
		GetNalogZaPlacanjeResponse getNalogZaPlacanjeResponse = (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);

		}

}
