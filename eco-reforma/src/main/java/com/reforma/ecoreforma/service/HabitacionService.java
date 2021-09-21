package com.reforma.ecoreforma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.service.Impl.HabitacionServiceImpl;

/**
 * Interfaz de la capa de Servicios, 
 * describe un set de metodos de trabajo con el objeto {@link com.reforma.ecoreforma.domain.Habitacion}.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see Habitacion
 * @see HabitacionServiceImpl
 */
public interface HabitacionService {
	
	/**
	 * @return el numero de {@link Habitacion} existentes.
	 */
	Long NumeroArticulos();
	
    /**
     *  Metodo que devuelve todas las habitaciones existentes.
     * @param pagina
     * @return pageable objeto que especifica la informacion de la pagina solicitada.
     */
    Page<Habitacion> encuentraTodo(Pageable pagina);
	
	/**
	 *  Metodo que devuelve todas las habitaciones existentes segun el titulo or tipo.
	 * @param titulo
	 * @param tipo
	 * @param pageable
	 * @return pageable objeto que especifica la informacion de la pagina solicitada
	 */
	Page<Habitacion> encuentraPorTituloOrPorTipo(String titulo, String tipo, Pageable pageable);
	
    /**
     * Metodo que encuentra la habitacion segun el id
     * @param id
     * @return 
     */
    Habitacion encuentraPorId(long id);
	
	/**
	 * Metodo que guarda un nuevo recurso en la base de datos.
	 * @param habitacion
	 * @param Fichero
	 * @return
	 */
	Habitacion guardaHabitacion(Habitacion habitacion, MultipartFile Fichero);
	
	/**
	 * Metodo que actualiza la habitacion.
	 * @param habitacion
	 * @param Fichero
	 */
	void actualizarHabitacion(Habitacion habitacion);
	
	/**
	 * Metodo que elimina la habitacion segun el id.
	 * @param id
	 */
	void eliminarHabitacionPorId(Long id);

}
