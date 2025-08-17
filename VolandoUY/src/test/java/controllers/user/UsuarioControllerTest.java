package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UsuarioMapper;
import domain.services.user.IUsuarioService;
import factory.ControllerFactory;
import factory.UsuarioFactoryMapper;
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
    private UsuarioMapper usuarioMapper;
    private UsuarioFactoryMapper usuarioFactoryMapper;
    private IUsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);
        usuarioMapper = mock(UsuarioMapper.class);
        usuarioFactoryMapper = mock(UsuarioFactoryMapper.class);
        usuarioController = ControllerFactory.crearUsuarioController(
                usuarioService,
                modelMapper,
                usuarioMapper,
                usuarioFactoryMapper
        );
    }

    @Test
    @DisplayName("Debe llamar a altaCliente y mapear correctamente el DTO")
    void altaCliente_deberiaLlamarAlServiceConEntidadMapeada() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNickname("gyabisito");
        clienteDTO.setNombre("Jose");
        clienteDTO.setApellido("Ramirez"); // <- obligatorio
        clienteDTO.setMail("gyabisito@example.com");
        clienteDTO.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        clienteDTO.setTipoDocumento(EnumTipoDocumento.CI);
        clienteDTO.setNumDocumento("12345678");
        clienteDTO.setNacionalidad("Uruguay");

        Cliente clienteMock = new Cliente();
        clienteMock.setNickname("gyabisito");

        when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(clienteMock);

        usuarioController.altaCliente(clienteDTO);

        verify(usuarioService).altaCliente(clienteDTO);
    }

    @Test
    @DisplayName("Debe llamar a altaAerolinea y mapear correctamente el DTO")
    void altaAerolinea_deberiaLlamarAlServiceConEntidadMapeada() {
        AerolineaDTO aerolineaDTO = new AerolineaDTO();
        aerolineaDTO.setNickname("flyuy");
        aerolineaDTO.setNombre("FlyUY");
        aerolineaDTO.setMail("flyuy@correo.com");
        aerolineaDTO.setDescripcion("Low cost desde el cielo");

        Aerolinea aerolineaMock = new Aerolinea();
        aerolineaMock.setNickname("flyuy");

        when(modelMapper.map(aerolineaDTO, Aerolinea.class)).thenReturn(aerolineaMock);

        usuarioController.altaAerolinea(aerolineaDTO);

        verify(usuarioService).altaAerolinea(aerolineaDTO);
    }


    @Test
    @DisplayName("Debe retornar todos los usuarios desde el service")
    void obtenerTodosLosUsuarios_deberiaRetornarListaDelService() {
        UsuarioDTO usuario = new ClienteDTO();
        usuario.setNickname("gyabisito");

        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(usuario));

        List<UsuarioDTO> resultado = usuarioController.obtenerTodosLosUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("gyabisito", resultado.get(0).getNickname());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si no hay usuarios")
    void obtenerTodosLosUsuarios_listaVacia() {
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(Collections.emptyList());

        List<UsuarioDTO> resultado = usuarioController.obtenerTodosLosUsuarios();

        assertTrue(resultado.isEmpty());
    }
}
