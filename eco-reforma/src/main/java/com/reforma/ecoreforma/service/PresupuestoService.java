package com.reforma.ecoreforma.service;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;

public interface PresupuestoService {
	
	long nrPresupuestos();
	
	long presupuestosTramitados();
	
	Page<Presupuesto> encuentraTodos(Pageable page);
	  
    Presupuesto guardar(Presupuesto presupuestoValida, Usuario usuario, PrePresupuesto presupuesto);
	  
    Page<Presupuesto> encuentraPorUsuario(Usuario usuario, Pageable pageable);
	  
	void actualizaPresupuesto(Map<String, String> form, Presupuesto presupuesto);
	  
	void eliminarPresupuesto(Long id);

}
