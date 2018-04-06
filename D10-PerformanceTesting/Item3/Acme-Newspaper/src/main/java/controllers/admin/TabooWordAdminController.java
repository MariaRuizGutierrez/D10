package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationSystemService;
import services.TabooWordService;
import controllers.AbstractController;
import domain.ConfigurationSystem;
import domain.TabooWord;

@Controller
@RequestMapping(value = "/tabooWord/admin")
public class TabooWordAdminController extends AbstractController{
	
	//Services--------------------------------------------

	@Autowired
	private TabooWordService tabooWordService;
	
	@Autowired
	private ConfigurationSystemService	configurationSystemService;

	//Constructor--------------------------------------------------------
	
	public TabooWordAdminController(){
		super();
	}
	
	//Creating--------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
			
		ModelAndView result;
		TabooWord tabooWord;
			
		tabooWord = this.tabooWordService.create();
			
		result = this.createEditModelAndView(tabooWord);
		return result;
			
	}
	
	//Edition---------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tabooWordId) {
		ModelAndView result;
		TabooWord tabooWord;

		tabooWord = this.tabooWordService.findOne(tabooWordId);

		Assert.notNull(tabooWord);
		result = this.createEditModelAndView(tabooWord);
		return result;
	}
	
	
	
	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TabooWord tabooWord, final BindingResult binding) {

		ModelAndView result;
		ConfigurationSystem configurationSystem;
		TabooWord tabooWordSaved;
		
		configurationSystem = this.configurationSystemService.findConfigurationSystem();

		if (binding.hasErrors())
			result = this.createEditModelAndView(tabooWord);
		else
			try {
				
				if(tabooWord.getId() == 0){
					tabooWordSaved = this.tabooWordService.save(tabooWord);
					configurationSystem.getTabooWords().add(tabooWordSaved);
					this.configurationSystemService.save(configurationSystem);
				}else
					tabooWordSaved = this.tabooWordService.save(tabooWord);
				
				result = new ModelAndView("redirect:/configurationSystem/admin/tabooWord/list.do?d-16544-p=1");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tabooWord, "tabooWord.commit.error");
			}
		
		return result;

		}
	
	//Auxiliary-----------------------

	protected ModelAndView createEditModelAndView(TabooWord tabooWord) {

		Assert.notNull(tabooWord);
		ModelAndView result;
		result = this.createEditModelAndView(tabooWord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(TabooWord tabooWord, String messageCode) {
		assert tabooWord != null;

		ModelAndView result;

		result = new ModelAndView("tabooWord/edit");
		result.addObject("tabooWord", tabooWord);
		result.addObject("message", messageCode);

		return result;

		}
}