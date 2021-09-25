package com.reforma.ecoreforma.creator;

import java.util.ArrayList;
import java.util.List;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.Usuario;

/**
 * Clase creadora de objetos para una mejora organizacion de los metodos dentro de la clase test.
 * @author Ana Tcaci
 *
 */
public class ItemReservaCreator {
    
	/**
	 * Crecion de articulos de una cesta de pre presupuesto.
	 * @return objeto ItemReserva
	 */
	public static ItemReserva crearTestItemReserva() {
		ItemReserva testIReserva = new ItemReserva();
		testIReserva.setId(1L);
		testIReserva.setHabitacion(new Habitacion(1L,"Dormitorio doble", "Dormitorio","Test bj.id:1.Clasico y luminoso..", 4545.7));
		testIReserva.setCantidad(1);
		testIReserva.setUsuario(new Usuario(1L, "Test", "Mail@test.com"));
		return testIReserva;
	}
		
	/**
	 * Crecion de una lista de articulos de una cesta de pre presupuesto.
	 * @return Lis<ItemReserva>
	 */
	public static List<ItemReserva> crearPrePresupuesto(){
		List<ItemReserva> testPrePresupuesto = new ArrayList<>();
		testPrePresupuesto.add(crearTestItemReserva());
		testPrePresupuesto.add(crearTestItemReserva());
		testPrePresupuesto.add(crearTestItemReserva());
		
		return testPrePresupuesto;
	}
}
