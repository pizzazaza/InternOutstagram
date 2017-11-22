package com.intern.datanode.domian.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadDto {
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
	public void setFiles(MultipartFile[] multipartFile) {
		this.multipartFile = multipartFile;
	}
	
}

/*
 * image upload에서 FileRestController's uplaodAtDataNode method ~ FileService's setFileInfoByUpladedFile까지 
 * 데이터 전달을 위해 사용되는 Dto
 */