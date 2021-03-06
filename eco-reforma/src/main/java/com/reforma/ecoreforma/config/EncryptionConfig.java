package com.reforma.ecoreforma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuracion 
 * La anotacion @Configuration encargada de definir que la clase es una clase de configuracion para el framework.
 * 
 * @author Ana Tcaci
 * @version 1.0
 * @see PasswordEncoder
 */
@Configuration
public class EncryptionConfig {
	
	 /**
	  * Metodo de incriptacion de contrasena.
	 * @Bean - anotacion que marca como bean cada  metodo de tal forma que esten disponibles para Spring.
	 * @return un objeto BCryptPasswordEncoder una combinacion de hashing  con 8-byte generados al azar 
	 */
	@Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(8);
	    }

}
