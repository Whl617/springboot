package springboot.service.article;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springboot.model.article.Article;

public interface IArticleService {

	public List<Article> findAll(int first,int last,String point);
	
	public List<Article> findUser(int first,int last,String username);
	public Article get(String id);
	
	public int insert(Article article);
	public int update(Article article);
	public int delete(String id);
	
	public int findMAll();
}
