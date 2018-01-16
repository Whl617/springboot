package springboot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import springboot.model.goods.Goods;
import springboot.model.goodsClass.GoodsClass;
import springboot.model.user.User;
import springboot.service.goods.IGoodsService;
import springboot.service.goodsClass.IGoodsClassService;
import springboot.utils.Page;
import springboot.utils.PageUtil;

@Controller
public class GoodsController {

	@Autowired
	IGoodsService goodsService;
	@Autowired
	IGoodsClassService goodsClassService;

	@RequestMapping(value = "/insert")
	@ResponseBody
	public HashMap<String, Object> insert(@RequestBody Goods goods, HttpSession session) throws IOException {
		goods.setId(UUID.randomUUID().toString().replace("-", ""));
		goods.setShopId((String) ((User) session.getAttribute("user")).getId());
		goods.setType(0);
		//
		HashMap<String, Object> result = new HashMap<String, Object>();
		int flag = goodsService.insertGoods(goods);
		if (flag == 1) {
			result.put("code", 1);
			result.put("message", "添加成功");
		} else {
			result.put("code", 0);
			result.put("message", "添加失败");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/updateGoods")
	@ResponseBody
	public HashMap<String, Object> update(@RequestBody Goods goods, HttpSession session) throws IOException {
		goods.setShopId((String) ((User) session.getAttribute("user")).getId());
		//
		HashMap<String, Object> result = new HashMap<String, Object>();
		goods.setType(0);
		int flag = goodsService.updateGoods(goods);
		if (flag == 1) {
			result.put("code", 1);
			result.put("message", "修改成功");
		} else {
			result.put("code", 0);
			result.put("message", "修改失败");
		}
		return result;
	}

	@RequestMapping(value = "imageUp")
	@ResponseBody
	public HashMap<String, Object> imageUp(@RequestParam(name = "image") MultipartFile image) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String filename = "";
		try {
			String uploadContentType = image.getContentType();
			String expandedName = ""; // 文件扩展名
			if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {
				// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
				expandedName = ".jpg";
			} else if (uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")) {
				// IE6上传的png图片的headimageContentType是"image/x-png"
				expandedName = ".png";
			} else if (uploadContentType.equals("image/gif")) {
				expandedName = ".gif";
			} else if (uploadContentType.equals("image/bmp")) {
				expandedName = ".bmp";
			} else {
				result.put("code", 0);
				result.put("message", "上传文件格式错误");
				return result;
			}
			//
			InputStream is = image.getInputStream();
			String uploadPath = "D://img/postImg/123";
			uploadPath = URLDecoder.decode(uploadPath, "UTF-8");
			File file = new File(uploadPath);
			if (!file.exists())
				file.mkdirs();
			filename = java.util.UUID.randomUUID().toString().replace("-", "") + expandedName;
			File toFile = new File(uploadPath, filename);
			OutputStream os = new FileOutputStream(toFile);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();

		} catch (Exception e) {
			// TODO: handle exception
			result.put("code", 0);
			result.put("message", "上传图片失败");
			return result;
		}
		result.put("code", 1);
		result.put("message", "上传图片成功");
		result.put("image", filename);
		return result;
	}

	@RequestMapping(value = "/addPage")
	public String addPage(HashMap<String, Object> hashMap) {
		List<GoodsClass> list=goodsClassService.findAll();
		hashMap.put("goodsClass", list);
		return "addGoods";
	}
	
	@RequestMapping(value = "/updatePage")
	public String update(@RequestParam(name="id")String id,HashMap<String, Object> hashMap) {
		List<GoodsClass> list=goodsClassService.findAll();
		hashMap.put("goodsClass", list);
		hashMap.put("goods", goodsService.findById(id));
		return "updateGoods";
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public HashMap<String, Object> delete(@RequestBody HashMap<String, Object> hashMap) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Goods goods=goodsService.findById((String) hashMap.get("id"));
		goods.setType(1);
		int flag = goodsService.updateGoods(goods);
		if (flag == 1) {
			result.put("code", 1);
			result.put("message", "删除成功");
		} else {
			result.put("code", 0);
			result.put("message", "删除失败");
		}
		return result;
	}

	@RequestMapping(value = "findAll")
	@ResponseBody
	public String findAll() {
		List<Goods> list = goodsService.findAll(0, -1);

		return list.size() + "";
	}

	@RequestMapping(value = "findById")
	public String get(@RequestParam(name = "id") String id,HashMap<String, Object> hashMap) {
		Goods goods = goodsService.findById(id);
		List<Goods> goodsList=goodsService.findByClass(goods.getGoodsClass(), 0, -1);
		List<Goods> goods2=new ArrayList<Goods>();
		for(int i=0;i<3&&i<goodsList.size();i++){
			int temp=(int)(Math.random()*goodsList.size());
			goods2.add(goodsList.get(temp));
			goodsList.remove(temp);
		}
		hashMap.put("goods", goods);
		hashMap.put("goodsList", goods2);
		return "showGoods";
	}

	@RequestMapping(value = "findByShop")
	public String findByShop(@RequestParam(name = "page") int page, HashMap<String, Object> hashMap,
			HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user==null||!user.getType().equals("商户")){
			return "403";
		}
		String id = (String) ((User) session.getAttribute("user")).getId();
		Page page2 = PageUtil.createPage(9, goodsService.findByShopId(id, 0, -1).size(), page);
		List<Goods> list = goodsService.findByShopId(id,
				((page2.getCurrentPage() == 0 ? 1 : page2.getCurrentPage()) - 1) * 9, 9);
		hashMap.put("goodsList", list);
		hashMap.put("page", page2);
		return "shop";
	}

