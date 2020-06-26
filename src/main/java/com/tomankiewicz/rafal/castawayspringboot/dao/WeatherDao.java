package com.tomankiewicz.rafal.castawayspringboot.dao;

import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

public interface WeatherDao {

	void save(Weather theWeather);

	void delete(Long id);

}
