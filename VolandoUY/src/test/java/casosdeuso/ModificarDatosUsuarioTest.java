package casosdeuso;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aereolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UsuarioMapper;
import domain.services.user.IUsuarioService;
import domain.services.user.UsuarioService;
import factory.ControllerFactory;
import factory.UsuarioFactoryMapper;
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
    private IUsuarioService usuarioService;
    private ModelMapper modelMapper;
    private UsuarioMapper usuarioMapper;
    private IUsuarioController usuarioController;


    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService(); // o podés mockearlo si querés
        modelMapper = new ModelMapper();
        usuarioMapper = new UsuarioMapper(modelMapper);
        usuarioController = ControllerFactory.crearUsuarioController();

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNickname("Nickname");
        clienteDTO.setMail("mail@gmail.com");
        clienteDTO.setNombre("Cliente");
        clienteDTO.setApellido("Apellido");
        clienteDTO.setNacionalidad("Nacionalidad");
        clienteDTO.setNumDocumento("55906938");
        clienteDTO.setTipoDocumento(EnumTipoDocumento.CI);
        clienteDTO.setFechaNacimiento(LocalDate.now());
        usuarioController.altaCliente(clienteDTO);
    }

    @Test
    public void modificarDatosCliente() {
        // Paso 1: Listar usuarios (ya hay uno cargado en @BeforeEach)
        List<String> nicknames = usuarioController.obtenerTodosLosNicknames();
        assertFalse(nicknames.isEmpty());

        // Paso 2: Seleccionar usuario original
        String nickname = nicknames.get(0);
        UsuarioDTO usuarioOriginalDTO = usuarioController.obtenerUsuarioPorNickname(nickname);
        assertEquals("Nickname", usuarioOriginalDTO.getNickname());
        System.out.println("Original: " + usuarioOriginalDTO);

        // Paso 3: Crear versión temporal modificada
        ClienteDTO modificado = new ClienteDTO(); // O usar un UsuarioDTO si estás usando uno genérico
        modificado.setNickname(usuarioOriginalDTO.getNickname()); // campo que no cambia
        modificado.setMail(usuarioOriginalDTO.getMail());         // campo que no cambia
        modificado.setNombre("NuevoNombre");
        modificado.setApellido("NuevoApellido");
        modificado.setNacionalidad("Uruguaya");
        modificado.setNumDocumento("987654321");
        modificado.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        modificado.setTipoDocumento(EnumTipoDocumento.RUT);

        UsuarioDTO temporal = usuarioController.modificarDatosUsuarioTemporal(modificado);
        System.out.println("No Temporal: " + usuarioOriginalDTO);
        System.out.println("Temporal: " + temporal);

        // Paso 4: Confirmar edición
        usuarioController.modificarDatosUsuario(nickname, temporal);

        // Paso 5: Volver a obtener y verificar cambios
        ClienteDTO finalDTO = (ClienteDTO) usuarioController.obtenerUsuarioPorNickname(nickname);
        System.out.println("Final: " + finalDTO);

        assertEquals("NuevoNombre", finalDTO.getNombre());
        assertEquals("NuevoApellido", finalDTO.getApellido());
        assertEquals("Uruguaya", finalDTO.getNacionalidad());
        assertEquals("987654321", finalDTO.getNumDocumento());
        assertEquals(EnumTipoDocumento.RUT, finalDTO.getTipoDocumento());

        // Verificar campos inmutables
        assertEquals("Nickname", finalDTO.getNickname());
        assertEquals("mail@gmail.com", finalDTO.getMail());
    }

    @Test
    public void modificarDatosAereolinea() {
        // Paso 1: Crear una aerolínea original y darla de alta
        AereolineaDTO original = new AereolineaDTO();
        original.setNickname("FlyTest");
        original.setMail("a@gmail.com");
        original.setNombre("NombreOriginal");
        original.setWeb("webOriginal.com");
        original.setDescripcion("Descripcion original");

        usuarioController.altaAereolinea(original);

        // Paso 2: Obtener el usuario original
        List<String> nicknames = usuarioController.obtenerTodosLosNicknames();
        assertFalse(nicknames.isEmpty());

        String nickname = nicknames.get(1);
        UsuarioDTO usuarioOriginalDTO = usuarioController.obtenerUsuarioPorNickname(nickname);
        assertEquals("FlyTest", usuarioOriginalDTO.getNickname());
        System.out.println("Original: " + usuarioOriginalDTO);

        // Paso 3: Crear versión temporal modificada
        AereolineaDTO modificada = new AereolineaDTO();
        modificada.setNickname(usuarioOriginalDTO.getNickname()); // campo inmutable
        modificada.setMail(usuarioOriginalDTO.getMail());         // campo inmutable
        modificada.setNombre("NombreTEMP");
        modificada.setWeb("webTEMP.com");
        modificada.setDescripcion("DescripcionTEMP");

        UsuarioDTO temporal = usuarioController.modificarDatosUsuarioTemporal(modificada);
        System.out.println("No Temporal: " + original);
        System.out.println("Temporal: " + temporal);

        // Paso 4: Confirmar edición
        usuarioController.modificarDatosUsuario(nickname, temporal);

        // Paso 5: Verificar cambios
        AereolineaDTO finalDTO = (AereolineaDTO) usuarioController.obtenerUsuarioPorNickname(nickname);
        System.out.println("Final: " + finalDTO);

        assertEquals("NombreTEMP", finalDTO.getNombre());
        assertEquals("webTEMP.com", finalDTO.getWeb());
        assertEquals("DescripcionTEMP", finalDTO.getDescripcion());

        // Verificar campos inmutables
        assertEquals("FlyTest", finalDTO.getNickname());
        assertEquals("a@gmail.com", finalDTO.getMail());
    }


}
