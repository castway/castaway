package com.tomankiewicz.rafal.castawayspringboot.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tomankiewicz.rafal.castawayspringboot.analysis.JSONProvider;
import com.tomankiewicz.rafal.castawayspringboot.analysis.TimeChartType;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip.CatchProperties;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.service.CatchService;
import com.tomankiewicz.rafal.castawayspringboot.service.ChartService;
import com.tomankiewicz.rafal.castawayspringboot.service.UserService;

@Controller
@RequestMapping("/catch")
public class AnalyticsController {

	private CatchService catchService;
	private UserService userService;
	private ChartService chartService;
	private JSONProvider jsonProvider;
	
	@Autowired
	public AnalyticsController(CatchService catchService, UserService userService, ChartService chartService, JSONProvider jsonProvider) {
		
		this.catchService = catchService;
		this.userService = userService;
		this.chartService = chartService;
		this.jsonProvider = jsonProvider;
	}
	
	@GetMapping("/chooseParams")
	public String showFormForParametersChoice(Model theModel) {
		
		List<TimeChartType> chartOptions = Arrays.asList(TimeChartType.values());
		theModel.addAttribute("chartOptions", chartOptions);
		
		return "/analytics/charts";
	}
	
	@PostMapping("/analytics")
	public String showCharts(Model theModel, @ModelAttribute("parameter") TimeChartType theChart) throws JsonProcessingException {
		
		
		Authentication auth = userService.getAuthenticationToken();
		String username = userService.getUsernameOfTheLoggedInUserFromDB(auth);
		
		List<Catch> catchList = catchService.getCatchListSortedByDates(username);
		
		List<String> labelsList = chartService.prepareLabelsListForTimeChartFrom(catchList);
				
		List<CatchProperties> tooltipDataList = chartService.prepareDataListFrom(catchList, theChart.getParameter()); 
		
		Map<String, String> chartProperties = chartService.prepareChartPropertiesMapFor(theChart);
				
	
		String labels = jsonProvider.prepareJsonFrom(labelsList);
		String tooltip = jsonProvider.prepareJsonFrom(tooltipDataList);

		theModel.addAttribute("labels", labels);
		theModel.addAttribute("tooltip", tooltip);
		theModel.addAttribute("properties", chartProperties);
		
		List<TimeChartType> chartOptions = Arrays.asList(TimeChartType.values());
		theModel.addAttribute("chartOptions", chartOptions);
		
		
	return "/analytics/charts";
	}
}
