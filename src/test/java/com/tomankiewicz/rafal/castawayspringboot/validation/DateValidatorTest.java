package com.tomankiewicz.rafal.castawayspringboot.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateValidatorTest {

	private DateValidator validator = new DateValidator();
	private LocalDate today = LocalDate.now();
	
	@Test
	void futureDatesShouldNotBeAllowedByTheValidator() {
		
		assertFalse(isValid(today.plusDays(1)));
		assertFalse(isValid(today.plusMonths(1)));
		assertFalse(isValid(today.plusYears(1)));
	}
	
	@Test
	void currentDateShouldBeAllowed() {
		
		assertTrue(isValid(today));
	}
	
	@Test
	void pastDatesShouldBeAllowed() {
		
		assertTrue(isValid(today.minusDays(1)));
		assertTrue(isValid(today.minusMonths(1)));
		assertTrue(isValid(today.minusYears(1)));
	}
	
	private boolean isValid(LocalDate date) {
		return validator.isValid(date, null);
	}

}
