package com.tomankiewicz.rafal.castawayspringboot.email;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class MessageCreatorTest {

	@Mock
	private EmailContext emailContext;
	
	@Mock
	private EmailEditor emailEditor;
	
	@InjectMocks
	private MessageCreator messageCreator;

	
	@Test
	void messageCreatorShouldOrderToSetMessageContext() throws MessagingException {
		
		//given
		User theUser = mock(User.class);
		MimeMessage mailMessage = mock(MimeMessage.class);
		
		//when
		messageCreator.prepareWelcomeMessage(mailMessage, theUser);
		
		//then
		then(emailContext).should().setMessageContext(theUser);
	}
	
	@Test
	void messageCreatorShouldOrderToSetTheMessageContents() throws MessagingException {
		
		//given
		User theUser = mock(User.class);
		MimeMessage mailMessage = mock(MimeMessage.class);
		Context context = new Context();
		given(emailContext.setMessageContext(theUser)).willReturn(context);
		
		//when
		messageCreator.prepareWelcomeMessage(mailMessage, theUser);
		
		//then
		then(emailEditor).should().setMessageContentForWelcomeEmail(theUser, mailMessage, context);
		
	}
	
	@Test
	void messageContextShouldContainTheUsername() {
		
		//given
		User theUser = new User();
		theUser.setUsername("testuser");
		EmailContext emailContextNotMock = new EmailContext();
		
		//when
		Context context = emailContextNotMock.setMessageContext(theUser);

		//then
		assertTrue(context.containsVariable("username"));
		assertTrue(context.getVariable("username").equals("testuser"));
		
	}
	

	
		
		
		
		
		
		
		
	

}
