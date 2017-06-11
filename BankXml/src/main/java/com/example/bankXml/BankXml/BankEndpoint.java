package com.example.bankXml.BankXml;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;


@Endpoint
public class BankEndpoint {

	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";

	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload GetNalogZaPlacanjeRequest request) {
		GetNalogZaPlacanjeResponse response = new GetNalogZaPlacanjeResponse();
		//response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}
	
}
