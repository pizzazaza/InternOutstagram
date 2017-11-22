package com.intern.outstagram.service;

import java.util.List;

import com.intern.outstagram.domain.dto.ImageUploadDto;
import com.intern.outstagram.domain.dto.LikeUpdateDto;
import com.intern.outstagram.domain.dto.PostViewTimeDto;
import com.intern.outstagram.domain.dto.ViewUpdateDto;

public interface PostService {
	
	public String setPostInfoByNewPost(ImageUploadDto imageUploadDto);
	
	public Integer setPostLikeByPostSeq(LikeUpdateDto likeUpdateDto);
	
	public Integer setPostViewByPostSeq(ViewUpdateDto viewUpdateDto);
	
	public Integer setPostTimeBySeq(PostViewTimeDto postViewTimeDto);
	
	public List<Integer> getPictureList(Integer postSeq);
}
