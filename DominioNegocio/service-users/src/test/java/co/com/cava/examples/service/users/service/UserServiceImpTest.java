package co.com.cava.examples.service.users.service;

import co.com.cava.examples.service.users.repository.UserRespository;
import com.cava.examples.common.entitis.Rol;
import com.cava.examples.common.entitis.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRespository repository;

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void updateUserActualizaDatosYGuarda() {
        Usuario existente = new Usuario();
        existente.setId(7L);
        existente.setUserName("viejo");
        existente.setPassword("passviejo");
        existente.setEnabled(false);

        Usuario nuevo = new Usuario();
        nuevo.setId(7L);
        nuevo.setUserName("nuevo");
        nuevo.setPassword("passnuevo");
        nuevo.setEnabled(true);
        Rol rol = new Rol();
        rol.setId(9L);
        rol.setNombreRol("USER");
        nuevo.setRoles(List.of(rol));

        when(repository.findById(7L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        Usuario resultado = userService.updateUser(nuevo);

        assertEquals("nuevo", resultado.getUserName());
        assertTrue(resultado.isEnabled());
    }

    @Test
    void findByNameRetornaUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(4L);
        usuario.setUserName("prueba");
        usuario.setPassword("pass");
        usuario.setEnabled(true);

        when(repository.findByUserName("prueba")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = userService.findByName("prueba");

        assertTrue(resultado.isPresent());
        assertEquals("prueba", resultado.get().getUserName());
    }
}
