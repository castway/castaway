package com.tomankiewicz.rafal.castawayspringboot.email;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Component
public class EmailEditor {
	
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	public EmailEditor(SpringTemplateEngine templateEngine) {
		
		this.templateEngine = templateEngine;
	}

	public MimeMessageHelper setMessageContentForWelcomeEmail(User theUser, MimeMessage mailMessage, Context context) throws MessagingException {

		MimeMessageHelper helper = new MimeMessageHelper(mailMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

		helper.setTo(theUser.getEmail());
		helper.setSubject("Welcome to CASTAWAY");
		helper.setFrom("CASTAWAY");

		String html = templateEngine.process("welcome-email", context);
		helper.setText(html, true);
		
		return helper;
	}

}
