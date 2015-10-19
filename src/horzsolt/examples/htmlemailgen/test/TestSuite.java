package horzsolt.examples.htmlemailgen.test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import horzsolt.examples.htmlemailgen.article.Article;
import horzsolt.examples.htmlemailgen.article.ArticleCategory;
import horzsolt.examples.htmlemailgen.auth.OAuth;
import horzsolt.examples.htmlemailgen.common.Logger;
import horzsolt.examples.htmlemailgen.common.TransformObjectToMap;
import horzsolt.examples.htmlemailgen.gdata.SpreadSheet;
import horzsolt.examples.htmlemailgen.template.EmailGenerator;

public class TestSuite {

	public void testSuccesfulLogin() throws Exception {

		Assert.assertTrue(OAuth.authorize() != null);
	}

	public void testListEntries() throws Exception {
		
		List<Article> articles = SpreadSheet.list(OAuth.authorize());
		Assert.assertTrue(articles.size() > 0);
	}

	@Test	
	public void testObjectToMapTransformation() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
		Article article = new Article();
		article.setTitle("_title [developer]");
		article.setCategory(ArticleCategory.BOOK);
		article.setDescription("_description");
		article.setUrl("_url");

		Logger.debug(article.toString());
		
		Map<String, String> map = TransformObjectToMap.transform(article);
		Assert.assertTrue(map.get("title").toString().equals("_title [developer]"));
		Assert.assertTrue(map.get("category").toString().equals("BOOK"));
		Assert.assertTrue(map.get("description").toString().equals("_description"));
		Assert.assertTrue(map.get("url").toString().equals("_url"));
		Assert.assertTrue(map.get("author").toString().equals("developer"));
		
		article = new Article();
		article.setTitle("_title");
		article.setCategory(ArticleCategory.BOOK);
		article.setDescription("_description");
		article.setUrl("_url");

		map = TransformObjectToMap.transform(article);
		Assert.assertTrue(map.get("title").toString().equals("_title"));
		Assert.assertTrue(map.get("category").toString().equals("BOOK"));
		Assert.assertTrue(map.get("description").toString().equals("_description"));
		Assert.assertTrue(map.get("url").toString().equals("_url"));
		Assert.assertTrue(map.get("author").toString().equals("anonymous"));		
	}
	
	@Test
	public void testEmailGen() throws Exception {
		
		List<Article> articles = SpreadSheet.list(OAuth.authorize());
		
		Assert.assertTrue(articles.size() > 0);
		EmailGenerator.generate(articles);
	}

}
