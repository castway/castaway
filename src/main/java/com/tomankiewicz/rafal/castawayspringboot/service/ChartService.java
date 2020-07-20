package com.tomankiewicz.rafal.castawayspringboot.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.tomankiewicz.rafal.castawayspringboot.analysis.TimeChartType;
import com.tomankiewicz.rafal.castawayspringboot.analysis.Tooltip.CatchProperties;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;

public interface ChartService {

	List<String> prepareLabelsListForTimeChartFrom(List<Catch> sortedList);

	List<CatchProperties> prepareDataListFrom(List<Catch> list, Function<Catch, Object> parameter);

	Map<String, String> prepareChartPropertiesMapFor(TimeChartType theChart);

}
