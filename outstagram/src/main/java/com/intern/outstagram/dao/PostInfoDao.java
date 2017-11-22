/*
 * sql문은 xml로 관리
 * 
 * 
 * @Author Sejun
 */
package com.intern.outstagram.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.intern.outstagram.domain.PostDomain;
import com.intern.outstagram.domain.dto.FileLocationDto;
import com.intern.outstagram.domain.dto.FileSearchDto;
import com.intern.outstagram.domain.dto.IsLikeForPostDto;
import com.intern.outstagram.domain.dto.LikeUpdateDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.PostViewTimeDto;
import com.intern.outstagram.domain.dto.ViewUpdateDto;

@Repository
public interface PostInfoDao {
	
	public List<PostViewDto> selectPost() throws SQLException;
	
	public Integer insertPost(PostDomain postDomain) throws SQLException;
	
	public Integer selectIsLikeByUserSeqAndPostSeq(IsLikeForPostDto isLikeForPostDto) throws SQLException;
	
	public FileLocationDto selectFilePathByPostId(FileSearchDto fileSearchDto) throws SQLException;
	
	public Integer updatePostLikeByPostId(LikeUpdateDto likeUpdateDto) throws SQLException;
	
	public Integer insertPostLike(LikeUpdateDto likeUpdateDto) throws SQLException;
	
	public Integer deletePostLike(LikeUpdateDto likeUpdateDto) throws SQLException;
	
	public Integer updatePostViewByPostSeq(ViewUpdateDto viewUpdateDto) throws SQLException;
	
	public Integer insertPostView(ViewUpdateDto viewUpdateDto) throws SQLException;
	
	public Integer updatePostViewTimeBySeq(PostViewTimeDto postViewTimeDto) throws SQLException;
	
	public List<Integer> selectPictureListByPostSeq(Integer postSeq) throws SQLException;
}

