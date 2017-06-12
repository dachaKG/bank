package com.example.bankXml.BankXml.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.bankXml.BankXml.mt102.GetMt102Request;
import com.example.bankXml.BankXml.mt102.GetMt102Response;
import com.example.bankXml.BankXml.mt102.Mt102;
import com.example.bankXml.BankXml.mt102.Mt102Service;
import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.NalogZaPlacanje;
import com.nalogzaplacanje.ObjectFactory;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaRequest;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaResponse;

@Component
public class BankClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	
	public void sendToNationalBank(NalogZaPlacanje nalog){
		ObjectFactory factory = new ObjectFactory();
		GetNalogZaPlacanjeRequest nalogZaPlacanjeRequest = factory.createGetNalogZaPlacanjeRequest();
		
		GetNalogZaPlacanjeResponse response = (GetNalogZaPlacanjeResponse) webServiceTemplate.marshalSendAndReceive(nalogZaPlacanjeRequest);
		
	}
	
	public GetStrukturaRtgsNalogaResponse sendMt103ToNationalBank(GetStrukturaRtgsNalogaRequest request){
		
		return (GetStrukturaRtgsNalogaResponse) webServiceTemplate.marshalSendAndReceive(request);
	}
	
	public GetMt102Response sendMt102ToNationalBank(Mt102 mt102){
		com.example.bankXml.BankXml.mt102.ObjectFactory factory = new com.example.bankXml.BankXml.mt102.ObjectFactory();
		
		GetMt102Request request = factory.createGetMt102Request();
		request.setMt102(mt102);
		
		return (GetMt102Response) webServiceTemplate.marshalSendAndReceive(request);
		
		/*response.getMt102().setObradjen(true);
		mt102Service.save(response.getMt102());*/
		//return null;
	}
	
}
