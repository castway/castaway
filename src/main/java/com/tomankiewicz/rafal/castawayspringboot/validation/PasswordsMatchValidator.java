package com.tomankiewicz.rafal.castawayspringboot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordFieldsMatch, Object> {

	private String firstField;
	private String secondField;
	private String message;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	@Override
	public void initialize(PasswordFieldsMatch constraintAnnotation) {
		
		this.firstField = constraintAnnotation.firstField();
		this.secondField = constraintAnnotation.secondField();
		this.message = constraintAnnotation.message();
		
	}


	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		boolean valid = true;
		Object firstObject = null;
		Object secondObject = null;
		
		try {

			firstObject = new BeanWrapperImpl(value).getPropertyValue(firstField);
			secondObject = new BeanWrapperImpl(value).getPropertyValue(secondField);
//			logger.info(getClass().getName() + " FIRST STRING: " + firstObject.toString() + " SECOND STRING: " + secondObject.toString() + " EQUALS: " + firstObject.equals(secondObject));
			valid = firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
//			logger.info("Lines moved around " + valid);
//			logger.info("BCRYPT :" + BCrypt.checkpw(secondObject.toString(), firstObject.toString()));
			valid = firstObject == null && secondObject == null || firstObject != null && BCrypt.checkpw(secondObject.toString(), firstObject.toString());
		}
		catch (final Exception ignored) {
			
		}

//		logger.info("VALIDATION RESULT: " + valid);
		
		return valid;
			
	}
	
}
