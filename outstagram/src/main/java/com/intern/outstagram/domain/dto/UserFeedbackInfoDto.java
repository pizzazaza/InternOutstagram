package com.intern.outstagram.domain.dto;

public class UserFeedbackInfoDto {
	private Boolean like;
	private Integer viewCount;
	private Float viewTime;
	
	
	public Float getViewTime() {
		return viewTime;
	}
	public void setViewTime(Float viewTime) {
		this.viewTime = viewTime;
	}
	public Boolean getLike() {
		return like;
	}
	public void setLike(Boolean like) {
		this.like = like;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
}
