package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;

import controllers.AbstractController;
import domain.Article;

@Controller
@RequestMapping(value = "/article/admin")
public class ArticleAdminController extends AbstractController{
	
	//Services--------------------------------------------

	@Autowired
	private ArticleService	articleService;
	
	//Constructor--------------------------------------------------------
	
	public ArticleAdminController(){
		super();
	}
	
	//Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Article> articles;
			

		articles = this.articleService.findAll();

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/admin/list.do?d-16544-p=1");
		return result;

	}
	
	//Listing taboo word
	
	@RequestMapping(value = "/listTabooWord", method = RequestMethod.GET)
	public ModelAndView listTabooWord() {

		ModelAndView result;
		Collection<Article> articles;
			

		articles = this.articleService.articleWithTabooWord();

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/admin/listTabooWord.do?d-16544-p=1");
		return result;

	}
		
	//Delete---------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(int articleId) {
		ModelAndView result;
		Article article;

		article = this.articleService.findOne(articleId);
		Assert.notNull(article);
		try {
			this.articleService.delete(article);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if(oops.getMessage().equals("This article can't delete because his newspaper have subscriptions"))
				result = this.listWithMessage("article.commit.error.newspaper");
			else
				result = this.listWithMessage("article.commit.error");
		}

		return result;
	}
	
	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<Article> articles;
		articles = this.articleService.findAll();
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "/article/admin/list.do");
		result.addObject("message", message);
		return result;

	}
		

}