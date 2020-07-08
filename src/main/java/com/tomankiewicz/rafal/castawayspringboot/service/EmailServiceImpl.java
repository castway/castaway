package com.tomankiewicz.rafal.castawayspringboot.service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tomankiewicz.rafal.castawayspringboot.email.MessageCreator;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Service
public class EmailServiceImpl implements EmailService {
	
	private MessageCreator messageCreator;
	
	private JavaMailSender mailSender;
	
	@Autowired
	public EmailServiceImpl(MessageCreator messageCreator, JavaMailSender mailSender) {

		this.messageCreator = messageCreator;
		this.mailSender = mailSender;
	}
	
	@Override
	public void sendWelcomeEmail(User theUser) throws MessagingException {
		
		
		MimeMessage mailMessage = messageCreator.createMimeMessage(mailSender);
		messageCreator.prepareWelcomeMessage(mailMessage, theUser);
		
		mailSender.send(mailMessage);
		
	}

	
	
	
	



}
