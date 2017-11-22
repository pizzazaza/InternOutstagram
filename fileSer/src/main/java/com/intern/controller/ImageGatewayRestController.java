package com.intern.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intern.domain.dto.UploadFileDto;
import com.intern.service.SearchFilePathService;
import com.intern.service.GatwayService;

@RestController
@RequestMapping("/fileserver")
public class ImageGatewayRestController {
	private static final Logger logger = LoggerFactory.getLogger(ImageGatewayRestController.class);
	private GatwayService gatewayService;

	
	
	@Autowired
	public ImageGatewayRestController(GatwayService gatewayService) {
	
		this.gatewayService = gatewayService;
	}

	@PostMapping
	public String uploadGate(@ModelAttribute UploadFileDto uploadFileDto) {
		
		logger.info("postID: "+uploadFileDto.getPostSeq());
		logger.info("fileList_length: "+uploadFileDto.getMultipartFile().length);
		for(MultipartFile mf : uploadFileDto.getMultipartFile()) {
			logger.info("file_name: "+mf.getName());
			logger.info("file_size: "+mf.getSize());
			logger.info("file_content_type: "+mf.getContentType());
		}
		
		return gatewayService.imageUpload(uploadFileDto);
	}
	
	//pvd.setPath("http://localhost:8001/fileserver?postSeq="+pvd.getSeq());
	@GetMapping
	public void downloadImageGate(@RequestParam(value="postSeq", defaultValue="-1") Integer postSeq, 
								@RequestParam(value="fileSeq", defaultValue="-1") Integer fileSeq,
								HttpServletResponse response) {
		ResponseEntity<byte[]> res = null;
		if(postSeq > 0) {
			res = gatewayService.downloadImage(postSeq);
		}else {
			res = gatewayService.downloadOtherImage(fileSeq);
		}
		try {
			response.getOutputStream().write(res.getBody());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/*
 * 이미지 송수신을 담당하는 Controller
 * @ModelAttribute을 통해 전달 받은 값을 ImageUploadDto로 메핑 
 * 
 */