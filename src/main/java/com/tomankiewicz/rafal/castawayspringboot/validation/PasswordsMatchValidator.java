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
		logger.info(getClass().getName() + " FIRST STRING: " + firstField + " SECOND OBJECT: " + secondField + "EQUALS: " + firstField.equals(secondField));

	}



	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {

		boolean valid = true;
		
		try {
			final Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstField);
			final Object secondObject = new BeanWrapperImpl(value).getPropertyValue(secondField);
			logger.info(getClass().getName() + " FIRST OBJECT: " + firstObject.toString() + " SECOND OBJECT: " + secondObject.toString() + " EQUALS: " + firstObject.equals(secondObject));
		
			logger.info("BCRYPT :" + BCrypt.checkpw(secondObject.toString(), firstObject.toString()));
			logger.info(BCrypt.gensalt());
			valid = firstObject == null && secondObject == null || firstObject != null && BCrypt.checkpw(secondObject.toString(), firstObject.toString());
		}
		catch (final Exception ignored) {
			
		}
		
		if (!valid) {
			
			context.buildConstraintViolationWithTemplate(message)
					.addPropertyNode(firstField)
					.addConstraintViolation()
					.disableDefaultConstraintViolation();
		}
		
		return valid;
			
//			if (firstObject != null) {
//				
//				return firstObject.equals(secondObject);
//				
//			} else {
//				
//				return secondObject == null;
//			}
			
	}
	
	

}
