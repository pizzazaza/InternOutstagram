package ex.recommend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import ex.recommend.dto.LikeDto;

public interface RecommendMapper {
	final String selectUserForRecommend = "SELECT \n" + 
			"			User.seq\n" + 
			"		FROM \n" + 
			"			User\n" + 
			"		WHERE\n" + 
			"			User.user_id like 'user%'\n" + 
			"		ORDER BY\n" + 
			"			User.seq\n" + 
			"		LIMIT\n" + 
			"			3500";
	
	final String selectLikeForRecommendByUserSeq = "SELECT\n" + 
			"			Post_Like.user_seq, Post_Like.post_seq\n" + 
			"		FROM\n" + 
			"			Post_Like\n" + 
			"		WHERE\n" + 
			"			Post_Like.user_seq = #{seq}\n" + 
			"		ORDER BY\n" + 
			"			Post_Like.post_seq";
	
	final String selectViewForRecommendByUserSeq = "SELECT \n" + 
			"			Post_View.post_seq, max(view_time) as viewTime , count(seq) as viewCount\n" + 
			"		FROM  \n" + 
			"			Post_View\n" + 
			"		WHERE\n" + 
			"			Post_View.user_seq = #{seq}\n" + 
			"		GROUP BY \n" + 
			"			Post_View.post_seq, Post_View.user_seq\n" + 
			"		ORDER BY\n" + 
			"			Post_View.post_seq;";
	

}
