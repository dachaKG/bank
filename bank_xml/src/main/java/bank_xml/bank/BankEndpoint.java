package bank_xml.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import localhost._8080.ws.nalogzaplacanje.NalogZaPlacanje;

@Endpoint
public class BankEndpoint {

	private static final String NAMESPACE_URI = "http://localhost:8080/ws/nalogZaPlacanje";

	
	@Autowired
	private BankClient bankClient;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "nalogZaPlacanje")
	@ResponsePayload
	public NalogZaPlacanje nalogZaPlacanje(@RequestPayload NalogZaPlacanje request) {
		
		bankClient.s();
		return request;
	}
	

}
