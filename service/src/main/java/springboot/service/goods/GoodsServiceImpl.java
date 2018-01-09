package springboot.service.goods;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.dao.goods.GoodsDao;
import springboot.model.goods.Goods;

@Service
public class GoodsServiceImpl implements IGoodsService {

	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public Goods findById(String id) {
		// TODO Auto-generated method stub
		return goodsDao.findById(id);
	}

	@Override
	public List<Goods> findAll(int first, int last) {
		// TODO Auto-generated method stub
		return goodsDao.findAll(first, last);
	}

	@Override
	public List<Goods> findByShopId(String id, int first, int last) {
		// TODO Auto-generated method stub
		return goodsDao.findByShopId(id, first, last);
	}

	@Override
	public List<Goods> findByClass(String goodsClass, int first, int last) {
		// TODO Auto-generated method stub
		return goodsDao.findByClass(goodsClass, first, last);
	}

	@Override
	public List<Goods> findByLike(HashMap hashMap) {
		// TODO Auto-generated method stub
		return goodsDao.findByLike(hashMap);
	}

	@Override
	public int insertGoods(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.insertGoods(goods);
	}

	@Override
	public int updateGoods(Goods goods) {
		// TODO Auto-generated method stub
		return goodsDao.updateGoods(goods);
	}

	@Override
	public int deleteGoods(String id) {
		// TODO Auto-generated method stub
		return goodsDao.deleteGoods(id);
	}

}
