package springboot.service.gouwu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.dao.gouwu.GouwuDao;
import springboot.model.gouwu.Gouwu;
@Service
public class GouwuService implements IgouwuService{

	@Autowired
	private GouwuDao gouwuDao;
	@Override
	public int insert(Gouwu gouwu) {
		// TODO Auto-generated method stub
		return gouwuDao.insert(gouwu);
	}

	@Override
	public List<Gouwu> findAll(int first, int last,String userId) {
		// TODO Auto-generated method stub
		return gouwuDao.findAll(first, last,userId);
	}

	@Override
	public List<Gouwu> findAllB(int first, int last, String shopId) {
		// TODO Auto-generated method stub
		return gouwuDao.findAllB(first, last, shopId);
	}

	@Override
	public int MonthAll() {
		// TODO Auto-generated method stub
		return gouwuDao.MonthAll();
	}

	@Override
	public int All() {
		// TODO Auto-generated method stub
		return gouwuDao.All();
	}

	@Override
	public double AllMoney() {
		// TODO Auto-generated method stub
		return gouwuDao.AllMoney();
	}

}
