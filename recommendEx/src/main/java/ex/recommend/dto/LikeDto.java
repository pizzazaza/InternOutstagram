package ex.recommend.dto;

public class LikeDto {
	
	private Integer userSeq;
	private Integer postSeq;
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Integer getPostId() {
		return postSeq;
	}
	public void setPostId(Integer postSeq) {
		this.postSeq = postSeq;
	}
	
	
}
