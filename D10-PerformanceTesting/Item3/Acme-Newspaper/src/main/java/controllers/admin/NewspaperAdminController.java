package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NewspaperService;

import controllers.AbstractController;
import domain.Newspaper;

@Controller
@RequestMapping(value = "/newspaper/admin")
public class NewspaperAdminController extends AbstractController{
	
	//Services--------------------------------------------
	
	@Autowired
	private NewspaperService newspaperService;
	
	//Constructor--------------------------------------------------------
	
	public NewspaperAdminController(){
		super();
	}
	
	//Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Newspaper> newspapers;
				

		newspapers = this.newspaperService.findAll();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/admin/list.do?d-16544-p=1");
		return result;
	}	
	
	//Listing
	
	@RequestMapping(value = "/listTabooWord", method = RequestMethod.GET)
	public ModelAndView listTabooWord() {

		ModelAndView result;
		Collection<Newspaper> newspapers;
				

		newspapers = this.newspaperService.NewspaperWithTabooWord();

		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "newspaper/admin/listTabooWord.do?d-16544-p=1");
		return result;
	}	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(int newspaperId) {
		ModelAndView result;
		Newspaper newspaper;

		newspaper = this.newspaperService.findOne(newspaperId);
		Assert.notNull(newspaper);
		try {
			this.newspaperService.delete(newspaper);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			
			if(oops.getMessage().equals("Se pueden eliminar los periodicos publicos y privado que aún no tengas suscripciones"))
				result = this.listWithMessage("newspaper.commit.error.private");
			else
				result = this.listWithMessage("newspaper.commit.error");
		}

		return result;
	}
	
	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<Newspaper> newspapers;
		newspapers = this.newspaperService.findAll();
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("requestURI", "/newspaper/admin/list.do");
		result.addObject("message", message);
		return result;

	}

}
