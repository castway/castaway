package com.tomankiewicz.rafal.castawayspringboot.dao;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

public interface UserDao {

	User findByUsername(String name);

	void save(User user);

	User findByEmail(String email);

}
