/*
 * 게시물의 정보관리 
 * 
 * like 증감 
 * 
 * @Author Sejun
 */
package com.intern.outstagram.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intern.outstagram.domain.dto.LikeUpdateDto;
import com.intern.outstagram.domain.dto.PostViewTimeDto;
import com.intern.outstagram.domain.dto.ViewUpdateDto;
import com.intern.outstagram.service.PostService;


@RestController
@RequestMapping("/post")
public class PostInfoRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostInfoRestController.class);
	private PostService postService;
	
	@Autowired
	public PostInfoRestController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping("/like")
	public void modifyLikeCount(@RequestBody LikeUpdateDto likeUpdateDto) {
		logger.info("likecount : "+likeUpdateDto.getLikeCount());
		logger.info("state : "+likeUpdateDto.getState());
		logger.info("postSeq : "+likeUpdateDto.getPostSeq());
		logger.info("userSeq : "+likeUpdateDto.getUserSeq());
		
		postService.setPostLikeByPostSeq(likeUpdateDto);
		
		//return "why?";
	}
	
	@PostMapping("/view")
	public Integer modifyViewCount(@RequestBody ViewUpdateDto viewUpdateDto) {
		
		logger.info("userSeq: " + viewUpdateDto.getUserSeq());
		logger.info("postSeq: " + viewUpdateDto.getPostSeq());
		
		return postService.setPostViewByPostSeq(viewUpdateDto);
	}
	
	@PostMapping("/viewtime")
	public void modifyViewTime(@RequestBody PostViewTimeDto postViewTimeDto) {
		logger.info("userSeq: " + postViewTimeDto.getStayTime());
		
		
		postService.setPostTimeBySeq(postViewTimeDto);
	}
	
	@GetMapping("")
	public String getPictureList(@RequestParam(value="postSeq") Integer postSeq){
		List<Integer> picList = postService.getPictureList(postSeq);
		logger.info(picList.toString());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileSeq", picList);
		logger.info(jsonObject.toJSONString());
		return jsonObject.toJSONString();
	}
}