	@RequestMapping(value = "findByClass")
	public String findByClass(@RequestParam(name = "class") String goodsClass, @RequestParam(name = "page") int page,
			HashMap<String, Object> hashMap) {
		Page page2 = PageUtil.createPage(12, goodsService.findByClass(goodsClass, 0, -1).size(), page);
		List<Goods> list = goodsService.findByClass(goodsClass,
				((page2.getCurrentPage() == 0 ? 1 : page2.getCurrentPage()) - 1) * 12, 12);
		if (list.size() == 0) {
			int size = goodsService.findAll(0, -1).size() - 6;
			if (size < 0)
				size = 0;
			List<Goods> list2 = goodsService.findAll((int) (Math.random() * size), 6);
			hashMap.put("goods2", list2);
		}
		hashMap.put("goodsList", list);
		hashMap.put("goodsClass", goodsClass);
		hashMap.put("page", page2);
		return "serach";
	}

	@RequestMapping(value = "findByLike")
	public String findByLike(@RequestParam(name = "name") String name, HashMap<String, Object> hashMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%" + name + "%");
		map.put("goodsClass", "%" + name + "%");
		List<Goods> list = goodsService.findByLike(map);
		hashMap.put("goodsList", list);
		if (list.size() == 0) {
			int size = goodsService.findAll(0, -1).size() - 6;
			if (size < 0)
				size = 0;
			List<Goods> list2 = goodsService.findAll((int) (Math.random() * size), 6);
			hashMap.put("goods2", list2);
		}
		return "likeSerach";
	}

	@RequestMapping(value = "findInShop")
	public String findInShop(@RequestParam(name = "name") String name, HashMap<String, Object> hashMap,
			HttpSession session) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%" + name + "%");
		map.put("goodsclass", "%" + name + "%");
		map.put("shopId", (String) ((User) session.getAttribute("user")).getId());

		List<Goods> list = goodsService.findByLike(map);
		hashMap.put("goodsList", list);
		int size = goodsService.findAll(0, -1).size() - 15;
		if (size < 0)
			size = 0;
		return "shop";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String uodate(@RequestBody Goods goods) {
		int flag = goodsService.updateGoods(goods);
		if (flag == 1)
			return "success";
		else {
			return "fail";
		}
	}
}
