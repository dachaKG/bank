package com.example.bankXml.BankXml.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.NalogZaPlacanje;
import com.nalogzaplacanje.ObjectFactory;

@Component
public class BankClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	public void sendToNationalBank(NalogZaPlacanje nalog){
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest nalogZaPlacanjeRequest = factory.createGetNalogZaPlacanjeRequest();
		
		GetNalogZaPlacanjeResponse response = (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);
		
	}
	
}
