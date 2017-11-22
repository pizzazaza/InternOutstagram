package ex;

import java.util.List;

import com.intern.outstagram.domain.UserDomain;

public interface UserService {
	public Integer addUser(UserDomain user);
    public List<UserDomain> getAll();
    public UserDomain getUserById(Long userId);
    public UserDomain getUserBySnsId(String userSnsId);
}
