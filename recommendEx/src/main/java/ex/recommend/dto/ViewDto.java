package ex.recommend.dto;

public class ViewDto {

	private Integer viewCount;
	private Integer postSeq;
	private Float viewTime;
	private Integer userSeq;
	
	
	public Integer getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(Integer userSeq) {
		this.userSeq = userSeq;
	}
	public Float getViewTime() {
		return viewTime;
	}
	public void setViewTime(Float viewTime) {
		this.viewTime = viewTime;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Integer postSeq) {
		this.postSeq = postSeq;
	}
}
