package com.intern.outstagram.domain;

public class FileDomain {


	private Integer seq;
	private Integer postId;
	private String path;
	private String thumnailFile;
	private String originalFile;
	private Integer width;
	private Integer height;
	private Long fileLength;
	private String type;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getThumnailFile() {
		return thumnailFile;
	}
	public void setThumnailFile(String thumnailFile) {
		this.thumnailFile = thumnailFile;
	}
	public String getOriginalFile() {
		return originalFile;
	}
	public void setOriginalFile(String originalFile) {
		this.originalFile = originalFile;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Long getFileLength() {
		return fileLength;
	}
	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}

/*
 * DB File table의 attribute와 같은 값을 property를 가지는 도메인  
 */