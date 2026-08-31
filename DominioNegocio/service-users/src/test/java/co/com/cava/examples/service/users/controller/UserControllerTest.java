package co.com.cava.examples.service.users.controller;

import co.com.cava.examples.service.users.service.UserService;
import com.cava.examples.common.entitis.Rol;
import com.cava.examples.common.entitis.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void listarRetornaUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUserName("admin");
        usuario.setPassword("secret");
        usuario.setEnabled(true);
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombreRol("ADMIN");
        usuario.setRoles(List.of(rol));

        when(userService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("admin"));
    }

    @Test
    void buscarPorUsernameRetornaUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUserName("prueba");
        usuario.setPassword("pass");
        usuario.setEnabled(true);

        when(userService.findByName("prueba")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/buscar-username")
                        .param("userName", "prueba")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("prueba"));
    }

    @Test
    void updateUserActualizaUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setUserName("nuevo");
        usuario.setPassword("pass2");
        usuario.setEnabled(true);

        when(userService.updateUser(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());

        verify(userService).updateUser(any(Usuario.class));
    }
}
