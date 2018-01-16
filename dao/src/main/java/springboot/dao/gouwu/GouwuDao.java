package springboot.dao.gouwu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springboot.model.gouwu.Gouwu;

public interface GouwuDao {

	public int insert(Gouwu gouwu);
	
	public List<Gouwu> findAll(int first,@Param("last")int last,@Param("userId")String userId);
	public List<Gouwu> findAllB(int first,@Param("last")int last,@Param("shopId")String shopId);
	public int MonthAll();
	public int All();
	public double AllMoney();
}
