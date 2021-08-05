package com.reforma.ecoreforma.creator;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.reforma.ecoreforma.domain.EstadoPresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;

public class PresupuestoCreator {
	
	public static Presupuesto crearTestPresupuesto() {
		Presupuesto presupuesto = new Presupuesto();
		presupuesto.setNombre("nombreTest");
		presupuesto.setTelefono("634-123-123");
		presupuesto.setEmail("TestPresupusto@mail.com");
		presupuesto.setFechaPresupuesto(LocalDate.now());
		presupuesto.setPrecioTotal(BigDecimal.valueOf(2367));
		presupuesto.setEstado(EstadoPresupuesto.INICIAL);
		return presupuesto;
	}
	public static List<Presupuesto> crearListaPresupuestos(){
		List<Presupuesto> testPresupuestos = new ArrayList<>();
		testPresupuestos.add(crearTestPresupuesto());
		testPresupuestos.add(crearTestPresupuesto());
		testPresupuestos.add(crearTestPresupuesto());
		return testPresupuestos ;
	}
}
