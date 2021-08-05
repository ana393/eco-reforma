package com.reforma.ecoreforma.service.Impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reforma.ecoreforma.domain.EstadoPresupuesto;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Presupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.ItemReservaRepository;
import com.reforma.ecoreforma.repository.PresupuestoRepository;
import com.reforma.ecoreforma.service.PresupuestoService;

@Service
public class PresupuestoServiceImpl  implements PresupuestoService{
	
	
	private final PresupuestoRepository presupuestoRepository;
	private final ItemReservaRepository itemRepository;
	private static final Logger log = LoggerFactory.getLogger(PresupuestoServiceImpl.class);
	
	public PresupuestoServiceImpl(PresupuestoRepository presupuestoRepository, ItemReservaRepository itemRepository) {
		this.presupuestoRepository = presupuestoRepository;
		this.itemRepository = itemRepository;
	}

	@Override
	public Page<Presupuesto> encuentraTodos(Pageable page) {
		return presupuestoRepository.findAll(page);
	}

	@Override
	public Presupuesto guardar(Presupuesto presupuestoValida, Usuario usuario, PrePresupuesto prePresupuesto) {
		Presupuesto presupuestDB = new Presupuesto();
		presupuestDB.setNombre(presupuestoValida.getNombre());
		presupuestDB.setTelefono(presupuestoValida.getTelefono());
		presupuestDB.setEmail(presupuestoValida.getEmail());
		presupuestDB.setFechaPresupuesto(LocalDate.now());
		presupuestDB.setUsuario(usuario);
		presupuestDB.setPrecioTotal(presupuestoValida.getPrecioTotal());
		presupuestDB.setEstado(EstadoPresupuesto.PENDIENTE);
		presupuestoRepository.save(presupuestDB);
		
		List<ItemReserva> lista = prePresupuesto.getPrePresupusetoItemos();
		for (ItemReserva item : lista) {
			item.setPresupuesto(presupuestDB);
			itemRepository.save(item);
			
		}
		return presupuestDB;
	}

	@Override
	public Page<Presupuesto> encuentraPorUsuario(Usuario usuario, Pageable pageable) {
		return presupuestoRepository.findByUsuario(usuario, pageable);
	}

	@Override
	public void actualizaPresupuesto(Map<String, String> form, Presupuesto presupuesto) {
		Set <String> estados = Arrays.stream(EstadoPresupuesto.values())
				.map(EstadoPresupuesto::name)
				.collect(Collectors.toSet());
		
		for (String key : form.keySet()) {
			if (estados.contains(key)) {
				presupuesto.setEstado(EstadoPresupuesto.valueOf(key));
				log.info("IN actualizarPresupuesto(): {}", presupuesto.getEstado());
				presupuestoRepository.save(presupuesto);
			}
		}	
		
	}

	@Override
	public void eliminarPresupuesto(Long id) {
		presupuestoRepository.deleteById(id);
		
	}

	@Override
	public Long nrPresupuestos() {
		return presupuestoRepository.count();
	}

	@Override
	public Long presupuestosTramitados() {
		return presupuestoRepository.nrTramitados();
	}

}
