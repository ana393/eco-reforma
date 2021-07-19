package com.reforma.ecoreforma.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.HabitacionService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
	private final HabitacionService habitacionService;
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	public UsuarioController(HabitacionService habitacionService) {
		this.habitacionService = habitacionService;
	}
	
	
	 @GetMapping("/gestion")
	 public String verPaginaAdmin() {
	 	   log.info("IN verPaginAdmin() - lista: {} ");
		return "admin/gestion";
	}
	 
	 @GetMapping("articulosList")
	 public String listaArticulos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		 Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
			int[] pagination = ControllerUtil.computePagination(page);
			log.info("IN pagina_Recursos(): ", pagination.length);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/usuario/articulosList");
			model.addAttribute("page", page);
	 	   log.info("IN listaArticulos() - lista: {} ");
		return "admin/articulosList";
	}
	 
	 @RequestMapping("/articulosList/borrar/{id}")
	 public String borrarRecurso(@PathVariable("id") Long id){
		  habitacionService.eliminarHabitacionPorId(id);
		  log.debug("ADMIN elimino un articulo de la DB: id={}", id);
		  return "redirect:/usuario/articulosList";
	 }
	 
	 @GetMapping("/crea_recurso")
	 public String andeHabitacion( Model model) {
		
		Habitacion habitacion = new Habitacion();
		model.addAttribute("habitacion", habitacion);
		
		log.info("IN anadeHabitacion() -  Categorias {} ");
		return "admin/crea_recurso";
	}
	 
	 @PostMapping("/guardar_recurso")
	 public String guardarRecurso( @Valid Habitacion habitacion,
			  						BindingResult bindingResult,
			  						Model model,
			  						@RequestParam("fichero")MultipartFile fichero)  {
		  	if(bindingResult.hasErrors()) {
		  		Map<String, String> eroresMap = ControllerUtil.obtenerErrores(bindingResult);
		  		log.info("In registro(): errors - {}", eroresMap.toString());
		  		model.mergeAttributes(eroresMap);
		  		return "admin/crea_recurso";
		  	}
		  	
		   habitacionService.guardaHabitacion(habitacion, fichero); 
           log.debug("ADMIN anadio articulo a DB: id={}, titulo={}, precio={}",
        		   habitacion.getId(), habitacion.getTitulo(), habitacion.getPrecio());
		return "redirect:/usuario/articulosList";
	  }
	 
	 //Metodo get mostrar lista usuarios
}
