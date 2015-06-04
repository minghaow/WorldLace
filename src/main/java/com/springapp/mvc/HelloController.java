package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HelloController {

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String printWelcome(ModelMap model) {
//		model.addAttribute("message", "Hello world!");
//		return "hello";
//	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView printWelcomeTest(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return new ModelAndView("test");
	}

}