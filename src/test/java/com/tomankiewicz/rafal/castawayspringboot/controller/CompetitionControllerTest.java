package com.tomankiewicz.rafal.castawayspringboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.given;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tomankiewicz.rafal.castawayspringboot.entity.Competition;
import com.tomankiewicz.rafal.castawayspringboot.service.CompetitionService;

@ExtendWith(MockitoExtension.class)
class CompetitionControllerTest {

	@Mock
	private CompetitionService competitionService;
	
	@InjectMocks
	private CompetitionController controller;
	
	@Test
	void getRequestToShowFormAddShouldReturnTheAddCompetitionFormViewUrl() {
	
		//given
		Model theModel = mock(Model.class);
		
		//when
		String output = controller.showAddCompetition(theModel);
		
		//then
		assertThat(output, containsString("add-competition-form"));
		
		
	}
	
	@Test
	void postRequestToSaveShouldTriggerSaveMethodOnService() {
		
		//given
		Competition theCompetition = mock(Competition.class);
		BindingResult br = mock(BindingResult.class);
		
		//when
		controller.saveCompetition(theCompetition, br);
		
		//then
		then(competitionService).should().save(theCompetition);
	}
	
	@Test
	void postRequestToSaveShouldReturnConfirmationViewUrlIfNoErrors() {
		
		//given
		Competition theCompetition = mock(Competition.class);
		BindingResult br = mock(BindingResult.class);
		
		//when
		String output = controller.saveCompetition(theCompetition, br);
		
		//then
		assertThat(output, containsString("competition-creation-confirmation"));
	}
	
	@Test
	void postRequestToSaveShouldNotTriggerSaveMethodOnServiceIfBindingResultHasErrors() {
		
		//given 
		Competition theCompetition = mock(Competition.class);
		BindingResult br = mock(BindingResult.class);
		given(br.hasErrors()).willReturn(true);
		
		//when
		String output = controller.saveCompetition(theCompetition, br);
		
		//then
		then(competitionService).should(never()).save(theCompetition);
		assertThat(output, containsString("add-competition-form"));
	}

}
