package com.reforma.ecoreforma.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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

@Controller
@RequestMapping("/pre-presupuesto")
public class PrePresupuestoController {
	
	private final HabitacionService habitacionService; 
	private final PrePresupuestoService prePresupuestoService;
	private static final Logger logger = LoggerFactory.getLogger(PrePresupuestoController.class);
	
	public PrePresupuestoController(HabitacionService habitacionService, PrePresupuestoService prePresupuestoService) {
		this.habitacionService = habitacionService;
		this.prePresupuestoService = prePresupuestoService;
	}
	
	@GetMapping
	public String prePresupuesto(Authentication usuarioSession, Model model) {
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		logger.info("In prePresupuesto : {}", usuarioDB.toString());
		PrePresupuesto prePresupuesto = prePresupuestoService.obtenPrePresupuesto(usuarioDB);
		model.addAttribute("pre_reservas", prePresupuesto.getPrePresupusetoItemos());
		model.addAttribute("pre_presupuesto", prePresupuesto);
		
		logger.info("In prePresupuesto : {}", prePresupuesto.toString());
		return "pre-presupuesto";
	}
	
	
	@PostMapping("/anade")
	public String anadePreReserva(@RequestParam("anade") Habitacion habitacion,
								   RedirectAttributes attributes,
								  Model model, Authentication usuarioSession) {
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		int qty = 1;
		habitacion = habitacionService.encuentraPorId(habitacion.getId());
		
		prePresupuestoService.anadeItemAPrePresupuesto(habitacion, usuarioDB, qty);
		logger.info("In anadePreReserva : {}", usuarioDB.toString());
	    return "redirect:/pre-presupuesto";
	}
	
	@GetMapping("/elimina-item/{id}")
	public String eliminaPreReserva(@PathVariable("id") long id) {
		ItemReserva item = prePresupuestoService.encuentraItemPorId(id);
		prePresupuestoService.eliminarItemReserva(item);
	    return "redirect:/pre-presupuesto";
	 }
}
