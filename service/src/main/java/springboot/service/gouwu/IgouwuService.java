package springboot.service.gouwu;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springboot.model.gouwu.Gouwu;

public interface IgouwuService {

	public int insert(Gouwu gouwu);

	public List<Gouwu> findAll(int first,int last,String userId);
	
	public List<Gouwu> findAllB(int first,@Param("last")int last,String shopId);
	public int MonthAll();
	public int All();
	public double AllMoney();
}
