package com.reforma.ecoreforma.domain;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * La clase describe la entidad "Usuario" implementa metodos de la interface {@link UserDetails}.
 * La anotacion @Entity nos surgiere que la clase esta mapeada por hibernate.
 * la anotacion @Table indica la existencia de la tabla "usuario" en la Base de datos."
 *
 */
/**
 * @author atcac
 *
 */
/**
 * @author atcac
 *
 */
@Entity(name = "usuario")
public class Usuario implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	/**
	 * codigo unico del objeto.
	 *  @Id - indica que el campo tiene la clave primaria;
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Nombre de usuario.
	 * La anotacion @NotBlank surgiere que el campo no debe de estar vacío.
	 */
	@NotBlank(message = "Complete el campo.")
	private String username;
	
	/**
	 * La contrasena del usuario para identificarse con el sistema.
	 * La anotacion @Size surgiere que el campo no debe de tener menos 6 caracteres.
	 */
	@Size(min=6, max=20, message = "Su contraseña debe tener minimo 6 caracteres")
	private String password;
	
	
	/**
	 * El email del usuario necesario para su identificacion.
	 * La anotacion @Pattern surgiere el mensaje del email incorecto.
	 */
	@Pattern(regexp="[A-Sa-s0-9._%-+]+@[-Sa-s0-9._%-]+\\.[A-Sa-s]{2,4}",
			message="Error en el formato del email")
	private String email;
    
	/**
	 * El rol del usuario que puede tomar distintos valores @see {@link Roles}
	 * @ElementCollection indica la relacion @OneToMany con la clase embebida {@link Roles}, 
	 *     dependiendo totalmente del comportamento de la clase "usuario"
	 * @CollectionTable 
	 */
	@ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "rol_usuario", joinColumns = @JoinColumn(name = "usuario_id"))
	@Enumerated(EnumType.STRING)
	private Set<Roles> roles;
	
    public Usuario(){}
	
	public Usuario(long id ,String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}
   


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	

	public void setPassword(String password) {
		this.password =  password;
	}
	
	
	public void setUsername(String username) {
		this.username =  username;
	}
	
	
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public Set<Roles> getRoles() {
		return roles;
	}


	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}



	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
   
	/**
	 * Metodo que comprueba los derechos de administrador.
	 */
	public boolean isAdmin() {
		return roles.contains(Roles.ADMIN);
	}

	/**
	 * Metodo de la interfaz implimentada {@link UserDetails}
	 * retorna un valor de tipo booleano dependiendo de la fecha de caducidad de la cuenta.
	 *  
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	/**
	 * Metodo de la interfaz implimentada {@link UserDetails}
	 *Indica el estado de la cuenta del usuario.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	/**
	 * Metodo de la interfaz implimentada {@link UserDetails}
	 * Indica si los credenciales del usuario han caducado.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	/**
	 * Metodo de la interfaz implimentada {@link UserDetails}
	 * Indica si el usuario esta activo a no.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Metodo de la interfaz implimentada {@link UserDetails}
	 * Retorna las autoridades ofrcidas al usuario.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return Objects.equals(id, usuario.getId()) &&
			   Objects.equals(email, usuario.getEmail());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", roles=" + roles + "]";
	}
    
}
