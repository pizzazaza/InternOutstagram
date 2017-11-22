package com.intern.outstagram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.intern.outstagram.domain.dto.ImageUploadDto;
import com.intern.outstagram.service.PostService;

@RestController
@RequestMapping("/upload")
public class ImageUpLoadController {
	private static final Logger logger = LoggerFactory.getLogger(ImageUpLoadController.class);
	private PostService postService;
	

	@Autowired
	public ImageUpLoadController(PostService postService) {
		this.postService = postService;
	}

	
	@RequestMapping(method=RequestMethod.POST)
	public String CreatePost(@ModelAttribute ImageUploadDto imageUploadDto){

		logger.info("postID: "+imageUploadDto.getUserId());
		logger.info("fileList_length: "+imageUploadDto.getMultipartFile().length);
		for(MultipartFile mf : imageUploadDto.getMultipartFile()) {
			logger.info("file_name: "+mf.getName());
			logger.info("file_size: "+mf.getSize());
			logger.info("file_content_type: "+mf.getContentType());
		}
		
		String result = null;
		
		result = postService.setPostInfoByNewPost(imageUploadDto);
		return result;
	}
	
}

/* 
 * file upload 요청에 대한 컨트롤러 
 * @ModelAttribute을 통해 전달 받은 값을 ImageUploadDto로 메핑 
 */