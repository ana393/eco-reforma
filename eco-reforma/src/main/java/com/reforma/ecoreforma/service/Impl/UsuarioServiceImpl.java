package com.reforma.ecoreforma.service.Impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reforma.ecoreforma.domain.Role;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.UsuarioRepository;
import com.reforma.ecoreforma.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	/**
	 * @param email del usuario de la Base de Datos.
	 * @return Optional de los usuarios
	 */
	@Transactional
	public Optional<Usuario> findByEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	
	@Override
	public Usuario guardar(Usuario usuario) {
		usuario.setRoles(Collections.singleton(Role.USER));
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		
		Usuario registered = usuarioRepository.save(usuario);
		logger.info("IN guardar() - user: {} guardado", registered);
		return registered;
	}

	@Override
	public Page<Usuario> encuentraTodos(Pageable page) {
		return usuarioRepository.findAll(page);
	}

	@Override
	public void actualizaUsuario(Map<String, String> form, Usuario usuario) {
		Set <String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());
		usuario.getRoles().clear();
		
		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				usuario.getRoles().add(Role.valueOf(key));
			}
		}
		
		usuarioRepository.save(usuario);	
	}

	@Override
	public Usuario findById(Long id) {
		Usuario result = usuarioRepository.findById(id).orElse(null);
		return result;
	}

	@Override
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	    logger.info("IN delete() - user with id: {} successfully deleted");
		
	}

	@Override
	public boolean usuarioExiste(Usuario usuario) {
		return usuarioRepository.findByEmail(usuario.getEmail()).isPresent();
	}

}
