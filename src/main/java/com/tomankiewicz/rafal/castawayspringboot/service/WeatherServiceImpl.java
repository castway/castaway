package com.tomankiewicz.rafal.castawayspringboot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

@Service
public class WeatherServiceImpl implements WeatherService {

	private final String URL = "https://www.metaweather.com/api/location/523920/";
	
	@Override
	public Weather callApi(@Valid Catch theCatch) throws IOException{

		String date = theCatch.formatDate();
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url(URL + date).build();
		ObjectMapper mapper = new ObjectMapper();
		
		List<Weather> weatherList;
		
		try (ResponseBody responseBody = client.newCall(request).execute().body()) {

			/**
			 * retrieving the list of weather objects (the weather API responds with a list
			 * of weather measurements - for now only one exemplary measurement is needed,
			 * so the first one from the list is retrieved
			 **/

				weatherList = mapper.readValue(responseBody.string(), new TypeReference<List<Weather>>() {
				});
			} catch (IOException e) {
				
				// will throw custom exception to notify about weather API error
				weatherList = new ArrayList<Weather>();
				weatherList.add(new Weather());
			}
			
			return weatherList.get(0);
		
	}

}
