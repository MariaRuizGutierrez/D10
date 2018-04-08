
package controllers.customer;

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
import services.CustomerService;
import services.NewspaperService;
import services.SubscriptionService;
import controllers.AbstractController;
import domain.Article;
import domain.Customer;
import domain.Newspaper;
import domain.Subscription;

@Controller
@RequestMapping("/subscription/customer")
public class SubscriptionCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ArticleService		articleService;

	@Autowired
	private SubscriptionService	subscriptionService;


	// Display Newspaper----------------------------------------------------------------

	@RequestMapping(value = "/displayNewspaper", method = RequestMethod.GET)
	public ModelAndView displayNewspaper(@RequestParam final int newspaperId) {
		final ModelAndView result;
		Newspaper newspaper = new Newspaper();
		final Collection<Article> articles;
		Collection<Newspaper> myNewspapersSubscription;

		newspaper = this.newspaperService.findOne(newspaperId);
		myNewspapersSubscription = this.newspaperService.findNewspapersSubscribedByCustomerId(this.customerService.findByPrincipal().getId());
		Assert.isTrue(myNewspapersSubscription.contains(newspaper), "you are not subscribed to this newspaper");

		//TODOS LOS ARTÍCULOS DE UN PERIÓDICO
		articles = this.articleService.findArticlesByNewspaperId(newspaperId);

		result = new ModelAndView("newspaper/display");
		result.addObject("newspaper", newspaper);
		result.addObject("articles", articles);
		result.addObject("showButtonDisplayArticle", true);
		result.addObject("requestURI", "subscription/customer/displayNewspaper.do");

		return result;
	}

	// Display Newspaper----------------------------------------------------------------

	@RequestMapping(value = "/displayArticle", method = RequestMethod.GET)
	public ModelAndView displayArticle(@RequestParam final int articleId) {
		final ModelAndView result;
		Article article;
		Collection<Newspaper> myNewspapersSubscription;

		article = this.articleService.findOne(articleId);
		myNewspapersSubscription = this.newspaperService.findNewspapersSubscribedByCustomerId(this.customerService.findByPrincipal().getId());
		Assert.isTrue(myNewspapersSubscription.contains(article.getNewspaper()), "you are not subscribed to the newspaper of this article");

		result = new ModelAndView("article/display");
		result.addObject("article", article);
		result.addObject("requestURI", "subscription/customer/displayArticle.do");

		return result;
	}

	//List private newspapers-----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Newspaper> newspapersToSubscribe;
		Collection<Newspaper> newspapersSubscribed;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		newspapersToSubscribe = this.newspaperService.findPrivateAndPublicatedNewspapersToSubscribe();
		newspapersSubscribed = this.newspaperService.findNewspapersSubscribedByCustomerId(customer.getId());

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapersToSubscribe);
		result.addObject("newspapersSubscribed", newspapersSubscribed);
		result.addObject("showButtonSubscription", true);
		result.addObject("showButtonDisplay", true);
		result.addObject("requestURI", "subscription/customer/list.do");

		return result;

	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int newspaperId) {
		ModelAndView result;
		Subscription subscription;

		subscription = this.subscriptionService.create(newspaperId);

		result = this.createEditModelAndView(subscription);
		return result;
	}

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Subscription subscription, final BindingResult bindingResult) {
		ModelAndView result;

		subscription = this.subscriptionService.reconstruct(subscription, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(subscription);
		else
			try {
				this.subscriptionService.save(subscription);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("el cliente ya esta subscrito a este periodico"))
					result = this.createEditModelAndView(subscription, "subscription.customerHasThisSubscription.error");
				else if (oops.getMessage().equals("El cliente de la subscripcion debe ser el mismo que el logueado"))
					result = this.createEditModelAndView(subscription, "subscription.customerEqualsLogged.error");
				else if (oops.getMessage().equals("solo se pueden subscribir a los periodicos privados"))
					result = this.createEditModelAndView(subscription, "subscription.onlyPrivateNewspaper.error");
				else if (oops.getMessage().equals("solo se pueden subscribir a los periodicos publicados"))
					result = this.createEditModelAndView(subscription, "subscription.onlyPublishedNewspaper.error");
				else
					result = this.createEditModelAndView(subscription, "subscription.commit.error");
			}
		return result;
	}

	//Auxiliares ---------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Subscription subscription) {

		Assert.notNull(subscription);
		ModelAndView result;
		result = this.createEditModelAndView(subscription, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Subscription subscription, final String messageCode) {
		assert subscription != null;

		ModelAndView result;

		result = new ModelAndView("subscription/edit");
		result.addObject("subscription", subscription);
		result.addObject("message", messageCode);
		return result;

	}

}
