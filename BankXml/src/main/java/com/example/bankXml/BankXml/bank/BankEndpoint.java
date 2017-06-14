package com.example.bankXml.BankXml.bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.PrivateKey;
import java.util.Date;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.bankXml.BankXml.client.BankClient;
import com.example.bankXml.BankXml.firm.FirmService;
import com.example.bankXml.BankXml.firm.Firma;
import com.example.bankXml.BankXml.mt102.Mt102;
import com.example.bankXml.BankXml.mt102.Mt102Service;
import com.example.bankXml.BankXml.mt102.NalogZaMT102;
import com.example.bankXml.BankXml.mt102.NalogZaMT102Service;
import com.nalogzaplacanje.GetNalogZaPlacanjeRequest;
import com.nalogzaplacanje.GetNalogZaPlacanjeResponse;
import com.nalogzaplacanje.NalogZaPlacanje;
import com.strukturartgsnaloga.GetMt910Request;
import com.strukturartgsnaloga.GetMt910Response;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaRequest;
import com.strukturartgsnaloga.GetStrukturaRtgsNalogaResponse;
import com.strukturartgsnaloga.ObjectFactory;
import com.strukturartgsnaloga.StrukturaRtgsNaloga;

import encryption.KeyStoreReader;
import encryption.XMLEncryptionUtility;



@Endpoint
public class BankEndpoint {
	
	@Autowired
	private BankClient bankClient;

	@Autowired
	private FirmService firmService;
	
	@Autowired
	private Mt102Service mt102Service;
	
	@Autowired
	private NalogZaMT102Service nalogZaMt102Service;
	
	private static final String NAMESPACE_URI = "http://strukturaRtgsNaloga.com";

