package com.reforma.ecoreforma.service;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.Impl.PresupuestoServiceImpl;

/**
 * Interfaz de la capa de Servicios, 
 * describe un set de metodos de trabajo con el objeto {@link Presupuesto}.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see Presupuesto
 * @see PresupuestoServiceImpl
 */
public interface PresupuestoService {
	
	
	/**
	 * Metodo que encuentra todos los {@link Presupuesto} existentes.
	 * @param page  objeto que especifica la informacion de la pagina solicitada. 
	 * @return lista paginada de {@link Presupuesto}.
	 */
	Page<Presupuesto> encuentraTodos(Pageable page);
	  
    /**
     * Metodo que guarda un nuevo presupuesto.
     * @param presupuestoValida
     * @param usuario
     * @param presupuesto
     * @return
     */
    Presupuesto guardar(Presupuesto presupuestoValida, Usuario usuario, PrePresupuesto presupuesto);
	  
    /**
     * Metodo que encuentra todos los {@link Presupuesto} existentes segun {@link Usuario}. 
     * @param usuario
     * @param pageable objeto que especifica la informacion de la pagina solicitada.
     * @return
     */
    Page<Presupuesto> encuentraPorUsuario(Usuario usuario, Pageable pageable);
	  
	/**
	 * Metodo que actualiza un  presupuesto existente.
	 * @param form
	 * @param presupuesto
	 */
	void actualizaPresupuesto(Map<String, String> form, Presupuesto presupuesto);
	  
	/**
	 * Metodo que elimina un presupuesto. 
	 * @param id 
	 */
	void eliminarPresupuesto(Long id);

	/**
	 * 
	 * @return el numero de presupuestos existentes.
	 */
	Long nrPresupuestos();
	
	/**
	 * @return el numero de presupuestos  en estado tramitado.
	 */
	Long presupuestosTramitados();
	
	/**
	 * @return el numero de presupuestos en eatdo inicial.
	 */
	Long presupuestosInicial();
	
	/**
	 * @return el numero de presupuestos en estado reformado.
	 */
	Long presupuestosReformados();

}
