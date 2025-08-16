package domain.services.user;

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

    Cliente crearCliente(String nick) {
        Cliente c = new Cliente();
        c.setNickname(nick);
        c.setNombre("Juan");
        c.setApellido("Pérez");
        c.setMail("juan@example.com");
        c.setNumDocumento("123");
        c.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        c.setTipoDocumento(EnumTipoDocumento.CI);
        return c;
    }

    Aerolinea crearAerolinea(String nick) {
        Aerolinea a = new Aerolinea();
        a.setNickname(nick);
        a.setNombre("Air Gyabisito");
        a.setMail("aerolinea@gyabisito.com");
        a.setDescripcion("9999");
        a.setWeb("www.google.com");
        return a;
    }

    @Nested
    @DisplayName("altaCliente()")
    class AltaCliente {

        @Test
        @DisplayName("Debe agregar un nuevo cliente")
        void agregaClienteValido() {
            Cliente cliente = crearCliente("gyabisito");
            usuarioService.altaCliente(cliente);

            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertEquals(1, usuarios.size());
            assertEquals("gyabisito", usuarios.get(0).getNickname());
        }

        @Test
        @DisplayName("No debe permitir nickname duplicado (case-insensitive)")
        void nicknameDuplicado_lanzaExcepcion() {
            Cliente cliente1 = crearCliente("Gyabisito");
            Cliente cliente2 = crearCliente("gyabisito");

            usuarioService.altaCliente(cliente1);
            UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () ->
                    usuarioService.altaCliente(cliente2)
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
            Aerolinea aerolinea = crearAerolinea("skyline");
            usuarioService.altaAerolinea(aerolinea);

            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
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
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertTrue(usuarios.isEmpty());
        }

        @Test
        @DisplayName("Debe retornar todos los usuarios agregados")
        void retornaUsuariosCreados() {
            usuarioService.altaCliente(crearCliente("uno"));
            usuarioService.altaAerolinea(crearAerolinea("dos"));

            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            assertEquals(2, usuarios.size());
        }
    }
}
