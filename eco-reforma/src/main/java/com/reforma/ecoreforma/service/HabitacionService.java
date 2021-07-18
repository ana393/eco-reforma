package com.reforma.ecoreforma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.Habitacion;

public interface HabitacionService {
	
    Page<Habitacion> encuentraTodo(Pageable pagina);
	
	Page<Habitacion> encuentraPorTitulo(String titulo, Pageable pagina);
	
    Habitacion encuentraPorId(long id);
	
	Habitacion guardaHabitacion(Habitacion habitacion, MultipartFile Fichero);
	
	void eliminarHabitacionPorId(Long id);
}
