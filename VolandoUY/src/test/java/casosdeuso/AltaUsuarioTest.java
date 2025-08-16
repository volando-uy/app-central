package casosdeuso;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Aereolinea;
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
        ClienteDTO dto = new ClienteDTO();
        dto.setNickname("gyabisito");
        dto.setNombre("Jose");
        dto.setApellido("Ramirez"); // <- obligatorio
        dto.setMail("gyabisito@example.com");
        dto.setFechaNacimiento(LocalDate.of(2000, 1, 1));
        dto.setTipoDocumento(EnumTipoDocumento.CI);
        dto.setNumDocumento("01234567");
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

}
