package com.reforma.ecoreforma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	
	List<Presupuesto> findByUsuario(Usuario ususario);
}
