package casosdeuso;
import domain.models.user.Aereolinea;
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

public class ModificarDatosUsuarioTest {
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
    public void modificarDatosCliente() {
        Cliente cliente = crearCliente("test");

        //Given
        Cliente clienteTemp = new Cliente();
        clienteTemp.setNickname(cliente.getNickname());
        clienteTemp.setMail(cliente.getMail());


        //When
        when(modelMapper.map(cliente, Cliente.class)).thenReturn(clienteTemp);
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(cliente));
        when(usuarioService.obtenerUsuarioPorNickname(cliente.getNickname())).thenReturn(cliente);

        //Then
        // Ya es seguro modificar clienteTemp
        clienteTemp.setNacionalidad("NacTEMP");
        clienteTemp.setNumDocumento("DocTEMP");
        clienteTemp.setApellido("ApeTEMP");
        clienteTemp.setNombre("NombreTEMP");
        clienteTemp.setFechaNacimiento(LocalDate.now());
        clienteTemp.setTipoDocumento(EnumTipoDocumento.RUT);

        System.out.println("Cliente " + cliente);
        System.out.println("ClienteTemp " + clienteTemp);
        //Supongamos que desea confirmar
        cliente.actualizarDatosDesde(clienteTemp);
        assertEquals(clienteTemp, cliente);
        clienteTemp=null;
    }
    @Test
    public void modificarDatosAereolinea() {
        Aereolinea aereolinea = crearAereolinea("test");

        //Given
        Aereolinea aereolineaTemp = new Aereolinea();
        aereolineaTemp.setNickname(aereolinea.getNickname());
        aereolineaTemp.setMail(aereolinea.getMail());


        //When
        when(modelMapper.map(aereolineaTemp, Aereolinea.class)).thenReturn(aereolineaTemp);
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(aereolinea));
        when(usuarioService.obtenerUsuarioPorNickname(aereolinea.getNickname())).thenReturn(aereolinea);

        //Then
        // Ya es seguro modificar AereolineTemp
        aereolineaTemp.setNombre("NombreTEMP");
        aereolineaTemp.setWeb("webTEMP");
        aereolineaTemp.setDescripcion("descripcionTEMP");

        System.out.println("Aereolinea " + aereolinea);
        System.out.println("AereolineaTemp " + aereolineaTemp);
        //Supongamos que desea confirmar
        aereolinea.actualizarDatosDesde(aereolineaTemp);
        assertEquals(aereolineaTemp, aereolinea);
        aereolineaTemp=null;
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
    Aereolinea crearAereolinea(String nick) {
        Aereolinea a = new Aereolinea();
        a.setNickname(nick);
        a.setNombre("Juan");
        a.setWeb("www.google.com");
        a.setDescripcion("desc");
        a.setMail("a@gmail.com");
        return a;
    }
}
