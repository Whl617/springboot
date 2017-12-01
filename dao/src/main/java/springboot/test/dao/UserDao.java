package springboot.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import springboot.model.test.User;


public interface UserDao  {

	public List<User> getAll();
	
}
