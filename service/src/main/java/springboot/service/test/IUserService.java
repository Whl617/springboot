package springboot.service.test;

import java.util.List;

import org.springframework.stereotype.Component;

import springboot.model.test.User;


public interface IUserService {
	public List<User> get();
}
