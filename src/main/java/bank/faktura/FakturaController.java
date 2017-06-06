package bank.faktura;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.firm.FirmService;
import bank.firm.Firma;
import bank.user.User;
import bank.user.UserService;

@RestController
@RequestMapping("/faktura")
public class FakturaController {

	@Autowired
	private FakturaService fakturaService;
	@Autowired
	private FirmService firmService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<Faktura> getFakture(){
		User user = getUserDetails();
		Firma firm = user.getFirma();
		List<Faktura> fakture = new ArrayList<Faktura>();
		for(int i = 0 ; i < firm.getFakture().size(); i++){
			if(!firm.getFakture().get(i).isObradjena()){
				fakture.add(firm.getFakture().get(i));
			}
		}
		return fakture;
	}
	
	@GetMapping("/findFirm")
	public Firma findFirm(){
		User user = getUserDetails();
		Firma firm = user.getFirma();
		return firm;
	}
	
	@PostMapping
	public void addFaktura(@Valid @RequestBody Faktura faktura){
		Firma firm = firmService.findByName(faktura.getNazivKupca());
		faktura.setFirma(firm);
		faktura.setObradjena(false);
		fakturaService.save(faktura);
		
		
	}
	
	@GetMapping("/findAllFirms")
	public List<Firma> findAllFirms(){
		User user = getUserDetails();
		Firma firm = user.getFirma();
		List<Firma> firms = new ArrayList<Firma>();
		for(int i = 0 ; i < firmService.findAll().size(); i++){
			if(firmService.findAll().get(i).getId() != firm.getId()){
				firms.add(firmService.findAll().get(i));
			}
		}
		return firms;
	}
	
	private User getUserDetails() {

		SecurityContext context = SecurityContextHolder.getContext();

		Authentication authentication = context.getAuthentication();

		/*org.springframework.security.core.userdetails.UserDetails currentUser = userDetailsService
				.loadUserByUsername(authentication.getName());*/
		
		User user = userService.findByUsername(authentication.getName());


		return user;

	}
	
	
	
}
