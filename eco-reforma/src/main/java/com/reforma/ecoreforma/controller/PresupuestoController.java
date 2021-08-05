package com.reforma.ecoreforma.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.PrePresupuestoService;
import com.reforma.ecoreforma.service.PresupuestoService;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoController {
	
	private final PresupuestoService presupuestoService;
	private final PrePresupuestoService prePresupueastoService;
	
	public PresupuestoController(PresupuestoService presupuestoService, PrePresupuestoService prePresupueastoService) {
		this.presupuestoService = presupuestoService;
		this.prePresupueastoService = prePresupueastoService;
	}
 
	@GetMapping
	public String obtenerPresupuesto(Authentication usuarioSession, Model model){
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		PrePresupuesto pr = prePresupueastoService.obtenPrePresupuesto(usuarioDB);
		
		model.addAttribute("presupuestos", pr.getPrePresupusetoItemos());
		model.addAttribute("prePresupuesto", pr);
		return "presupuesto";
	
	}
	

	@PostMapping
	public String postPresupuesto(Authentication usuarioSession,
								@Valid Presupuesto reservaValida, BindingResult bindingResult,
								Model model) {
		
		Usuario usuarioDB = (Usuario)usuarioSession.getPrincipal();
		PrePresupuesto prePresupuesto = prePresupueastoService.obtenPrePresupuesto(usuarioDB);
		
		if(bindingResult.hasErrors()) {
			Map<String, String> mapErrores = ControllerUtil.obtenerErrores(bindingResult) ;
			model.mergeAttributes(mapErrores);
			model.addAttribute("presupuestos", prePresupuesto.getPrePresupusetoItemos());
			model.addAttribute("prePresupuesto", prePresupuesto);
			return "presupuesto";
		} else {
			presupuestoService.guardar(reservaValida, usuarioDB, prePresupuesto);		  
		}
		    return "/finalizar-presupuesto";
	}
	
	@GetMapping("/finalizar-presupuesto")
	public String finalizaReserva(Model model) {
		Presupuesto presupuesto = (Presupuesto) model.asMap().get("reservaUsuario");
		if(presupuesto == null) {
			return "redirect:/";
		}
		model.addAttribute("reservaUsuario", presupuesto);
		return "finalizar-presupuesto";
	}

}
