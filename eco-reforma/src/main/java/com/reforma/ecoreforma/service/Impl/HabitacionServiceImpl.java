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
import org.springframework.web.multipart.MultipartFile;

import com.reforma.ecoreforma.domain.Habitacion;
import org.springframework.util.StringUtils;
import com.reforma.ecoreforma.repository.HabitacionRepository;
import com.reforma.ecoreforma.service.HabitacionService;

@Service
public class HabitacionServiceImpl implements HabitacionService{
	
	private final HabitacionRepository habitacionRepository;
	private static final Logger log = LoggerFactory.getLogger(HabitacionServiceImpl.class);
	
	@Value("${upload.path}")
	private String uploadPath;
	
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
		log.info("IN encontrarPorId() - habitacion: {} encontrada por id: {}", result);
		return result;
	}

	@Override
	public Habitacion guardaHabitacion(Habitacion habitacion,  MultipartFile fichero){
		log.info("IN guardarFichero() - carpeta {}", uploadPath);
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
			   e.printStackTrace();
			}
		  habitacion.setImg_url(nombre_Fichero);
		 } 
		
		Habitacion persistente = habitacionRepository.save(habitacion);
		log.info("IN guardaHabitacion() - habitacion: {}  se guardo", persistente);
		
		return persistente;
	}

	@Override
	@Transactional
	public void eliminarHabitacionPorId(Long id) {
		habitacionRepository.deleteById(id);
		log.info("IN eliminarHabitacion() - habitacion con id: {} se ha eliminado");			
	}


	@Override
	public void actualizarHabitacion(Habitacion habitacion) {
		habitacionRepository.save(habitacion);
		log.info("IN guardaHabitacion() - habitacion: {}  se guardo");
	}
}
