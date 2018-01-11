package springboot.service.user;

import java.util.List;

import springboot.model.user.User;

public interface IUserService {
	public List<User> getAll(int first,  int last);

	public User getById(String id);

	public User getByPhone(String phone);

	public int insert(User user);

	public int update(User user);

	public int delete(String id);

}
