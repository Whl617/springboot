package springboot.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import springboot.model.test.User;


@Repository
@Mapper
public interface UserDao {
 
	@Select("select * from user")
	
	public List<User> get();
	
}
