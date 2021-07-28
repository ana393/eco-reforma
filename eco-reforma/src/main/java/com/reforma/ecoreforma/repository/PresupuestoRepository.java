package com.reforma.ecoreforma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	
	Page<Presupuesto> findByUsuario(Usuario ususario, Pageable pageable);
}
