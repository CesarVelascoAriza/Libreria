package com.cava.examples.seguridad.service.Impl;

import com.cava.examples.common.entitis.Rol;
import com.cava.examples.common.entitis.Usuario;
import com.cava.examples.seguridad.ususario.FeingUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private FeingUsuario feingUsuario;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void loadUserByUsernameRetornaUserDetailsConRoles() {
        Usuario usuario = new Usuario();
        usuario.setUserName("juan");
        usuario.setPassword("secret");
        usuario.setEnabled(true);

        Rol rol = new Rol();
        rol.setNombreRol("ADMIN");
        usuario.setRoles(List.of(rol));

        when(feingUsuario.buscarPorUserName("juan")).thenReturn(usuario);

        UserDetails userDetails = userService.loadUserByUsername("juan");

        assertEquals("juan", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN")));
    }

    @Test
    void loadUserByUsernameLanzaExcepcionCuandoNoExiste() {
        when(feingUsuario.buscarPorUserName("inexistente")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("inexistente"));
    }
}
