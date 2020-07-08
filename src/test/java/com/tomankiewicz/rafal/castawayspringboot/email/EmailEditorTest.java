package com.tomankiewicz.rafal.castawayspringboot.email;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class EmailEditorTest {

	@Mock
	private SpringTemplateEngine templateEngine;
	
	@Test
	void emailEditorShouldSetEmailsToFromSubject() throws MessagingException, IOException {
		
		//given
		templateEngine = new SpringTemplateEngine();
		
		User theUser = new User();
		theUser.setEmail("example@email.com");
		theUser.setUsername("testuser");
		
		JavaMailSender mailSender = new JavaMailSenderImpl();
		MimeMessage mailMessage = mailSender.createMimeMessage();
		Context context = new Context();
		
		EmailEditor emailEditor = new EmailEditor(templateEngine);
		
		//when
		MimeMessageHelper helper = emailEditor.setMessageContentForWelcomeEmail(theUser, mailMessage, context);
		
		//then
		assertTrue(helper.getMimeMessage().getFrom() != null, "Message author missing");
		assertTrue(helper.getMimeMessage().getRecipients(Message.RecipientType.TO) != null, "Message recipient missing");
		assertTrue(helper.getMimeMessage().getSubject() != null, "Message subject missing");

		
	}

}
