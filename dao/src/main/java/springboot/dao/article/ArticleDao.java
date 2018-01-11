package springboot.dao.article;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springboot.model.article.Article;

public interface ArticleDao {

	public List<Article> findAll(int first,@Param("last")int last,@Param("point")String point);
	
	public Article get(String id);
	
	public int insert(Article article);
	public int update(Article article);
}
