package dumaya.dev.BibWeb.controller;

import dumaya.dev.BibWeb.modelAPI.Usager;
import dumaya.dev.BibWeb.service.UsagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	private final UsagerService usagerService;

	public LoginController(UsagerService usagerService) {
		this.usagerService = usagerService;
	}

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(Model model){
		LOGGER.debug("login");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		LOGGER.debug("registration");
		ModelAndView modelAndView = new ModelAndView();
		Usager usager = new Usager();
		modelAndView.addObject("usager", usager);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView creerNouveauUsager(@Valid Usager usager, BindingResult bindingResult) {
		LOGGER.debug("creerusager");
		ModelAndView modelAndView = new ModelAndView();
		Usager usagerExists = usagerService.findUsagerByEmail(usager.getEmail());
		if (usagerExists != null) {
			bindingResult
					.rejectValue("email", "error.usager",
							"There is already a usager registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			usagerService.saveUsager(usager);
			modelAndView.addObject("successMessage", "Usager has been registered successfully");
			modelAndView.addObject("usager", usager);
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		LOGGER.debug("home");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usager usager = usagerService.findUsagerByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + usager.getNom() + " " + usager.getPrenom() + " (" + usager.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
