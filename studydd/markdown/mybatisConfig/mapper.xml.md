##mapper.xml


	<?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="com.test.dao.testMapper">
	    <select id="testSelect" resultType="Integer">
	        select 1 from dual
	    </select>
	    <select id="allSelect" resultType="com.test.domain.FileDomain">
	        select
	          file_id,
	          file_name,
	          save_file_name,
	          file_extention,
	          file_length,
	          create_date,
	          modify_date,
	          comment,
	          content_type
	        from
	          files
	    </select>
	    <select id="allSelectMap" resultType="HashMap">
	        select * from files
	    </select>
	    <select id="allSelectByKey" parameterType="HashMap" resultType="com.test.domain.FileDomain">
	        SELECT
	          file_id,
	          file_name,
	          save_file_name,
	          file_extention,
	          file_length,
	          create_date,
	          modify_date,
	          comment,
	          content_type
	        FROM
	          files
	        WHERE
	          file_id = ${fileId}
	    </select>
	    <select id="allSelectString" resultType="String">
	        select

          file_name,
          save_file_name,
          file_extention,
          file_length,
          create_date,
          modify_date,
          comment,
          content_type
        from
          files
        WHERE
          file_id = 65
    </select>
	</mapper>


