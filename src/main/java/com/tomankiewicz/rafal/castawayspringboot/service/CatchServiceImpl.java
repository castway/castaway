package com.tomankiewicz.rafal.castawayspringboot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomankiewicz.rafal.castawayspringboot.dao.CatchDao;
import com.tomankiewicz.rafal.castawayspringboot.dao.WeatherDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


@Service
class CatchServiceImpl implements CatchService {

	private CatchDao catchDao;
	private WeatherDao weatherDao;

	@Autowired
	public CatchServiceImpl(CatchDao catchDao, WeatherDao weatherDao) {

		this.catchDao = catchDao;
		this.weatherDao = weatherDao;
	}

	@Override
	@Transactional
	public List<Catch> getCatchList() {
		return catchDao.getCatchList();
	}

	@Override
	@Transactional
	public Catch findById(int id) {
		return catchDao.findById(id);
	}

	@Override
	@Transactional
	public void delete(int id) {
		catchDao.delete(id);

	}

	@Override
	@Transactional
	public void save(Catch theCatch) {

		catchDao.save(theCatch);
	}

	public Weather callApi(Catch theCatch) throws IOException {

		OkHttpClient client = new OkHttpClient();

		String date = theCatch.formatDate();
		Request request = new Request.Builder().url("https://www.metaweather.com/api/location/523920/" + date).build();
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

	@Override
	@Transactional
	public void deleteWeather(Long id) {
		weatherDao.delete(id);
	}

	@Override
	@Transactional
	public Map<String, Integer> getAllCatchesSortedByUser() {
		
		List<Catch> allCatches = catchDao.getAllCatches();
		
		// Create a map with username as key and points sum as value:
		Map<String, Integer> mapGroupedByUsers = allCatches.stream()
														  .collect(Collectors.groupingBy((c) -> c.getUser().getUsername(), Collectors.summingInt(Catch::calculatePoints)));
		
		// return map sorted by number of points
		
		return mapGroupedByUsers.entrySet().stream()
										   .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
										   .collect(Collectors.toMap(Map.Entry::getKey,  Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
	}

}
