package com.reforma.ecoreforma.service;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PrePresupuestoService {
	PrePresupuesto obtenPreReserva(Usuario usuario);
	
	int obtenerNrItemos(Usuario usuario);
	
	ItemReserva encuentraItemPorId(Long id);
	
	ItemReserva anadeItemAPreRresupuesto(Habitacion habitacion,Usuario usuario, int qty);
		
	void eliminarItemReserva(ItemReserva item);
	
	void actualizarItemReserva(ItemReserva item, Integer qty);
	
	void vaciarPrePresupuesto(Usuario usuario);
}
