package com.intern.outstagram.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.intern.outstagram.domain.PostDomain;
import com.intern.outstagram.domain.dto.PageDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RPostViewDto;

public interface TimeLineService {
	
	public List<PostViewDto> getTimeLineList(Integer userSeq) throws IOException;	
	
	public List<PostViewDto> getNewPostList(PageDto pageDto);
	
	public List<RPostViewDto> getRecommendPostList(RPageDto pageDto);
	
	public List<PostViewDto> getLikePostList(PageDto pageDto);
}
