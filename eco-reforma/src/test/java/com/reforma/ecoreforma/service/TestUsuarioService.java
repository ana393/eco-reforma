package com.reforma.ecoreforma.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.reforma.ecoreforma.domain.Role;
import com.reforma.ecoreforma.domain.Usuario;
import com.reforma.ecoreforma.repository.UsuarioRepository;
import com.reforma.ecoreforma.service.Impl.UsuarioServiceImpl;

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
		usuario.setRoles(Collections.singleton(Role.USER));
		
		
		when(usuarioRepositoryMock.save(usuario)).thenReturn(usuario);
		
		assertEquals(usuario, usuarioService.guardar(usuario));
		assertTrue(CoreMatchers.is(usuario.getRoles()).matches(Collections.singleton(Role.USER)));
		Mockito.verify(usuarioRepositoryMock, Mockito.times(1)).save(usuario);
	}
	
	@Test
	public void usuarioExisteTest() {
		Usuario usuario = new Usuario();
		usuario.setEmail("Mail@test.com");
		boolean esUsuarioPresente = usuarioService.usuarioExiste(usuario);
	
		
		assertTrue(usuario.getEmail().equals("Mail@test.com"));
		assertTrue(esUsuarioPresente);
		
	}
}
