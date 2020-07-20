package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.tomankiewicz.rafal.castawayspringboot.analysis.TimeChartType;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip.CatchProperties;
import com.tomankiewicz.rafal.castawayspringboot.dao.CatchDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.species.Fish;


class ChartServiceImplTest {

	private ChartService chartService;
	private Tooltip tooltipProvider;
	
	@Test
	void prepareLabelsListMethodShouldReturnAListOfStrings() {

		//given
		chartService = new ChartServiceImpl(tooltipProvider);
		CatchDao catchDaoStub = new CatchDaoTestStub(LocalDate.of(2020, 5, 10), LocalDate.of(2020, 5, 10), LocalDate.of(2020, 5, 10));
		List<Catch> sortedList = catchDaoStub.getCatchList("username");
		
		//when
		List<String> list = chartService.prepareLabelsListForTimeChartFrom(sortedList);
		
		//then
		assertTrue(list.get(0) instanceof String);
	}
	
	@Test
	void labelsValuesShouldBeFormattedStringRepresentationsOfCorrespondingDates() {
		
		//given
		chartService = new ChartServiceImpl(tooltipProvider);
		
		LocalDate date1 = LocalDate.of(2020, 5, 10);
		LocalDate date2 = LocalDate.of(2018, 8, 10);
		LocalDate date3 = LocalDate.of(2019, 12, 10);
		Pattern pattern = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$");

		CatchDao catchDaoStub = new CatchDaoTestStub(date1, date2, date3);
		List<Catch> catchList = catchDaoStub.getCatchList("username");
		
		//when
		List<String> labelsList = chartService.prepareLabelsListForTimeChartFrom(catchList);
		Matcher matcher1 = pattern.matcher(labelsList.get(0));
		Matcher matcher2 = pattern.matcher(labelsList.get(1));
		Matcher matcher3 = pattern.matcher(labelsList.get(2));
		
		//then
		assertTrue(matcher1.matches() && matcher2.matches() && matcher3.matches());
	}
	
	@Test
	void labelsListShouldBeEmptyIfCreatedOutOfAnEmptyOrNullCatchList() {
		
		//given
		chartService = new ChartServiceImpl(tooltipProvider);
		List<Catch> nullList = null;
		List<Catch> emptyList = new ArrayList<Catch>();
		//when
		List<String> labelsListFromNull = chartService.prepareLabelsListForTimeChartFrom(nullList);
		List<String> labelsListFromEmpty = chartService.prepareLabelsListForTimeChartFrom(emptyList);
		
		//then
		assertNotNull(labelsListFromNull);
		assertThat(labelsListFromNull, is(IsEmptyCollection.empty()));
		assertNotNull(labelsListFromEmpty);
		assertThat(labelsListFromEmpty, is(IsEmptyCollection.empty()));

	}
	
	@Test
	void dataListShouldBeEmptyIfCreatedOutOfAnEmptyOrNullCatchList() {
		
		//given
		chartService = new ChartServiceImpl(tooltipProvider);
		List<Catch> nullList = null;
		
		//when
		List<CatchProperties> dataList = chartService.prepareDataListFrom(nullList, (c) -> c.getWeather().getAir_pressure());
		
		//then
		assertNotNull(dataList);
		assertThat(dataList, is(IsEmptyCollection.empty()));
	}
	
	@Test
	void dataListShouldContainCatchDetailDeterminedByLambdaExpression() {
		
		//given
		tooltipProvider = new Tooltip();
		chartService = new ChartServiceImpl(tooltipProvider);
		CatchDaoTestStub catchDao = new CatchDaoTestStub(1020, 1016, 1010);
		catchDao.setTemp(18, 20, 25);
		catchDao.setSpecies(Fish.ASP, Fish.BARBEL, Fish.BREAM);
		catchDao.setLength(50, 60, 70);
		catchDao.setWeight(1, 2, 3);
		List<Catch> catchList = catchDao.getCatchList("username");
		
		//when
		List<CatchProperties> dataList = chartService.prepareDataListFrom(catchList, (c) -> c.getWeather().getAir_pressure());
		
		//then
		assertThat(dataList, not(contains(20, 18, 25)));
	}
	
	@Test
	void chartPropertiesMapShouldCorrectAppropriateDataDependingOnChartType() {
		
		//given
		TimeChartType temperature = TimeChartType.TEMPERATURE;
		TimeChartType pressure = TimeChartType.AIR_PRESSURE;
		TimeChartType humidity = TimeChartType.HUMIDITY;
		TimeChartType windDirection = TimeChartType.WIND_DIRECTION;
		TimeChartType windSpeed = TimeChartType.WIND_SPEED;
		chartService = new ChartServiceImpl(tooltipProvider);
		
		//when
		Map<String, String> tempChart = chartService.prepareChartPropertiesMapFor(temperature);
		Map<String, String> pressChart = chartService.prepareChartPropertiesMapFor(pressure);
		Map<String, String> humChart = chartService.prepareChartPropertiesMapFor(humidity);
		Map<String, String> windDirChart = chartService.prepareChartPropertiesMapFor(windDirection);
		Map<String, String> windSpeedChart = chartService.prepareChartPropertiesMapFor(windSpeed);
		
		//then
		assertTrue(tempChart.containsValue("Temperature"));
		assertTrue(tempChart.containsValue("deg C"));
		assertTrue(tempChart.size() == 2);
		
		assertTrue(pressChart.containsValue("Air pressure"));
		assertTrue(pressChart.containsValue("hPa"));
		assertTrue(pressChart.size() == 2);
		
		assertTrue(humChart.containsValue("Humidity"));
		assertTrue(humChart.containsValue("%"));
		assertTrue(humChart.size() == 2);
		
		assertTrue(windDirChart.containsValue("Wind direction"));
		assertTrue(windDirChart.containsValue("deg"));
		assertTrue(windDirChart.size() == 2);
		
		assertTrue(windSpeedChart.containsValue("Wind speed"));
		assertTrue(windSpeedChart.containsValue("mph"));
		assertTrue(windSpeedChart.size() == 2);

		
	}
	
	

}
