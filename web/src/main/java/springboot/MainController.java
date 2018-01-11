package springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springboot.model.article.Article;
import springboot.model.goods.Goods;
import springboot.model.goodsClass.GoodsClass;
import springboot.service.article.IArticleService;
import springboot.service.goods.IGoodsService;
import springboot.service.goodsClass.IGoodsClassService;

@Controller
public class MainController {

	@Autowired
	IGoodsService goodsService;
	
	@Autowired
	IGoodsClassService goodsClassService;
	
	@Autowired
	IArticleService articleService;
	
	@RequestMapping("/home")
	public String init(HashMap<String, Object> hashMap){
		
		List<GoodsClass> goodsClasses=goodsClassService.findAll();
		List<Article> articles=articleService.findAll(0, 5, "");
		List<List<Goods>> allGoods=new ArrayList<List<Goods>>();
		for (GoodsClass goodsClass : goodsClasses) {
			List<Goods> goods=goodsService.findByClass(goodsClass.getName(), 0, 3);
			allGoods.add(goods);
		}
		hashMap.put("goodsClasss", goodsClasses);
		hashMap.put("allGoods", allGoods);
		hashMap.put("articles", articles);
		return "home";
	}
}
