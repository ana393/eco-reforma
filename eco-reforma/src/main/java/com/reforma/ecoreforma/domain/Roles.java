package com.reforma.ecoreforma.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Entity enumeracion que accede a los permisos predefinidos,
 *       implementa metodos de la interefaz {@link GrantedAuthority}.
 */
public enum Roles implements GrantedAuthority {
	/**
	 * Los posibiles roles de los usuarios de la aplicacion.
	 */
	USER, ADMIN;
	
	@Override
	public String getAuthority() {
		return name();
	}
}
