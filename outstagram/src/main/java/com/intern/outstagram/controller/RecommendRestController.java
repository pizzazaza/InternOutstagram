/*
 * 추천에 관련된 로직을 수행하기 위한 컨트롤러 
 * 
 * 
 * @Author 김세준 
 */

package com.intern.outstagram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.outstagram.domain.dto.CalculRequestDto;
import com.intern.outstagram.domain.dto.ItemBasedDto;
import com.intern.outstagram.domain.dto.UserBasedDto;
import com.intern.outstagram.service.RecommendCalculService;
import com.intern.outstagram.service.impl.RecommendCalculServiceImpl;

@RestController
@RequestMapping("/api")
public class RecommendRestController {
	private static final Logger logger = LoggerFactory.getLogger(RecommendRestController.class);
	private RecommendCalculService recommendCalculService;
	
	@Autowired
	public RecommendRestController(RecommendCalculService recommendCalculService) {
		this.recommendCalculService = recommendCalculService;
	}
	
	@PostMapping("/dataset")
	public String arrageDataSet(@ModelAttribute CalculRequestDto calculRequestDto ) {
		Integer count = recommendCalculService.createRecommendData();
		
		return "Data Set{uSeq, pSeq, isLike, vCount, vTime} : " + count + " create \n";   
	}
	
	@PostMapping("/csv")
	public String scoringDataSet(@ModelAttribute CalculRequestDto calculRequestDto) {
		
		Integer count = recommendCalculService.createRecommendCSV();
		
		return "CSV Data Set{uSeq, pSeq, score} : " + count + " create\n";
	}
	
	@PostMapping("/pre")
	public void preDataSet(@ModelAttribute CalculRequestDto calculRequestDto) {
		recommendCalculService.createRecommendData();
		recommendCalculService.createRecommendCSV();
	}
	
	@PostMapping("/ran/data")
	public String createRandomData() {
		
		Integer count = recommendCalculService.createRandomUserData();
		
		return "random data : " + count + " create\n";
	}
	
	
	@PostMapping("/recommender/itembased")
	public void recommendItemBased(@ModelAttribute ItemBasedDto itemBasedDto) {
		
		recommendCalculService.getItemBasedRecommend(itemBasedDto);
	}
	
	@PostMapping("/recommender/userbased")
	public void recommendUserBased(@ModelAttribute UserBasedDto userBasedDto) {
		
		recommendCalculService.getUserBasedRecommend(userBasedDto);
	}
	
	@PostMapping("/ran/user")
	public void crateRandomUser() {
		recommendCalculService.createRandomUser();
	}
}
