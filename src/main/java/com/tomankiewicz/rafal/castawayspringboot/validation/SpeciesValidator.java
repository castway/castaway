package com.tomankiewicz.rafal.castawayspringboot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jboss.logging.Logger;

import com.tomankiewicz.rafal.castawayspringboot.species.Fish;

//this validator only checks for null, as species input is already a select item in the html

public class SpeciesValidator implements ConstraintValidator<ValidSpecies, Fish> {

	
	@Override
	public boolean isValid(Fish value, ConstraintValidatorContext context) {
		
		if (value == null) {
			return false;
		}
		
		return true;
	}
	
	
	

}
