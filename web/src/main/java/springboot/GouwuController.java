package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springboot.model.goods.Goods;
import springboot.model.gouwu.GOUWUS;
import springboot.model.gouwu.Gouwu;
import springboot.model.user.User;
import springboot.service.goods.IGoodsService;
import springboot.service.gouwu.IgouwuService;
import springboot.service.user.IUserService;
import springboot.utils.Page;
import springboot.utils.PageUtil;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class GouwuController {

	@Autowired
	private IgouwuService gouwuService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IUserService userService;
	@RequestMapping(value="addGouwu")
	@ResponseBody
	public HashMap<String, Object> addGouwu(@RequestBody HashMap<String, Object> hashMap,HttpSession session){
		HashMap<String, Object> result =new HashMap<String, Object>();
		String goodsId=(String)hashMap.get("id");
		Goods goods=goodsService.findById(goodsId);
		double price=Double.parseDouble((String)hashMap.get("price"));
		int number=Integer.parseInt((String)hashMap.get("number"));
		if(goods.getNumber()<number)
		{
			result.put("code", 0);
			result.put("message","购买失败，库存不足");
			return result;
		}
		String userId=(String)((User)session.getAttribute("user")).getId();
		User user=(User)session.getAttribute("user");
		if(goods.getShopId().equals(userId)){
			result.put("code", 0);
			result.put("message","购买失败，不可以买自己的东西呀!");
			return result;
		}
		Gouwu gouwu=new Gouwu();
		gouwu.setId(UUID.randomUUID().toString().replace("-", ""));
		gouwu.setGoodsId(goodsId);
		gouwu.setNum(number);
		gouwu.setTotalprice(number*price);
		gouwu.setCreatetime(new Timestamp(new Date().getTime()));
		gouwu.setUserId(userId);
		gouwu.setShopId(goods.getShopId());
		if(gouwu.getTotalprice()>user.getMoney()){
			result.put("code", 0);
			result.put("message","购买失败，你太穷!");
			return result;
		}
		try {
			if(gouwuService.insert(gouwu)==1){
				user.setMoney(user.getMoney()-gouwu.getTotalprice());
				userService.update(user);
				User shop=userService.getById(goods.getShopId());
				shop.setMoney(shop.getMoney()+gouwu.getTotalprice());
				userService.update(shop);
				result.put("code", 1);
				result.put("message","购买成功");
				goods.setNumber(goods.getNumber()-number);
				goodsService.updateGoods(goods);
			}
			else{
				result.put("code", 0);
				result.put("message","购买失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message","购买失败");
		}
		return result;
		
	}
	
	
	@RequestMapping(value="findAllG")
	public String findAll(@RequestParam(name="page")int page,HttpSession session,HashMap<String, Object> hashMap){
		User user=(User)session.getAttribute("user");
		Page page2 = PageUtil.createPage(12, gouwuService.findAll( 0, -1,user.getId()).size(), page);
		List<Gouwu> list = gouwuService.findAll(
				((page2.getCurrentPage() == 0 ? 1 : page2.getCurrentPage()) - 1) * 10, 10,user.getId());
		
		List<GOUWUS> list2=new ArrayList<GOUWUS>();
		for (Gouwu gouwu : list) {
			String goodsid=gouwu.getGoodsId();
			Goods goods=goodsService.findById(goodsid);
			GOUWUS gouwus=new GOUWUS();
			gouwus.setName(goods.getName());
			gouwus.setNumber(gouwu.getNum());
			gouwus.setPrice(goods.getPrice());
			gouwus.setCreatetime(gouwu.getCreatetime());
			gouwus.setTotalprice(gouwu.getTotalprice());
			list2.add(gouwus);
		}
		hashMap.put("gouwus", list2);
		hashMap.put("page", page2);
		return "gouwu";
		
	}
	
	@RequestMapping(value="findAllB")
	public String findAllB(@RequestParam(name="page")int page,HttpSession session,HashMap<String, Object> hashMap){
		
		User user=(User)session.getAttribute("user");
		if(user==null||!user.getType().equals("商户")){
			return "403";
		}
		Page page2 = PageUtil.createPage(12, gouwuService.findAllB( 0, -1,user.getId()).size(), page);
		List<Gouwu> list = gouwuService.findAllB(
				((page2.getCurrentPage() == 0 ? 1 : page2.getCurrentPage()) - 1) * 10, 10,user.getId());
		
		List<GOUWUS> list2=new ArrayList<GOUWUS>();
		for (Gouwu gouwu : list) {
			String goodsid=gouwu.getGoodsId();
			Goods goods=goodsService.findById(goodsid);
			User buy=userService.getById(gouwu.getUserId());
			GOUWUS gouwus=new GOUWUS();
			gouwus.setName(goods.getName());
			gouwus.setNumber(gouwu.getNum());
			gouwus.setPrice(goods.getPrice());
			gouwus.setCreatetime(gouwu.getCreatetime());
			gouwus.setTotalprice(gouwu.getTotalprice());
			gouwus.setBuyName(buy.getName());
			list2.add(gouwus);
		}
		hashMap.put("gouwus", list2);
		hashMap.put("page", page2);
		return "shouchu";
		
	}
}
