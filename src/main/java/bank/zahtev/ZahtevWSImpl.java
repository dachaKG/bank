/*package bank.zahtev;

import java.io.File;
import java.util.List;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import localhost._8080.__.bank.ws.ZahtevZaDobijanjeIzvoda;

@WebService
public class ZahtevWSImpl implements ZahtevWS {

	Zahtevi zahtevi = new Zahtevi();

	@Override
	public List<ZahtevZaDobijanjeIzvoda> findAll() {
		return zahtevi.getZahtevi();
	}

	@Override
	public ZahtevZaDobijanjeIzvoda findOne(String brojRacuna) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ZahtevZaDobijanjeIzvoda zahtevZaDobijanjeIzvoda) {
		// TODO Auto-generated method stub
		//zahtevi.getZahtevi().add(zahtevZaDobijanjeIzvoda);
		// System.out.println(analyticsOfStatements);
		//zahtevi = new Zahtevi();
		zahtevi.getZahtevi().add(zahtevZaDobijanjeIzvoda);
		File file = new File("zahtevi.xml");
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Zahtevi.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(zahtevi, file);
			jaxbMarshaller.marshal(zahtevi, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
*/