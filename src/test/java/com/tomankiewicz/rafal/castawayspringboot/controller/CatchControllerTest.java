package com.tomankiewicz.rafal.castawayspringboot.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;
import com.tomankiewicz.rafal.castawayspringboot.service.CatchService;

@ExtendWith(MockitoExtension.class)
class CatchControllerTest {

	@Mock
	private CatchService catchService;
	
	@InjectMocks
	private CatchController controller;
	

	@Test
	void getRequestToCatchListShouldReturnCatchListViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		
		//when
		String result = controller.getCatchList(theModel);
		
		//then
		assertThat(result, containsString("catch-list"));
	}

	@Test
	void getRequestToShowFormAddShouldReturnAddCatchFormViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		
		//when
		String result = controller.showFormAdd(theModel);
		
		//then
		assertThat(result, containsString("add-catch-form"));
	}
	
	@Test
	void callToWeatherApiShouldBeSentifNewCatchIsAdded() throws IOException {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		BindingResult br = mock(BindingResult.class);
		Catch theCatch = mock(Catch.class);
		
		given(theCatch.getId()).willReturn(0);
		
		//when
		controller.saveCatch(theCatch, br);
		
		//then
		then(catchService).should().callApi(theCatch);
		
	}
	
	@Test
	void callToWeatherApiShouldBeSentIfCatchDateIsUpdated() throws IOException {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		BindingResult br = mock(BindingResult.class);
			
			// two catch instances with different dates:
		Catch existingCatch = new Catch();
		Catch theCatch = new Catch();
		
			// weather instance created for the existing catch - catchservice uses the weather's date for 
			// evaluating if the date is being updated:
		Weather weather = new Weather();
		weather.setApplicable_date(LocalDate.now().minusDays(2));
		existingCatch.setWeather(weather);
		
		theCatch.setDate(LocalDate.now());
		theCatch.setId(1);
		
		given(catchService.findById(theCatch.getId())).willReturn(existingCatch);
		
				
		//when
		controller.saveCatch(theCatch, br);
				
		//then
		then(catchService).should().callApi(theCatch);
		
	}
	
	@Test
	void callToWeatherApiShouldNotBeSentIfCatchUpdatedButDateDoesNotChange() throws IOException {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		BindingResult br = mock(BindingResult.class);
			
			// two catch instances with same dates:
		Catch existingCatch = new Catch();
		Catch theCatch = new Catch();
		
			// weather instance created for the existing catch - catchservice uses the weather's date for 
			// evaluating if the date is being updated:
		Weather weather = new Weather();
		weather.setApplicable_date(LocalDate.now());
		existingCatch.setWeather(weather);
		
		theCatch.setDate(LocalDate.now());
		theCatch.setId(1);
		
		given(catchService.findById(theCatch.getId())).willReturn(existingCatch);
		
				
		//when
		controller.saveCatch(theCatch, br);
				
		//then
		then(catchService).should(never()).callApi(theCatch);
		
	}
	
	
	@Test
	void getRequestToShowFormUpdateShouldReturnAddCatchFormViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		Catch theCatch = mock(Catch.class);
		given(catchService.findById(theCatch.getId())).willReturn(theCatch);
		//when
		
		String result = controller.showFormUpdate(theCatch.getId(), theModel);
		
		//then
		assertThat(result, containsString("add-catch-form"));
	}
	
	@Test
	void getRequestToDeleteShouldTriggerServiceDeleteMethod() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		Catch theCatch = mock(Catch.class);
		
		//when
		controller.delete(theCatch.getId(), theModel);
		
		//then
		verify(catchService).delete(theCatch.getId());
		
	}
	
	@Test
	void getRequestToDeleteShouldRedirectToCatchListViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		Catch theCatch = mock(Catch.class);
		
		//when
		String result = controller.delete(theCatch.getId(), theModel);
		
		//then
		assertThat(result, containsString("catchList"));
	}
	
	@Test
	void getRequestToShowRankingShouldReturnRankViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		
		//when
		String result = controller.showRanking(theModel);
		
		//then
		assertThat(result, containsString("rank"));
	}
	
	@Test
	void getRequestToShowCatchDetailsShouldTriggerServiceFindByIdMethod() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		Catch theCatch = mock(Catch.class);
		given(catchService.findById(theCatch.getId())).willReturn(theCatch);
		
		//when
		controller.showCatchDetails(theCatch.getId(), theModel);
		
		//then
		verify(catchService).findById(theCatch.getId());
		
	}
	
	@Test
	void getRequestToShowCatchDetailsShouldReturnCatchDetailsViewUrl() {
		
		//given
		Model theModel = mock(org.springframework.ui.Model.class);
		Catch theCatch = mock(Catch.class);
		given(catchService.findById(theCatch.getId())).willReturn(theCatch);
	
		//when
		String result = controller.showCatchDetails(theCatch.getId(), theModel);
		
		//then
		assertThat(result, containsString("catch-details"));
	}
}
