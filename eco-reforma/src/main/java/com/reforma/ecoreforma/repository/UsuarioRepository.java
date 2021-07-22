package com.reforma.ecoreforma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/**
	 * @param email de usuario a devolver
	 * @return el objeto {@link Usuario}
	 */
	Optional<Usuario> findByEmail(String email);
}
