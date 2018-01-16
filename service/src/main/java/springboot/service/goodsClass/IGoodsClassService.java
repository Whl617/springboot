package springboot.service.goodsClass;

import java.util.List;

import springboot.model.goodsClass.GoodsClass;

public interface IGoodsClassService {

	public List<GoodsClass> findAll();

	public int insert(GoodsClass goodsClass);

	public int delete(String id);

	public int update(GoodsClass goodsClass);

	public List<GoodsClass> findBypage(int first, int last);
	
	public GoodsClass findById(String id);

	public List<GoodsClass> findByName(String name);
}
