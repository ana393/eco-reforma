package com.reforma.ecoreforma.service.Impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.ItemReserva;
import com.reforma.ecoreforma.domain.PrePresupuesto;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.ItemReservaRepository;
import com.reforma.ecoreforma.service.PrePresupuestoService;

@Service
public class PrePresupuestoServiceImpl implements PrePresupuestoService {

	private final ItemReservaRepository itemReservaRepo;
	private static final Logger log = LoggerFactory.getLogger(PrePresupuestoServiceImpl.class);
	
	@Autowired
	public PrePresupuestoServiceImpl(ItemReservaRepository itemReservaRepo) {
		this.itemReservaRepo = itemReservaRepo;
	}

	@Override
	public PrePresupuesto obtenPrePresupuesto(Usuario usuario) {
		return new PrePresupuesto(itemReservaRepo.findAllByUsuarioAndPresupuestoIsNull(usuario));
	}

	@Override
	public int obtenerNrItemos(Usuario usuario) {
		return itemReservaRepo.countDistinctByUsuarioAndPresupuestoIsNull(usuario);
	}

	@Override
	public ItemReserva encuentraItemPorId(Long id) {
		Optional<ItemReserva> opt = itemReservaRepo.findById(id);
		return opt.orElseThrow();//NotFoundElementException
	}

	@Override
	public ItemReserva anadeItemAPrePresupuesto(Habitacion habitacion, Usuario usuario, int qty) {
		PrePresupuesto prePresupuesto = this.obtenPrePresupuesto(usuario);
		ItemReserva itemReserva = prePresupuesto.encuentraItemPorHabitacion(habitacion.getId());
		if(itemReserva != null) {
			itemReserva = itemReservaRepo.save(itemReserva);
		} else {
			itemReserva = new ItemReserva();
			itemReserva.setUsuario(usuario);
			itemReserva.setHabitacion(habitacion);
			itemReserva.setCantidad(qty);
			itemReserva = itemReservaRepo.save(itemReserva);
		}
		return itemReserva;
	}

	@Override
	public void eliminarItemReserva(ItemReserva item) {
		log.debug("req a eliminar item del Cache: {}",item.getId());
		boolean i = itemReservaRepo.existsById(item.getId());
		if (i) {
		itemReservaRepo.deleteById(item.getId());
		}
	}

}
