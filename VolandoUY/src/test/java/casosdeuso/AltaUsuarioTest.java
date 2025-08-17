package casosdeuso;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UsuarioMapper;
import domain.services.user.IUsuarioService;
import factory.ControllerFactory;
import factory.UsuarioFactoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AltaUsuarioTest {

    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private IUsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);
        usuarioController = new UsuarioController(
                usuarioService,
                modelMapper,
                new UsuarioMapper(modelMapper),
                new UsuarioFactoryMapper(modelMapper)
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
        clienteDTO.setNumDocumento("01234567");
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

}
