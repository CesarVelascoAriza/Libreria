package co.com.cava.examples.service.users.controller;

import co.com.cava.examples.service.users.service.UserService;
import com.cava.examples.common.entitis.Rol;
import com.cava.examples.common.entitis.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void updateUserActualizaUsuario() {
        ReflectionTestUtils.setField(userController, "service", userService);
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUserName("nuevo");
        usuario.setPassword("pass2");
        usuario.setEnabled(true);
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombreRol("USER");
        usuario.setRoles(List.of(rol));

        when(userService.updateUser(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = userController.updateUser(3L, usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).updateUser(any(Usuario.class));
    }

    @Test
    void buscarPorUsernameRetornaUsuarioCuandoExiste() {
        ReflectionTestUtils.setField(userController, "service", userService);

        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUserName("prueba");
        usuario.setPassword("pass");
        usuario.setEnabled(true);

        when(userService.findByName("prueba")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = userController.getUserName("prueba");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("prueba", ((Usuario) response.getBody()).getUserName());
    }

    @Test
    void buscarPorUsernameRetornaNoContentCuandoNoExiste() {
        ReflectionTestUtils.setField(userController, "service", userService);

        when(userService.findByName("inexistente")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUserName("inexistente");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
