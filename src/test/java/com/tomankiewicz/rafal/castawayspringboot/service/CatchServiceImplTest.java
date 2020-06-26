package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

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
	void callApiMethodShouldConvertTheDateToAFormatAcceptedByWeatherApi() {
		
		//given
		Catch theCatch = new Catch();
		theCatch.setDate(LocalDate.now());
		
		//when
		String date = theCatch.formatDate();
		Pattern correctPattern = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");  //  YYYY/MM/DD
		Pattern rawPattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");		 //  YYYY-MM-DD
		
		//then
		assertThat(date, matchesPattern(correctPattern));
		assertThat(LocalDate.now().toString(), matchesPattern(rawPattern));
	}
	
	@Test
	void callApiMethodShouldPerformACallToWeatherApi() throws IOException {
		
		//given
		Catch theCatch = new Catch();
		theCatch.setDate(LocalDate.now());
		
		//when
		Weather weather = catchService.callApi(theCatch);
		
		//then
		assertNotNull(weather);
		assertNotNull(weather.getApplicable_date());    // means no exception was thrown. Exception can be thrown during a call to API
		
		
	}
	
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

}
