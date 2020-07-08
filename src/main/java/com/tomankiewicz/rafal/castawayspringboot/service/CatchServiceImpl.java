package com.tomankiewicz.rafal.castawayspringboot.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomankiewicz.rafal.castawayspringboot.dao.CatchDao;
import com.tomankiewicz.rafal.castawayspringboot.dao.WeatherDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;


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

	@Override
	public boolean checkIfNewCatchIsBeingCreated(@Valid Catch theCatch) {
		
		// If catch is being created, the object retrieved from model will have id == 0
		return theCatch.getId() == 0;
	}

	@Override
	public boolean checkIfDateIsBeingUpdated(@Valid Catch theCatch) {

		Catch existingCatch = findById(theCatch.getId());
		LocalDate existingDate = existingCatch.getWeather().getApplicable_date();
		
		return !existingDate.equals(theCatch.getDate());
	}

	@Override
	public Weather getWeatherFromExistingCatch(@Valid Catch theCatch) {

		Catch existingCatch = findById(theCatch.getId());
		
		return existingCatch.getWeather();
	}

}
