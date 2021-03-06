package com.reforma.ecoreforma.service.Impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Capa de Servicios que implementa los metodos de acceso del objeto {@link com.reforma.ecoreforma.domain.Presupuesto} 
 *  por la interfaz {@link com.reforma.ecoreforma.service.PresupuestoService}.
 * <p>
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
public class PresupuestoServiceImpl  implements PresupuestoService{
	
	
	/**
	 * Se implementa la interfaz {@link com.reforma.ecoreforma.repository.PresupuestoRepository}.
	 */
	private final PresupuestoRepository presupuestoRepository;
	/**
	 * Se implementa la interfaz {@link com.reforma.ecoreforma.repository.ItemReservaRepository}.
	 */
	private final ItemReservaRepository itemRepository;
	/**
	 * Constructor para la inicializacion  de las variables principales.
	 * La anotacion @Autowired proporciona la inicializacion automatica de los objetos.
	 *  
	 * @param presupuestoRepository implimentacion de la interfaz {@link com.reforma.ecoreforma.repository.PresupuestoRepository}
	 *                    para el procesamiento de presupuestos de la base de datos.
	 * @param itemRepository implimentacion de la interfaz {@link com.reforma.ecoreforma.repository.ItemReservaRepository}.
	 */
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
		presupuestDB.setEstado(EstadoPresupuesto.INICIAL);
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
		Set<String> estados = Arrays.stream(EstadoPresupuesto.values())
				.map(EstadoPresupuesto::name)
				.collect(Collectors.toSet());
		
		for (String key : form.keySet()) {
			if (estados.contains(key)) {
				presupuesto.setEstado(EstadoPresupuesto.valueOf(key));
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

	@Override
	public Long presupuestosInicial() {
		return  presupuestoRepository.nrInicial();
	}

	@Override
	public Long presupuestosReformados() {
		return presupuestoRepository.nrReformado();
	}

}
