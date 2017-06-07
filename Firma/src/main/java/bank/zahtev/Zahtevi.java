/*package bank.zahtev;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import localhost._8080.__.bank.ws.ZahtevZaDobijanjeIzvoda;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "zahtevi")
public class Zahtevi {


	@XmlElement
	private List<ZahtevZaDobijanjeIzvoda> zahtevi;

	public List<ZahtevZaDobijanjeIzvoda> getZahtevi() {
		File file = new File("zahtevi.xml");

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Zahtevi.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setEventHandler(new MyValidationEventHandler());
			Zahtevi unmarshal = (Zahtevi) jaxbUnmarshaller.unmarshal(file);
			zahtevi = unmarshal.getZahtevi();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (zahtevi == null) {
			zahtevi = new ArrayList<ZahtevZaDobijanjeIzvoda>();
		}
		return zahtevi;
	}

	public void setZahtevi(List<ZahtevZaDobijanjeIzvoda> zahtevi) {
		this.zahtevi = zahtevi;
	}

}
*/