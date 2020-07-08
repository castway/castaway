package com.tomankiewicz.rafal.castawayspringboot.email;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Component
public class EmailContext {
	
	private Map<String, Object> model;
	private Context context;
	
	@Autowired
	public EmailContext() {
		
		this.model = new HashMap<String, Object>();
		this.context = new Context();
	}

	public Context setMessageContext(User theUser) {

		model.put("username", theUser.getUsername());
		context.setVariables(model);
		
		return context;
	}
	

}
