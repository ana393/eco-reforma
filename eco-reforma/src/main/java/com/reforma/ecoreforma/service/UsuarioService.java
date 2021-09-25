package com.reforma.ecoreforma.service;

import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reforma.ecoreforma.domain.Usuario;

/**
 * Interfaz de la capa de Servicios, 
 * describe un set de metodos de trabajo con el objeto {@link com.reforma.ecoreforma.domain.Usuario}.
 * <p>
 * @author Ana Tcaci
 * @version 1.0
 * @see Usuario
 * @see UsuarioServiceImpl
 *
 */
public interface UsuarioService {
	
	/**
	 * Metodo que devuelve un contenedor con valor or no del objeto {@link com.reforma.ecoreforma.domain.Usuario}.
	 * @param email del usuario de la Base de Datos.
	 * @return Optional de los usuarios
	 */
	@Transactional
	Optional<Usuario> findByEmail(String email);
	
	/**
	 * Metodo que registra informacion del usuario.
	 * 
	 * @param Usuario usuario
	 * @return el {@link com.reforma.ecoreforma.domain.Usuario} objeto que se guarda en la base de datos
	 */
	Usuario guardar(Usuario usuario);
	
	/**
	 * Metodo que encuentra todos los usuarios existentes.
	 * @param page  objeto que especifica la informacion de la pagina solicitada. 
	 * @return lista paginada de {@link Usuario}.
	 */
	Page<Usuario> encuentraTodos(Pageable page);
	
	/**
	 * Metodo que actualiza el rol del usuario.
	 * 
	 * @param form formulario con los roles de los usuarios.
	 * @param usuario de la base de datos.
	 */
	void actualizaUsuario( Map<String, String> form, Usuario usuario);
	
     /**
      * Metodo que encuentra al usuario segun el Id.
     * @param id
     * @return usuario encontrado
     */
    Usuario findById(Long id);
    
     /**
     * @return numero de usuarios
     */
    Long nrUsuarios();
	
	/**
	 * Elimina el usuario de la Base de datos.
	 * 
	 * @param id del usuario de la base de datos.
	 */
	void delete(Long id);
    
	
    /**
     * Metodo que comprueba la existencia del usuario.
     * 
     * @param email del usuario de la Base de Datos.
     * @return true si el usuario existe en la Base de Datos.
     */
    boolean usuarioExiste(String email);

}
