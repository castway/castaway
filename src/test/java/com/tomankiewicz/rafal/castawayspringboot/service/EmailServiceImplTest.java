package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.tomankiewicz.rafal.castawayspringboot.email.MessageCreator;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

	@Mock
	private MessageCreator messageCreator;
	
	@Mock
	private JavaMailSender mailSender;
	
	@Test
	void messageCreatorShouldCreateTheMailMessage() throws MessagingException {
		
		//given
		EmailServiceImpl emailService = new EmailServiceImpl(messageCreator, mailSender);
		User theUser = mock(User.class);
		
		//when
		emailService.sendWelcomeEmail(theUser);
				
		//then
		then(messageCreator).should().createMimeMessage(mailSender);
	}
	
	@Test
	void sendWelcomeEmailMethodShouldSendMailMessage() throws MessagingException {

		//given
		EmailServiceImpl emailService = new EmailServiceImpl(messageCreator, mailSender);
		User theUser = mock(User.class);
		MimeMessage mailMessage = mock(MimeMessage.class);

		given(messageCreator.createMimeMessage(mailSender)).willReturn(mailMessage);
		
		//when
		emailService.sendWelcomeEmail(theUser);
		
		//then
		then(mailSender).should().send(mailMessage);
		
	}
	
	
	@Test
	void messageCreatorShouldPrepareTheContentsOfTheMailMessage() throws MessagingException {
	
		//given
		EmailServiceImpl emailService = new EmailServiceImpl(messageCreator, mailSender);
		User theUser = mock(User.class);
		MimeMessage mailMessage = mock(MimeMessage.class);
		given(messageCreator.createMimeMessage(mailSender)).willReturn(mailMessage);

		//when
		emailService.sendWelcomeEmail(theUser);
		
		//then
		then(messageCreator).should().prepareWelcomeMessage(mailMessage, theUser);
	}
	
}
