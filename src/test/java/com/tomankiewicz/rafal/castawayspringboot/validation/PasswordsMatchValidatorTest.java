package com.tomankiewicz.rafal.castawayspringboot.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class PasswordsMatchValidatorTest {

	private static Validator validator;
	
	@BeforeAll
	static void init() {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	void passwordValidatorShouldDetectViolationIfPasswordFieldsAreDifferent() {
		
		//given
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("example@example.com");
		user.setPassword("abcdefg");
		user.setPasswordConfirmation("abcdefgt");
		
		//when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		//then
		assertThat(constraintViolations, is(not(empty())));
		assertThat(constraintViolations, hasSize(1));
	}
	
	@Test
	void passwordValidatorShouldNotDetectViolationsIfPasswordFieldsAreTheSame() {

		//given
		User user = new User();
		user.setUsername("testuser");
		user.setEmail("example@example.com");
		user.setPassword("abcdefg");
		user.setPasswordConfirmation("abcdefg");
		
		//when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		
		//then
		assertThat(constraintViolations, is(empty()));
		
	}
	

}
