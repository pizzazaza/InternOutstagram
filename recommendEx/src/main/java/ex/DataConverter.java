package ex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ex.recommend.app.RecommendInfoMake;
import ex.recommend.dao.RecommendDao;
import ex.recommend.dto.LikeDto;
import ex.recommend.dto.UserDto;
import ex.recommend.dto.ViewDto;



public class DataConverter {

	public static void main(String[] args) throws IOException {
//		RecommendInfoMake recommendInfoMake = new RecommendInfoMake();
//		recommendInfoMake.createRecommendData();
		Random random = new Random();
		Double score = 0.d;
		for(int vc = 0; vc < 10; vc++) {
			if(random.nextInt(100) <= (5)) {
				System.out.println("hit");
			}
			
		}
		
	}
}
