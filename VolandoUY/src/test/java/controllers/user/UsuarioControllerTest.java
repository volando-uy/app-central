package controllers.user;

import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Aereolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private IUsuarioService usuarioService;
    private ModelMapper modelMapper;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);
        usuarioController = new UsuarioController(usuarioService, modelMapper);
    }

    @Test
    @DisplayName("Debe llamar a altaCliente y mapear correctamente el DTO")
    void altaCliente_deberiaLlamarAlServiceConEntidadMapeada() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNickname("gyabisito");
        dto.setNombre("Jose");
        dto.setApellido("Ramirez"); // <- obligatorio
        dto.setMail("gyabisito@example.com");
        dto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        dto.setTipoDocumento(EnumTipoDocumento.CI);
        dto.setNumDocumento("12345678");
        dto.setNacionalidad("Uruguay");

        Cliente clienteMock = new Cliente();
        clienteMock.setNickname("gyabisito");

        when(modelMapper.map(dto, Cliente.class)).thenReturn(clienteMock);

        usuarioController.altaCliente(dto);

        verify(modelMapper).map(dto, Cliente.class);
        verify(usuarioService).altaCliente(clienteMock);
    }

    @Test
    @DisplayName("Debe llamar a altaAereolinea y mapear correctamente el DTO")
    void altaAereolinea_deberiaLlamarAlServiceConEntidadMapeada() {
        AereolineaDTO dto = new AereolineaDTO();
        dto.setNickname("flyuy");
        dto.setNombre("FlyUY");
        dto.setMail("flyuy@correo.com");
        dto.setDescripcion("Low cost desde el cielo");

        Aereolinea aereolineaMock = new Aereolinea();
        aereolineaMock.setNickname("flyuy");

        when(modelMapper.map(dto, Aereolinea.class)).thenReturn(aereolineaMock);

        usuarioController.altaAereolinea(dto);

        verify(modelMapper).map(dto, Aereolinea.class);
        verify(usuarioService).altaAereolinea(aereolineaMock);
    }


    @Test
    @DisplayName("Debe retornar todos los usuarios desde el service")
    void obtenerTodosLosUsuarios_deberiaRetornarListaDelService() {
        Usuario u = new Cliente();
        u.setNickname("gyabisito");

        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(u));

        List<Usuario> resultado = usuarioController.obtenerTodosLosUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("gyabisito", resultado.get(0).getNickname());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si no hay usuarios")
    void obtenerTodosLosUsuarios_listaVacia() {
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(Collections.emptyList());

        List<Usuario> resultado = usuarioController.obtenerTodosLosUsuarios();

        assertTrue(resultado.isEmpty());
    }
}
