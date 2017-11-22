package com.recommend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recommend.domain.dto.CalculRequestDto;
import com.recommend.service.RecommendCalculService;

@RestController
@RequestMapping("/api")
public class RecommendRestController {
	
	private RecommendCalculService recommendCalculService;
	
	@Autowired
	public RecommendRestController(RecommendCalculService recommendCalculService) {
		this.recommendCalculService = recommendCalculService;
	}
	
	@PostMapping("/sim")
	public void calculRecommendSim(@ModelAttribute CalculRequestDto calculRequestDto ) {
		
	}
}
