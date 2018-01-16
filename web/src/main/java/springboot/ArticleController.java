package springboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import springboot.model.article.Article;
import springboot.model.goodsClass.GoodsClass;
import springboot.model.user.User;
import springboot.service.article.IArticleService;
import springboot.service.goodsClass.IGoodsClassService;
import springboot.utils.Page;
import springboot.utils.PageUtil;

@Controller
public class ArticleController {

	@Autowired
	IArticleService articleService;
	@Autowired
	IGoodsClassService goodsClassService;

	@RequestMapping(value = "/getAllArticle")
	public String get(@RequestParam(name = "point") String point, HashMap<String, Object> hashMap,
			@RequestParam(name = "page") int page) {
		Page page2 = PageUtil.createPage(15, articleService.findAll(1, -1, "").size(), page);
		List<Article> list = null;
		System.out.println(page2.getCurrentPage());
		if (point.equals("point")) {
			list = articleService.findAll(((page2.getCurrentPage()==0?1:page2.getCurrentPage()) - 1) * 15, 15, "point");
		} else {
			list = articleService.findAll(((page2.getCurrentPage()==0?1:page2.getCurrentPage()) - 1) * 15, 15, "");
		}
		for (Article article : list) {
			System.out.println(article.getName());
		}
		hashMap.put("art", list);
		hashMap.put("page", page2);
		return "showAllArt";
	}

	@RequestMapping(value = "/getUserArticle")
	public String get(HashMap<String, Object> hashMap, @RequestParam(name = "page") int page, HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user==null){
			return "403";
		}
		String userid = (String) ((User) session.getAttribute("user")).getId();
		Page page2 = PageUtil.createPage(15, articleService.findUser(1, -1, userid).size(), page);
		List<Article> list = null;
		System.out.println(page2.getCurrentPage());

		list = articleService.findUser(((page2.getCurrentPage()==0?1:page2.getCurrentPage()) - 1) * 15, 15, userid);

