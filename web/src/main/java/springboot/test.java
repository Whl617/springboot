package springboot;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {

	@RequestMapping(value="/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/test")
	public String test(Model model){
		model.addAttribute("aa","aaa");
		return "index";
	}
	
	@RequestMapping(value="/home")
	public String home(HashMap<String, Object> map){
		map.put("a", "aaa");
		return "home";
	}
    
}
