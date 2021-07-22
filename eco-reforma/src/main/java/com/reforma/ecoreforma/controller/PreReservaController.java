package com.reforma.ecoreforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PreReservaController {

	
	
	@GetMapping
	public String preReserva() {
		return "reservas";
	}
}
