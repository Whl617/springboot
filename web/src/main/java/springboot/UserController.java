package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springboot.model.user.User;
import springboot.service.user.IUserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class UserController {

	@Autowired
	IUserService userService;

	@RequestMapping(value = "/regist1", method = RequestMethod.POST)
	public HashMap<String, Object> regist(@RequestBody HashMap<String, String> hashMap,HttpSession session) {
		User user = new User();
		user.setName(hashMap.get("name"));
		user.setPhone(hashMap.get("phone"));
		user.setPassword(hashMap.get("password"));
		user.setType(hashMap.get("type"));
		HashMap<String, Object> result = new HashMap<String, Object>();
		user.setId(UUID.randomUUID().toString().replace("-", ""));
		user.setMoney(0);
		if (!user.getPassword().equals(hashMap.get("repassword"))) {
			result.put("code", 0);
			result.put("message", "两次密码输入不一致");
			return result;
		}

		User user2 = userService.getByPhone(user.getPhone());
		if (user2 != null) {
			result.put("code", 0);
			result.put("message", "该手机号码已注册");
			return result;
		}
		int type = -1;
		try {
			type = userService.insert(user);
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "注册失败，服务器异常，联系技术人员");
		}

		if (type == 1) {
			result.put("code", 1);
			result.put("message", "注册成功");
			session.setAttribute("user", user);
		} else {
			result.put("code", 0);
			result.put("message", "注册失败");
		}

		return result;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public HashMap<String, Object> update(@RequestBody HashMap<String, String> hashMap,HttpSession session) {
		User user = new User();
		user.setName(hashMap.get("name"));
		user.setPhone(hashMap.get("phone"));
		user.setPassword(hashMap.get("password"));
		user.setType(hashMap.get("type"));
		HashMap<String, Object> result = new HashMap<String, Object>();
		user.setId(hashMap.get("id"));
		if (!user.getPassword().equals(hashMap.get("repassword"))) {
			result.put("code", 0);
			result.put("message", "两次密码输入不一致");
			return result;
		}
		
		User user2 = userService.getByPhone(user.getPhone());
		if (user2 != null) {
			if(!user.getPhone().equals((String)((User)session.getAttribute("user")).getPhone())){
				result.put("code", 0);
				result.put("message", "该手机号码已注册");
				return result;
			}
		}
		int type = -1;
		try {
			type = userService.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "修改失败，服务器异常，联系技术人员");
		}
		
		if (type == 1) {
			result.put("code", 1);
			result.put("message", "修改成功");
			session.setAttribute("user", user);
		} else {
			result.put("code", 0);
			result.put("message", "修改失败");
		}
		return result;
	}
    @RequestMapping(value="UUPage")
    public ModelAndView updatePage(HttpSession session){
    	ModelAndView modelAndView=new ModelAndView("/updateUser");
    	modelAndView.addObject("user", (User)session.getAttribute("user"));
    	return modelAndView;
    }
	@RequestMapping(value = "login1")
	public HashMap<String, Object> login(@RequestBody HashMap<String, String> hashMap, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		User user=null;
		try {
			user = userService.getByPhone(hashMap.get("phone"));
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "登录失败");
		}
		if (user == null || !user.getPassword().equals(hashMap.get("password"))) {
			result.put("code", 0);
			result.put("message", "账号或密码错误");
			return result;
		}
		session.setAttribute("user", user);
		result.put("code", 1);
		result.put("message", "登录成功");
		
		if(user.getType().equals("管理员")){
			result.put("code", 2);
			result.put("message", "管理员登录成功");
		}
		return result;

	}
	
	@RequestMapping(value="logout")
	public HashMap<String, Object> logout(HttpSession session){
		
		if((User)session.getAttribute("user")!=null){
			session.setAttribute("user", null);
		}
		
		HashMap<String, Object> result =new HashMap<String, Object>();
		
		result.put("code", 1);
		result.put("message", "注销成功");
		return result;
 		
	}
	
	@RequestMapping(value="addmoney")
	public HashMap<String, Object> addMoney(@RequestParam("money") double money,HttpSession session){
		User user=(User)session.getAttribute("user");
		double oldM=user.getMoney();
		HashMap<String, Object> result=new HashMap<String, Object>();
		try {
			user.setMoney(money+user.getMoney());
			userService.update(user);
			session.setAttribute("user", user);
			result.put("code", 1);
			result.put("message", "充值成功");
		} catch (Exception e) {
			e.printStackTrace();
			user.setMoney(oldM);
			result.put("code", 0);
			result.put("message", "充值失败");
		}
		return result;
	}
}
