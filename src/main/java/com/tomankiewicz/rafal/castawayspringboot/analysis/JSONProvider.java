package com.tomankiewicz.rafal.castawayspringboot.analysis;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JSONProvider {

	private ObjectMapper mapper;
	
	public JSONProvider() {
		mapper = new ObjectMapper();
	}

	public <T> String prepareJsonFrom(List<T> list) throws JsonProcessingException {
		return mapper.writeValueAsString(list);
		
	}
	
	
}
