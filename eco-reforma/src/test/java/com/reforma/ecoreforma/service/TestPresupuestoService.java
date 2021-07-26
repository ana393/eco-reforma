package com.reforma.ecoreforma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.reforma.ecoreforma.creator.PresupuestoCreator;
import com.reforma.ecoreforma.domain.EstadoPresupuesto;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.PresupuestoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPresupuestoService {
   
	@Autowired
	private PresupuestoService presupuestoService;
	
	@MockBean
	private PresupuestoRepository presupuestoRepositoryMock;
	
	
	@Test
	public  void debe_devolver_listaPagina_con_Presupuestos() {
		//given
		List<Presupuesto> presupuestosList = PresupuestoCreator.crearListaPresupuestos();
		Pageable pageable = PageRequest.of(0, 3);
		Page<Presupuesto> page = new PageImpl<>(presupuestosList);
		//when
		when(presupuestoRepositoryMock.findAll(pageable)).thenReturn(page);
		//then
		assertNotNull(presupuestosList);
		assertEquals(3, presupuestoService.encuentraTodos(pageable).getSize());	
	}
	
	@Test
	public void debe_devolver_Presupuesto_Guardado() {
		//given
		Presupuesto presupuestoTest = PresupuestoCreator.crearTestPresupuesto();
		Presupuesto presupuestoValidoTest = new Presupuesto();
		Usuario usuarioTest = new Usuario();
		List<ItemReserva> listaTest = new ArrayList<>();
		PrePresupuesto ppTest = new PrePresupuesto(listaTest);
		usuarioTest.setId(1L);
		presupuestoTest.setUsuario(usuarioTest);
		
		//when
		when(presupuestoRepositoryMock.save(presupuestoTest)).thenReturn(presupuestoTest);
		
		//then
		assertNotNull(presupuestoValidoTest);
		assertNotNull(usuarioTest);
		assertNotNull(ppTest);
		
		assertEquals(presupuestoTest.getFechaPresupuesto(), LocalDate.now());
		assertEquals(presupuestoTest.getEstado(), EstadoPresupuesto.PENDIENTE);
	    assertEquals(presupuestoTest, presupuestoService.guardar(presupuestoValidoTest, usuarioTest, ppTest));
		Mockito.verify(presupuestoRepositoryMock, Mockito.times(1)).save(presupuestoTest);
	}
	
	@Test
	public void debe_devolver_presupuesto_Por_Usuario() {
		//given
		List<Presupuesto> presupuestosList = PresupuestoCreator.crearListaPresupuestos();
		Presupuesto presupuestoTest = new Presupuesto();
		Usuario usuarioTest = new Usuario();
		usuarioTest.setId(1L);
		presupuestoTest.setUsuario(usuarioTest);
		
		//when
		when(presupuestoRepositoryMock.findByUsuario(usuarioTest)).thenReturn(presupuestosList);
		//then
		assertEquals(presupuestosList, presupuestoService.encuentraPorUsuario(usuarioTest));
		Mockito.verify(presupuestoRepositoryMock, Mockito.times(1)).findByUsuario(usuarioTest);
	}
	@Test
	public void debe_Comprobar_Actualizacion_del_Estado_Presupuesto() {
		//given
		Presupuesto presupuestoTest = new Presupuesto();
		Map<String, String> formTest = new HashMap<String, String>();
		formTest.put("RECIBIDO", "PENDIENTE");
		presupuestoTest.setEstado(EstadoPresupuesto.RECIBIDO);
		
		//when
		when(presupuestoRepositoryMock.save(presupuestoTest)).thenReturn(presupuestoTest);
		presupuestoService.actualizaPresupuesto(formTest, presupuestoTest);
		
		//then
		assertTrue(CoreMatchers.is(presupuestoTest.getEstado()).matches(EstadoPresupuesto.RECIBIDO));
	}
	
	@Test
	public void debe_Eliminar_Prsupuesto() {
		//given
		Presupuesto presupuestoTest = new Presupuesto();
		presupuestoTest.setId(1L);
	    //when
		presupuestoService.eliminarPresupuesto(presupuestoTest.getId());
		//then
		verify(presupuestoRepositoryMock, Mockito.times(1)).deleteById(presupuestoTest.getId());
			
	}
}
