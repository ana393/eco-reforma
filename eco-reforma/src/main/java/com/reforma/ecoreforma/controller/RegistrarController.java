package com.reforma.ecoreforma.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.UsuarioService;



/**
 *  Clase Controller para el registro del usuario.
 * Este controlador permite acceso a las paginas relacionadas por todos los usuarios, independiente de su rol
 * 
 * La anotacion @Controller  sirve para informar a Spring que esta es una clase @Bean,
 * y que se debe cargar cuando se lanza la aplicacion.
 * 
 * <p>
 * Se recabara la informacion de los posibiles errores con el mecanismo {@link org.slf4j.Logger}; {@link org.slf4j.LoggerFactory}.
 * 
 *@author Ana Tcaci
 *@version 1.0
 *@see Usuario
 *@see UsuarioService
 *
 */
@Controller
public class RegistrarController {
	
	private final UsuarioService usuarioService;
	private static final Logger log = LoggerFactory.getLogger(RegistrarController.class);
	
	/**
	 * Constructor para la inicializacion  de las variables principales.
	 * Con la anotacion @Autowired  se lleva a cabo la inyección de dependencias de los objetos.
	 * 
	 * @param usuarioService
	 */
	@Autowired
	public RegistrarController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	/**
	 * Devuelve la pagina de login
	 * URL {"/login"}, metodo GET
	 * @param usuario
	 * @return 
	 */
	@GetMapping("/login")
	public String logeo(Usuario usuario) {
		return "login";
	}
	
	/**
	 * Devuelve la pagina de registro
	 * URL {"/registro"}, metodo GET
	 * @return 
	 */
	@GetMapping("/registro")
	public String registro() {
		return "registro";
	} 
	
	/**
	 * Registra un nuevo usuario.
	 * URL {"/registro"}, metodo POST
	 * @param contrasenaConfirm
	 * @param usuario
	 * @param bindingResult
	 * @param model
	 * @return redirect /login.
	 */
	@PostMapping("/registro")
	public String registro(
			@RequestParam("password2") String contrasenaConfirm,
			@Valid Usuario usuario,
			BindingResult bindingResult,
			Model model) {
		log.info("Se hace la Peticion POST para el registro de un nuevo usuario.");
		boolean esConfirmVacio = contrasenaConfirm.isBlank();
		boolean esContrasenaDiferente = usuario.getPassword() !=null && !usuario.getPassword().equals(contrasenaConfirm);
		
		if(esConfirmVacio) {
			model.addAttribute("password2Error", "Confirme su contrasena, por favor.");	
		}
		
		if(esContrasenaDiferente) {
			model.addAttribute("passwordError", "Las contrasenas no coinciden.");
		}
		
		
		if(esConfirmVacio || esContrasenaDiferente || bindingResult.hasErrors()) {
			Map<String, String> errors = ControllerUtil.obtenerErrores(bindingResult);
			model.mergeAttributes(errors);
			log.info("Los errores al registrar un recurso: errors - {}", errors.size());
			return "registro";
		} 

		if(usuarioService.usuarioExiste(usuario.getEmail())) {
			model.addAttribute("emailError", "Este usuario existe!");
			return "registro";
		}
			usuarioService.guardar(usuario);
			model.addAttribute("messageType", "alert-danger");
			model.addAttribute("message", "Gracias por unirse a nuestro equipo");
		return "redirect:/login";
	}
}
