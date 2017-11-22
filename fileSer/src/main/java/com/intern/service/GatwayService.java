package com.intern.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.intern.domain.dto.UploadFileDto;

public interface GatwayService {
	public String imageUpload(UploadFileDto uploadFileDto);
	
	public ResponseEntity<byte[]> downloadImage(Integer postSeq);

	public ResponseEntity<byte[]> downloadOtherImage(Integer fileSeq);
}
