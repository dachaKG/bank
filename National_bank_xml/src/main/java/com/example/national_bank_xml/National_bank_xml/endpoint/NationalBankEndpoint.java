package com.example.national_bank_xml.National_bank_xml.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.bankxml.bankxml.mt102.GetMt102Request;
import com.example.bankxml.bankxml.mt102.GetMt102Response;
import com.example.bankxml.bankxml.mt102.GetMt910RequestMt102;
import com.example.bankxml.bankxml.mt102.Mt102;
import com.example.national_bank_xml.National_bank_xml.bank.Bank;
import com.example.national_bank_xml.National_bank_xml.bank.BankService;
import com.example.national_bank_xml.National_bank_xml.client.NationalBankClient;
import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.strukturartgsnaloga.GetMt910Request;
import com.strukturartgsnaloga.GetMt910Response;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaRequest;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaResponse;
import com.strukturartgsnaloga.Mt900;
import com.strukturartgsnaloga.Mt910;
import com.strukturartgsnaloga.ObjectFactory;
import com.strukturartgsnaloga.StrukturaRtgsNaloga;


@Endpoint
public class NationalBankEndpoint {

	
	
	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";
	private static final String NAMESPACE_URI = "http://strukturaRtgsNaloga.com";
	private static final String NAMESPACE_URI2 = "http://mt102.BankXml.bankXml.example.com";
	
	
	@Autowired
	private NationalBankClient client;
	
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
		
		ObjectFactory factory = new ObjectFactory();
		GetMt910Request mt910Request = factory.createGetMt910Request();
		
		Mt910 mt910 = factory.createMt910();
		mt910.setDatumValute(null);
		mt910.setIdPoruke("MT910");
		mt910.setIdPorukeNaloga("Nalog za prenos");
		mt910.setIznos(rtgsNalog.getIznos());
		mt910.setObracunskiRacunBankePoverioca(rtgsNalog.getObracunskiRacunBankePoverioca());
		mt910.setSifraValute(rtgsNalog.getSifraValute());
		mt910.setSwiftKodBankePoverioca(rtgsNalog.getSwiftKodBankePoverioca());
		
		mt910Request.setMt910(mt910);
		mt910Request.setRtgsNalog(rtgsNalog);
		GetMt910Response mt910Response = client.sendMt910(mt910Request);
		
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
	
	@PayloadRoot(namespace = NAMESPACE_URI2, localPart = "getMt102Request")
	@ResponsePayload
	public GetMt102Response getMt102(@RequestPayload GetMt102Request request) {
		////
		com.example.bankxml.bankxml.mt102.ObjectFactory factory = new com.example.bankxml.bankxml.mt102.ObjectFactory();
		com.example.bankxml.bankxml.mt102.Mt910 mt910 = factory.createMt910();
		GetMt910RequestMt102 mt910Request = factory.createGetMt910RequestMt102();
		
		mt910.setDatumValute(null);
		mt910.setIdPoruke("MT910");
		mt910.setIdPorukeNaloga("Nalog za prenos");
		mt910.setIznos(request.getMt102().getUkupanIznos());
		mt910.setObracunskiRacunBankePoverioca(request.getMt102().getObracunskiRacunBankePoverioca());
		mt910.setSifraValute(request.getMt102().getSifraValute());
		mt910.setSwiftKodBankePoverioca(request.getMt102().getSWIFTKodBankePoverioca());
		
		mt910Request.setMt910(mt910);
		mt910Request.setMt102(request.getMt102());
		com.example.bankxml.bankxml.mt102.GetMt910Response mt910response = client.sendMt910mt102(mt910Request);
		
		/////////////////////
		com.example.bankxml.bankxml.mt102.Mt900 mt900 = new com.example.bankxml.bankxml.mt102.Mt900();
		mt900.setDatumValute(null);
		mt900.setIdPoruke("MT900");
		mt900.setIdPorukeNaloga("Nalog za prenos");
		mt900.setIznos(request.getMt102().getUkupanIznos());
		mt900.setObracunskiRacunBankeDuznika(request.getMt102().getObracunskiRacunBankeDuznika());
		mt900.setSifraValute(request.getMt102().getSifraValute());
		mt900.setSwiftBankeDuznika(request.getMt102().getSwiftKodBankeDuznika());
		GetMt102Response response = new GetMt102Response();
		response.setMt900(mt900);
	
		return response;
	}
}
