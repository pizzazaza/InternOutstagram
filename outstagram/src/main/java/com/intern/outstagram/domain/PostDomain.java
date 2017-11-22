/*
 * 
 * DB Post table의 attribute와 같은 값을 property를 가지는 도메인
 * 
 *  @Author Sejun
 */
package com.intern.outstagram.domain;

import java.util.Date;

public class PostDomain {
	private Integer seq;
	private String userSeq;
	private String context;
	private Date createDate;
	private Date modifyDate;
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
