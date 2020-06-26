package com.tomankiewicz.rafal.castawayspringboot.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE})
public @interface PasswordFieldsMatch {

	String message() default "The passwords must match";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	String firstField();
	
	String secondField();
	
	@Retention(RUNTIME)
	@Target({ TYPE, FIELD, ANNOTATION_TYPE })
	@interface List {
		
		PasswordFieldsMatch[] value();
	}
	
}
