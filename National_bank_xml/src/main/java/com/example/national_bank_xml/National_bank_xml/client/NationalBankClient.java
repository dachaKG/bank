package com.example.national_bank_xml.National_bank_xml.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.strukturartgsnaloga.GetMt910Request;
import com.strukturartgsnaloga.GetMt910Response;

@Component
public class NationalBankClient {

	@Autowired
	private WebServiceTemplate webServiceTemplate;
	
	public GetMt910Response sendMt910(GetMt910Request request){
		//webServiceTemplate.setDefaultUri("");
		
		GetMt910Response response  = (GetMt910Response) webServiceTemplate.marshalSendAndReceive(request);
		return response;
	}
	
}
