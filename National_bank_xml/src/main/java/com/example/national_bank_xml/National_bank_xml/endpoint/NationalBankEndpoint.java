package com.example.national_bank_xml.National_bank_xml.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;


@Endpoint
public class NationalBankEndpoint {

	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";

	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload GetNalogZaPlacanjeRequest request) {
		GetNalogZaPlacanjeResponse response = new GetNalogZaPlacanjeResponse();
		System.out.println("usao narodna banka");
		//response.setCountry(countryRepository.findCountry(request.getName()));
		//bankClient.sendToNationalBank(response.getNalogZaPlacanje());
		return response;
	}
	
}
