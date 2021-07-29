package com.reforma.ecoreforma.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.EstadoPresupuesto;
import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Roles;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.HabitacionService;
import com.reforma.ecoreforma.service.PresupuestoService;
import com.reforma.ecoreforma.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
	private final UsuarioService usuarioService;
	private final HabitacionService habitacionService;
	private final PresupuestoService presupuestoService;
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	public UsuarioController(HabitacionService habitacionService, UsuarioService usuarioService, PresupuestoService presupuestoService) {
		this.habitacionService = habitacionService;
		this.usuarioService = usuarioService;
		this.presupuestoService = presupuestoService;
	}
	
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @GetMapping("/gestion")
	 public String verPaginaAdmin() {
	 	   log.info("IN verPaginAdmin() - lista: {} ");
		return "admin/gestion";
	}
	 
	 @GetMapping("articulosList")
	 public String listaArticulos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		 Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
			int[] pagination = ControllerUtil.computePagination(page);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/usuario/articulosList");
			model.addAttribute("page", page);
	 	   log.info("IN listaArticulos() - lista: {}");
		return "admin/articulosList";
	}
	 
	 @GetMapping("mis_presupuestos")
	 public String articulosPorUsuario(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest,
			 							Authentication usuarioSession,
			 							Model model) {
		 Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		 Page<Presupuesto> page = presupuestoService.encuentraPorUsuario(usuarioDB, pageRequest) ;
			int[] pagination = ControllerUtil.computePagination(page);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/usuario/mis_presupuestos");
			model.addAttribute("page", page);
	 	   log.info("IN articulosPorUsuario() - lista: {}");
		return "mis-presupuestos";
	} 
	
	   
	@RequestMapping("/eliminar_presupuesto")
	public String eliminarMiPresupuesto(@RequestParam("id") Long id) {
		log.info("IN eliminarReserva() : {}", id);
		presupuestoService.eliminarPresupuesto(id);
		return "redirect:/usuario/mis_presupuestos";
	}

	
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @RequestMapping("/articulosList/borrar/{id}")
	 public String borrarRecurso(@PathVariable("id") Long id){
		  habitacionService.eliminarHabitacionPorId(id);
		  log.debug("ADMIN elimino un articulo de la DB: id={}", id);
		  return "redirect:/usuario/articulosList";
	 }
	 
	 @PreAuthorize("hasAuthority('ADMIN')")
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
	 
	 
	 @PostMapping("/actualizar_recurso")
	 public String actualizarRecurso(Habitacion habitacion){
		habitacionService.actualizarHabitacion(habitacion);
        log.debug("ADMIN actualizarRecurso()  : id={}, titulo={}, precio={}",
        		   habitacion.getId(), habitacion.getTitulo(), habitacion.getPrecio());
           return "redirect:/usuario/articulosList";
		
	  }
	 
	 
	 //Metodo get mostrar lista usuarios
	  @GetMapping("/usuarios") 
	  @PreAuthorize("hasAuthority('ADMIN')")
	  public String listaUsuarios(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
			 Page<Usuario> page = usuarioService.encuentraTodos(pageRequest);
				int[] pagination = ControllerUtil.computePagination(page);
				
				model.addAttribute("pagina", pagination);
				model.addAttribute("url","/usuario/usuarios");
				model.addAttribute("page", page);
				model.addAttribute("roles", Roles.values());
				
		 	   log.info("IN verPaginAdmin() - lista: {} ");
		  
		  return "admin/usuarios";
	  }
	  
	  
	  @PostMapping("/roles")
	  @PreAuthorize("hasAuthority('ADMIN')")
	  public String editarRoles(@RequestParam Map<String, String> form,
	  		  					   @RequestParam("usuarioId") Usuario usuario) {
	  	  usuarioService.actualizaUsuario(form, usuario);
	  	  log.info("IN editarRoles() : {}", form);
	      return "redirect:/usuario/usuarios";
	    }
	 
		/**
	     * Retorna todas los presupuesto del consumidor.
	     * URL request {"/gestion"}, metodo GET.
	     *
	     * @param model objeto {@link Model}.
	     * @return reservas pagina con atributos uso del objeto  {@link Model}.
	     */
	    @GetMapping("/presupuestos")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public String obtenerListaPresupuesto(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
			 Page<Presupuesto> page = presupuestoService.encuentraTodos(pageRequest);
				int[] pagination = ControllerUtil.computePagination(page);
				log.info("IN obtenerListaPresupuesto(): ", pagination.length);
				model.addAttribute("pagina", pagination);
				model.addAttribute("url","/usuario/presupuestos");
				model.addAttribute("page", page);
				model.addAttribute("estados", EstadoPresupuesto.values()); 
	        log.info("IN obtenerListaPresupuesto() {}", page);
	        return "admin/presupuestos";
	    }
	    

	    /**
	     * metodo para guardar el estado de la {@link Presupueseto}.
	     * 
	     * @param form que guarda los valores en Map<String, String>
	     * @param prseupuesto el objeto {@link Presupueseto} editado.
	     * @return la vista con la ruta /usuario/presupuestos.
	     */
	    @PostMapping("/presupuesto-editar")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public String editarEstado(@RequestParam Map<String, String> form,
	  		  					   @RequestParam("presupuestoId") Presupuesto presupuesto) {
	  	  presupuestoService.actualizaPresupuesto(form, presupuesto);
	  	  log.info("IN editarEstado() : {}", form);
	      return "redirect:/usuario/presupuestos";
	    	}
	    
	   
	    @RequestMapping("/eliminar")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public String eliminarReserva(@RequestParam("id") Long id) {
	    	log.info("IN eliminarReserva() : {}", id);
	    	presupuestoService.eliminarPresupuesto(id);
	    	return "redirect:/usuario/presupuestos";
	    }
	 
	    
}
