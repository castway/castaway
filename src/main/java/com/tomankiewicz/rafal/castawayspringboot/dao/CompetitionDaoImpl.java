package com.tomankiewicz.rafal.castawayspringboot.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.tomankiewicz.rafal.castawayspringboot.entity.Competition;

@Repository
public class CompetitionDaoImpl implements CompetitionDao {

	private EntityManager entityManager;

	@Autowired
	public CompetitionDaoImpl(EntityManager entityManager) {

		this.entityManager = entityManager;
	}
	
	@Override
	public void save(Competition theCompetition) {

		Session session = entityManager.unwrap(Session.class);
		// Retrieve the authenticated user, set the username as the competition's owner
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		theCompetition.setOwner(username);
		
		session.saveOrUpdate(theCompetition);
	}

}
