package com.reforma.ecoreforma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	
	String OBTENER_NR_PRESUPUESTOS_TRAMITADOS = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'TRAMITADO'";
	String OBTENER_NR_PRESUPUESTOS_INICIAL = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'INICIAL'";
	String OBTENER_NR_PRESUPUESTOS_REFORMADO = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'REFORMADO'";
	
	Page<Presupuesto> findByUsuario(Usuario ususario, Pageable pageable);
	
	@Query(value = OBTENER_NR_PRESUPUESTOS_TRAMITADOS, nativeQuery = true)
	Long nrTramitados();
	
	@Query(value = OBTENER_NR_PRESUPUESTOS_INICIAL, nativeQuery = true)
	Long nrInicial();
	
	@Query(value = OBTENER_NR_PRESUPUESTOS_REFORMADO, nativeQuery = true)
	Long nrReformado();
}
