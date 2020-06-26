package com.tomankiewicz.rafal.castawayspringboot.controller;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	// Custom data binder to remove whitespaces from user input

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@Autowired
	public UserController(UserService userService) {

		this.userService = userService;
	}

	@GetMapping("/registration")
	public String showRegistrationForm(Model theModel) {

		User user = new User();
		theModel.addAttribute("user", user);

		return "/registration-form";
	}

	@PostMapping("/registration/registerUser")
	public String registerUser(@Valid @ModelAttribute("user") User user, Errors errors, Model theModel) {

		if (errors.hasErrors()) {
			return "/registration-form";
		}

		User existing = null;
		// Checking if username exists:
		try {
		
			existing = userService.findByUsername(user.getUsername());
		
		} catch (Exception e) {
			
		}
		
		if (existing != null) {
			theModel.addAttribute("user", new User());
			theModel.addAttribute("registrationErrorUsername", "Username already exists");
			return "/registration-form";
		}

		// Checking if email exists:
		User existingEmail = null;
		
		try {
		
			existingEmail = userService.findByEmail(user.getEmail());
		
		} catch (Exception e) {
			
		} 
		

		if (existingEmail != null) {
			theModel.addAttribute("user", new User());
			theModel.addAttribute("registrationErrorEmail", "Email already exists");
			return "/registration-form";
		}

		userService.save(user);

		return "/registration-confirmation";
	}
}
