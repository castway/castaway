package com.tomankiewicz.rafal.castawayspringboot.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

	private LocalDate today;
	
	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		
		today = LocalDate.now();
		
		if (value == null) {
			return false;
		}
		
		if ( value.compareTo(today) > 0) {
			return false;
		}
		
		return true;
		
		
	}

}
