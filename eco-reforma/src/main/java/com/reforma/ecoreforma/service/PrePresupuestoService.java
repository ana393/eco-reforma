package com.reforma.ecoreforma.service;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.service.Impl.PrePresupuestoServiceImpl;
/**
 * Interfaz de la capa de Servicios, 
 * describe un set de metodos de trabajo con el objeto {@link com.reforma.ecoreforma.domain.PrePresupuesto}.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see PrePresupuesto
 * @see PrePresupuestoServiceImpl
 */
public interface PrePresupuestoService {
	/**
	 * @param usuario
	 * @return el prePresupuesto segun el uusario.
	 */
	PrePresupuesto obtenPrePresupuesto(Usuario usuario);
	
	/**
	 * Obtiene el numero de items.
	 * @param usuario
	 * @return
	 */
	int obtenerNrItemos(Usuario usuario); 
	
	/**
	 * Metodo que encuentra el {@link ItemReserva} segun el id.
	 * @param id
	 * @return
	 */
	ItemReserva encuentraItemPorId(Long id);
	
	/**
	 * Metodo que anade {@link ItemReserva} a {@link PrePresupuesto}.
	 * @param habitacion
	 * @param usuario
	 * @param qty
	 * @return
	 */
	ItemReserva anadeItemAPrePresupuesto(Habitacion habitacion,Usuario usuario, int qty);
		
	/**
	 * Metodo que elimina {@link ItemReserva}.
	 * @param item
	 */
	void eliminarItemReserva(ItemReserva item);
	
}
