##service

- interface


		package com.test.service;
		import com.test.domain.FileDomain;
		
		
		import java.sql.SQLException;
		import java.util.List;
		import java.util.Map;
		
		public interface testService {
		    public Integer testSelect();
	
	    public List<FileDomain> allSelect();
	
	    public List<Map<String, Object>> allSelectMap();
	
	    public FileDomain allSelectByKey(Integer fileId);
	
	    public String allSelectString();
		}

- implements

		package com.test.service;
		
		import com.test.dao.testMapper;
		import com.test.domain.FileDomain;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.stereotype.Service;
		import org.springframework.transaction.annotation.Transactional;
		
		import java.sql.SQLException;
		import java.util.HashMap;
		import java.util.List;
		import java.util.Map;
		
		@Service
		@Transactional
		public class testServiceImpl implements testService{
	
	    private testMapper testM;
	
	    @Autowired
	    public testServiceImpl(testMapper testM){
	        this.testM = testM;
	    }
	
	    @Override
	    public Integer testSelect(){
	        try{
	            return testM.testSelect();
	        } catch(SQLException e){
	            return null;
	        }
	    }
	
	    @Override
	    public List<FileDomain> allSelect(){
	        try{
	            return testM.allSelect();
	        }catch (SQLException e){
	            return null;
	        }
	    }
	
	    @Override
	    public List<Map<String, Object>> allSelectMap(){
	        try{
	            return testM.allSelectMap();
	        }catch(SQLException e){
	            return null;
	        }
	    }
	
	    @Override
	    public FileDomain allSelectByKey(Integer fileId) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("fileId", fileId);
	        try{
	            return testM.allSelectByKey(params);
	        }catch (SQLException e) {
	            return null;
	        }
	    }
	
	    @Override
	    public String allSelectString() {
	        String str;
	        try{
	            str = testM.allSelectString();
	            return str;
	        }catch(SQLException e){
	            return null;
	        }
	    }
		}
	


