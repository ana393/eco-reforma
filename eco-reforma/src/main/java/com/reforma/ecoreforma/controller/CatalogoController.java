package com.reforma.ecoreforma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.HabitacionService;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {
	
 private final HabitacionService habitacionService;
	 
	 private static final Logger log = LoggerFactory.getLogger(CatalogoController.class);
	 public CatalogoController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	 
	@GetMapping
	public String pagina_Catalogo(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
		int[] pagination = ControllerUtil.computePagination(page);
		log.info("IN pagina_Catalogo(): ", pagination.length);
		model.addAttribute("pagina", pagination);
		model.addAttribute("url","/catalogo");
		model.addAttribute("page", page);
		log.info("IN pagina_Recursos()");
		return "catalogo";
		}
		
}
