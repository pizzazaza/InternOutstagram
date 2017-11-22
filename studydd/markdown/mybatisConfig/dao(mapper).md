##dao(mapper)

	package com.test.dao;
	
	import com.test.domain.FileDomain;
	import org.apache.ibatis.jdbc.SQL;
	import org.springframework.stereotype.Repository;
	
	import java.sql.SQLException;
	import java.util.List;
	import java.util.Map;
	
	@Repository
	public interface testMapper {
	    public Integer testSelect() throws SQLException;

    public List<FileDomain> allSelect() throws SQLException;

    public List<Map<String,Object>> allSelectMap() throws SQLException;

    public FileDomain allSelectByKey(Map<String, Object> params) throws SQLException;

    public String allSelectString() throws SQLException;
}



