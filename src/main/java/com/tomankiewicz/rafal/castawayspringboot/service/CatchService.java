package com.tomankiewicz.rafal.castawayspringboot.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

public interface CatchService {

	List<Catch> getCatchList();

	Catch findById(int id);

	void delete(int id);

	void save(Catch theCatch) throws IOException;

	void deleteWeather(Long id);

	Map<String, Integer> getAllCatchesSortedByUser();

	boolean checkIfNewCatchIsBeingCreated(@Valid Catch theCatch);

	boolean checkIfDateIsBeingUpdated(@Valid Catch theCatch);

	Weather getWeatherFromExistingCatch(@Valid Catch theCatch);

}
