package springboot.service.article;

import java.util.List;

import springboot.model.article.Article;

public interface IArticleService {

	public List<Article> findAll(int first,int last,String point);
	
	public Article get(String id);
	
	public int insert(Article article);
	public int update(Article article);
}
