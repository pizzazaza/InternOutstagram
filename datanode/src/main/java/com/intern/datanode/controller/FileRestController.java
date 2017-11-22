/*
 * image 요청시
 * 
 * rest api로 작성
 * 파일 upload 및 파일정보 db insert 구현  
 * 반환값은 json 객체나 필요에 따라 ResponseEntity로 http 상태 코드 반환 
 * 
 * 
 * Author Sejun
 */
package com.intern.datanode.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intern.datanode.domain.FileDomain;
import com.intern.datanode.domian.dto.ImageUploadDto;
import com.intern.datanode.service.FileService;

@RestController
@RequestMapping
public class FileRestController {
	private static final Logger logger = LoggerFactory.getLogger(FileRestController.class);

	private FileService fileService;
	
	@Autowired
	public FileRestController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@GetMapping("/file")
	public void requestOriginalPicture(@RequestParam(value="fileSeq") Integer fileSeq, HttpServletResponse response) {
		fileService.getOriginalImageByFileId(fileSeq, response);
		
	}
	
	@PostMapping("/file")
	public List<FileDomain> uploadToDataNode(@RequestParam(value="postSeq") Integer postSeq, 
						@RequestParam(value="multipartFile") MultipartFile[] multipartFile) {

		logger.info("postSeq: "+postSeq);
		logger.info("fileList_length: "+multipartFile.length);
		for(MultipartFile mf : multipartFile) {
			logger.info("file_name: "+mf.getName());
			logger.info("file_size: "+mf.getSize());
			logger.info("file_content_type: "+mf.getContentType());
		}
		
		ImageUploadDto imageUploadDto = new ImageUploadDto();
		imageUploadDto.setPostSeq(postSeq);
		imageUploadDto.setFiles(multipartFile);
		
		return fileService.setFileInfoByUpladedFile(imageUploadDto);
		
	}
	/*
	 * image upload
	 * imageUploadDto에 필요한 정보를 담아 fileService로 전달
	 */
	
	@GetMapping("/image")
	public void requestThumnail(@RequestParam(value="postSeq") Integer postSeq, HttpServletResponse response){
		fileService.getThumbnailImageByPostId(postSeq, response);
	}
	
	
}

