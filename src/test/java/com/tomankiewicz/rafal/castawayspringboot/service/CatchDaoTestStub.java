package com.tomankiewicz.rafal.castawayspringboot.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tomankiewicz.rafal.castawayspringboot.dao.CatchDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;
import com.tomankiewicz.rafal.castawayspringboot.species.Fish;

public class CatchDaoTestStub implements CatchDao {
	
	private Catch c1 = new Catch();
	private Catch c2 = new Catch();
	private Catch c3 = new Catch();
	
	public CatchDaoTestStub(LocalDate c1, LocalDate c2, LocalDate c3) {
		this.c1.setDate(c1);
		this.c2.setDate(c2);
		this.c3.setDate(c3);
		this.c1.setWeather(new Weather());
		this.c2.setWeather(new Weather());
		this.c3.setWeather(new Weather());
	}
	
	public CatchDaoTestStub(double pressure1, double pressure2, double pressure3) {
		this.c1.setWeather(new Weather());
		this.c2.setWeather(new Weather());
		this.c3.setWeather(new Weather());
		this.c1.getWeather().setAir_pressure(pressure1);
		this.c2.getWeather().setAir_pressure(pressure2);
		this.c3.getWeather().setAir_pressure(pressure3);
		
	}
	
	public void setTemp(double t1, double t2, double t3) {
	
		this.c1.getWeather().setThe_temp(t1);
		this.c2.getWeather().setThe_temp(t2);
		this.c3.getWeather().setThe_temp(t3);
	}
	
	public void setSpecies(Fish f1, Fish f2, Fish f3) {
		this.c1.setSpecies(f1);
		this.c2.setSpecies(f2);
		this.c3.setSpecies(f3);
	}
	
	public void setWeight(double w1, double w2, double w3) {
		this.c1.setWeigth(new BigDecimal(w1));
		this.c2.setWeigth(new BigDecimal(w2));
		this.c3.setWeigth(new BigDecimal(w3));
	}

	public void setLength(double l1, double l2, double l3) {
		this.c1.setLength(new BigDecimal(l1));
		this.c2.setLength(new BigDecimal(l2));
		this.c3.setLength(new BigDecimal(l3));
	}
	

	@Override
	public List<Catch> getCatchList(String username) {

		List<Catch> catchList = new ArrayList<>();
		
		catchList.add(c1);
		catchList.add(c2);
		catchList.add(c3);
		
		return catchList;
	}

	@Override
	public Catch findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Catch save(Catch theCatch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Catch> getAllCatches() {
		// TODO Auto-generated method stub
		return null;
	}

}
