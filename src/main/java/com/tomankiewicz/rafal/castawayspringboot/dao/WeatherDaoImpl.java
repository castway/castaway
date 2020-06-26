package com.tomankiewicz.rafal.castawayspringboot.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tomankiewicz.rafal.castawayspringboot.entity.Weather;

@Repository
public class WeatherDaoImpl implements WeatherDao {

	private EntityManager entityManager;

	@Autowired
	public WeatherDaoImpl(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	@Override
	public void save(Weather theWeather) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(theWeather);
	}

	@Override
	public void delete(Long id) {

		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete from Weather where id=:id");
		query.setParameter("id", id);

		query.executeUpdate();
		session.flush();
		session.clear();
	}

}
