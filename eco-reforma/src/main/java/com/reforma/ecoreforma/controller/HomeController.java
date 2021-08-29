package com.reforma.ecoreforma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.HabitacionService;
/**
 * Clase controller Home.
 * Con la anotacion @Controller  Spring podrá detectar la clase HomeController cuando realice el escaneo de componentes.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see HabitacionService
 *
 */
@Controller
public class HomeController {

	private final HabitacionService habitacionService;
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Constructor para la inicializacion  de la variable principal.
	 * Con la anotacion @Autowired   se lleva a cabo la inyección de dependencias del objeto.
	 * @param habitacionService
	 */
	@Autowired
	public HomeController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	

  /**
 * 
 * @return pagina principal - Home.html
 */
@GetMapping("/home")
  public String home() {
  return "home";
 }	
  

  
  /**
 * Devuelve el catalogo con recursos disponibiles
 * URL request {"/buscar"}, metodo GET.
 * @param pageRequestv {@link PageRequest}
 * @param filtro 
 * @param model {@link Model}
 * @return pagina catalogo.html
 */
@GetMapping("/buscar")
  public String buscar(
		 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest,
		 @RequestParam String filtro,
		 Model model) {
	  
	  Page<Habitacion> page;
	  if(filtro != null && !filtro.isEmpty()) {
		  page = habitacionService.encuentraPorTituloOrPorTipo(filtro, filtro, pageRequest);
		  log.info(String.format("Estas encontrando articulos segun el criterio [%s]  existen [%s] recurso.", filtro.toString(), page.getNumberOfElements()));
	  } else {
		  page =habitacionService.encuentraTodo(pageRequest); 
	  }
	
	int[] pagination = ControllerUtil.computePagination(page);
	model.addAttribute("pagina", pagination);
	model.addAttribute("url","/catalogo");
	model.addAttribute("page", page);
	
	return "catalogo";
  }
}
