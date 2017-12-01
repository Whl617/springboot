package springboot.service.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.model.test.User;
import springboot.test.dao.UserDao;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> get(){ 
		return userDao.getAll();
	}
}
