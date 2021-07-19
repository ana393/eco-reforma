package com.reforma.ecoreforma.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/*
 * Clase que describe la entidad "Habitacion"
 * La anotacion @Entity indica que el objeto de esta clase estara procesado por hibernate.
 * La anotacion @Table indica el nombre de la tabla en la base de datos MySQL.
 */
@Entity
@Table(name = "habitacion")
public class Habitacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@NotBlank(message="Complete el campo, por favor!")
    private String titulo;
	
	private String tipo;
	@Length(max = 1024, message = "Mensaje demasiado largo (mas de 1kb)!")
	@NotBlank(message="Complete el campo, por favor!")
    private String descripcion;
	 
    private String img_url;
    
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
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		return "Habitacion [id=" + id + ", nombre=" + titulo + ", descripcion=" + descripcion + ", img_url=" + img_url
				+ ", precio=" + precio + ", categoria=" + tipo + "]";
	}
}
