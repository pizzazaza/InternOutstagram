package com.intern.outstagram.domain.dto;

public class RecommendResultDto {
	private Integer userSeq;
	private Long postSeq;
	private Float score;
	
	
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Long getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Long postSeq) {
		this.postSeq = postSeq;
	}
}