		for (Article article : list) {
			System.out.println(article.getName());
		}
		hashMap.put("art", list);
		hashMap.put("page", page2);
		return "userArt";
	}
	@RequestMapping(value = "/deleteArt")
	@ResponseBody
	public HashMap<String, Object> delete(@RequestBody HashMap<String, Object> hashMap) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int flag = articleService.delete((String) hashMap.get("id"));
		if (flag == 1) {
			result.put("code", 1);
			result.put("message", "删除成功");
		} else {
			result.put("code", 0);
			result.put("message", "删除失败");
		}
		return result;
	}

	@RequestMapping(value = "/addArt")
	@ResponseBody
	public HashMap<String, Object> addArt(@RequestBody HashMap<String, Object> hashMap, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			result.put("code", 0);
			result.put("message", "请先登录");
			return result;
		}
		Article article = new Article();
		article.setId(UUID.randomUUID().toString().replace("-", ""));
		article.setContent((String) hashMap.get("content"));
		article.setName((String) hashMap.get("name"));
		article.setGoodsclass((String) hashMap.get("goodsClass"));
		article.setPoint(0);
		article.setUsername(user.getName());
		article.setUserid(user.getId());
		article.setCreatetime(new Timestamp(new Date().getTime()));
		try {
			if (articleService.insert(article) == 1) {
				result.put("code", 1);
				result.put("message", "发表成功");
			} else {
				result.put("code", 0);
				result.put("message", "发表失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("code", 0);
			result.put("message", "服务器错误，发表失败");
		}

		return result;
	}

	@RequestMapping(value = "addArtPage")
	public String addPage(HashMap<String,Object> hashMap) {
		List<GoodsClass> list=goodsClassService.findAll();
		hashMap.put("goodsClass", list);
		return "addArt";
	}

	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("upload") MultipartFile upload, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		String uploadContentType = upload.getContentType();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// CKEditor提交的很重要的一个参数
		String callback = request.getParameter("CKEditorFuncNum");

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
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
					+ "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");
			out.println("</script>");
			return null;
		}

		if (upload.getSize() > 600 * 1024) {
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于600k');");
			out.println("</script>");
			return null;
		}

		InputStream is = upload.getInputStream();
		String uploadPath = "D://img/postImg/123";
		uploadPath = URLDecoder.decode(uploadPath, "UTF-8");
		File file = new File(uploadPath);
		if (!file.exists())
			file.mkdirs();
		String filename = java.util.UUID.randomUUID().toString().replace("-", "") + expandedName;
		File toFile = new File(uploadPath, filename);
		OutputStream os = new FileOutputStream(toFile);
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		is.close();
		os.close();

		// 返回“图像”选项卡并显示图片
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'"
				+  "http://localhost:8081/load?fileName="  + filename + "','')");
		out.println("</script>");

		return null;
	}

	@RequestMapping(value = "findall")
	public String findall(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String realPath = "D://img/postImg/123";
		realPath = URLDecoder.decode(realPath, "UTF-8");
		File folder = new File(realPath);
		if (!folder.exists()) {
			return null;
		}
		List<String> subFileerSet = new ArrayList<String>();
		File[] subFiles = folder.listFiles();
		if (null != subFiles && 0 < subFiles.length) {
			for (int i = 0; i < subFiles.length; i++) {
				File _file = subFiles[i];
				if (_file.isDirectory()) {
					continue;
				} else {
					System.out.println(_file.getName());
					subFileerSet.add(_file.getName());
				}
			}
		}
		String callback = request.getParameter("CKEditorFuncNum");
		PrintWriter out;

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try {
			out = response.getWriter();
			out.println("<script>");

			// 定义点击选择js
			out.println("function choose(obj){");
			out.println("window.opener.CKEDITOR.tools.callFunction(" + callback + ",obj);");
			out.println("window.close();");
			out.println("}");
			out.println("</script>");

			// 如果是文件夹，则显示文件夹并且可以再次触发下级和上级目录

			// 如果是文件，则点击就选择文件到控件中
			if (0 < subFileerSet.size()) {
				Iterator<String> subFileerSetIndex = subFileerSet.iterator();
				while (subFileerSetIndex.hasNext()) {
					String ftemp = subFileerSetIndex.next();
					String fileUrl = "http://localhost:8081/load?fileName=" + ftemp;
					// fileUrl = StringUtils.replace(fileUrl, "//", "/");

					out.print(
							"<div style='width:150px;height:150px;float:left;word-break:break-all;padding:5px;background:#666699;margin:5px;'>");
					out.print("<a href='javascript:void(0)' onclick=choose('" + fileUrl
							+ "')><img style='border:none;width:145px;height:145px;' src=" + fileUrl + " mce_src="
							+ fileUrl + " title='" + fileUrl + "'/></a>");
					out.print("</div>");

				}
			}

			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "findArt")
	public String findArt(@RequestParam(name = "id") String id, HashMap<String, Object> hashMap) {
		Article article = articleService.get(id);
		article.setPoint(article.getPoint() + 1);
		articleService.update(article);
		List<Article> articles = articleService.findAll(0, 10, "");
		List<Article> articles1 = articles.subList(0, 5);
		List<Article> articles2 = articles.subList(5, articles.size());
		hashMap.put("article", article);
		hashMap.put("articles1", articles1);
		hashMap.put("articles2", articles2);

		return "showArt";
	}

	@RequestMapping(value = "load")
	public void loadImage(@RequestParam("fileName") String fileName, HttpServletRequest req,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String path = "D://img/postImg/123";
		File file;

		file = new File(path + "/" + fileName);// c:/resource/images/news/123.jpg

		if (file.exists()) {
			resFile(fileName, response, file);
		} else {// 文件不存在
			File nofile = new File(path + "/images/noimg.png");
			resFile("noimg.png", response, nofile);
		}

	}

	private void resFile(String fileName, HttpServletResponse response, File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int len = 0;

			// 设置返回的文件类型
			response.setContentType("image/" + fileName.substring(fileName.indexOf('.')));

			while ((len = fis.read(bytes)) > 0) {
				response.getOutputStream().write(bytes, 0, len);
			}
			response.getOutputStream().flush();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
