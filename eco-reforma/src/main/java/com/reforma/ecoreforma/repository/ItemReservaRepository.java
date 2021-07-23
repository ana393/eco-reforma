package com.reforma.ecoreforma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.Usuario;

public interface ItemReservaRepository  extends JpaRepository<ItemReserva, Long>{
	
    @EntityGraph(attributePaths = {"habitacion"})
	List<ItemReserva> findAllByUsuarioAndReservaIsNull(Usuario usuario);
	
    void deleteAllByUsuarioAndReservaIsNull(Usuario usuario);
	
	int countDistinctByUsuarioAndReservaIsNull(Usuario usuario);
}
