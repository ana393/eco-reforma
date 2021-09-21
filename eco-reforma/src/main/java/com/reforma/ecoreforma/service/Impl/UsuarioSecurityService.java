package com.reforma.ecoreforma.service.Impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.UsuarioRepository;

/**
 * Capa de Servicios que implementa los metodos de acceso del objeto {@link com.reforma.ecoreforma.domain.Usuario} 
 *  por la interfaz {@link org.springframework.security.core.userdetails.UserDetailsService}.
 * <p>
 * La anotacion @Service nos anuncia que esta clase es un componente de la capa de servicio,
 *                      que es un subtipo de la clase @Component.
 * Usanddo la anotacion @Service se autodetecta el bean durante el escaneo del .classpath
 *
 * La anotacion @Transactional - crea un punto AOP que asegura la ejecucion de todas las queries en una sola transaccion.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see Usuario
 * @see UserDetailsService
 *
 */
@Transactional
@Service("UsuarioSecurityService")
public class UsuarioSecurityService implements UserDetailsService {

	

	private final UsuarioRepository usuarioRepository;
	private static final Logger logger = LoggerFactory.getLogger(UsuarioSecurityService.class);
	
	/**
	 * Constructor para la inicializacion  de las variable principal.
	 * Con la anotacion @Autowired  se lleva a cabo la inyección de dependencias del objeto.
	 *              
	 * @param usuarioRepository implimentacion de la interfaz {@link UsuarioRepository}
	 *                    para el procesamiento de usuarios de la base de datos.
	 *                    
	 */
	@Autowired
	public UsuarioSecurityService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	/**
	 * Localiza  al usuario segun su email.
	 * @param email identifica la informacion  del usuario.
	 * @return un registro populado por datos completos del usuario.
	 * @throws UsernameNotFoundException si el usuario no se pudo encontrar o no tiene la autoridad concedida
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe!"));
		logger.info("Authenticando al usuario: - {}", usuario.toString());
		return usuario;
	}
	
//	public void authenticationUser(String email) {
//		UserDetails userDetails = loadUserByUsername(email);
//		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
//					userDetails.getAuthorities());
//		logger.info("Authenticando usuario: auth: {}", authentication.toString());
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//	} 
}
