package com.reforma.ecoreforma.service.Impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.UsuarioRepository;

@Transactional
@Service("UsuarioSecurityService")
public class UsuarioSecurityService implements UserDetailsService {


	private final UsuarioRepository usuarioRepository;
	private static final Logger logger = LoggerFactory.getLogger(UsuarioSecurityService.class);
	
	@Autowired
	public UsuarioSecurityService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn´t exists"));
		logger.info("IN loadUserByUserName(): - {}", usuario.toString());
		return usuario;
	}
	
	public void authenticationUser(String email) {
		UserDetails userDetails = loadUserByUsername(email);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
					userDetails.getAuthorities());
		logger.info("IN authenticationUser(): auth: {}", authentication.toString());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	} 
}
