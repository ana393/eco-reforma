package com.reforma.ecoreforma.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.HabitacionService;
import com.reforma.ecoreforma.service.PrePresupuestoService;
/**
 * Clase controller PrePresupuesto.
 * La anotacion @Controller  Spring podrá detectar la clase PrePresupuestoController cuando realice el escaneo de componentes.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see HabitacionService
 * @see PrePresupuestoService
 *
 */
@Controller
@RequestMapping("/tus-favoritos")
public class PrePresupuestoController {
	
	private final HabitacionService habitacionService; 
	private final PrePresupuestoService prePresupuestoService;
	private static final Logger logger = LoggerFactory.getLogger(PrePresupuestoController.class);
	/**
	 * Constructor para la inicializacion  de las variables principales.
	 * Con la anotacion @Autowired se lleva a cabo la inyección de dependencias de los objetos.
	 * 
	 * @param habitacionService
	 * @param usuarioService
	 * @param presupuestoService
	 */
	public PrePresupuestoController(HabitacionService habitacionService, PrePresupuestoService prePresupuestoService) {
		this.habitacionService = habitacionService;
		this.prePresupuestoService = prePresupuestoService;
	}
	
	/**
	 * Devuelve la pagina de los articulos favoritos
	 * URL {/tus-favoritos}, metodo GET.
	 * @param usuarioSession
	 * @param model {@link Model}
	 * @return la pagina de tus-favoritos.html
	 */
	@GetMapping
	public String prePresupuesto(Authentication usuarioSession, Model model) {
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		PrePresupuesto prePresupuesto = prePresupuestoService.obtenPrePresupuesto(usuarioDB);
		model.addAttribute("pre_reservas", prePresupuesto.getPrePresupusetoItemos());
		model.addAttribute("pre_presupuesto", prePresupuesto);
		
		return "tus-favoritos";
	}
	
	
	/**
	 * Añade articulos a la lista de elementos favoritos
	 *  URL {/anade}, metodo POST. 
	 * @param habitacion
	 * @param attributes
	 * @param model {@link Model}
	 * @param usuarioSession
	 * @return redirect /tus-favoritos.
	 */
	@PostMapping("/anade")
	public String anadePrePresupuesto(@RequestParam("anade") Habitacion habitacion,
								   RedirectAttributes attributes,
								   Model model, Authentication usuarioSession) {
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		int qty = 1;
		habitacion = habitacionService.encuentraPorId(habitacion.getId());
		
		prePresupuestoService.anadeItemAPrePresupuesto(habitacion, usuarioDB, qty);
		logger.debug("Usuario añade PrePresupusesto : id={}, usuario={}", habitacion.getId(), usuarioDB.toString());
		
	    return "redirect:/tus-favoritos";
	}
	
	/**
	 * Elimina una preReserva.
	 * URL {"/elimina-item/{id}"}, metodo GET.
	 * @param id
	 * @return redirect /elimina-item/{id}
	 */
	@GetMapping("/elimina-item/{id}")
	public String eliminaPreReserva(@PathVariable("id") long id) {
		ItemReserva item = prePresupuestoService.encuentraItemPorId(id);
		prePresupuestoService.eliminarItemReserva(item);
		
		logger.debug("Usuario elimina item: id={}", item.getId());
		
	    return "redirect:/tus-favoritos";
	 }
}
