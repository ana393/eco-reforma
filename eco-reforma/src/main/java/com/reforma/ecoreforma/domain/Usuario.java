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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "usuario")
public class Usuario implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Complete el campo.")
	private String username;
	
	@NotBlank(message = "Protege su cuenta con una contrasena.")
	private String password;
	
	
	@Email(message = "Email incorecto")
	@NotBlank(message = "El email no puede estar vacio")
	private String email;
    
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "rol_usuario", joinColumns = @JoinColumn(name = "usuario_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;
	
    public Usuario(){}
	
	public Usuario(long id ,String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
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


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
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
   

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isEnabled() {
		return true;
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
