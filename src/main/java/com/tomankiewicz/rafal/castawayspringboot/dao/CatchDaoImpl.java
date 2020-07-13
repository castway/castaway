package com.tomankiewicz.rafal.castawayspringboot.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.tomankiewicz.rafal.castawayspringboot.entity.Catch;
import com.tomankiewicz.rafal.castawayspringboot.entity.User;

@Repository
public class CatchDaoImpl implements CatchDao {

	private EntityManager entityManager;

	@Autowired
	public CatchDaoImpl(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	@Override
	public List<Catch> getCatchList(String username) {

		Session session = entityManager.unwrap(Session.class);

		Query<Catch> query = session
				.createQuery("from Catch " + "	where angler_username=:user " + "order by length DESC", Catch.class);
		query.setParameter("user", username);

		return query.getResultList();
	}

	@Override
	public Catch save(Catch theCatch) {

		Session session = entityManager.unwrap(Session.class);
		session.flush();
		session.clear();

		session.saveOrUpdate(theCatch);

		return theCatch;
	}

	@Override
	public Catch findById(int id) {

		Session session = entityManager.unwrap(Session.class);
		Catch theCatch = session.get(Catch.class, id);

		return theCatch;
	}

	@Override
	public void delete(int id) {

		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("delete from Catch where id=:id");
		query.setParameter("id", id);

		query.executeUpdate();
	}

	@Override
	public List<Catch> getAllCatches() {

		Session session = entityManager.unwrap(Session.class);
		Query<Catch> query = session.createQuery("from Catch", Catch.class);
		
		List<Catch> allCatches = query.getResultList();
		
		return allCatches;
	}
	
}
