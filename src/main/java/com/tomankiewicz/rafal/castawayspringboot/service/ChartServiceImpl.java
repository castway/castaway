package com.tomankiewicz.rafal.castawayspringboot.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomankiewicz.rafal.castawayspringboot.analysis.TimeChartType;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip.CatchProperties;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;

@Service
public class ChartServiceImpl implements ChartService {

	@Autowired
	private Tooltip tooltipProvider;
	
	private DateTimeFormatter formatter;  
	
	public ChartServiceImpl(Tooltip tooltipProvider) {
		
		this.tooltipProvider = tooltipProvider;
		this.formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	}
	
	@Override
	public List<String> prepareLabelsListForTimeChartFrom(List<Catch> sortedList) {
		
		if (sortedList == null) {
			return new ArrayList<String>();
		}
		
		return 	sortedList.stream()
				.map((c) -> c.getDate().format(formatter).toString())
				.collect(Collectors.toList());
	}


	@Override
	public List<CatchProperties> prepareDataListFrom(List<Catch> catchList, Function<Catch, Object> parameter) {
		
		if (catchList == null) {
			return new ArrayList<CatchProperties>();
			
		}
		
		List<Object> dataList = catchList.stream()
				   				.map(parameter)
				   				.collect(Collectors.toList());
		
		return tooltipProvider.prepareTooltipFrom(catchList, dataList);
	}

	@Override
	public Map<String, String> prepareChartPropertiesMapFor(TimeChartType theChart) {
		return theChart.generateChartProperties();
	}}
