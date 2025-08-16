package casosdeuso;

import controllers.user.UsuarioController;
import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AltaUsuarioTest {
    private IUsuarioService usuarioService;
    private ModelMapper modelMapper;
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUsuarioService.class);
        modelMapper = mock(ModelMapper.class);
        usuarioController = new UsuarioController(usuarioService, modelMapper);
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
    @DisplayName("Debe llamar a altaAerolinea y mapear correctamente el DTO")
    void altaAerolinea_deberiaLlamarAlServiceConEntidadMapeada() {
        AerolineaDTO dto = new AerolineaDTO();
        dto.setNickname("flyuy");
        dto.setNombre("FlyUY");
        dto.setMail("flyuy@correo.com");
        dto.setDescripcion("Low cost desde el cielo");

        Aerolinea aerolineaMock = new Aerolinea();
        aerolineaMock.setNickname("flyuy");

        when(modelMapper.map(dto, Aerolinea.class)).thenReturn(aerolineaMock);

        usuarioController.altaAerolinea(dto);

        verify(modelMapper).map(dto, Aerolinea.class);
        verify(usuarioService).altaAerolinea(aerolineaMock);
    }

}
