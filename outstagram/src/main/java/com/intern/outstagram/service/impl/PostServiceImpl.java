/*
 * 게시물에 관리에 대한 비지니스 로직 
 * 게시물 생성 
 * 
 * 좋아요 증감 
 * 
 * 조회 증가 
 * 
 * 조회시 사진 감상시간 저장 
 * 
 * @Author Sejun
 */
package com.intern.outstagram.service.impl;

import java.net.URI;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.intern.outstagram.common.FileServerClient;
import com.intern.outstagram.dao.PostInfoDao;
import com.intern.outstagram.domain.PostDomain;
import com.intern.outstagram.domain.dto.ImageUploadDto;
import com.intern.outstagram.domain.dto.LikeUpdateDto;
import com.intern.outstagram.domain.dto.PostViewTimeDto;
import com.intern.outstagram.domain.dto.ViewUpdateDto;
import com.intern.outstagram.service.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	private PostInfoDao postInfoDao; 

	@Value("${app.target.url}")
	private String targetUrl;
	
	@Autowired
	public PostServiceImpl(PostInfoDao postInfoDao) {
		this.postInfoDao = postInfoDao;
	}
	
	@Override
	public String setPostInfoByNewPost(ImageUploadDto imageUploadDto) {
		// TODO Auto-generated method stub
		//fileserver로 요청
		String result = "";
		Integer postId = null;
		URI uri = URI.create(targetUrl + "fileserver");
		
		PostDomain postDomain = new PostDomain();
		
		postDomain.setUserSeq(imageUploadDto.getUserId());
		postDomain.setContext(imageUploadDto.getContext());
		postDomain.setCreateDate(new Date());
		logger.info("Upload To file server "+ uri + " Method : POST");
		try {
			postInfoDao.insertPost(postDomain);
			result = FileServerClient.sendPOST(uri, postDomain.getSeq(), imageUploadDto.getMultipartFile());
			logger.info("Result : " + result);
			return result;
		}catch(Exception e) {
			logger.info("Requeset Exception To " + uri);
			return "no";
		}
		
	}
	//게시물 정보를 DB에 insert후 해당 post의 id를 파일과 함께 같이파일서버로 전달 

	@Override
	public Integer setPostLikeByPostSeq(LikeUpdateDto likeUpdateDto) {
		logger.info("like update : " +likeUpdateDto.getState());
		if(likeUpdateDto.getState()) {//create like
			try {
				postInfoDao.insertPostLike(likeUpdateDto);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {//delete like
			try {
				postInfoDao.deletePostLike(likeUpdateDto);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("like Post : " +likeUpdateDto.getLikeCount());
		try {
			postInfoDao.updatePostLikeByPostId(likeUpdateDto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//post like change
		
		
		return null;
	}

	@Override
	public Integer setPostViewByPostSeq(ViewUpdateDto viewUpdateDto) {
		logger.info("view update");
		
		try {
			postInfoDao.updatePostViewByPostSeq(viewUpdateDto);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			postInfoDao.insertPostView(viewUpdateDto);
			logger.info("Post View Seq : "+ viewUpdateDto.getSeq());
			return viewUpdateDto.getSeq();
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public Integer setPostTimeBySeq(PostViewTimeDto postViewTimeDto) {
		logger.info("view time update");
		
		try {
			if(postViewTimeDto.getStayTime() > 5) {
				postViewTimeDto.setStayTime(5.f);
			}
			postInfoDao.updatePostViewTimeBySeq(postViewTimeDto);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Integer> getPictureList(Integer postSeq) {
		
		try {
			return postInfoDao.selectPictureListByPostSeq(postSeq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}

