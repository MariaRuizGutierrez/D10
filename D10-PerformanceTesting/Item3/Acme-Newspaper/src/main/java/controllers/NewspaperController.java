
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import domain.Article;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController {

	// Services---------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;
	@Autowired
	private ArticleService		articleService;


	//Constructor--------------------------------------------------------
	public NewspaperController() {
		super();
	}

	//Search -----------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView listNewspaperByKeyword(@RequestParam final String keyword) {
		final ModelAndView result;
		Collection<Newspaper> newspapers;

		newspapers = this.newspaperService.findNewspapersByKeyword(keyword);

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("showSearch", true);
		result.addObject("requestURI", "newspaper/search.do");
		result.addObject("requestURISearchNewspaper", "newspaper/search.do");
		return result;
	}

	//Listing-----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String messageCode) {

		ModelAndView result;
		Collection<Newspaper> newspapers;

		//user = this.userService.findByPrincipal();
		newspapers = this.newspaperService.findNewspapersPublishedAndOpen();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("showSearch", true);
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("requestURISearchNewspaper", "newspaper/search.do");
		result.addObject("message", messageCode);
		return result;

	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		final ModelAndView result;
		Newspaper newspaper = new Newspaper();
		final Collection<Article> articles;

		newspaper = this.newspaperService.findOne(newspaperId);

		//TODOS LOS ARTÍCULOS DE UN PERIÓDICO
		articles = this.articleService.findArticlesByNewspaperId(newspaperId);

		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("requestURI", "newspaper/display.do");

		return result;
	}

}
