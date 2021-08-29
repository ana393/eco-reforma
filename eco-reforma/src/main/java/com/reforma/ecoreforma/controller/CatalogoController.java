package com.reforma.ecoreforma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.HabitacionService;
/**
 * Clase controller Catalogo.
 * Con la anotacion @Controller , Spring podrá detectar la clase CatalogoController cuando realice el escaneo de componentes.
 * @RequestMapping - anotacion que permite el mapeo a los metodos del controlador.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see HabitacionService
 *
 */
@Controller
@RequestMapping("/catalogo")
public class CatalogoController {
	
	 private final HabitacionService habitacionService;

		/**
		 * Constructor para la inicializacion de la variable principal. Con la
		 * anotacion @Autowired se lleva a cabo la inyección de dependencias del objeto.
		 * 
		 * @param habitacionService
		 */
     @Autowired
	 public CatalogoController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	 
	/**
	 * Devuelve la lista de recursos existentes
	 * @param pageRequest {@link PageRequest}
	 * @param model {@link Model}
	 * @return la pagina catalogo.html
	 */
	@GetMapping
	public String obtenerCatalogo(
			@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, 
			Model model) {
		 
		Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
		int[] pagination = ControllerUtil.computePagination(page);

		model.addAttribute("pagina", pagination);
		model.addAttribute("url","/catalogo");
		model.addAttribute("page", page);
		
		return "catalogo";
		}
}
