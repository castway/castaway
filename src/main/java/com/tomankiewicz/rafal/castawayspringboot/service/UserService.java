package com.tomankiewicz.rafal.castawayspringboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

public interface UserService extends UserDetailsService{

	User findByUsername(String name);

	void save(User user);

	User findByEmail(String email);

}
