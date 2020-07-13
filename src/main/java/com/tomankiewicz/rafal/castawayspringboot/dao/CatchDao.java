package com.tomankiewicz.rafal.castawayspringboot.dao;

import java.util.List;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;

public interface CatchDao {

	List<Catch> getCatchList(String username);

	Catch findById(int id);

	void delete(int id);

	Catch save(Catch theCatch);

	List<Catch> getAllCatches();

}
