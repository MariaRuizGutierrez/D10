
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.CustomerService;
import services.NewspaperService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/article/customer")
public class ArticleCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private CustomerService		customerService;


	//Search -----------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView listNewspaperByKeyword(@RequestParam final String keyword) {
		final ModelAndView result;
		Collection<Article> articles;

		articles = this.articleService.findArticleByKeyword(keyword);

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("showSearch", true);
		result.addObject("requestURI", "article/customer/search.do");
		result.addObject("requestURISearchArticle", "article/customer/search.do");
		return result;
	}
	// List ---------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listArticlesByUser(@RequestParam final int userId) {

		ModelAndView result;
		final Collection<Article> articles;

		articles = this.articleService.findArticlesByUserId(userId);
		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "article/customer/list.do");
		return result;
	}

	// Display Article Subscripted----------------------------------------------------------------

	@RequestMapping(value = "/displayArticleSubscripted", method = RequestMethod.GET)
	public ModelAndView displayArticle(@RequestParam final int articleId) {
		final ModelAndView result;
		Article article;
		Collection<Newspaper> myNewspapersSubscription;

		article = this.articleService.findOne(articleId);
		myNewspapersSubscription = this.newspaperService.findNewspapersSubscribedByCustomerId(this.customerService.findByPrincipal().getId());
		Assert.isTrue(myNewspapersSubscription.contains(article.getNewspaper()), "you are not subscribed to the newspaper of this article");

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("requestURI", "article/customer/displayArticleSubscripted.do");

		return result;
	}

	@RequestMapping(value = "/listArticles", method = RequestMethod.GET)
	public ModelAndView list(final int newspaperId) {

		ModelAndView result;
		Collection<Article> articles;

		articles = this.articleService.findAllArticlesByCustomerNewspaperList(newspaperId);

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("requestURI", "newspaper/customer/list.do");
		result.addObject("requestURISearchNewspaper", "newspaper/customer/search.do");
		result.addObject("showSearch", true);

		return result;
	}

}
