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
/**
 * Capa de Servicios que implementa los metodos de acceso del objeto {@link com.reforma.ecoreforma.domain.PrePresupuesto} 
 *  por la interfaz {@link com.reforma.ecoreforma.service.PrePresupuestoService}.
 * 
 * La anotacion @Service nos anuncia que esta clase es un componente de la capa de servicio,
 *   que es un subtipo de la clase @Component.
 * Usanddo la anotacion @Service se autodetecta el bean durante el escaneo del .classpath
 * <p>
 * Se recabara la informacion de los posibiles errores con el mecanismo {@link org.slf4j.Logger}; {@link org.slf4j.LoggerFactory}.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see Usuario
 * @see UsuarioServiceImpl
 *
 */
@Service
public class PrePresupuestoServiceImpl implements PrePresupuestoService {

	private final ItemReservaRepository itemReservaRepo;
	private static final Logger log = LoggerFactory.getLogger(PrePresupuestoServiceImpl.class);
	
	/**
	 * Constructor para la inicializacion  de la variable principal.
	 * Con la anotacion @Autowired  se lleva a cabo la inyección de dependencias del objeto.
	 * 
	 * @param itemReservaRepo implimentacion de la interfaz {@link ItemReservaRepository}
	 */
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
		log.info(String.format("Se esta añadiendo articulos [%S] a tu lista de favoritos (pre-Presupuesto) ", habitacion.toString()));
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
		log.debug("Se está a eliminar item del Cache: {}",item.getId());
		boolean i = itemReservaRepo.existsById(item.getId());
		if (i) {
		itemReservaRepo.deleteById(item.getId());
		}
	}

}
