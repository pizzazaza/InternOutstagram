package ex;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intern.outstagram.domain.UserDomain;

@Repository
public interface UserDao {

	public Integer insert(UserDomain user) throws SQLException;
    public List<UserDomain> selectAll() throws SQLException;
    public UserDomain selectById(Long userId) throws SQLException;
    public UserDomain selectBySnsId(String userSnsId) throws SQLException;
}
