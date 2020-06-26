package com.tomankiewicz.rafal.castawayspringboot.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tomankiewicz.rafal.castawayspringboot.entity.Authority;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Repository
public class AuthorityDaoImpl implements AuthorityDao {

	private EntityManager entityManager;
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	public AuthorityDaoImpl(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	@Override
	public void setAuthority(User user) {

		Session session = entityManager.unwrap(Session.class);

		Authority role = new Authority();
		role.setUser(user);
		role.setAuthority("ROLE_USER");
		logger.info(getClass().getName() + " role created");

		session.saveOrUpdate(role);
		logger.info(getClass().getName() + " authority saved");
	}

}
