package com.tomankiewicz.rafal.castawayspringboot.service;

import java.io.IOException;

import javax.validation.Valid;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

public interface WeatherService {

	Weather callApi(@Valid Catch theCatch) throws IOException;

}
