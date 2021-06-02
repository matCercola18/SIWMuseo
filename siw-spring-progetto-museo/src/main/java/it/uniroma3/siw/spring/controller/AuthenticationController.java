package it.uniroma3.siw.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "home";
	}
	
}
