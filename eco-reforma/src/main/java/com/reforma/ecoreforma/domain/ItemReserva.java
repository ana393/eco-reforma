package com.reforma.ecoreforma.domain;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Clase que describe la entidad "ItemReserva".
 * La anotacion @Entity nos surgiere que la clase esta mapeada por hibernate.
 * la anotacion @Table indica la existencia de la tabla "habitacion" en la Base de datos.
 *
 */
@Entity
@Table(name = "items_reserva")
public class ItemReserva {
	/**
	 * codigo unico del objeto.
	 *  @Id - indica que el campo tiene la clave primaria;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * cantidad de las habitacion a reformar 
	 */
	private int cantidad;
	/*
	 * @ManyToOne, Relacion unidireccional
	 */
	/**
	 * campo para la habitacion reservada o deseada a reformar
	 * @OneToOne indica el tipo de relacion con el objeto {@link Habitacion}.
	 * @JoinColumn indica con que columna se identifica.
	 */
	@OneToOne
	@JoinColumn(name="habitacion_id")
	private Habitacion habitacion;
	
	/**
	 * @ManyToOne indica la relacion entre el items reservados y el usuario.
	 * @JoinColumn indica con que columna se identifica. 
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	/**
	 * @ManyToOne indica la relacion entre el items reservados y la tabla presupuesto.
	 * @JoinColumn indica con que columna se identifica. 
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="presupuesto_id")
	private Presupuesto presupuesto;
	
	
	
	/**
	 * Metodo invocado por la clase {@PrePresupuesto}.
	 * @return el precio de la habitacion multiplicado por la cantidad resrvada;
	 */
	public BigDecimal Total() {
		return new BigDecimal(habitacion.getPrecio()).multiply(new BigDecimal(cantidad));
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ItemReserva item = (ItemReserva) o;
		return Objects.equals(id, item.getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
