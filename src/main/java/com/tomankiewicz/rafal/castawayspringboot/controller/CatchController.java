package com.tomankiewicz.rafal.castawayspringboot.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;
import com.tomankiewicz.rafal.castawayspringboot.service.CatchService;
import com.tomankiewicz.rafal.castawayspringboot.service.WeatherService;

@Controller
@RequestMapping("/catch")
public class CatchController {

	private CatchService catchService;
	private WeatherService weatherService;

	// Custom data binder to allow entering digits with coma and dot as a decimal separator:

	@InitBinder
	public void bigDecimalCustomBinder(WebDataBinder dataBinder) {
		final DecimalFormat FORMATTER = (DecimalFormat) NumberFormat.getNumberInstance(Locale.FRANCE);

		// Allow the HTML field to be empty without generating any exception
		boolean allowEmptyValue = true;

		// Creation of a new binder for the type "BigDecimal"
		CustomNumberEditor binder = new CustomNumberEditor(BigDecimal.class, new NumberFormat() {

			private static final long serialVersionUID = 1L;

			@Override
			public Number parse(String source, ParsePosition parsePosition) {
				if (source != null) {
					source = source.replace('.', ',');
				} 
				
				return FORMATTER.parse(source, parsePosition);
			}

			@Override
			public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
				return FORMATTER.format(number, toAppendTo, pos);
			}

			@Override
			public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
				return FORMATTER.format(number, toAppendTo, pos);
			}
		}, allowEmptyValue);

		// Registration of the binder
		dataBinder.registerCustomEditor(BigDecimal.class, binder);
	}
	

	@Autowired
	public CatchController(CatchService catchService, WeatherService weatherService) {

		this.catchService = catchService;
		this.weatherService = weatherService;
	}

	@GetMapping("/catchList")
	public String getCatchList(Model theModel) {

		List<Catch> catches = catchService.getCatchList();
		theModel.addAttribute("catchList", catches);

		return "/catch/catch-list";
	}

	@GetMapping("/showFormAdd")
	public String showFormAdd(Model theModel) {

		Catch theCatch = new Catch();

		theModel.addAttribute("catch", theCatch);

		return "/catch/add-catch-form";
	}

	@PostMapping("/save")
	public String saveCatch(@Valid @ModelAttribute("catch") Catch theCatch, BindingResult bindingResult)
			throws IOException {

		if (bindingResult.hasErrors()) {
			return "/catch/add-catch-form";
		}

		/**
		 * Check if the catch is being created (1) or updated (2). The steps for these
		 * cases will be different
		 */

		// (1) Check if the catch is being created. In this case id retrieved from the
		// model would be == 0. New call to weather API is made:
		boolean newCatchIsCreated = catchService.checkIfNewCatchIsBeingCreated(theCatch);

		Weather weather = null;

		
		if (newCatchIsCreated) {

			weather = weatherService.callApi(theCatch);
		}
		
		// (2) If the catch is being updated:
		
		else {

			boolean dateIsUpdated = catchService.checkIfDateIsBeingUpdated(theCatch);
			
			// (2a) check if the date is being updated. If so, remove the catch from the existing weather and call API for new one
			
			if (dateIsUpdated) {

				weather = weatherService.callApi(theCatch);
				
			} else {
				
				weather = catchService.getWeatherFromExistingCatch(theCatch);
				
			}

		}

		theCatch.setWeather(weather);
		catchService.save(theCatch);

		return "redirect:/catch/catchList";
	}

	@GetMapping("/showFormUpdate")
	public String showFormUpdate(@RequestParam("catchId") int id, Model theModel) {

		Catch theCatch = catchService.findById(id);
		theModel.addAttribute("catch", theCatch);

		return "/catch/add-catch-form";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("catchId") int id, Model theModel) {

		catchService.delete(id);

		return "redirect:/catch/catchList";
	}
	
	@GetMapping("/showRank")
	public String showRanking(Model theModel) {
		
		Map<String, Integer> theGroupedCatches = catchService.getAllCatchesSortedByUser();
		theModel.addAttribute("groupedCatches", theGroupedCatches);
		
		return "/catch/rank";
	}
	
	@GetMapping("/details")
	public String showCatchDetails(@RequestParam("catchId") int id, Model theModel) {
			
		Catch theCatch = catchService.findById(id);
		Weather theWeather = theCatch.getWeather();
		
		theModel.addAttribute("catch", theCatch);
		theModel.addAttribute("weather", theWeather);
		
		return "/catch/catch-details";
		
	}
	
}
