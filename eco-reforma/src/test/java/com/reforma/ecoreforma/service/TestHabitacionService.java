package com.reforma.ecoreforma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.repository.HabitacionRepository;
import com.reforma.ecoreforma.service.Impl.HabitacionServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHabitacionService {
	
	private HabitacionService habitacionService;
	
	@MockBean
	private HabitacionRepository habitacionRepositoryMock;
	  
	
	@Test
	public  void debe_devolver_listapagina_con_Habitaciones() {
		habitacionService = new HabitacionServiceImpl(habitacionRepositoryMock);
		List<Habitacion> habitacionList = new ArrayList<>();
		habitacionList.add(new Habitacion(1L,"Dormitorio doble", "Dormitorio","Test bj.id:1.Clasico y luminoso..", 45.7));
		habitacionList.add(new Habitacion(2L,"Salon Vanguardista", "Salon","Test bj.id:2.Clasico y luminoso..", 456.7));
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Habitacion> page = new PageImpl<>(habitacionList);

		when(habitacionRepositoryMock.findAll(pageable)).thenReturn(page);
		
		assertNotNull(habitacionList);
		assertEquals(2, habitacionService.encuentraTodo(pageable).getSize());	
	}
	
	@Test
	public  void encuentraPorTipoOPorTitulo() {
		habitacionService = new HabitacionServiceImpl(habitacionRepositoryMock);
		List<Habitacion> habitacionList = new ArrayList<>();
		Habitacion habitacion = new Habitacion();
		habitacion.setTipo("Dormitorio");
		habitacion.setTitulo("Matrimonio");
		habitacionList.add(habitacion);
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Habitacion> page = new PageImpl<>(habitacionList);

		when(habitacionRepositoryMock.findByTituloOrTipo(habitacion.getTitulo(), habitacion.getTipo(), pageable)).thenReturn(page);
		
		assertNotNull(habitacionList);
		assertNotNull(habitacion.getTipo());
		assertNotNull(habitacion.getTitulo());
		assertEquals(1, habitacionService.encuentraPorTituloOrPorTipo(habitacion.getTitulo(), habitacion.getTipo(), pageable).getSize());
		assertEquals("Dormitorio", habitacionService.encuentraPorTituloOrPorTipo(habitacion.getTitulo(), habitacion.getTipo(), pageable).getContent().get(0).getTipo());
		assertEquals("Matrimonio", habitacionService.encuentraPorTituloOrPorTipo(habitacion.getTitulo(), habitacion.getTipo(), pageable).getContent().get(0).getTitulo());
	}
	
}
