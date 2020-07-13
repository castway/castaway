package com.tomankiewicz.rafal.castawayspringboot.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	private EntityManager entityManager;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public UserDaoImpl(EntityManager entityManager) {
		
		this.entityManager = entityManager;
	}
	
	@Override
	public User findByUsername(String name) {
		
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User where username=:name", User.class);
		query.setParameter("name", name);
//		logger.info(getClass().getName() + "QUERY CREATED");
		User user = null;
		
		try {
			user = query.getSingleResult();
//			logger.info("Query for username executed");
		}
		catch (Exception e){
			user = null;
		}
		return user;
	}
	
	@Override
	public void save(User user) {

		Session session = entityManager.unwrap(Session.class);
		
		session.saveOrUpdate(user);
//		logger.info(getClass().getName() + " user saved");
	}

	@Override
	public User findByEmail(String email) {
		
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User where email=:email", User.class);
		query.setParameter("email", email);
		
//		logger.info(getClass().getName() + "QUERY CREATED");
		User user = null;
		
		try {
			user = query.getSingleResult();
//			logger.info("Query for username executed");
		}
		catch (Exception e){
			user = null;
		}
		
		return user;
	}

}
