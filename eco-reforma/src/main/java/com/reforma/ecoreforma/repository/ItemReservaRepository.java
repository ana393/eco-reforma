package com.reforma.ecoreforma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.Usuario;

/**
 * Repositorio para el objeto {@link ItemReserva}
 *  que proporciona un conjunto de metodos para trabajar con la Base de Datos.
 *  
 *  Hereda de la interfaz {@link JpaRepository}
 */
public interface ItemReservaRepository  extends JpaRepository<ItemReserva, Long>{
	
    /**
     * @EntityGraph nos permiten definir un grafo de atributos habitacion  a cargar en una query.
     * @param usuario
     * @return list de objetos {@link ItemReserva}
     */
    @EntityGraph(attributePaths = {"habitacion"})
	List<ItemReserva> findAllByUsuarioAndPresupuestoIsNull(Usuario usuario);
	
    
	/**
	 * @param usuario
	 * @return numero de items distintos
	 */
	int countDistinctByUsuarioAndPresupuestoIsNull(Usuario usuario);
}
