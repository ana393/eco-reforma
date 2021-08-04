package com.reforma.ecoreforma.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.Habitacion;

public interface HabitacionService {
	
	Long NumeroArticulos();
	
    Page<Habitacion> encuentraTodo(Pageable pagina);
	
	Page<Habitacion> encuentraPorTituloOrPorTipo(String titulo, String tipo, Pageable pageable);
	
    Habitacion encuentraPorId(long id);
	
	Habitacion guardaHabitacion(Habitacion habitacion, MultipartFile Fichero);
	
	void actualizarHabitacion(Habitacion habitacion);
	
	void eliminarHabitacionPorId(Long id);
}
