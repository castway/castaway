package com.tomankiewicz.rafal.castawayspringboot.dao;

import java.util.List;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

public interface CatchDao {

	List<Catch> getCatchList();

	Catch findById(int id);

	void delete(int id);

	Catch save(Catch theCatch);

	List<Catch> getAllCatches();

}
