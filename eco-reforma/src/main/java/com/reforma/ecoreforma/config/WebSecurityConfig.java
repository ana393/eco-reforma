package com.reforma.ecoreforma.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
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

	
	@Autowired
	public WebSecurityConfig(UsuarioSecurityService usuarioSecurity, PasswordEncoder passwordEncoder) {
		this.usuarioSecurity = usuarioSecurity;
		this.passwordEncoder = passwordEncoder;
	}

	
	private static final String[] PUBLIC_MATCHERS = {"/home", "/login", "/catalogo", "/registro", "**/**/**.css", "/buscar", "/img/**"};

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
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioSecurity).passwordEncoder(passwordEncoder);
	}

}
