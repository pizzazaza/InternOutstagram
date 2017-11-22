/*
 * 추천에 필요한 DB mapper 
 * 
 * @author 김세준 
 */
package ex.recommend.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ex.recommend.app.RecommendSessionFactory;
import ex.recommend.dto.LikeDto;
import ex.recommend.dto.UserDto;
import ex.recommend.dto.ViewDto;





public class RecommendDao {
	
	
	// UserDAO가 호출될때 생성자를 통해 sqlSessionFactory 생성
	private SqlSessionFactory sqlSessionFactory;
	
	public RecommendDao(){
		sqlSessionFactory = RecommendSessionFactory.getSqlSessionFactory();
	}


	public List<UserDto> selectUserForRecommend() throws SQLException{
		
		SqlSession session = sqlSessionFactory.openSession(); 
		try{ 
			List<UserDto> userList = session.selectList("selectUserForRecommend");
			
			
			return userList; 
		}finally{ 
				session.close(); 
		}
	}
	
	public List<LikeDto> selectLikeForRecommendByUserSeq(Integer seq) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(); 
		try{
			List<LikeDto> userLikeList = session.selectList("selectLikeForRecommendByUserSeq");
			
			
			return userLikeList; 
		}finally{ 
				session.close(); 
		}
	}
	
	public List<ViewDto> selectViewForRecommendByUserSeq(Integer seq) throws SQLException {
		SqlSession session = sqlSessionFactory.openSession(); 
		try{ 
			List<ViewDto> userViewList = session.selectList("selectViewForRecommendByUserSeq");
			
			
			return userViewList; 
		}finally{ 
				session.close(); 
		}
	}
	
	
	
	
	
//	public List<LikeDto> selectLikeForRecommend() throws SQLException;
//	
//	public List<ViewDto> selectViewForRecommend() throws SQLException;
//	
//	public void insertRandomUser(RandomUserDto randomUserDto) throws SQLException;
//	
//	public List<Integer> selectPostByUserId(String potographer) throws SQLException;
//	
//	public void updateRandomPostLikeAndViewByPostSeq(PostViewLikeDto postViewLikeDto) throws SQLException;
//	
//	public void updateRandomPostViewCountByPostSeq(PostViewLikeDto postViewLikeDto) throws SQLException;
//	
//	public void insertRandomPostLikeByPostSeqUserSeq(LikeDto likeDto) throws SQLException;
//	
//	public void insertRandomPostViewByPostSeqUserSeq(ViewDto viewDto) throws SQLException;
//	
//	public List<Integer> selectUserSeq(UserCategoryDto userCategoryDto) throws SQLException;
//	
//	public List<Integer> selectUserPostView(Integer userSeq) throws SQLException;
}
