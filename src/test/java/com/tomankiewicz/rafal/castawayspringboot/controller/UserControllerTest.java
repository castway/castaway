package com.tomankiewicz.rafal.castawayspringboot.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.service.EmailService;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;
	
	@Mock
	private EmailService emailService;
	
	@InjectMocks
	private UserController userController;
	
	@Test
	void getRequestToRegistrationShouldReturnRegistrationFormViewUrl() {

		//given
		Model theModel = mock(Model.class);
		
		//when
		String output = userController.showRegistrationForm(theModel);
		
		//then
		assertThat(output, containsString("registration-form"));
	}
	
	@Test
	void postRequestToRegisterUserShouldNotTriggerSaveMethodOnServiceIfErrors() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(bindingResult.hasErrors()).willReturn(true);
		
		//when
		userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		then(userService).should(never()).save(theUser);
		
	}
	
	@Test
	void postRequestToRegisterUserShouldReturnRegistrationFormIfErrors() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(bindingResult.hasErrors()).willReturn(true);
		
		//when
		String output = userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		assertThat(output, containsString("registration-form"));
	}
	
	@Test
	void postRequestToRegisterUserShouldNotTriggerSaveOnServiceIfUsernameExists() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByUsername(theUser.getUsername())).willReturn(theUser);
	
		//when
		userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		then(userService).should(never()).save(theUser);
	}

	@Test
	void postRequestToRegisterUserShouldReturnRegistrationFormIfUsernameExists() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByUsername(theUser.getUsername())).willReturn(theUser);
	
		//when
		String output = userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		assertThat(output, containsString("registration-form"));
	}

	@Test
	void postRequestToRegisterUserShouldNotTriggerSaveOnServiceIfEmailExists() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByEmail(theUser.getEmail())).willReturn(theUser);
	
		//when
		userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		then(userService).should(never()).save(theUser);
	}
	
	@Test
	void postRequestToRegisterUserShouldReturnRegistrationFormIfEmailExists() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByEmail(theUser.getEmail())).willReturn(theUser);
	
		//when
		String output = userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		assertThat(output, containsString("registration-form"));
	}
	
	@Test
	void postRequestToRegisterUserShouldTriggerSaveOnServiceIfUserDoesNotExist() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByEmail(theUser.getEmail())).willReturn(null);
		given(userService.findByUsername(theUser.getUsername())).willReturn(null);
			
		//when
		userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		then(userService).should().save(theUser);
		
	}
	
	@Test
	void postRequestToRegisterUserShouldReturnConfirmationIfUserDoesNotExist() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindingResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(userService.findByEmail(theUser.getEmail())).willReturn(null);
		given(userService.findByUsername(theUser.getUsername())).willReturn(null);
			
		//when
		String output = userController.registerUser(theUser, bindingResult, theModel);
		
		//then
		assertThat(output, containsString("registration-confirmation"));
		
	}
	
	@Test
	void sendWelcomeEmailShouldBeInvokedAfterUserSaved() throws MessagingException {
		
		//given
		Model theModel = mock(Model.class);
		BindingResult bindinResult = mock(BindingResult.class);
		User theUser = mock(User.class);
		given(bindinResult.hasErrors()).willReturn(false);
		
		//when
		userController.registerUser(theUser, bindinResult, theModel);
		
		//then
		then(emailService).should().sendWelcomeEmail(theUser);
		
	}
}
