package com.tomankiewicz.rafal.castawayspringboot.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tomankiewicz.rafal.castawayspringboot.species.Fish;

class CatchTest {

	private static Catch theCatch;
	
	@BeforeAll
	static void init() {

		theCatch = new Catch();

	}
	
	@Test
	void catchBelowLegalLengthShouldGetZeroPoints() {

		//when
		theCatch.setSpecies(Fish.PIKE);
		theCatch.setLength(new BigDecimal(10));
		theCatch.setWeigth(new BigDecimal(0.1));
		
		//given
		int points = theCatch.calculatePoints();
		
		//then
		assertThat(points, is(0));
	}
	
	@Test
	void catchEqualToLegalLengthShouldNotGetZeroPoints() {
		
		//when
		theCatch.setSpecies(Fish.PIKE);
		theCatch.setLength(new BigDecimal(Fish.PIKE.getMinLength()));
		theCatch.setWeigth(new BigDecimal(0.1));
				
		//given
		int points = theCatch.calculatePoints();

		//then
		assertThat(points, is(not(0)));
	}
	
	@Test
	void catchBiggerThanLegalLengthShouldNotGetZeroPoints() {
		
		//when
		theCatch.setSpecies(Fish.PIKE);
		theCatch.setLength(new BigDecimal(Fish.PIKE.getMinLength() + 10));
		theCatch.setWeigth(new BigDecimal(0.1));
				
		//given
		int points = theCatch.calculatePoints();

		//then
		assertThat(points, is(not(0)));
	}
	
	@Test
	void formatDateMethodShouldConvertTheDateToAFormatAcceptedByWeatherApi() {
		
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

}
