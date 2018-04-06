
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import domain.Article;

@Controller
@RequestMapping("/article")
public class ArticleController extends AbstractController {

	// Services---------------------------------------------------------
	@Autowired
	private ArticleService	articleService;


	// List ---------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMyArticles(@RequestParam final int newspaperId) {

		ModelAndView result;
		final Collection<Article> articles;
		//final Newspaper newspaper;

		articles = this.articleService.findArticlesByNewspaperId(newspaperId);

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		//result.addObject("newspaper", newspaper);
		//result.addObject("showButtonEdit", true);
		result.addObject("requestURI", "article/list.do");

		return result;

	}

}