	private static final String NAMESPACE_URI1 = "http://nalogZaPlacanje.com";
	/*
	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload GetNalogZaPlacanjeRequest request) {
		NalogZaPlacanje nalogZaPlacanje = request.getNalogZaPlacanje();
		String oznakaBankeDuznika = request.getNalogZaPlacanje().getRacunDuznika().substring(0, 3);
		String oznakaBankePoverioca = request.getNalogZaPlacanje().getRacunPoverioca().substring(0,3);
		Firma duznik = firmService.findByAccount(request.getNalogZaPlacanje().getRacunDuznika());
		Firma poverilac = firmService.findByAccount(request.getNalogZaPlacanje().getRacunPoverioca());
		GetNalogZaPlacanjeResponse responseNalog = new GetNalogZaPlacanjeResponse();
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
			bankClient.sendToNationalBank(responseNalog.getNalogZaPlacanje());
		}
		else{
			//MT102
			Mt102 mt102 = mt102Service.checkBankAccount(duznik.getBank().getObracunskiRacunBanke(), poverilac.getBank().getObracunskiRacunBanke(),false);
			if(mt102 == null){
				mt102 = new Mt102();
				mt102.setIdPoruke("MT102");
				mt102.setSwiftKodBankeDuznika(duznik.getBank().getSwiftKodBanke());
				mt102.setSWIFTKodBankePoverioca(poverilac.getBank().getSwiftKodBanke());
				mt102.setObracunskiRacunBankePoverioca(poverilac.getBank().getObracunskiRacunBanke());
				mt102.setObracunskiRacunBankeDuznika(duznik.getBank().getObracunskiRacunBanke());
				mt102.setUkupanIznos(nalogZaPlacanje.getIznos());
				mt102.setDatum(new Date());
				mt102.setDatumValute(new Date());
				mt102.setSifraValute("RSD");
				mt102.setBanka(duznik.getBank());
				mt102.setObradjen(false);
				NalogZaMT102 nalog = new NalogZaMT102();
				nalog.setIdNalogaZaPlacanje("idNaloga");
				nalog.setDuznikNalogodavac(nalogZaPlacanje.getDuznikNalogodavac());
				nalog.setSvrhaPlacanja(nalogZaPlacanje.getSvrhaPlacanja());
				nalog.setPrimalacPoverilac(nalogZaPlacanje.getPrimalacPoverilac());
				nalog.setDatumNaloga(nalogZaPlacanje.getDatumNaloga());
				nalog.setRacunDuznika(nalogZaPlacanje.getRacunDuznika());
				nalog.setModelZaduzenja(nalogZaPlacanje.getModelZaduzenja());
				nalog.setPozivNaBrojZaduzenja(nalogZaPlacanje.getPozivNaBrojZaduzenja());
				nalog.setRacunPoverioca(nalogZaPlacanje.getRacunPoverioca());
				nalog.setModelOdobrenja(nalogZaPlacanje.getModelOdobrenja());
				nalog.setPozivNaBrojOdobrenja(nalogZaPlacanje.getPozivNaBrojOdobrenja());
				nalog.setIznos(nalogZaPlacanje.getIznos());
				nalog.setSifraValute("SRB");
				
				mt102 = mt102Service.save(mt102);
				nalog.setMt102(mt102);
				nalogZaMt102Service.save(nalog);
				//mt102.getNalogZaMT102().add(nalog);
				//nalog.setMt102(mt102);
				//mt102Service.save(mt102);
			} else {
				//Mt102 mt102 = new Mt102();
				NalogZaMT102 nalog = new NalogZaMT102();
				nalog.setIdNalogaZaPlacanje("idNaloga");
				nalog.setDuznikNalogodavac(nalogZaPlacanje.getDuznikNalogodavac());
				nalog.setSvrhaPlacanja(nalogZaPlacanje.getSvrhaPlacanja());
				nalog.setPrimalacPoverilac(nalogZaPlacanje.getPrimalacPoverilac());
				nalog.setDatumNaloga(nalogZaPlacanje.getDatumNaloga());
				nalog.setRacunDuznika(nalogZaPlacanje.getRacunDuznika());
				nalog.setModelZaduzenja(nalogZaPlacanje.getModelZaduzenja());
				nalog.setPozivNaBrojZaduzenja(nalogZaPlacanje.getPozivNaBrojZaduzenja());
				nalog.setRacunPoverioca(nalogZaPlacanje.getRacunPoverioca());
				nalog.setModelOdobrenja(nalogZaPlacanje.getModelOdobrenja());
				nalog.setPozivNaBrojOdobrenja(nalogZaPlacanje.getPozivNaBrojOdobrenja());
				nalog.setIznos(nalogZaPlacanje.getIznos());
				nalog.setSifraValute("SRB");
				//mt102.getNalogZaMT102().add(nalog);
				//mt102 = mt102Service.save(mt102);
				nalog.setMt102(mt102);
				
				nalogZaMt102Service.save(nalog);
				//mt102.setNalogZaMT102(nalogZaMT102);
				//mt102Service.save(mt102);
			}
		}

		GetNalogZaPlacanjeResponse response = new GetNalogZaPlacanjeResponse();
		
		return response;
	}
	*/
	
	
	@PayloadRoot(namespace = NAMESPACE_URI1, localPart = "getNalogZaPlacanjeRequest")
	@XmlAnyElement
	@ResponsePayload
	public GetNalogZaPlacanjeResponse getNalogZaPlacanje(@RequestPayload Element request) {
		System.out.println("--------Stigao---------");
		try{
			
		Document document = request.getOwnerDocument();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
		Document document1 = db.newDocument();
		NodeList nodeList = document.getElementsByTagNameNS("*", "nalogZaPlacanje");
		document1.appendChild(document1.adoptNode(nodeList.item(0).cloneNode(true)));
		saveDocument(document1,"C:\\Users\\Nebojsa\\Desktop\\dokument_Pristigao.xml");
		
		XMLEncryptionUtility encUtility = new XMLEncryptionUtility();
        KeyStoreReader ksReader = new KeyStoreReader();
		PrivateKey privateKey = ksReader.readPrivateKey("C:\\Users\\Nebojsa\\Desktop\\primer.jks", "primer", "primer", "primer");
		document1 = encUtility.decrypt(document1, privateKey);
		saveDocument(document1,"C:\\Users\\Nebojsa\\Desktop\\dokument_Dekriptovan.xml");
		
		
		}catch(Exception g)
		{
			g.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	
	
	private void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			
			transformer.transform(source, result);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMt910Request")
	@ResponsePayload
	public GetMt910Response getMt910(@RequestPayload GetMt910Request request){
		Firma poverilac = firmService.findByAccount(request.getRtgsNalog().getRacunPoverioca());
		poverilac.setStanjeRacuna(poverilac.getStanjeRacuna()+ request.getRtgsNalog().getIznos().intValue());
		firmService.save(poverilac);
		return null;
	}
	
	
	
}
