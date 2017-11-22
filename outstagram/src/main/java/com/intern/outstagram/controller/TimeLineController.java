/*
 * main page와 그 외 연관된 페이지들 요청 처리 
 * 
 * @Author Sejun
 */
package com.intern.outstagram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.service.TimeLineService;

@Controller
public class TimeLineController {
	private static final Logger logger = LoggerFactory.getLogger(TimeLineController.class);
	
	private TimeLineService timeLineService;
	private Integer userSeq = 48;
	
	@Autowired
	public TimeLineController(TimeLineService timeLineService) {
		this.timeLineService = timeLineService;
	}
	
	@GetMapping("/upload")
	public ModelAndView uploadPage(ModelAndView modelAndView) {
		logger.info("image upload reqest : " + userSeq + " : user ");
		
		modelAndView.setViewName("imageRegister");
		modelAndView.addObject("userId", userSeq);
		
		return modelAndView; 
	}
	//upload page 
	
	
	@GetMapping("")
	public ModelAndView mainPageLoading(ModelAndView modelAndView) {
		logger.info("main page start");
		modelAndView.setViewName("index");
		List<PostViewDto> postViewDtoList = null;
		
		try {
			postViewDtoList = timeLineService.getTimeLineList(userSeq);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("like?" + postViewDtoList.get(0).getIsLike());
		modelAndView.addObject("offset",12);
		modelAndView.addObject("userSeq", userSeq);
		modelAndView.addObject("postViewDtoList", postViewDtoList);
		
		return modelAndView;
	}
	//main page - time line

}

