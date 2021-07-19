package com.reforma.ecoreforma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
	
	 Page<Habitacion> findAll(Pageable pageable);
	 
	 Page<Habitacion> findByTipoOrTitulo(String tipo, String titulo, Pageable pagina);  
}
