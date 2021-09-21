package com.reforma.ecoreforma.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * Clase que describe la entidad "Habitacion"
 * La anotacion @Entity nos surgiere que la clase esta mapeada por hibernate.
 * la anotacion @Table indica la existencia de la tabla "habitacion" en la Base de datos."
 *
 *@author Ana Tcaci
 *@version 1.0.0
 */
@Entity
@Table(name = "habitacion")
public class Habitacion {
	/**
	 * codigo unico del objeto.
	 *  @Id - indica que el campo tiene la clave primaria;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/**
	 * titulo de la habitacion.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío.
	 */
	@NotBlank(message="Complete el campo, por favor!")
    private String titulo;
	
	/**
	 * tipo de la habitacion. 
	 */
	@NotBlank(message="Complete el campo, por favor!")	
	private String tipo;
	/**
	 * campo para la descripcion relevante de la descripcion.
	 * @Length indica la longitud permitida.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío. 
	 */
	@Length(max = 1024, message = "Mensaje demasiado largo (mas de 1kb)!")
	@NotBlank(message="Complete el campo, por favor!")
    private String descripcion;
	 
    /**
     * campo para la URL de la imagen.
     */
    private String img_url;
    
    /**
     *  precio de la habitacion
     */
    private Double precio;
   
    public Habitacion() {}
   
    public Habitacion(long id, String titulo, String tipo, String descripcion, double precio) {
    	this.id = id;
  		this.titulo = titulo;
  		this.descripcion = descripcion;
  		this.precio = precio;
  		this.tipo = tipo;
  	}
   public Habitacion(String titulo, String tipo, String descripcion, String img_url, double precio) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.img_url = img_url;
		this.precio = precio;
		this.tipo = tipo;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}


	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
  /*
   * @{code equal}
   */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Habitacion habitacion = (Habitacion) o;
		return Objects.equals(id, habitacion.getId())&&
			   Objects.equals(titulo, habitacion.getTitulo()) &&
			   Objects.equals(precio, habitacion.getPrecio());
	}
	
   /*
	 * @{code hashCode}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, titulo, precio);
	}
	
	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", img_url=" + img_url
				+ ", precio=" + precio + ", tipo=" + tipo + "]";
	}
}
