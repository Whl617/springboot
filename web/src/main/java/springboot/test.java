package springboot;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class test {

	@RequestMapping(value="/home")
	public String home(HashMap<String, Object> map){
		map.put("a", "aaa");
		return "home";
	}
    
}
