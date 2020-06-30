package com.tomankiewicz.rafal.castawayspringboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.never;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Mock
	private UserService userService;
	
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
	void postRequestToRegisterUserShouldNotTriggerSaveMethodOnServiceIfErrors() {
		
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
	void postRequestToRegisterUserShouldReturnRegistrationFormIfErrors() {
		
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
	void postRequestToRegisterUserShouldNotTriggerSaveOnServiceIfUsernameExists() {
		
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
	void postRequestToRegisterUserShouldReturnRegistrationFormIfUsernameExists() {
		
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
	void postRequestToRegisterUserShouldNotTriggerSaveOnServiceIfEmailExists() {
		
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
	void postRequestToRegisterUserShouldReturnRegistrationFormIfEmailExists() {
		
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
	void postRequestToRegisterUserShouldTriggerSaveOnServiceIfUserDoesNotExist() {
		
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
	void postRequestToRegisterUserShouldReturnConfirmationIfUserDoesNotExist() {
		
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
}
