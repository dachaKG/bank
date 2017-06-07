/*package bank.zahtev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import localhost._8080.__.bank.ws.GetZahtevRequest;
import localhost._8080.__.bank.ws.GetZahtevResponse;

public class ZahteviZaDobijanjeIzvodaEndpoint {
	private static final String NAMESPACE_URI = "http://localhost:8080/#/bank/ws/";
	
	@Autowired
	private ZahtevRepo zahtevRepository;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getZahtevRequest")
	@ResponsePayload
	public GetZahtevResponse getZahtev(@RequestPayload GetZahtevRequest request){
		GetZahtevResponse response = new GetZahtevResponse();
		
		response.setZahtevZaDobijanjeIzvoda(zahtevRepository.findZahtev(request.getBrojRacuna()));
		
		return response;
	}
}
*/