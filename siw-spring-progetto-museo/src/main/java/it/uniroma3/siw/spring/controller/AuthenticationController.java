package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.service.CredentialsService;

@Controller
public class AuthenticationController {

	
	@Autowired
	private CredentialsService credentialsService;
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		
		logger.debug("Admin: login");
		return "loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		
		logger.debug("Admin: logout");
		return "home";
	}
	
	@GetMapping("/init")
	public String init(Model model) {
		
		Credentials credentials=new Credentials("admin","admin");
		try {
			credentialsService.saveCredentials(credentials);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
}
