package com.reforma.ecoreforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class home {

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}
}
