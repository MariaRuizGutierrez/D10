
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;
import domain.Newspaper;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController {

	// Services---------------------------------------------------------
	@Autowired
	private NewspaperService	newspaperService;


	//Constructor--------------------------------------------------------
	public NewspaperController() {
		super();
	}

	//Listing-----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String messageCode) {

		ModelAndView result;
		Collection<Newspaper> newspapers;

		//user = this.userService.findByPrincipal();
		newspapers = this.newspaperService.findNewspapersPublished();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/list.do");
		result.addObject("message", messageCode);
		return result;

	}

}
