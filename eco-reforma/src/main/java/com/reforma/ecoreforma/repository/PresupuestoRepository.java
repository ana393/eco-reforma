package com.reforma.ecoreforma.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;

/**
 * Repositorio para el objeto {@link com.reforma.ecoreforma.domain.Presupuesto}
 *  que proporciona un conjuntos de metodos para trabajar con la Base de Datos.
 *  
 *  Hereda de la interfaz {@link JpaRepository}
 */
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
	/**
	 * Lista de llamadas nativas MySQL a la Base de Datos.
	 */
	String OBTENER_NR_PRESUPUESTOS_TRAMITADOS = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'TRAMITADO'";
	String OBTENER_NR_PRESUPUESTOS_INICIAL = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'INICIAL'";
	String OBTENER_NR_PRESUPUESTOS_REFORMADO = "SELECT count(*) FROM presupuesto WHERE estado_presupuesto = 'REFORMADO'";
	
	/**
	 * Metodo que encuentra la lista de presupuestos segun usuario.
	 * 
	 * @param ususario
	 * @param pageable
	 * @return list de objetos {@link Presupuesto}
	 */
	Page<Presupuesto> findByUsuario(Usuario ususario, Pageable pageable);
	
	/**
	 * @Query  queries directamente sobre el metodo del repositorio.
	 * @return numero de obj {@link Presupuestos} segun {@link EstadoPresupuesto}
	 */
	@Query(value = OBTENER_NR_PRESUPUESTOS_TRAMITADOS, nativeQuery = true)
	Long nrTramitados();
	/**
	 * @Query  queries directamente sobre el metodo del repositorio.
	 * @return numero de obj {@link Presupuestos} segun {@link EstadoPresupuesto}
	 */
	@Query(value = OBTENER_NR_PRESUPUESTOS_INICIAL, nativeQuery = true)
	Long nrInicial();
	/**
	 * @Query  queries directamente sobre el metodo del repositorio.
	 * @return numero de obj {@link Presupuestos} segun {@link EstadoPresupuesto}
	 */
	@Query(value = OBTENER_NR_PRESUPUESTOS_REFORMADO, nativeQuery = true)
	Long nrReformado();
}
