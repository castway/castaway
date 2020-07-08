package com.tomankiewicz.rafal.castawayspringboot.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Component
public class MessageCreator {

	private EmailContext emailContext;
	private EmailEditor emailEditor;
	
	@Autowired
	public MessageCreator(EmailContext emailContext, EmailEditor emailEditor) {
		
		this.emailContext = emailContext;
		this.emailEditor = emailEditor;
	}
	
	public MimeMessage createMimeMessage(JavaMailSender mailSender) {
		
		return mailSender.createMimeMessage();
	}
	
	public void prepareWelcomeMessage(MimeMessage mailMessage, User theUser) throws MessagingException {
		
		Context context = emailContext.setMessageContext(theUser);
		emailEditor.setMessageContentForWelcomeEmail(theUser, mailMessage, context);
		
	}
	
}
