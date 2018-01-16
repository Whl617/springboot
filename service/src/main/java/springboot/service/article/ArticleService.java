package springboot.service.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.dao.article.ArticleDao;
import springboot.model.article.Article;
@Service
public class ArticleService implements IArticleService{

	@Autowired
	ArticleDao articleDao;
	@Override
	public List<Article> findAll(int first, int last, String point) {
		// TODO Auto-generated method stub
		return articleDao.findAll(first, last, point);
	}

	@Override
	public Article get(String id) {
		// TODO Auto-generated method stub
		return articleDao.get(id);
	}

	@Override
	public int insert(Article article) {
		// TODO Auto-generated method stub
		return articleDao.insert(article);
	}

	@Override
	public int update(Article article) {
		// TODO Auto-generated method stub
		return articleDao.update(article);
	}

	@Override
	public List<Article> findUser(int first, int last, String username) {
		// TODO Auto-generated method stub
		return articleDao.findUser(first, last, username);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return articleDao.delete(id);
	}

	@Override
	public int findMAll() {
		// TODO Auto-generated method stub
		return articleDao.findMAll();
	}

}
