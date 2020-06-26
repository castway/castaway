package com.tomankiewicz.rafal.castawayspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomankiewicz.rafal.castawayspringboot.entity.Competition;
import com.tomankiewicz.rafal.castawayspringboot.service.CompetitionService;

@Controller
@RequestMapping("/competition")
public class CompetitionController {

	private CompetitionService competitionService;
	
	@Autowired
	public CompetitionController(CompetitionService competitionService) {
		
		this.competitionService = competitionService;
	}
	
	@RequestMapping("/showFormAdd")
	public String showAddCompetition(Model theModel) {
		
		Competition theCompetition = new Competition();
		theModel.addAttribute("competition", theCompetition);
		
		return "/competition/add-competition-form";
	}
	
	@PostMapping("/save")
	public String saveCompetition(@ModelAttribute("competition") Competition theCompetition, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "/competition/add-competition-form";
		}
		
		competitionService.save(theCompetition);
		
		return "/competition/competition-creation-confirmation";
	}
}
