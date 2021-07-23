package com.reforma.ecoreforma.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.Role;
import com.reforma.ecoreforma.domain.Usuario;

public class ItemReservaCreator {
    
	public static ItemReserva crearTestItemReserva() {
		ItemReserva testIReserva = new ItemReserva();
		testIReserva.setId(1L);
		testIReserva.setHabitacion(new Habitacion(1L,"Dormitorio doble", "Dormitorio","Test bj.id:1.Clasico y luminoso..", 45.7));
		testIReserva.setCantidad(1);
		testIReserva.setUsuario(new Usuario(1L, "Test", "Mail@test.com"));
		return testIReserva;
	}
		
	public static List<ItemReserva> crearPrePresupuesto(){
		List<ItemReserva> testPrePresupuesto = new ArrayList<>();
		testPrePresupuesto.add(crearTestItemReserva());
		testPrePresupuesto.add(crearTestItemReserva());
		testPrePresupuesto.add(crearTestItemReserva());
		
		return testPrePresupuesto;
	}
}
