package com.tomankiewicz.rafal.castawayspringboot.email;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class EmailContextTest {

	
	@Test
	void setMethodShouldSetUsernameAsContextVariable() {

		//given
		EmailContext emailContext = new EmailContext();
		User theUser = new User();
		theUser.setUsername("testuser");
		
		//when
		Context context = emailContext.setMessageContext(theUser);
		
		//then
		assertTrue(context.getVariableNames().size() == 1);
		assertTrue(context.getVariable("username").equals(theUser.getUsername()));
	}

}
