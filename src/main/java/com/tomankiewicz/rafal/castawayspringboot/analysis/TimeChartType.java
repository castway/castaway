package com.tomankiewicz.rafal.castawayspringboot.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
public enum TimeChartType implements Function<Catch, Object> {

	TEMPERATURE("Temperature", (c) -> Math.round(c.getWeather().getThe_temp() * 100.0) / 100.0, "deg C"),
	AIR_PRESSURE("Air pressure", (c) -> Math.round(c.getWeather().getAir_pressure() * 100.0) / 100.0, "hPa"),
	WIND_SPEED("Wind speed", (c) -> Math.round(c.getWeather().getWind_speed() * 100.0) / 100.0, "mph"),
	WIND_DIRECTION("Wind direction", (c) -> Math.round(c.getWeather().getWind_direction() * 100.0) / 100.0, "deg"),
	HUMIDITY("Humidity", (c) -> c.getWeather().getHumidity(), "%");
	
	private final String displayValue;
	private final Function<Catch, Object> parameter;
	private final String unitOfMeasure;
	
	private TimeChartType(String displayValue, Function<Catch, Object> parameter, String unitOfMeasure) {
		this.displayValue = displayValue;
		this.parameter = parameter;
		this.unitOfMeasure = unitOfMeasure;
				
	}

	public Function<Catch, Object> getParameter() {
		return parameter;
	}
	
	

	public String getDisplayValue() {
		return displayValue;
	}
	
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	@Override
	public Object apply(Catch t) {
		return parameter.apply(t);
	}
	
	public Map<String, String> generateChartProperties(){
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("unit", unitOfMeasure);
		properties.put("title", displayValue);
		
		return properties;
	}
	
	
	
}
