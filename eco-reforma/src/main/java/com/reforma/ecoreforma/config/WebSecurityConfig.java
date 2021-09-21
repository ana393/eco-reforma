package com.reforma.ecoreforma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.reforma.ecoreforma.service.Impl.UsuarioSecurityService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 * objeto UserDetailsService los credenciales del usuario registrados
	 */
	private  final UsuarioSecurityService usuarioSecurity;
	private  final PasswordEncoder passwordEncoder;

	
	/**
	 * Constructor para la inicializacion  de las variable principal.
	 * Con la anotacion @Autowired  se llevar a cabo la inyección de dependencias del objeto.
	 * @param usuarioSecurity implimentacion del servicio {@link UsuarioSecurityService }.
	 * @param passwordEncoder implimentacion de la clase {@link PasswordEncoder}.
	 */
	@Autowired
	public WebSecurityConfig(UsuarioSecurityService usuarioSecurity, PasswordEncoder passwordEncoder) {
		this.usuarioSecurity = usuarioSecurity;
		this.passwordEncoder = passwordEncoder;
	}

	
	/**
	 * variable que representa un array de url permitidos al acceso publico.
	 */
	private static final String[] PUBLIC_MATCHERS = {"/home", "/login", "/catalogo", "/registro", "**/**/**.css", "/buscar", "/img/**"};

	/**
	 * Reglas de configuracion de acceso a la pagina para usuarios.
	 * Se indica la direccion a los recursos con acceso limitado.
	 * 
	 * @param http del objeto {@link HttpSecurity} para la configuracion de los derechos de acceso a las paginas.
	 * @throws Exception metodo de la clase {@link HttpSecurity}.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
			.permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.permitAll()	
		.and()
		    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/salir"))
		    .permitAll()
		.and()
	        .csrf().disable();
		
		
	}
	
	/**
	 * Configuracion de usuarios con sus roles. Los usuarios se cargaran de la base de datos
	 *  usando los metodos de la interfaz implimentada {@link UserDetailsService}.
	 *  
	 * @param auth objeto {@link AuthenticationManagerBuilder}.
	 * @throws Exception metodo de la clase {@link AuthenticationManagerBuilder}.  
	 *
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioSecurity).passwordEncoder(passwordEncoder);
	}

}
