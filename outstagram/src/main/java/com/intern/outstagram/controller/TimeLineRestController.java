package com.intern.outstagram.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.outstagram.domain.dto.PageDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RPostViewDto;
import com.intern.outstagram.service.PostService;
import com.intern.outstagram.service.TimeLineService;


@RestController
@RequestMapping("/timeline")
public class TimeLineRestController {

	private static final Logger logger = LoggerFactory.getLogger(TimeLineRestController.class);
	private PostService postService;
	private TimeLineService timeLineService;
	
	
	@Autowired
	public TimeLineRestController(PostService postService, TimeLineService timeLineService) {
		this.postService = postService;
		this.timeLineService = timeLineService;
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET, produces="application/json; charset=utf-8")
	public String getNewPostList(@RequestParam(value="userSeq") Integer userSeq,
								@RequestParam(value="limit", defaultValue = "12") Integer limit,
								@RequestParam(value="offset", defaultValue = "0") Integer offset) {
		logger.info("limit : " + limit + ", " + "offset : " + offset);
		PageDto pageDto = new PageDto();
		pageDto.setLimit(limit);
		pageDto.setOffset(offset);
		pageDto.setUserSeq(userSeq);
		List<PostViewDto> postList = timeLineService.getNewPostList(pageDto);
		
		JSONArray jsonArray = new JSONArray();
		
		for(PostViewDto postViewDto : postList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("context",postViewDto.getContext());
			if(postViewDto.getIsLike()) {
				jsonObject.put("isLike","active");
			}
			else {
				jsonObject.put("isLike","");
			}
			jsonObject.put("path", postViewDto.getPath());
			jsonObject.put("postSeq", postViewDto.getSeq());
			jsonObject.put("userId", postViewDto.getUserId());
			jsonObject.put("view", postViewDto.getView());
			jsonObject.put("like", postViewDto.getLike());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toJSONString();
	}
	
	
	@RequestMapping(value="/like", method=RequestMethod.GET, produces="application/json; charset=utf-8")
	public String getLikePostList(@RequestParam(value="userSeq") Integer userSeq,
								@RequestParam(value="limit", defaultValue = "12") Integer limit,
								@RequestParam(value="offset", defaultValue = "0") Integer offset) {
		logger.info("limit : " + limit + ", " + "offset : " + offset);
		PageDto pageDto = new PageDto();
		pageDto.setLimit(limit);
		pageDto.setOffset(offset);
		pageDto.setUserSeq(userSeq);
		List<PostViewDto> postList = timeLineService.getLikePostList(pageDto);
		
		JSONArray jsonArray = new JSONArray();
		logger.info("postList size : " + postList.size());
		for(PostViewDto postViewDto : postList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("context",postViewDto.getContext());
			if(postViewDto.getIsLike()) {
				jsonObject.put("isLike","active");
			}
			else {
				jsonObject.put("isLike","");
			}
			jsonObject.put("path", postViewDto.getPath());
			jsonObject.put("postSeq", postViewDto.getSeq());
			jsonObject.put("userId", postViewDto.getUserId());
			jsonObject.put("view", postViewDto.getView());
			jsonObject.put("like", postViewDto.getLike());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toJSONString();
	}
	
	@RequestMapping(value="/recommend", method=RequestMethod.GET, produces="application/json; charset=utf-8")
	public String getRecommendPostList(@RequestParam(value="userSeq") Integer userSeq,
									@RequestParam(value="limit", defaultValue = "12") Integer limit,
									@RequestParam(value="offset", defaultValue = "0") Integer offset) {
		logger.info("limit : " + limit + ", " + "offset : " + offset);
		RPageDto pageDto = new RPageDto();
		pageDto.setLimit(limit);
		pageDto.setOffset(offset);
		pageDto.setUserSeq(userSeq);
		List<RPostViewDto> postList = timeLineService.getRecommendPostList(pageDto);

		
		JSONArray jsonArray = new JSONArray();
		
		for(RPostViewDto postViewDto : postList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("context",postViewDto.getContext());
			if(postViewDto.getIsLike()) {
				jsonObject.put("isLike","active");
			}
			else {
				jsonObject.put("isLike","");
			}
			jsonObject.put("path", postViewDto.getPath());
			jsonObject.put("postSeq", postViewDto.getSeq());
			jsonObject.put("userId", postViewDto.getUserId());
			jsonObject.put("view", postViewDto.getView());
			jsonObject.put("like", postViewDto.getLike());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toJSONString();
	}
	
}
