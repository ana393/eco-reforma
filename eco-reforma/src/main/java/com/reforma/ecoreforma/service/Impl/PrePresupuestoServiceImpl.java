package com.reforma.ecoreforma.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	
	@Autowired
	public PrePresupuestoServiceImpl(ItemReservaRepository itemReservaRepo) {
		this.itemReservaRepo = itemReservaRepo;
	}

	@Override
	public PrePresupuesto obtenPreReserva(Usuario usuario) {
		return new PrePresupuesto(itemReservaRepo.findAllByUsuarioAndReservaIsNull(usuario));
	}

	@Override
	@Cacheable("nr_itemos")
	public int obtenerNrItemos(Usuario usuario) {
		return itemReservaRepo.countDistinctByUsuarioAndReservaIsNull(usuario);
	}

	@Override
	public ItemReserva encuentraItemPorId(Long id) {
		Optional<ItemReserva> opt = itemReservaRepo.findById(id);
		return opt.orElseThrow();
	}

	@Override
	public ItemReserva anadeItemAPreRresupuesto(Habitacion habitacion, Usuario usuario, int qty) {
		PrePresupuesto prePresupuesto = this.obtenPreReserva(usuario);
		ItemReserva itemReserva = prePresupuesto.encuentraItemPorHabitacion(habitacion.getId());
		if(itemReserva != null) {
			itemReserva.anadeCantidad(qty);
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
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void eliminarItemReserva(ItemReserva item) {
		boolean i = itemReservaRepo.existsById(item.getId());
		if (i) {
		itemReservaRepo.deleteById(item.getId());
		}
	}

	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void actualizarItemReserva(ItemReserva item, Integer qty) {
		if(qty == null || qty <= 0) {
			this.eliminarItemReserva(item);
		}
		
	}

	@Override
	@CacheEvict(value = "nr_itemos", allEntries = true)
	public void vaciarPrePresupuesto(Usuario usuario) {
		itemReservaRepo.deleteAllByUsuarioAndReservaIsNull(usuario);
		
	}

}
