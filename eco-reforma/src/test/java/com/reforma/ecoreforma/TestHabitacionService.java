package com.reforma.ecoreforma;

import com.reforma.ecoreforma.domain.Habitacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.reforma.ecoreforma.repository.HabitacionRepository;
import com.reforma.ecoreforma.service.HabitacionService;
import com.reforma.ecoreforma.service.Impl.HabitacionServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHabitacionService {
	
	private HabitacionService habitacionService;
	
	@MockBean
	private HabitacionRepository habitacionRepositoryMock;
	  
	
	@Test
	public  void encuentraTodo() {
		habitacionService = new HabitacionServiceImpl(habitacionRepositoryMock);
		List<Habitacion> habitacionList = new ArrayList<>();
		habitacionList.add(new Habitacion(1L,"Dormitorio", "Dormitorio","Clasico y luminoso..", 45.7));
		habitacionList.add(new Habitacion(2L,"Salon", "Salon","Clasico y luminoso..", 456.7));
		
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

		when(habitacionRepositoryMock.findByTipoOrTitulo(habitacion.getTipo(), habitacion.getTitulo(), pageable)).thenReturn(page);
		
		assertNotNull(habitacionList);
		assertNotNull(habitacion.getTipo());
		assertNotNull(habitacion.getTitulo());
		assertEquals(1, habitacionService.encuentraPorTipoOPorTitulo(habitacion.getTipo(), habitacion.getTitulo(), pageable).getSize());
		assertEquals("Dormitorio", habitacionService.encuentraPorTipoOPorTitulo(habitacion.getTipo(), habitacion.getTitulo(), pageable).getContent().get(0).getTipo());
	}
	
	@Test
	public void encuntraPorId() {
		Long id = 6L;
		Habitacion habitacion = new Habitacion();
		habitacion.setId(id);
		
		assertNotNull(habitacion.getId());
		assertEquals(id, habitacion.getId());
	}
}
