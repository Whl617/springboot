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

@Controller
public class GoodsController {

	@Autowired
	IGoodsService goodsService;
	
	@RequestMapping(value="/insertGoods")
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
	
	@RequestMapping(value="/deleteGoods")
	@ResponseBody
	public String delete(@RequestParam(name="id")String id){
		int flag=goodsService.deleteGoods(id);
		if(flag==1)
			return "success";
			else {
				return "fail";
			}
	}
	
	@RequestMapping(value="findAllGoods")
	@ResponseBody
	public String findAll(){
		List<Goods> list=goodsService.findAll(0, 2);
		
		return list.size()+"";
	}
	
	@RequestMapping(value="findByIdGoods")
	@ResponseBody
	public String get(@RequestParam(name="id")String id){
		Goods goods=goodsService.findById(id);
		return goods.getContext();
	}
	
	@RequestMapping(value="findByShopGoods")
	@ResponseBody
	public String findByShop(@RequestParam(name="id")String id){
		List<Goods> list=goodsService.findByShopId(id, 0, 2);
		return list.get(0).getContext();
	}
	
	@RequestMapping(value="findByClassGoods")
	@ResponseBody
	public String findByClass(@RequestParam(name="class")String goodsClass){
		List<Goods> list=goodsService.findByClass(goodsClass, 0, 2);
		return list.get(0).getContext();
	}
	
	@RequestMapping(value="findByLikeGoods")
	@ResponseBody
	public String findByLike(@RequestParam(name="class")String goodsClass,
			@RequestParam(name="name") String name
			){
		HashMap<String,Object> map=new HashMap<String, Object>();
		map.put("name", "%"+name+"%");
		map.put("goodsClass", "%"+goodsClass+"%");
		List<Goods> list=goodsService.findByLike(map);
		return list.get(0).getContext();
	}
	
	
	@RequestMapping(value="/updateGoods")
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
