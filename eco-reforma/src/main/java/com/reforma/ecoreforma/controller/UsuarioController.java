package com.reforma.ecoreforma.controller;

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

/**
 * Clase controller Usuario.
 * Con la anotacion @Controller  Spring podrá detectar la clase UsuarioController cuando realice el escaneo de componentes.
 * @RequestMapping - anotacion que permite el mapeo a los metodos del controlador.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see UsuarioService
 * @see HabitacionService
 * @see PresupuestoService
 *
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
	private final UsuarioService usuarioService;
	private final HabitacionService habitacionService;
	private final PresupuestoService presupuestoService;
	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	/**
	 * Constructor para la inicializacion  de las variables principales.
	 * Con la anotacion @Autowired  se lleva a cabo la inyección de dependencias de los objetos.
	 * 
	 * @param habitacionService
	 * @param usuarioService
	 * @param presupuestoService
	 */
	@Autowired
	public UsuarioController(HabitacionService habitacionService, UsuarioService usuarioService, PresupuestoService presupuestoService) {
		this.habitacionService = habitacionService;
		this.usuarioService = usuarioService;
		this.presupuestoService = presupuestoService;
	}
	
	 /** 
	 * Metodo que prepara varios datos estatisticos para la pagina de gestion.
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/gestion"}, metodo GET.
	 * @param model {@link Model}
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	 @GetMapping("/gestion")
	 public String verPaginaAdmin(Model model) {
		 Long nrArticulos = habitacionService.NumeroArticulos();
		 Long nrPresupuestos = presupuestoService.nrPresupuestos();
		 Long nrUsuarios = usuarioService.nrUsuarios();
		 Long presupuestosTramitados = presupuestoService.presupuestosTramitados();
		 Long presupuestosIniciales = presupuestoService.presupuestosInicial();
		 Long presupuestosReformados = presupuestoService.presupuestosReformados();
		 
		 model.addAttribute("habitacionesBD",nrArticulos);
		 model.addAttribute("presupuestosBD", nrPresupuestos);
		 model.addAttribute("presupIniciales",presupuestosIniciales);
		 model.addAttribute("presupuestosTramitados", presupuestosTramitados);
		 model.addAttribute("presupReformados",presupuestosReformados);
		 model.addAttribute("usuariosBD", nrUsuarios);
		 
		return "admin/gestion";
	}
	 
	 /**
	 * Metodo que devuelve una lista de recursos para ser editados por el administrados
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/articulosList"}, metodo GET.
	 * @param model {@link Model}
	 * @param pageRequest objeto que espesifica la informacion solicitada.
	 * @return lista de recursos
	 */
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @GetMapping("articulosList")
	 public String listaArticulos(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
		 Page<Habitacion> page = habitacionService.encuentraTodo(pageRequest);
			int[] pagination = ControllerUtil.computePagination(page);
			model.addAttribute("pagina", pagination);
			model.addAttribute("url","/usuario/articulosList");
			model.addAttribute("page", page);
		return "admin/articulosList";
	}
	 
	 
	 /**
	 * Metodo que obtiene los presupuestos para el usuario.
	 * URL request {"/mis_presupuestos"}, metodo GET.
	 * @param pageRequest objeto que espesifica la informacion solicitada.
	 * @param usuarioSession 
	 * @param model {@link Model}
	 * @return la pagina /mis-presupuestos
	 */
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
	 	   log.info("Usuario revisando sus presupuestos - lista: {}", page.toString());
		return "mis-presupuestos";
	} 
	
	   
	/**
	 * Metodo para eliminar presupuesto.
	 * URL request {"/mis_presupuestos"}, @RequestMapping.
	 * @param id
	 * @return redirect a /usuario/mis_presupuestos
	 */
	@RequestMapping("/eliminar_presupuesto")
	public String eliminarMiPresupuesto(@RequestParam("id") Long id) {
		log.info("Se está eliminando un  presupuesto con id: -{}", id);
		presupuestoService.eliminarPresupuesto(id);
		return "redirect:/usuario/mis_presupuestos";
	}

	
	 /**
	  * Se elimina los recursos.
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/articulosList/borrar/{id}"}, metodo GET.
	 * @param id
	 * @return redirect a /usuario/articulosList
	 */
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @RequestMapping("/articulosList/borrar/{id}")
	 public String borrarRecurso(@PathVariable("id") Long id){
		  habitacionService.eliminarHabitacionPorId(id);
		  log.debug("ADMIN elimino un articulo de la DB: id={}", id);
		  return "redirect:/usuario/articulosList";
	 }
	 
	 /**
	 * Metodo que anade una habitacion.
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"admin/crea_recurso"}, metodo GET
	 * @param model
	 * @return
	 */
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @GetMapping("/crea_recurso")
	 public String andeHabitacion( Model model) {
		
		Habitacion habitacion = new Habitacion();
		model.addAttribute("habitacion", habitacion);
		log.info("Se está creando un articulo: -{}", habitacion.toString());
		return "admin/crea_recurso";
	}
	 
	 /**
	 * Se guarda un recurso
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/guardar_recurso"}, metodo POST.
	 * @param habitacion
	 * @param bindingResult
	 * @param model
	 * @param fichero
	 * @return redirect /usuario/articulosList
	 */
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @PostMapping("/guardar_recurso")
	 public String guardarRecurso( @Valid Habitacion habitacion,
			  						BindingResult bindingResult,
			  						Model model,
			  						@RequestParam("fichero") MultipartFile fichero)  {
		  	if(bindingResult.hasErrors()) {
		  		Map<String, String> eroresMap = ControllerUtil.obtenerErrores(bindingResult);
		  		log.info("Los errores al guardar un recurso: errors - {}", eroresMap.toString());
		  		model.mergeAttributes(eroresMap);
		  		return "admin/crea_recurso";
		  	}
		  	
		   habitacionService.guardaHabitacion(habitacion, fichero); 
		   
           log.debug("ADMIN añadio articulo a DB: id={}, titulo={}, precio={}",
        		   habitacion.getId(), habitacion.getTitulo(), habitacion.getPrecio());
		return "redirect:/usuario/articulosList";
	  }
	 
	 /**
	 * Se actualiza un recurso
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/actualizar_recurso"}, metodo POST.
	 * @param habitacion
	 * @return
	 */
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @PostMapping("/actualizar_recurso")
	 public String actualizarRecurso(Habitacion habitacion){
		habitacionService.actualizarHabitacion(habitacion);
        log.debug("ADMIN actualizo recurso  : id={}, titulo={}, precio={}",
        		   habitacion.getId(), habitacion.getTitulo(), habitacion.getPrecio());
           return "redirect:/usuario/articulosList";
		
	  }
	 
	 
	  /**
	 * Metodo para listar usuarios
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/usuarios"}, metodo GET.
	 * @param pageRequest
	 * @param model
	 * @return la pagina admin/usuarios
	 */
	  @PreAuthorize("hasAuthority('ADMIN')")
	  @GetMapping("/usuarios") 
	  public String listaUsuarios(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageRequest, Model model) {
			 Page<Usuario> page = usuarioService.encuentraTodos(pageRequest);
				int[] pagination = ControllerUtil.computePagination(page);
				
				model.addAttribute("pagina", pagination);
				model.addAttribute("url","/usuario/usuarios");
				model.addAttribute("page", page);
				model.addAttribute("roles", Roles.values());
				
		  return "admin/usuarios";
	  }
	  
	  /**
	 * 
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * URL request {"/roles"}, metodo POST.
	 * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	 * @param form
	 * @param usuario
	 * @return redirecta a  /usuario/usuarios
	 */
	  @PreAuthorize("hasAuthority('ADMIN')")
	  @PostMapping("/roles")
	  public String editarRoles(@RequestParam Map<String, String> form,
	  		  					   @RequestParam("usuarioId") Usuario usuario) {
	  	  usuarioService.actualizaUsuario(form, usuario);
	  	  log.debug("Admin edita los siguentes roles : id={}", form);
	      return "redirect:/usuario/usuarios";
	    }
	 
		/**
	     * Retorna todos los presupuestos de los clientes.
	     * URL request {"/presupuestos"}, metodo GET.
	     * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	     * @param model objeto {@link Model}.
	     * @return reservas pagina con atributos uso del objeto  {@link Model}.
	     */
	    @PreAuthorize("hasAuthority('ADMIN')")
	    @GetMapping("/presupuestos")
	    public String obtenerListaPresupuesto(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) 
	    									  Pageable pageRequest, Model model) {
			 Page<Presupuesto> page = presupuestoService.encuentraTodos(pageRequest);
				int[] pagination = ControllerUtil.computePagination(page);
				log.info("Los presupuestos existenetes en la BD: - {}", pagination.length);
				model.addAttribute("pagina", pagination);
				model.addAttribute("url","/usuario/presupuestos");
				model.addAttribute("page", page);
				model.addAttribute("estados", EstadoPresupuesto.values()); 
	        return "admin/presupuestos";
	    }
	    

	    /**
	     * metodo para guardar el estado del {@link Presupuesto}.
	     * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	     * URL request {"/presupuesto-editar"}, metodo GET.
	     * @param form que guarda los valores en Map<String, String>
	     * @param presupuesto el objeto {@link Presupuesto} editado.
	     * @return la vista con la ruta /usuario/presupuestos.
	     */

	    @PreAuthorize("hasAuthority('ADMIN')")
	    @PostMapping("/presupuesto-editar")
	    public String editarEstado(@RequestParam Map<String, String> form,
	  		  					   @RequestParam("presupuestoId") Presupuesto presupuesto) {
	  	  presupuestoService.actualizaPresupuesto(form, presupuesto);
	      return "redirect:/usuario/presupuestos";
	    	}
	    

	    /**
	     * Metodo que elimina Presupuestos.
	     * La anotacion @link PreAuthorize surgiere el acceso de la url solo para usuarios con derecho de administrador.
	     * URL {"/eliminar"}, @RequestMapping
	     * @param id
	     * @return redirect a /usuario/presupuestos
	     */
	    @PreAuthorize("hasAuthority('ADMIN')")
	    @RequestMapping("/eliminar")
	    public String eliminarPresupuesto(@RequestParam("id") Long id) {
	    	presupuestoService.eliminarPresupuesto(id);

	    	log.debug("Admin ha eliminado presupuesto : id={}", id);
	    	return "redirect:/usuario/presupuestos";
	    }  
}
