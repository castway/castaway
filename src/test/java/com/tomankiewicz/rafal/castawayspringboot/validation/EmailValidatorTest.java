package com.tomankiewicz.rafal.castawayspringboot.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailValidatorTest {

	private EmailValidator validator = new EmailValidator();
	
	@Test
	void invalidEmailShouldBeDetectedByTheValidator() {
		
		assertFalse(isValid("1234"));
		assertFalse(isValid("abcd"));
		assertFalse(isValid("example.com"));
		assertFalse(isValid("@example.com"));
		assertFalse(isValid("@examplecom"));
		assertFalse(isValid("example@examplecom"));
		assertFalse(isValid("example.example@com"));
		assertFalse(isValid("example@.com"));
		assertFalse(isValid("example@asdf.com "));
		assertFalse(isValid(" example@asdf.com"));
	}
	
	@Test
	void validEmailShouldBeAllowedByTheValidator() {
		
		assertTrue(isValid("example@example.com"));
		assertTrue(isValid("1234@example.com"));
		assertTrue(isValid("1234@example.pl"));
		assertTrue(isValid("1234_example@example.pl"));
	}
	
	private boolean isValid(String input) {
		return validator.isValid(input, null);
	}

}
