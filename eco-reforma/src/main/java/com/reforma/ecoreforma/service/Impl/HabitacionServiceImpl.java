package com.reforma.ecoreforma.service.Impl;

import java.io.File;
import java.io.IOException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.Habitacion;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.HabitacionRepository;
import com.reforma.ecoreforma.service.HabitacionService;

import javassist.NotFoundException;

/**
 * Capa de Servicios que implementa los metodos de acceso del objeto {@link com.reforma.ecoreforma.domain.Habitacion} 
 *  por la interfaz {@link com.reforma.ecoreforma.service.HabitacionService}.
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
public class HabitacionServiceImpl implements HabitacionService{
	
	
	private final HabitacionRepository habitacionRepository;
	private static final Logger log = LoggerFactory.getLogger(HabitacionServiceImpl.class);
	
	/**
	 * direccion de la carpeta que guarda los imagines de los recursos.
	 */
	@Value("${upload.path}")
	private String uploadPath;
	
	/**
	 * Constructor para la inicializacion  de la variable principal.
	 * Con la anotacion @Autowired  se lleva a cabo la inyección de dependencias del objetos.
	 * 
	 * @param habitacionRepository implimentacion de la interfaz {@link HabitacionService}
	 *                          para el procesamiento de habitaciones de la base de datos.
	 */
	@Autowired
	public HabitacionServiceImpl(HabitacionRepository habitacionRepository) {
			this.habitacionRepository = habitacionRepository;
		}
	  

	@Override
	public Page<Habitacion> encuentraTodo(Pageable pagina) {
		return habitacionRepository.findAll(pagina);
	}

	@Override
	public Page<Habitacion> encuentraPorTituloOrPorTipo(String titulo, String tipo, Pageable pageable) {
		return habitacionRepository.findByTituloOrTipo(titulo, tipo, pageable);
	}
	
	@Override
	public Habitacion encuentraPorId(long id) {
		Habitacion result = habitacionRepository.findById(id).orElse(null);
		return result;
	}

	@Override
	public Habitacion guardaHabitacion(Habitacion habitacion,  MultipartFile fichero){
		log.info(String.format("Estas guardando un nuevo recurso [%s]", habitacion.toString()));
		 Habitacion guardado = new Habitacion();
		  guardado.setTitulo(habitacion.getTitulo());
		  guardado.setTipo(habitacion.getTipo());
		  guardado.setDescripcion(habitacion.getDescripcion());
		  guardado.setPrecio(habitacion.getPrecio());
		 if (fichero == null) {
			 habitacion.setImg_url("vacio.jpg");
		 } else {
			 File SystemDir = new File(uploadPath);
			 if (!SystemDir.exists()) {
				 SystemDir.mkdir();
			 }
		 
		String nombre_Fichero = StringUtils.cleanPath(fichero.getOriginalFilename());
		
		try{
			fichero.transferTo(new File(uploadPath + "/" + nombre_Fichero));
			}catch(IOException e) {
	            log.error(String.format("No se pudo transferir el fichero [%s], a la carpeta uploadPath indicada.", e.toString()));
	        } catch (Exception exception) {
	            // excepciones inesperadas.
	           log.error(exception.toString());
	        }
			   
		  habitacion.setImg_url(nombre_Fichero);
		 } 
		
		Habitacion persistente = habitacionRepository.save(habitacion);
		log.info("El nuevo recurso - habitacion: {} se guardo corectamente", persistente);
		
		return persistente;
	}

	@Override
	@Transactional
	public void eliminarHabitacionPorId(Long id) {
		habitacionRepository.deleteById(id);
		log.info("Se está eliminando un recurso - habitacion con id: {} se ha eliminado", id);			
	}


	@Override
	public void actualizarHabitacion(Habitacion habitacion) {
	try {
			Habitacion result = habitacionRepository.findById(habitacion.getId())
					.orElseThrow(()-> new NotFoundException("Habitacion no encontrada" + habitacion.getId()));
			result.setTitulo(habitacion.getTitulo());
			result.setTipo(habitacion.getTipo());
			result.setPrecio(habitacion.getPrecio());
			result.setDescripcion(habitacion.getDescripcion());
			
			habitacionRepository.save(result);

			log.info("Se  esta actualizando un recurso - habitacion: {} ", habitacion);
		} catch (NotFoundException e) {
			log.warn("Habitacion no encontrada.");
		}	
	}


	@Override
	public Long NumeroArticulos() {
		return habitacionRepository.count();
	}
}
