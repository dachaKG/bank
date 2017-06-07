package bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
		/*try {
			System.out.println("peraaaa");
			javax.xml.ws.Endpoint.publish("http://localhost:8081/ws/zahtev", new ZahtevWSImpl());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
