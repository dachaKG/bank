package national_bank_xml.bank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bank")
public class BankController {
	
private final BankService bankService;

	@Autowired
	public BankController(final BankService service) {
		this.bankService = service;
	}
	
	@GetMapping
	public ResponseEntity<List<Bank>> findAll() {
		return new ResponseEntity<>(bankService.findAll(), HttpStatus.OK);
	}

}
