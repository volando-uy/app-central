package domain.services.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de UsuarioService")
class UsuarioServiceTest {

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService();
    }

    ClienteDTO crearClienteDTO(String nick) {
        ClienteDTO c = new ClienteDTO();
        c.setNickname(nick);
        c.setNombre("TEST_CLIENTE");
        c.setApellido("TEST_APELLIDO");
        c.setMail("TEST@TEST.TEST");
        c.setNumDocumento("11111111");
        c.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        c.setTipoDocumento(EnumTipoDocumento.CI);
        return c;
    }

    AerolineaDTO crearAerolineaDTO(String nick) {
        AerolineaDTO a = new AerolineaDTO();
        a.setNickname(nick);
        a.setNombre("TEST_AEROLINEA");
        a.setMail("TEST@TEST.TEST");
        a.setDescripcion("TEST_DESCRIPCION");
        a.setWeb("www.TEST_WEB.com");
        return a;
    }

    @Nested
    @DisplayName("altaCliente()")
    class AltaCliente {

        @Test
        @DisplayName("Debe agregar un nuevo cliente")
        void agregaClienteValido() {
            ClienteDTO cliente = crearClienteDTO("gyabisito");
            usuarioService.altaCliente(cliente);

            List<UsuarioDTO> usuariosDTO = usuarioService.obtenerTodosLosUsuarios();
            assertEquals(1, usuariosDTO.size());
            assertEquals("gyabisito", usuariosDTO.get(0).getNickname());
        }

        @Test
        @DisplayName("No debe permitir nickname duplicado (case-insensitive)")
        void nicknameDuplicado_lanzaExcepcion() {
            ClienteDTO primerClienteDTO = crearClienteDTO("Gyabisito");
            ClienteDTO segundoClienteDTO = crearClienteDTO("gyabisito");

            usuarioService.altaCliente(primerClienteDTO);
            UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () ->
                    usuarioService.altaCliente(segundoClienteDTO)
            );
            assertTrue(ex.getMessage().contains("gyabisito"));
        }
    }

    @Nested
    @DisplayName("altaAerolinea()")
    class AltaAerolinea {

        @Test
        @DisplayName("Debe agregar una aerolínea correctamente")
        void agregaAerolinea() {
            AerolineaDTO aerolinea = crearAerolineaDTO("skyline");
            usuarioService.altaAerolinea(aerolinea);

            List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertEquals(1, usuarios.size());
            assertEquals("skyline", usuarios.get(0).getNickname());
        }
    }

    @Nested
    @DisplayName("obtenerTodosLosUsuarios()")
    class ObtenerTodos {

        @Test
        @DisplayName("Debe retornar lista vacía al inicio")
        void listaVaciaInicialmente() {
            List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertTrue(usuarios.isEmpty());
        }

        @Test
        @DisplayName("Debe retornar todos los usuarios agregados")
        void retornaUsuariosCreados() {
            usuarioService.altaCliente(crearClienteDTO("uno"));
            usuarioService.altaAerolinea(crearAerolineaDTO("dos"));

            List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertEquals(2, usuarios.size());
        }
    }
}
