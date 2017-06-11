package com.example.bankXml.BankXml.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.bankXml.BankXml.client.BankClient;
import com.example.bankXml.BankXml.firm.FirmService;
import com.example.bankXml.BankXml.firm.Firma;
import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.NalogZaPlacanje;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaRequest;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaResponse;
import com.strukturartgsnaloga.ObjectFactory;
import com.strukturartgsnaloga.StrukturaRtgsNaloga;


@Endpoint
public class BankEndpoint {
	
	@Autowired
	private BankClient bankClient;

	@Autowired
	private FirmService firmService;
	
	
	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";

	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload GetNalogZaPlacanjeRequest request) {
		NalogZaPlacanje nalogZaPlacanje = request.getNalogZaPlacanje();
		String oznakaBankeDuznika = request.getNalogZaPlacanje().getRacunDuznika().substring(0, 3);
		String oznakaBankePoverioca = request.getNalogZaPlacanje().getRacunPoverioca().substring(0,3);
		Firma duznik = firmService.findByAccount(request.getNalogZaPlacanje().getRacunDuznika());
		Firma poverilac = firmService.findByAccount(request.getNalogZaPlacanje().getRacunPoverioca());

		if(oznakaBankeDuznika.equals(oznakaBankePoverioca)){
		//u istoj su banci, samo prebaci novac sa racuna na racun
			
			duznik.setStanjeRacuna(duznik.getStanjeRacuna() - request.getNalogZaPlacanje().getIznos().intValue());
			firmService.save(duznik);
			poverilac.setStanjeRacuna(poverilac.getStanjeRacuna()+ request.getNalogZaPlacanje().getIznos().intValue());
			firmService.save(poverilac);
		}
		else if(request.getNalogZaPlacanje().isHitno() || request.getNalogZaPlacanje().getIznos().intValue() > 250000){
			//RTGS
			ObjectFactory factory = new ObjectFactory();
			GetStrukturaRtgsNalogaRequest rtgs = factory.createGetStrukturaRtgsNalogaRequest();
			
			StrukturaRtgsNaloga strukturaRtgsNaloga = factory.createStrukturaRtgsNaloga();
			strukturaRtgsNaloga.setDatumNaloga(nalogZaPlacanje.getDatumNaloga());
			strukturaRtgsNaloga.setDatumValute(nalogZaPlacanje.getDatumValute());
			strukturaRtgsNaloga.setDuznikNalogodavac(nalogZaPlacanje.getDuznikNalogodavac());
			strukturaRtgsNaloga.setIznos(nalogZaPlacanje.getIznos());
			strukturaRtgsNaloga.setModelOdobrenja(nalogZaPlacanje.getModelOdobrenja());
			strukturaRtgsNaloga.setModelZaduzenja(nalogZaPlacanje.getModelZaduzenja());
			strukturaRtgsNaloga.setObracunskiRacunBankeDuznika(duznik.getBank().getObracunskiRacunBanke());
			strukturaRtgsNaloga.setObracunskiRacunBankePoverioca(poverilac.getBank().getObracunskiRacunBanke());
			strukturaRtgsNaloga.setPozivNaBrojOdobrenja(nalogZaPlacanje.getPozivNaBrojOdobrenja());
			strukturaRtgsNaloga.setPozivNaBrojZaduzenja(nalogZaPlacanje.getPozivNaBrojZaduzenja());
			strukturaRtgsNaloga.setPrimalacPoverilac(nalogZaPlacanje.getPrimalacPoverilac());
			strukturaRtgsNaloga.setRacunDuznika(nalogZaPlacanje.getRacunDuznika());
			strukturaRtgsNaloga.setRacunPoverioca(nalogZaPlacanje.getRacunPoverioca());
			strukturaRtgsNaloga.setSifraValute(nalogZaPlacanje.getOznakaValute());
			strukturaRtgsNaloga.setSvrhaPlacanja(nalogZaPlacanje.getSvrhaPlacanja());
			strukturaRtgsNaloga.setSwiftKodBankeDuznika(duznik.getBank().getSwiftKodBanke());
			strukturaRtgsNaloga.setSwiftKodBankePoverioca(poverilac.getBank().getSwiftKodBanke());
			strukturaRtgsNaloga.setIdPoruke("MT103");
			
			rtgs.setStrukturaRtgsNaloga(strukturaRtgsNaloga);
			
			GetStrukturaRtgsNalogaResponse response = bankClient.sendMt103ToNationalBank(rtgs);
			if(response.getMt900().getIdPoruke().equals("MT900")){
				duznik.setStanjeRacuna(duznik.getStanjeRacuna()-response.getMt900().getIznos().intValue());
				firmService.save(duznik);
			}
		}
		else{
			//MT102
		}
		
		
		
		
		
		GetNalogZaPlacanjeResponse response = new GetNalogZaPlacanjeResponse();
		bankClient.sendToNationalBank(response.getNalogZaPlacanje());
		return response;
	}
	
}
