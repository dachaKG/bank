package com.example.national_bank_xml.National_bank_xml.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.national_bank_xml.National_bank_xml.bank.Bank;
import com.example.national_bank_xml.National_bank_xml.bank.BankService;
import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaRequest;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaResponse;
import com.strukturartgsnaloga.Mt900;
import com.strukturartgsnaloga.StrukturaRtgsNaloga;


@Endpoint
public class NationalBankEndpoint {

	
	
	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";
	private static final String NAMESPACE_URI = "http://strukturaRtgsNaloga.com";
	
	@Autowired
	private BankService bankService;
	
	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload GetNalogZaPlacanjeRequest request) {
		GetNalogZaPlacanjeResponse response = new GetNalogZaPlacanjeResponse();
		System.out.println("usao narodna banka");
		//response.setCountry(countryRepository.findCountry(request.getName()));
		//bankClient.sendToNationalBank(response.getNalogZaPlacanje());
		return response;
	}
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStrukturaRtgsNalogaRequest")
	@ResponsePayload
	public GetStrukturaRtgsNalogaResponse getStrukturaRtgsNaloga(@RequestPayload GetStrukturaRtgsNalogaRequest request) {
		StrukturaRtgsNaloga rtgsNalog = request.getStrukturaRtgsNaloga();
		Bank bankaDuznika = bankService.findBySwiftCode(rtgsNalog.getSwiftKodBankeDuznika());
		Bank bankaPoverioca = bankService.findBySwiftCode(rtgsNalog.getSwiftKodBankePoverioca());
		bankaPoverioca.setStanjeRacunaBanke(bankaPoverioca.getStanjeRacunaBanke() + rtgsNalog.getIznos().intValue());
		bankaDuznika.setStanjeRacunaBanke(bankaDuznika.getStanjeRacunaBanke()-rtgsNalog.getIznos().intValue());
		
		Mt900 mt900 = new Mt900();
		mt900.setDatumValute(null);
		mt900.setIdPoruke("MT900");
		mt900.setIdPorukeNaloga("Nalog za prenos");
		mt900.setIznos(rtgsNalog.getIznos());
		mt900.setObracunskiRacunBankeDuznika(bankaDuznika.getObracunskiRacunBanke());
		mt900.setSifraValute("RSD");
		mt900.setSwiftBankeDuznika(bankaDuznika.getSwiftKodBanke());
		
		
		
		GetStrukturaRtgsNalogaResponse response = new GetStrukturaRtgsNalogaResponse();
		response.setMt900(mt900);
		return response;
	}	
}
