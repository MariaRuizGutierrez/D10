package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import controllers.AbstractController;
import domain.Chirp;

@Controller
@RequestMapping(value = "/chirp/admin")
public class ChirpAdminController extends AbstractController{
	
	//Services--------------------------------------------

	@Autowired
	private ChirpService	chirpService;
		
	//Constructor--------------------------------------------------------
	
	public ChirpAdminController(){
		super();
	}

	
	//Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Chirp> chirps;
		

		chirps = this.chirpService.findAll();

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "chirp/admin/list.do?d-16544-p=1");
		return result;

	}
	
	//Delete---------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.findOne(chirpId);
		Assert.notNull(chirp);
		try {
			this.chirpService.delete(chirp);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listWithMessage("announcement.commit.error");
		}

		return result;
	}
	
	//ancially methods---------------------------------------------------------------------------

	protected ModelAndView listWithMessage(final String message) {
		final ModelAndView result;
		Collection<Chirp> chirps;
		chirps = this.chirpService.findAll();
		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "/chirp/admin/list.do");
		result.addObject("message", message);
		return result;

	}
}
