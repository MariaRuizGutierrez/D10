
package controllers.user;

import java.util.ArrayList;
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

import services.ChirpService;
import services.UserService;
import controllers.AbstractController;
import domain.Chirp;
import domain.User;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private UserService		userService;


	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int chirpId) {
		ModelAndView result;
		Chirp chirp;

		chirp = this.chirpService.create();

		result = this.createEditModelAndView(chirp);
		return result;
	}

	//Edition--------------------------------------------------------------------------------
	//		@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//		public ModelAndView edit(@RequestParam final int chirpId) {
	//			ModelAndView result;
	//			Chirp chirp;
	//
	//			chirp = this.chirpService.findOne(chirpId);
	//
	//			//Un usuario solo podr� editar sus anuncios.
	//			if (chirp.getId() != 0) {
	//				Collection<Announcement> announcementsOfUser;
	//				announcementsOfUser = this.announcementService.findAnnouncementByUserId();
	//				Assert.isTrue(announcementsOfUser.contains(chirp), "Cannot commit this operation, because it's illegal");
	//			}
	//
	//			Assert.notNull(chirp);
	//			result = this.createEditModelAndView(chirp);
	//			return result;
	//		}

	// Save -----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirp);
		else
			try {
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirp, "chirp.commit.error");
			}
		return result;
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		User userConnected;
		Collection<Chirp> chirp;

		userConnected = this.userService.findByPrincipal();
		chirp = new ArrayList<Chirp>(this.chirpService.getChirpsOfMyFollowers(userConnected.getId()));

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirp);
		result.addObject("requestURI", "chirp/user/list.do");

		return result;
	}

	//Auxiliares ---------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Chirp chirp) {

		Assert.notNull(chirp);
		ModelAndView result;
		result = this.createEditModelAndView(chirp, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Chirp chirp, final String messageCode) {
		assert chirp != null;

		ModelAndView result;

		result = new ModelAndView("chirp/edit");
		result.addObject("chirp", chirp);
		result.addObject("message", messageCode);
		return result;

	}

}