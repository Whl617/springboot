package springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springboot.model.article.Article;
import springboot.model.goods.Goods;
import springboot.model.goodsClass.GoodsClass;
import springboot.model.user.User;
import springboot.service.article.IArticleService;
import springboot.service.goods.IGoodsService;
import springboot.service.goodsClass.IGoodsClassService;
import springboot.service.gouwu.IgouwuService;
import springboot.service.user.IUserService;

@Controller
public class MainController {

	@Autowired
	IGoodsService goodsService;
	
	@Autowired
	IGoodsClassService goodsClassService;
	
	@Autowired
	IArticleService articleService;
	@Autowired
	IgouwuService gouwuService;
	
	@Autowired
	IUserService userService;
	
	@RequestMapping("/home")
	public String init(HashMap<String, Object> hashMap){
		
		List<GoodsClass> goodsClasses=goodsClassService.findAll();
		List<Article> articles=articleService.findAll(0, 5, "");
		List<List<Goods>> allGoods=new ArrayList<List<Goods>>();
		for (GoodsClass goodsClass : goodsClasses) {
			List<Goods> goods=goodsService.findByClass(goodsClass.getName(), 0, 3);
			allGoods.add(goods);
		}
		List<Goods> goods=goodsService.findAll(0, -1);
		List<Goods> goods2=new ArrayList<Goods>();
		for(int i=0;i<3;i++){
			int temp=(int)(Math.random()*goods.size());
			goods2.add(goods.get(temp));
			goods.remove(temp);
		}
		hashMap.put("goodsClasss", goodsClasses);
		hashMap.put("allGoods", allGoods);
		hashMap.put("articles", articles);
		hashMap.put("Cgoods", goods2);
		return "home";
	}
	
	@RequestMapping(value="main")
	public String index(HashMap<String, Object> hashMap,HttpSession httpSession){
		User user=(User)httpSession.getAttribute("user");
		if(user==null||!user.getType().equals("管理员")){
			return "403";
		}
		int MgouwuCount=gouwuService.MonthAll();
		int gouwuCount=gouwuService.All();
		int artCount=articleService.findAll(0, -1, "").size();
		int artMCount=articleService.findMAll();
		int userCount=userService.getAll(0, -1).size();
		int goodsCount=goodsService.findAll(0, -1).size();
		double totalMoney=gouwuService.AllMoney();
		
		hashMap.put("MgouwuCount", MgouwuCount);
		hashMap.put("gouwuCount", gouwuCount);
		hashMap.put("artCount", artCount);
		hashMap.put("artMCount", artMCount);
		hashMap.put("userCount", userCount);
		hashMap.put("goodsCount", goodsCount);
		hashMap.put("totalMoney", totalMoney);
		
		return "houtai";
	}
	
	@RequestMapping(value="/goodsClassManager")
	public String goodsClassManager(HashMap<String, Object> hashMap,HttpSession httpSession){
		User user=(User)httpSession.getAttribute("user");
		if(user==null||!user.getType().equals("管理员")){
			return "403";
		}
		List<GoodsClass> list=goodsClassService.findAll();
		hashMap.put("goodsClasss", list);
		return "goodsclass";
	}
	
	@RequestMapping(value="/updateClass")
	@ResponseBody
	public HashMap<String, Object> update(@RequestParam(name="id") String id,@RequestParam(name="name")String name){
		HashMap<String, Object> result=new HashMap<String, Object>();
		GoodsClass goodsClass=goodsClassService.findById(id);
		goodsClass.setName(name);
		try {
			if(	goodsClassService.update(goodsClass)==1){
				result.put("code", 1);
				result.put("message", "修改成功");
				
			}
			else{
				result.put("code", 0);
				result.put("message", "修改失败");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "修改失败");
			
		}
	return result;
		
		
	}
	
	@RequestMapping(value="/addClass")
	@ResponseBody
	public HashMap<String, Object> add(@RequestParam(name="name")String name){
		HashMap<String, Object> result=new HashMap<String, Object>();
		GoodsClass goodsClass=new GoodsClass();
		goodsClass.setId(UUID.randomUUID().toString().replace("-", ""));
		goodsClass.setName(name);
		try {
			if(	goodsClassService.insert(goodsClass)==1){
				result.put("code", 1);
				result.put("message", "添加成功");
				
			}
			else{
				result.put("code", 0);
				result.put("message", "添加失败");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "添加失败");
			
		}
		return result;
		
		
	}
	@RequestMapping(value="/deleteClass")
	@ResponseBody
	public HashMap<String, Object> del(@RequestParam(name="id")String id){
		HashMap<String, Object> result=new HashMap<String, Object>();
		try {
			if(	goodsClassService.delete(id)==1){
				result.put("code", 1);
				result.put("message", "删除成功");
				
			}
			else{
				result.put("code", 0);
				result.put("message", "删除失败");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "删除失败，此类别可能有绑定商品或服务器异常");
			
		}
		return result;
		
		
	}
}
