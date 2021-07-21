package com.reforma.ecoreforma.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Entity enum que accede a los permisos predefinidos, 
 * @see @Entity Permissions
 *  a base de los que recibimos acceso a los permisos de SimpleGrantedAuthority de Spring Security
 *
 */
public enum Role implements GrantedAuthority{
	USER,
	ADMIN;
	
	@Override
	public String getAuthority() {
		return name();
	}
}
