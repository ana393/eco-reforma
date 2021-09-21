package com.reforma.ecoreforma.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Clase que describe la entidad "Presupuesto".
 * La anotacion @Entity nos surgiere que la clase esta mapeada por hibernate.
 * la anotacion @Table indica la existencia de la tabla "presupuesto" en la Base de datos.
 *
 */
/**
 * @author atcac
 *
 */
@Entity(name="presupuesto")
public class Presupuesto {
	/**
	 * codigo unico del objeto.
	 * @Id - indica que el campo tiene la clave primaria;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	/**
	 * campo para el nombre del cliente.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío.
	 */
	@NotBlank(message = "Complete este campo, porfavor.")
	private String nombre;
	
	/**
	 * campo para el telefono del cliente.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío.
	 */
	@Size(min=9, message = "Su teléfono debe contener mínimo 9 cifras")
	private String telefono;
	
	/**
	 * campo para el email del cliente.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío.
	 */
	@Email(message="Facilite un correo electrónico válido.")
	private String email;
	
	
	/**
	 * campo para la lista de items reservados por el cliente.
	 * @OneToMany relacion entre las entidades "presupuesto" y  "items_reserva" {@link com.reforma.ecoreforma.ItemReserva}.
	 *           cascade=CascadeType.ALL tipo de propagacion,
	 *           fetch = FetchType.EAGER - tipo de carga de los objetos traidos de la Base de Datos, que sera totalmente.
	 *            
	 */
	@OneToMany(mappedBy="presupuesto", cascade=CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
	private List<ItemReserva>  itemsReserva = new ArrayList<>();
	
	/**
	 * campo para el estado del presupuesto.
	 * @Column indica la columna para la clase embebida.
	 * @Enumerated indica el mapeo de enumeraciones {@link com.reforma.ecoreforma.domain.EstadoPresupuesto}
	 */
	@Column(name = "estado_presupuesto")
	@Enumerated(EnumType.STRING)
	private EstadoPresupuesto estado;
	
	/**
	 * @ManyToOne indica la relacion con la entidad {@link Usuario}
	 */
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * campo para el precio total del presupuesto
	 */
	private BigDecimal precioTotal;
	/**
	 * campo para la fecha actual del presupuesto
	 */
	private LocalDate fechaPresupuesto;

	
	public Presupuesto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public LocalDate getFechaPresupuesto() {
		return fechaPresupuesto;
	}

	public void setFechaPresupuesto(LocalDate fechaPresupuesto) {
		this.fechaPresupuesto = fechaPresupuesto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EstadoPresupuesto getEstado() {
		return estado;
	}

	public void setEstado(EstadoPresupuesto estado) {
		this.estado = estado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<ItemReserva> getItemsReserva() {
		return itemsReserva;
	}

	public void setItemsReserva(List<ItemReserva> itemsReserva) {
		this.itemsReserva = itemsReserva;
	}
	
	/**
	 * Metodo que compruebe el estado del presupuesto reformado,
	 *             para el renderizaje de elementos en la pagina.
	 * @return boolean
	 */
	public boolean isReformado() {
		boolean result = false;
		if(this.estado.equals(EstadoPresupuesto.REFORMADO)) {
			result = true;
		}
		return result;
	}

	/**
	 * @{code equal}
	 */
	@Override
	public boolean equals(Object o) {
			if(this == o) return true;
			if(o == null || getClass() != o.getClass()) return false;
			Presupuesto presupuesto = (Presupuesto) o;
			return Objects.equals(id, presupuesto.getId())&&
				   Objects.equals(usuario, presupuesto.getUsuario()) ;
		}
	
	/**
	 * @{code equal}
	 */
	@Override
	public int hashCode() {
			return Objects.hash(id, usuario, itemsReserva);
		}
}
