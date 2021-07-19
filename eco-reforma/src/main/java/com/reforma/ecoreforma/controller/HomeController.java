package com.reforma.ecoreforma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.HabitacionService;

@Controller
public class HomeController {

	private HabitacionService habitacionService;
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	public HomeController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	

  @GetMapping("/home")
  public String home() {
  return "home";
 }	
  
  @GetMapping("/buscar")
  public String buscar(
		 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest,
		 @RequestParam String filtro,
		 Model model) {
	  
	Page<Habitacion> page = habitacionService.encuentraPorTipoOPorTitulo(filtro, filtro, pageRequest);
	log.info("IN pagina_Catalogo(): ",filtro.toString());
	int[] pagination = ControllerUtil.computePagination(page);
	model.addAttribute("pagina", pagination);
	model.addAttribute("url","/catalogo");
	model.addAttribute("page", page);
	log.info("IN pagina_Catalogo(): ", pagination.length);
	return "catalogo";
  }
}
