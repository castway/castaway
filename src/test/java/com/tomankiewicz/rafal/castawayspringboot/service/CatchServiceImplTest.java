package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.then;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.tomankiewicz.rafal.castawayspringboot.dao.CatchDao;
import com.tomankiewicz.rafal.castawayspringboot.dao.WeatherDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;
import com.tomankiewicz.rafal.castawayspringboot.species.Fish;

@ExtendWith(MockitoExtension.class)
class CatchServiceImplTest {

	@Mock
	private CatchDao catchDao;
	
	@Mock
	private WeatherDao weatherDao;
	
	@InjectMocks
	private CatchServiceImpl catchService;
	

	
	@Test
	void getAllCatchesSortedByUserMethodShouldSortTheGroupedMapByPointsDescending() {
		
		//given
		List<Catch> catches = new ArrayList<>();
		
		User user1 = new User();
		user1.setUsername("joe");
		
		User user2 = new User();
		user2.setUsername("sue");
		
		Catch big = new Catch(1, Fish.PIKE, new BigDecimal(100), new BigDecimal(9), user1, new Weather());
		Catch small1 = new Catch(1, Fish.ROACH, new BigDecimal(25), new BigDecimal(0.2), user2, new Weather());
		Catch small2 = new Catch(2, Fish.PERCH, new BigDecimal(20), new BigDecimal(0.2), user2, new Weather());
		
		catches.add(big);
		catches.add(small1);
		catches.add(small2);
		
		given(catchDao.getAllCatches()).willReturn(catches);
		
		//when
		Map<String, Integer> map = catchService.getAllCatchesSortedByUser();
		
		//then
		assertThat(map.size(), is(2));
		assertThat(map.keySet().toArray()[0].toString(), is("joe"));
		
	}
	
	@Test
	void dateUpdateCheckerMethodShouldReturnTrueIfDateUpdated() {
		
		//given
		Catch theCatch = new Catch();
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		theCatch.setDate(today);
		
		Catch existingCatch = new Catch();
		Weather weather = new Weather();
		weather.setApplicable_date(yesterday);
		existingCatch.setWeather(weather);
		
		given(catchService.findById(theCatch.getId())).willReturn(existingCatch);
		
		boolean dateUpdated = catchService.checkIfDateIsBeingUpdated(theCatch);
		
		assertTrue(dateUpdated);
		
	}
	
	@Test
	void dateUpdateCheckerMethodShouldReturnFalseIfDateNotUpdated() {
		
		//given
		Catch theCatch = new Catch();
		LocalDate today = LocalDate.now();
		theCatch.setDate(today);
		
		Catch existingCatch = new Catch();
		Weather weather = new Weather();
		weather.setApplicable_date(today);
		existingCatch.setWeather(weather);
		
		given(catchService.findById(theCatch.getId())).willReturn(existingCatch);
		
		boolean dateUpdated = catchService.checkIfDateIsBeingUpdated(theCatch);
		
		assertFalse(dateUpdated);
		
	}
	
	

}
