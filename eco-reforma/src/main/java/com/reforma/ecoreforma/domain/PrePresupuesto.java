package com.reforma.ecoreforma.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que opera con los datos de la clase {@link ItemReserva}.
 * 
 */
public class PrePresupuesto {
	/**
	 * Lista de objetos {@link ItemReserva} inicializada  {@link ArrayList} del paquete Java.util
	 */
	List<ItemReserva> lista = new ArrayList<>();

	public PrePresupuesto(List<ItemReserva> lista) {
		this.lista = lista;
	}
	
	
	/**
	 * Metodo que invoca al metodo {@see ItemReserva.Total()}
	 * @return  total
	 */
	public BigDecimal obtenerTotal() {
		BigDecimal total = new BigDecimal(0);
		for (ItemReserva item : this.lista) {
			total = total.add(item.Total());
		}
		return total;	
	}
	
	 /**
	 * Metodo que comprueba la existencia de items en la lista.
	 * @return boolean
	 */
	public boolean esVacio() {
		 return lista.isEmpty();
	 }
	
	 /**
	 * Metodo que elimina elementos de la lista.
	 * @param itemReserva
	 */
	public void eliminarItemReserva(ItemReserva itemReserva) {
		 lista.removeIf(item -> item.getId() == itemReserva.getId());
	 }
	 
	
	 /**
	 * Metodo que encuentra un item en la lista de objetos {@link ItemReserva}
	 * @param id
	 * @return
	 */
	public ItemReserva encuentraItemPorHabitacion(long id) {
			for (ItemReserva item : this.lista) {
				if (item.getHabitacion().getId() == id ) {
					return item;
				}
			}
			return null;
		}
		
	/**
	 * Metodo que retorna el numero de items reservados
	 * @return int
	 */
	public int obtenNrItemos() {
			return this.lista.size();
		}	
	
	
	/**
	 * @return list de objetos {@link Presupuesto}
	 */
	public List<ItemReserva> getPrePresupusetoItemos() {
		return lista;
	}
	
public void setPrePresupusetoItemos(List<ItemReserva> lista) {
		this.lista = lista;
	}
	
}
