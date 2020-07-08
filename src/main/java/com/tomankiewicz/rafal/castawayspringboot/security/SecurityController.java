package com.tomankiewicz.rafal.castawayspringboot.security;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	private Logger logger = Logger.getLogger(getClass().getName());

	@GetMapping("")
	public String redirectToWelcomeOrCatchList() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		logger.info(auth.toString());
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			return "redirect:/catch/catchList";
		}
		
		return "welcome";
	}
	
	@GetMapping("/")
	public String redirectToWelcomeOrCatchList2() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info(auth.toString());
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			return "redirect:/catch/catchList";
		}
		
		return "welcome";
	}
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@GetMapping("/welcome")
	public String showLandingPage() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info(auth.toString());
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			
			return "redirect:/catch/catchList";
		}
		
		return "welcome";
	}
	
	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	
	@GetMapping("/contact")
	public String showContactPage() {
		return "contact";
	}
}
