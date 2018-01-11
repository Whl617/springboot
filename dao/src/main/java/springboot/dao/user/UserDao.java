package springboot.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import springboot.model.user.User;


public interface UserDao  {

	public List<User> getAll(int first,@Param("last")int last);
	
	public User getById(String id);
	
	public User getByPhone(String phone);
	
	public int insert(User user);
	
	public int update(User user);
	
	public int delete(String id);
	
}
