package springboot.dao.goods;

import java.util.HashMap;
import java.util.List;

import springboot.model.goods.Goods;

public interface GoodsDao {

	public Goods findById(String id); 
	
	public List<Goods> findAll(int first,int last);
	
	public List<Goods> findByShopId(String id,int first,int last);
	
	public List<Goods> findByClass(String goodsClass,int first,int last);
	
	public List<Goods> findByLike(HashMap hashMap);
	
	public int insertGoods(Goods goods);
	
	public int updateGoods(Goods goods);
	
	public int deleteGoods(String id);
}
