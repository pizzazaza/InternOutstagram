package com.intern.outstagram.domain.dto;

public class IsLikeForPostDto {
	private Integer userSeq;
	private Integer postSeq;
	
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Integer getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Integer postSeq) {
		this.postSeq = postSeq;
	}
}
