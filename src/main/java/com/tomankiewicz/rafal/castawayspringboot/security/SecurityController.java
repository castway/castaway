package com.tomankiewicz.rafal.castawayspringboot.security;

import java.util.Map;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.service.EmailService;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@Controller
public class SecurityController {
	
	private Logger logger = Logger.getLogger(getClass().getName());
	private UserService userService;
	private EmailService emailService;

	@Autowired
	public SecurityController(UserService userService, EmailService emailService) {
		this.userService = userService;
		this.emailService = emailService;
	}

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
//		logger.info(auth.toString());
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
//		logger.info(auth.toString());
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
	
	@GetMapping("/privacy-policy")
	public String showPrivacyPolicy() {
		return "privacy-policy";
	}
	
	@GetMapping("/oauth2LoginSuccess")
	public String getLoginInformation(@AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) throws MessagingException {
		
		// get name and email
		
		OAuth2User authenticatedUser = authenticationToken.getPrincipal();
		Map<String, Object> userDetails = authenticatedUser.getAttributes();
		
		User user = new User();
		user.setUsername(userDetails.get("name").toString());
		user.setEmail(userDetails.get("email").toString());
		
//		logger.info(userDetails.get("email").toString());
		
		// check if email already exists:
		
		
		User existingUser = userService.findByEmail(user.getEmail()); 
		
//		logger.info(existingUser);
		if (existingUser == null) {
			userService.save(user);
			emailService.sendWelcomeEmail(user);
		} 
		
		return "redirect:/catch/catchList";
	}
}
