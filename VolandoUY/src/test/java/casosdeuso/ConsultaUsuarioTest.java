package casosdeuso;

import controllers.user.UsuarioController;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
public class ConsultaUsuarioTest {
    @Mock
    private IUsuarioService usuarioService;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);


    }


    @Test
    void consultClienteTest() {

        Cliente cliente=crearCliente("cliente");
        //El sistema muestra todos los usuarios

        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(cliente));
        when(usuarioService.obtenerUsuarioPorNickname("cliente")).thenReturn(cliente);


        System.out.println(usuarioService.obtenerTodosLosUsuarios());

        Usuario usuarioSeleccionado = usuarioService.obtenerUsuarioPorNickname("cliente");


        System.out.println("Usuario seleccionado: " + usuarioSeleccionado);

        assertNotNull(usuarioSeleccionado);
        assertEquals("cliente", usuarioSeleccionado.getNickname());

        //pa que se rompa porque tengo que hacer lops paquetes t-t
        System.out.println(usuarioSeleccionado instanceof Aerolinea);

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

}
