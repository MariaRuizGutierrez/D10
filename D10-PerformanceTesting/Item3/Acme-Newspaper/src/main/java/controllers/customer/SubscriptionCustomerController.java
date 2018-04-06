
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.NewspaperService;
import domain.Customer;
import domain.Newspaper;

@Controller
@RequestMapping("/subscription/customer")
public class SubscriptionCustomerController {

	// Services ---------------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;

	@Autowired
	private CustomerService		customerService;


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
		result.addObject("requestURI", "subscription/customer/list.do");

		return result;

	}

}
