package com.reforma.ecoreforma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Habitacion;

/**
 * Repositorio para el objeto {@link com.reforma.ecoreforma.domain.Habitacion}
 *  que proporciona un conjuntos de metodos para trabajar con la Base de Datos.
 *  
 *  Hereda de la interfaz {@link JpaRepository}
 */
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
	
	 /**
	 * Metodo que devuelve lista de objetos {@link Habitacion}  de la Base de Datos.
	 * @param pageable objeto que especifica la informacion de la pagina solicitada. 
	 */
	Page<Habitacion> findAll(Pageable pageable);
	
	/**
	 * Metodo que devuelve lista de objetos {@link Habitacion}  de la Base de Datos, 
	 *        segun @param  titulo, tipo, y pageable objeto que especifica la informacion de la pagina solicitada. 
	 */
	Page<Habitacion> findByTituloOrTipo(String titulo, String tipo, Pageable pagina);
	
}
