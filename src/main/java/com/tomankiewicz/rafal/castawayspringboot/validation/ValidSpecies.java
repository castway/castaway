package com.tomankiewicz.rafal.castawayspringboot.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tomankiewicz.rafal.castawayspringboot.species.Fish;

@Constraint(validatedBy = SpeciesValidator.class)
@Documented
@Retention(RUNTIME)
@Target({ ElementType.METHOD, FIELD, ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
public @interface ValidSpecies {

	
	String message() default "Choose species";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
