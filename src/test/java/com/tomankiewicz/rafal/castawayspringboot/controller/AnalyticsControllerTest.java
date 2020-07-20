package com.tomankiewicz.rafal.castawayspringboot.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tomankiewicz.rafal.castawayspringboot.analysis.JSONProvider;
import com.tomankiewicz.rafal.castawayspringboot.analysis.TimeChartType;
import com.tomankiewicz.rafal.castawayspringboot.service.CatchService;
import com.tomankiewicz.rafal.castawayspringboot.service.ChartService;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@ExtendWith(MockitoExtension.class)
class AnalyticsControllerTest {

	@Mock
	CatchService catchService;
	
	@Mock
	UserService userService;
	
	@Mock
	ChartService chartService;
	
	@Mock
	JSONProvider jsonProvider;
	
	@Test
	void allControllerMethodsShouldReturnTheSameAnalyticsView() throws JsonProcessingException {
		
		//given
		AnalyticsController controller = new AnalyticsController(catchService, userService, chartService, jsonProvider);
		Model theModel = mock(Model.class);
		TimeChartType theChart = TimeChartType.AIR_PRESSURE;
		
		
		//when
		String output1 = controller.showFormForParametersChoice(theModel);
		String output2 = controller.showCharts(theModel, theChart);
		
		//then
		assertTrue(output1.contains("charts") && output2.contains("charts"));
	}

}
