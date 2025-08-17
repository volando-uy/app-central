package casosdeuso;

import controllers.user.UsuarioController;
import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
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
    private IUsuarioService usuarioService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);
    }


    @Test
    void consultClienteTest() {

        ClienteDTO clienteDTO = crearCliente("cliente");
        //El sistema muestra todos los usuarios

        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(clienteDTO));
        when(usuarioService.obtenerUsuarioPorNickname("cliente")).thenReturn(clienteDTO);


        System.out.println(usuarioService.obtenerTodosLosUsuarios());

        UsuarioDTO usuarioSeleccionadoDTO = usuarioService.obtenerUsuarioPorNickname("cliente");


        System.out.println("Usuario seleccionado: " + usuarioSeleccionadoDTO);

        assertNotNull(usuarioSeleccionadoDTO);
        assertEquals("cliente", usuarioSeleccionadoDTO.getNickname());

        //pa que se rompa porque tengo que hacer lops paquetes t-t
        System.out.println(usuarioSeleccionadoDTO instanceof AerolineaDTO);

    }

    ClienteDTO crearCliente(String nick) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNickname(nick);
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Pérez");
        clienteDTO.setMail("juan@example.com");
        clienteDTO.setNumDocumento("123");
        clienteDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        clienteDTO.setTipoDocumento(EnumTipoDocumento.CI);
        return clienteDTO;
    }

}
