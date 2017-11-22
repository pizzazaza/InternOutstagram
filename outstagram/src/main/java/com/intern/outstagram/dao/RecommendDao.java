/*
 * 추천에 필요한 DB mapper 
 * 
 * @author 김세준 
 */
package com.intern.outstagram.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intern.outstagram.domain.dto.FollowDto;
import com.intern.outstagram.domain.dto.LikeDto;
import com.intern.outstagram.domain.dto.PostViewLikeDto;
import com.intern.outstagram.domain.dto.RandomUserDto;
import com.intern.outstagram.domain.dto.UserCategoryDto;
import com.intern.outstagram.domain.dto.UserDto;
import com.intern.outstagram.domain.dto.ViewDto;

@Repository
public interface RecommendDao {
	public List<FollowDto> selectFollowForRecommend() throws SQLException;
	
	public List<FollowDto> selectFollowForRecommendByUserSeq(Integer seq) throws SQLException;
	
	public List<LikeDto> selectLikeForRecommend() throws SQLException;
	
	public List<LikeDto> selectLikeForRecommendByUserSeq(Integer seq) throws SQLException;
	
	public List<ViewDto> selectViewForRecommend() throws SQLException;
	
	public List<ViewDto> selectViewForRecommendByUserSeq(Integer seq) throws SQLException;
	
	public List<UserDto> selectUserForRecommend() throws SQLException;
	
	public void insertRandomUser(RandomUserDto randomUserDto) throws SQLException;
	
	public List<Integer> selectPostByUserId(String potographer) throws SQLException;
	
	public void updateRandomPostLikeAndViewByPostSeq(PostViewLikeDto postViewLikeDto) throws SQLException;
	
	public void updateRandomPostViewCountByPostSeq(PostViewLikeDto postViewLikeDto) throws SQLException;
	
	public void insertRandomPostLikeByPostSeqUserSeq(LikeDto likeDto) throws SQLException;
	
	public void insertRandomPostViewByPostSeqUserSeq(ViewDto viewDto) throws SQLException;
	
	public List<Integer> selectUserSeq(UserCategoryDto userCategoryDto) throws SQLException;
	
	public List<Integer> selectUserPostView(Integer userSeq) throws SQLException;
	
	public List<UserDto> selectUserForRecommendSmall(Integer offset) throws SQLException;
	
}
