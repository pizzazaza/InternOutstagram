package ex.recommend.dto;

import java.util.Date;

public class UserDto {
	private Integer seq;
	private String userId;
	private String nikcname;
	private Date createDate;
	
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNikcname() {
		return nikcname;
	}
	public void setNikcname(String nikcname) {
		this.nikcname = nikcname;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
