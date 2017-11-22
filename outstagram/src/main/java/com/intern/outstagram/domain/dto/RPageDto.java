package com.intern.outstagram.domain.dto;

public class RPageDto {

	private Integer limit;
	private Integer offset;
	private Integer userSeq;
	private Long postSeq;
	 
	public Long getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Long postSeq) {
		this.postSeq = postSeq;
	}
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
