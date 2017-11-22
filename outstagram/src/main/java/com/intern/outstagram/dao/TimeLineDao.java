package com.intern.outstagram.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intern.outstagram.domain.dto.PageDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RPostViewDto;

@Repository
public interface TimeLineDao {
	
	public List<PostViewDto> selectNewPostList(PageDto pageDto) throws SQLException;
	
	public PostViewDto selectLikePostListByPostSeq(PageDto pageDto) throws SQLException;
	
	public List<PostViewDto> selectRecommendPostList(PageDto pageDto) throws SQLException;
	
	public List<Integer> selectPostLikeByUserSeq(PageDto pageDto) throws SQLException;
	
	public String selectUserIdByUserSeq(Integer userSeq) throws SQLException;
	
	public RPostViewDto selectRecommendPostByPostSeq(RPageDto pageDto) throws SQLException;
}
