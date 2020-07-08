package com.tomankiewicz.rafal.castawayspringboot.service;

import javax.mail.MessagingException;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

public interface EmailService {

	void sendWelcomeEmail(User theUser) throws MessagingException;

}
