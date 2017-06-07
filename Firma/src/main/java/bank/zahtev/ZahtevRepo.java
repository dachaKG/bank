/*package bank.zahtev;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import localhost._8080.__.bank.ws.ZahtevZaDobijanjeIzvoda;

@Component
public class ZahtevRepo {
	

	private Zahtevi zahtevi = new Zahtevi();
	
	@PostConstruct
	public void initData(){
		ZahtevZaDobijanjeIzvoda zahtev1 = new ZahtevZaDobijanjeIzvoda();
		zahtev1.setBrojRacuna("111");
		//zahtev1.setDatum(new );
		zahtev1.setRedniBrojPreseka(new BigInteger("12131"));
		
		ZahtevZaDobijanjeIzvoda zahtev2 = new ZahtevZaDobijanjeIzvoda();
		zahtev2.setBrojRacuna("222");
		//zahtev1.setDatum(new );
		zahtev2.setRedniBrojPreseka(new BigInteger("12131"));
		
		
		ZahtevZaDobijanjeIzvoda zahtev3 = new ZahtevZaDobijanjeIzvoda();
		zahtev2.setBrojRacuna("333");
		//zahtev1.setDatum(new );
		zahtev2.setRedniBrojPreseka(new BigInteger("12131"));
		zahtevi.getZahtevi().add(zahtev1);
		zahtevi.getZahtevi().add(zahtev2);
		zahtevi.getZahtevi().add(zahtev3);
		
	}
	
	public List<ZahtevZaDobijanjeIzvoda> findAll(){
		return zahtevi.getZahtevi();
	}
	
	public ZahtevZaDobijanjeIzvoda findZahtev(String brojRacuna){
		Assert.notNull(brojRacuna, "Must not be null");
		for(int i = 0 ; i < zahtevi.getZahtevi().size(); i++){
			if(zahtevi.getZahtevi().get(i).getBrojRacuna().equals(brojRacuna)){
				return zahtevi.getZahtevi().get(i);
			}
		}
		return null;
	}

}
*/