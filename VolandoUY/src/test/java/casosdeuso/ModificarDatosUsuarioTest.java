package casosdeuso;
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
    public void modificarDatosAerolinea() {
        Aerolinea aerolinea = crearAerolinea("test");

        //Given
        Aerolinea aerolineaTemp = new Aerolinea();
        aerolineaTemp.setNickname(aerolinea.getNickname());
        aerolineaTemp.setMail(aerolinea.getMail());


        //When
        when(modelMapper.map(aerolineaTemp, Aerolinea.class)).thenReturn(aerolineaTemp);
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(List.of(aerolinea));
        when(usuarioService.obtenerUsuarioPorNickname(aerolinea.getNickname())).thenReturn(aerolinea);

        //Then
        // Ya es seguro modificar AerolineTemp
        aerolineaTemp.setNombre("NombreTEMP");
        aerolineaTemp.setWeb("webTEMP");
        aerolineaTemp.setDescripcion("descripcionTEMP");

        System.out.println("Aerolinea " + aerolinea);
        System.out.println("AerolineaTemp " + aerolineaTemp);
        //Supongamos que desea confirmar
        aerolinea.actualizarDatosDesde(aerolineaTemp);
        assertEquals(aerolineaTemp, aerolinea);
        aerolineaTemp=null;
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
        a.setNombre("Juan");
        a.setWeb("www.google.com");
        a.setDescripcion("desc");
        a.setMail("a@gmail.com");
        return a;
    }
}
