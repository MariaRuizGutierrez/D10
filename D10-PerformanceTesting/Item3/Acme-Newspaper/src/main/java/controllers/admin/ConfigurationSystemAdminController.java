package controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import controllers.AbstractController;
import domain.ConfigurationSystem;

@Controller
@RequestMapping(value = "/configurationSystem/admin")
public class ConfigurationSystemAdminController extends AbstractController{
	
	//Services--------------------------------------------

	@Autowired
	private ConfigurationSystemService	configurationSystemService;
	
	//Constructor--------------------------------------------------------
	
	public ConfigurationSystemAdminController(){
		super();
	}
	
	//Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		ConfigurationSystem configurationSystem;
		

		configurationSystem = this.configurationSystemService.findConfigurationSystem();
		
		

		result = new ModelAndView("configurationSystem/listTabooWord");
		result.addObject("configurationSystem", configurationSystem);
		result.addObject("requestURI", "configurationSystem/admin/list.do");
		return result;

	}

}
