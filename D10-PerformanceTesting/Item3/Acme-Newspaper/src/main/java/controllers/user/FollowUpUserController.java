
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ArticleService;
import services.FollowUpService;
import controllers.AbstractController;
import domain.Article;
import domain.FollowUp;

@Controller
@RequestMapping("/followUp/user")
public class FollowUpUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FollowUpService	followUpService;

	@Autowired
	private ArticleService	articleService;


	// Create -----------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int articleId) {
		ModelAndView result;
		final Article article;
		final FollowUp followUp;

		article = this.articleService.findOne(articleId);
		followUp = this.followUpService.create(article);

		result = this.createEditModelAndView(followUp);
		return result;
	}

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FollowUp followUp, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(followUp);
		else
			try {

				this.followUpService.save(followUp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(followUp, "followUp.commit.error");
			}
		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int articleId) {
		ModelAndView result;
		Collection<FollowUp> followUps;

		followUps = this.followUpService.findFollowUpsByArticle(articleId);

		result = new ModelAndView("announcement/list");
		result.addObject("followUps", followUps);
		result.addObject("requestURI", "followUp/user/list.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final FollowUp followUp) {
		Assert.notNull(followUp);
		ModelAndView result;
		result = this.createEditModelAndView(followUp, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final FollowUp followUp, final String messageCode) {
		assert followUp != null;

		ModelAndView result;

		result = new ModelAndView("followUp/edit");
		result.addObject("followUp", followUp);
		result.addObject("message", messageCode);
		return result;

	}

}
