package springboot.service.goodsClass;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.dao.goodsClass.GoodsClassDao;
import springboot.model.goodsClass.GoodsClass;
@Service
public class GoodsClassService implements IGoodsClassService{

	@Autowired
	private GoodsClassDao goodsClassDao;
	@Override
	public List<GoodsClass> findAll() {
		// TODO Auto-generated method stub
		return goodsClassDao.findAll();
	}

	@Override
	public int insert(GoodsClass goodsClass) {
		// TODO Auto-generated method stub
		return goodsClassDao.insert(goodsClass);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return goodsClassDao.delete(id);
	}

	@Override
	public int update(GoodsClass goodsClass) {
		// TODO Auto-generated method stub
		return goodsClassDao.update(goodsClass);
	}

	@Override
	public List<GoodsClass> findBypage(int first, int last) {
		// TODO Auto-generated method stub
		return goodsClassDao.findBypage(first, last);
	}

	@Override
	public List<GoodsClass> findByName(String name) {
		// TODO Auto-generated method stub
		return goodsClassDao.findByName(name);
	}

	@Override
	public GoodsClass findById(String id) {
		// TODO Auto-generated method stub
		return goodsClassDao.findById(id);
	}

}
