package com.reforma.ecoreforma.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reforma.ecoreforma.domain.Usuario;

/**
 * Interfaz de la capa de Servicios, 
 * describe un set de metodos de trabajo con el objeto {@link Usuario}.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see Usuario
 * @see UsuarioServiceImpl
 *
 */
public interface UsuarioService {
	
	long nrUsuarios();
	/**
	 * metodo que registra informacion del usuario
	 * @param Usuario usuario
	 * @return el {@link Usuario} objeto que se guarda en la base de datos
	 */
	Usuario guardar(Usuario usuario);
	
	/**
	 * @param Pageable page
	 * @return lista paginada de {@link Usuario}.
	 */
	Page<Usuario> encuentraTodos(Pageable page);
	
	/**
	 * @param form formulario con los roles de los usuarios.
	 * @param usuario de la base de datos.
	 */
	void actualizaUsuario( Map<String, String> form, Usuario usuario);
	
    Usuario findById(Long id);
	
	/**
	 * elimina el usuario de la Base de datos.
	 * @param id del usuario de la base de datos.
	 */
	void delete(Long id);
    
	
    /**
     * Retorna true si el usuario  existe.
     * 
     * @param email del usuario de la Base de Datos.
     * @return true si el usuario existe en la Base de Datos.
     */
    boolean usuarioExiste(String email);

}
