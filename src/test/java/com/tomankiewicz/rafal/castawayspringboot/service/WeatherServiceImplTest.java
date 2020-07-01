package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

class WeatherServiceImplTest {

	
	@Test
	void callApiMethodShouldPerformACallToWeatherApi() throws IOException {
		
		//given
		Catch theCatch = new Catch();
		theCatch.setDate(LocalDate.now());
		WeatherService weatherService = new WeatherServiceImpl();
		
		//when
		Weather weather = weatherService.callApi(theCatch);
		
		//then
		assertNotNull(weather);
		assertNotNull(weather.getApplicable_date());    // means no exception was thrown. Exception can be thrown during a call to API
		
		
	}

}
