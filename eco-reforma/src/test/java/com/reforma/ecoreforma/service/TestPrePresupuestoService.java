package com.reforma.ecoreforma.service;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.reforma.ecoreforma.creator.ItemReservaCreator;
import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.ItemReservaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPrePresupuestoService {
	
	@Autowired
	private PrePresupuestoService prePresupuestoService;
	
	@MockBean
	private ItemReservaRepository itemReservaRepoMock;
	
   
	@Test
	public void test_ObtenerPrePresupuesto() {
		Usuario usuarioTest = new Usuario(1L, "Test","test@mail.com");
		ItemReserva testIReserva = ItemReservaCreator.crearTestItemReserva();
		List<ItemReserva> testIRList = ItemReservaCreator.crearPrePresupuesto();
		testIRList.add(testIReserva);
		PrePresupuesto expectedTest = new PrePresupuesto(testIRList); 
		when(itemReservaRepoMock.findAllByUsuarioAndReservaIsNull(usuarioTest)).thenReturn(testIRList);
		
		PrePresupuesto resultTest = prePresupuestoService.obtenPrePresupuesto(usuarioTest);
	
	    assertNotEquals(expectedTest, resultTest);
	}
	
	@Test
	public void test_EncuentraItemPorId() {
		ItemReserva testIReserva = new ItemReserva();
		testIReserva.setId(1L);
		
		when(itemReservaRepoMock.findById(any(Long.class))).thenReturn(Optional.of(testIReserva));
		 
		 ItemReserva result = prePresupuestoService.encuentraItemPorId(1L);
		 
		 verify(itemReservaRepoMock, times(1)).findById(any(Long.class));
		
		 assertAll(() -> assertNotNull(result),
				   () -> assertEquals(testIReserva.getId(), result.getId()));
	}
	
	@Test
	public void debe_Devolver_NrItemos_DelCache() {
		Usuario usuarioTest = new Usuario(1L, "Test","test@mail.com");
		ItemReserva testIReserva = ItemReservaCreator.crearTestItemReserva();
		
		when(itemReservaRepoMock.countDistinctByUsuarioAndReservaIsNull(usuarioTest)).thenReturn(testIReserva.getCantidad());
		int result = prePresupuestoService.obtenerNrItemos(usuarioTest);
		
		assertEquals(testIReserva.getCantidad(), result);
		verify(itemReservaRepoMock, Mockito.times(1)).countDistinctByUsuarioAndReservaIsNull(usuarioTest);
	}
	
	@Test
	public void test_Anade_Item_Existente_A_PrePresupuesto() {
		ItemReserva testIReserva = new ItemReserva();
		Habitacion testHabitacion = new Habitacion();
		Usuario testUsuario = new Usuario();
		testHabitacion.setId(1L);
		testUsuario.setId(1L);
		int qty = 2;
		testIReserva.anadeCantidad(qty);
		
		when(itemReservaRepoMock.save(testIReserva)).thenReturn(testIReserva);
		
		ItemReserva result = prePresupuestoService.anadeItemAPrePresupuesto(testHabitacion, testUsuario, qty);
		
		assertTrue(CoreMatchers.is(testIReserva.getCantidad()).matches(result.getCantidad()));
		verify(itemReservaRepoMock, Mockito.times(1)).save(testIReserva);
	}
	
	@Test
	public void test_Anade_Item_Nuevo_A_PrePresupuesto() {
		ItemReserva testIReserva = new ItemReserva();
		Habitacion testHabitacion = new Habitacion();
		Usuario testUsuario = new Usuario();
		testHabitacion.setId(1L);
		testUsuario.setId(1L);
		int qty = 2;
		testIReserva.anadeCantidad(qty);
		
		when(itemReservaRepoMock.save(testIReserva)).thenReturn(testIReserva);
		
		ItemReserva result = prePresupuestoService.anadeItemAPrePresupuesto(testHabitacion, testUsuario, qty);
		
		assertTrue(CoreMatchers.is(testIReserva.getCantidad()).matches(result.getCantidad()));
		assertTrue(CoreMatchers.is(testIReserva.getHabitacion()).matches(result.getHabitacion()));
		assertTrue(CoreMatchers.is(testIReserva.getUsuario()).matches(result.getUsuario()));
		verify(itemReservaRepoMock, Mockito.times(1)).save(testIReserva);
	}
	
	@Test
	public void debe_EliminarItemReserva() {
		//given
		ItemReserva testIReserva = new ItemReserva();
		testIReserva.setId(1L);
	    //when
		prePresupuestoService.eliminarItemReserva(testIReserva);
		//then
		verify(itemReservaRepoMock, Mockito.times(1)).existsById(testIReserva.getId());
		verify(itemReservaRepoMock, Mockito.times(0)).deleteById(testIReserva.getId());
	}

}
