package com.intern.domain.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileDto {

	private Integer postSeq;
	private MultipartFile[] multipartFile;
	
	public Integer getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Integer postSeq) {
		this.postSeq = postSeq;
	}
	public MultipartFile[] getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile[] multipartFile) {
		this.multipartFile = multipartFile;
	}
	
	
}
