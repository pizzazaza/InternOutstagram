package com.intern.outstagram.service.impl;

import java.sql.SQLException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intern.outstagram.dao.RecommendDao;
import com.intern.outstagram.domain.dto.LikeDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.PostViewLikeDto;
import com.intern.outstagram.domain.dto.ViewDto;
import com.intern.outstagram.service.RandomDataService;

@Service
@Transactional
public class RandomDataServiceImpl implements RandomDataService {

	private RecommendDao recommendDao;
	
	@Autowired
	public RandomDataServiceImpl(RecommendDao recommendDao) {
		this.recommendDao = recommendDao;
	}
	
	@Override
	public void createPostLikeAndViewRandomData(Integer postSeq, Integer prefer) {
		Random random = new Random();
		int count = 0;
		LikeDto likeDto = new LikeDto();
		likeDto.setPostId(postSeq);
		likeDto.setUserSeq(prefer);
		
		try {
			recommendDao.insertRandomPostLikeByPostSeqUserSeq(likeDto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ViewDto viewDto = new ViewDto();
		viewDto.setPostSeq(postSeq);
		viewDto.setViewTime(random.nextFloat()+random.nextInt(2)+3);
		viewDto.setUserSeq(prefer);
		try {
			recommendDao.insertRandomPostViewByPostSeqUserSeq(viewDto);
			count++;
			while(random.nextInt(100) <= 50) {
				recommendDao.insertRandomPostViewByPostSeqUserSeq(viewDto);
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//like and view table insert 
		//post like, view count update
		PostViewLikeDto postViewLikeDto = new PostViewLikeDto();
		postViewLikeDto.setCount(count);
		postViewLikeDto.setPostSeq(postSeq);
		try {
			recommendDao.updateRandomPostLikeAndViewByPostSeq(postViewLikeDto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void createPostViewRandomData(Integer postSeq, Integer nonPrefer) {
		Random random = new Random();
		int count = 0;
		ViewDto viewDto = new ViewDto();
		viewDto.setPostSeq(postSeq);
		viewDto.setViewTime(random.nextFloat()+random.nextInt(4));
		viewDto.setUserSeq(nonPrefer);
		try {
			recommendDao.insertRandomPostViewByPostSeqUserSeq(viewDto);
			count++;
			while(random.nextInt(100) <= 20) {
				recommendDao.insertRandomPostViewByPostSeqUserSeq(viewDto);
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//view table insert 
		//post view count update
		PostViewLikeDto postViewLikeDto = new PostViewLikeDto();
		postViewLikeDto.setCount(count);
		postViewLikeDto.setPostSeq(postSeq);
		try {
			recommendDao.updateRandomPostViewCountByPostSeq(postViewLikeDto);
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
