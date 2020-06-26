package com.tomankiewicz.rafal.castawayspringboot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.tomankiewicz.rafal.castawayspringboot.dao.AuthorityDao;
import com.tomankiewicz.rafal.castawayspringboot.dao.UserDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserDao userRepository;
	
	@Mock
	private AuthorityDao authorityDao;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private static User user;
	
	@BeforeAll
	static void init() {
		user = new User("example", "abcd", "abcd", "example@hotmail.com", 0, new ArrayList<Catch>());
	}
	
	@Test
	void userShouldBeEnabledWithinSaveMethod() {
		
		//given
			// user created in before method
		//when
		userService.save(user);
		
		//then
		assertEquals(1, user.getEnabled());
	}
	
	@Test
	void passwordShouldBeEncryptedWithinSaveMethod() {
		
		//given
		//when
			//user created in before method, save method invoked in first test method. 
	
		//then
		assertThat(user.getPassword(), containsString("$2a$"));
		assertThat(user.getPassword(), is(not("abcd")));
	}

}
