package com.tomankiewicz.rafal.castawayspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tomankiewicz.rafal.castawayspringboot.dao.CompetitionDao;
import com.tomankiewicz.rafal.castawayspringboot.entity.Competition;

@Service
public class CompetitionServiceImpl implements CompetitionService {

	private CompetitionDao competitionDao;
	
	@Autowired
	public CompetitionServiceImpl(CompetitionDao competitionDao) {
		
		this.competitionDao = competitionDao;
	}

	@Override
	@Transactional
	public void save(Competition theCompetition) {
		competitionDao.save(theCompetition);
	}
	
	
	
}
