package springboot;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import springboot.model.goods.Goods;
import springboot.service.goods.IGoodsService;
import springboot.utils.Page;
import springboot.utils.PageUtil;

@Controller(value="/goods")
public class GoodsController {

	@Autowired
	IGoodsService goodsService;
	
	@RequestMapping(value="/insert")
	@ResponseBody
	public String insert(@RequestBody Goods goods){
		goods.setId(UUID.randomUUID().toString().replace("-", ""));
		int flag=goodsService.insertGoods(goods);
		if(flag==1)
		return "success";
		else {
			return "fail";
		}
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public String delete(@RequestParam(name="id")String id){
		int flag=goodsService.deleteGoods(id);
		if(flag==1)
			return "success";
			else {
				return "fail";
			}
	}
	
	@RequestMapping(value="findAll")
	@ResponseBody
	public String findAll(){
		List<Goods> list=goodsService.findAll(0, -1);
		
		return list.size()+"";
	}
	
	@RequestMapping(value="findById")
	@ResponseBody
	public String get(@RequestParam(name="id")String id){
		Goods goods=goodsService.findById(id);
		return goods.getContext();
	}
	
	@RequestMapping(value="findByShop")
	@ResponseBody
	public String findByShop(@RequestParam(name="id")String id){
		List<Goods> list=goodsService.findByShopId(id, 0, 2);
		return list.get(0).getContext();
	}
	
	@RequestMapping(value="findByClass")
	public String findByClass(@RequestParam(name="class")String goodsClass,
			@RequestParam(name="page") int page,
		HashMap<String, Object> hashMap){
		Page page2=PageUtil.createPage(12, goodsService.findByClass(goodsClass,0, -1).size(), page);
		System.out.println(page2.getCurrentPage());
		List<Goods> list=goodsService.findByClass(goodsClass, (page2.getCurrentPage()-1)*12, 12);
		hashMap.put("goodsList", list);
		hashMap.put("goodsClass", goodsClass);
		hashMap.put("page", page2);
		return "serach";
	}
	
	@RequestMapping(value="findByLike")
	public String findByLike(
			@RequestParam(name="name") String name
			,HashMap<String, Object> hashMap){
		HashMap<String,Object> map=new HashMap<String, Object>();
		map.put("name", "%"+name+"%");
		map.put("goodsClass", "%"+name+"%");
		List<Goods> list=goodsService.findByLike(map);
		hashMap.put("goodsList", list);
		int size=goodsService.findAll(0, -1).size()-15;
		if(size < 0)size=0;
		if(list.size()==0){
			List<Goods> list2=goodsService.findAll((int)(Math.random()*size), 15);
			hashMap.put("goods2", list2);
		}
		return "serach1";
	}
	
	
	@RequestMapping(value="/update")
	@ResponseBody
	public String uodate(@RequestBody Goods goods){
		int flag=goodsService.updateGoods(goods);
		if(flag==1)
		return "success";
		else {
			return "fail";
		}
	}
}
