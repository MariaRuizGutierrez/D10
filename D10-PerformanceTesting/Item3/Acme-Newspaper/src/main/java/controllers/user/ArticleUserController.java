
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.NewspaperService;
import services.UserService;
import controllers.AbstractController;
import domain.Article;
import domain.Newspaper;
import domain.User;

@Controller
@RequestMapping("/article/user")
public class ArticleUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private UserService			userService;


	//List my articles-----------------------------------------------------------

	@RequestMapping(value = "/listMyArticles", method = RequestMethod.GET)
	public ModelAndView listMyArticles(@RequestParam final int newspaperId) {

		ModelAndView result;
		final Collection<Article> articles;
		Newspaper newspaper;
		User user;

		user = this.userService.findByPrincipal();
		newspaper = this.newspaperService.findOne(newspaperId);
		Assert.isTrue(newspaper.getPublisher().equals(user));
		articles = newspaper.getArticles();

		result = new ModelAndView("article/list");
		result.addObject("articles", articles);
		result.addObject("newspaper", newspaper);
		result.addObject("showButtonEdit", true);
		result.addObject("requestURI", "article/user/list.do");

		return result;

	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		ModelAndView result;
		Article article;

		article = this.articleService.create(newspaperId);

		result = this.createEditModelAndView(article);
		return result;
	}

	//Edition--------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int articleId) {
		ModelAndView result;
		Article article;
		User user;

		user = this.userService.findByPrincipal();
		article = this.articleService.findOne(articleId);
		Assert.isTrue(user.getArticles().contains(article), "Cannot commit this operation, because it's illegal");
		Assert.isTrue(article.getPublishedMoment() == null && article.isDraftMode() == true, "Tiene que estar en modo borrador y sin fecha de publicacion");
		Assert.notNull(article);
		result = this.createEditModelAndView(article);
		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Article article, final BindingResult bindingResult) {
		ModelAndView result;

		article = this.articleService.reconstruct(article, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(article);
		else
			try {
				this.articleService.save(article);
				result = this.listMyArticles(article.getNewspaper().getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("El articulo a guardar no puede ser nulo"))
					result = this.createEditModelAndView(article, "article.notNull.error");
				else if (oops.getMessage().equals("el escritor del articulo debe ser el mismo que el usuario logueado"))
					result = this.createEditModelAndView(article, "article.writerEqualsLogged.error");
				else if (oops.getMessage().equals("el escritor del articulo debe ser el mismo que el publicador del periodico"))
					result = this.createEditModelAndView(article, "article.writerEqualsPublished.error");
				else if (oops.getMessage().equals("tiene que asignarse un periodico para poder guardar en modo final"))
					result = this.createEditModelAndView(article, "article.finalModeWithOneNewspaper.error");
				else if (oops.getMessage().equals("el periodico no puede estar publicado para introducir este articulo"))
					result = this.createEditModelAndView(article, "article.newspaperNotPublished.error");
				else
					result = this.createEditModelAndView(article, "article.commit.error");
			}
		return result;
	}

	//Auxiliares ---------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Article article) {

		Assert.notNull(article);
		ModelAndView result;
		result = this.createEditModelAndView(article, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Article article, final String messageCode) {
		assert article != null;

		ModelAndView result;

		result = new ModelAndView("article/edit");
		result.addObject("article", article);
		result.addObject("message", messageCode);
		return result;

	}

}
