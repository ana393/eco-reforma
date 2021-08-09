package com.reforma.ecoreforma.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.reforma.ecoreforma.domain.Roles;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUsuarioService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean
	private UsuarioRepository usuarioRepositoryMock;

	@MockBean
    private PasswordEncoder passwordEncoder;
	
	@Test
	public void guardarUsuarioTest() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setEmail("Mail@test.com");
		usuario.setRoles(Collections.singleton(Roles.USER));
		
		
		when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);
		
		assertEquals(usuario, usuarioService.guardar(usuario));
		assertTrue(CoreMatchers.is(usuario.getRoles()).matches(Collections.singleton(Roles.USER)));
		Mockito.verify(usuarioRepositoryMock, Mockito.times(1)).save(usuario);
	}
	
	@Test
	public void usuarioExisteTest() {
		String email = "Mail@test.com";
		boolean esUsuarioPresente = usuarioService.usuarioExiste(email);
	
		
		assertTrue(email.equals("Mail@test.com"));
		assertFalse(esUsuarioPresente);
		
	}
}
