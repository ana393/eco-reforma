package com.reforma.ecoreforma.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PrePresupuesto {
	List<ItemReserva> lista = new ArrayList<>();

	public PrePresupuesto(List<ItemReserva> lista) {
		this.lista = lista;
	}
	
	public BigDecimal obtenerTotal() {
		BigDecimal total = new BigDecimal(0);
		for (ItemReserva item : this.lista) {
			total = total.add(item.Total());
		}
		return total;	
	}
	
	 public boolean esVacio() {
		 return lista.isEmpty();
	 }
	
	 public void eliminarItemReserva(ItemReserva itemReserva) {
		 lista.removeIf(item -> item.getId() == itemReserva.getId());
	 }
	 
	 public ItemReserva encuentraItemPorHabitacion(long id) {
			for (ItemReserva item : this.lista) {
				if (item.getHabitacion().getId() == id ) {
					return item;
				}
			}
			return null;
		}
		
	public int obtenNrItemos() {
			return this.lista.size();
		}	
		
	public List<ItemReserva> getPrePresupusetoItemos() {
			return lista;
		}
		
	public void setPrePresupusetoItemos(List<ItemReserva> lista) {
			this.lista = lista;
		}
}
