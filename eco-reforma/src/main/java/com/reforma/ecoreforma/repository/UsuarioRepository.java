package com.reforma.ecoreforma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Usuario;

/**
 * Repositorio para el objeto {@link Usuario}
 *  que proporciona un conjuntos de metodos para trabajar con la Base de Datos.
 *  
 *  Hereda de la interfaz {@link JpaRepository}
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	/**
	 * Metodo que nos devuelve los usuarios con el email proporcionado.
	 * @param email de usuario a devolver
	 * @return el objeto {@link Usuario}
	 */
	Optional<Usuario> findByEmail(String email);
}
