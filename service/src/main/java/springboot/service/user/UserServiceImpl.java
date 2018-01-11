package springboot.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.dao.user.UserDao;
import springboot.model.user.User;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getAll(int first, int last) {
		// TODO Auto-generated method stub
		return userDao.getAll(first, last);
	}

	@Override
	public User getById(String id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

	@Override
	public User getByPhone(String phone) {
		// TODO Auto-generated method stub
		return userDao.getByPhone(phone);
	}

	@Override
	public int insert(User user) {
		// TODO Auto-generated method stub
		return userDao.insert(user);
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return userDao.update(user);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return userDao.delete(id);
	}
	
	
}
