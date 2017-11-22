package ex.recommend.dto;

import java.util.List;

public class RecommendDataFeatureDto {
	private List<FollowDto> followList;
	private List<LikeDto> likeList;
	private List<ViewDto> viewList;
	private List<UserDto> userList;
	
	
	public List<FollowDto> getFollowList() {
		return followList;
	}
	public void setFollowList(List<FollowDto> followList) {
		this.followList = followList;
	}
	public List<LikeDto> getLikeList() {
		return likeList;
	}
	public void setLikeList(List<LikeDto> likeList) {
		this.likeList = likeList;
	}
	public List<ViewDto> getViewList() {
		return viewList;
	}
	public void setViewList(List<ViewDto> viewList) {
		this.viewList = viewList;
	}
	public List<UserDto> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDto> userList) {
		this.userList = userList;
	}
}
