package com.reforma.ecoreforma.service;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PrePresupuestoService {
	PrePresupuesto obtenPrePresupuesto(Usuario usuario);
	
	int obtenerNrItemos(Usuario usuario);
	
	ItemReserva encuentraItemPorId(Long id);
	
	ItemReserva anadeItemAPrePresupuesto(Habitacion habitacion,Usuario usuario, int qty);
		
	void eliminarItemReserva(ItemReserva item);
	
	void actualizarItemReserva(ItemReserva item, Integer qty);
	
	void vaciarPrePresupuesto(Usuario usuario);
}
