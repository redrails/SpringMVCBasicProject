package webAppProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("index");
	}

	@RequestMapping(value="/UserPage", method=RequestMethod.GET)
	public ModelAndView loginForm() {
		return new ModelAndView("userpage");
	}

	@RequestMapping(value="/AdminPage", method=RequestMethod.GET)
	public ModelAndView invalidLogin() {
		return new ModelAndView("adminpage");
	}
}