package casosdeuso;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CategoryDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
public class GetUserTest {
    private IUserService usuarioService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        usuarioService = mock(IUserService.class);
        modelMapper = mock(ModelMapper.class);
    }


    @Test
    void getClientTest() {
        CategoryDTO customerDTO = createCustomer("cliente");
        //El sistema muestra todos los usuarios

        when(usuarioService.getAllUsers()).thenReturn(List.of(customerDTO));
        when(usuarioService.getUserByNickname("cliente")).thenReturn(customerDTO);


        System.out.println(usuarioService.getAllUsers());

        UserDTO selectedUserDTO = usuarioService.getUserByNickname("cliente");


        System.out.println("User seleccionado: " + selectedUserDTO);

        assertNotNull(selectedUserDTO);
        assertEquals("cliente", selectedUserDTO.getNickname());

        //pa que se rompa porque tengo que hacer lops paquetes t-t
        System.out.println(selectedUserDTO instanceof AirlineDTO);

    }

    CategoryDTO createCustomer(String nick) {
        CategoryDTO customerDTO = new CategoryDTO();
        customerDTO.setNickname(nick);
        customerDTO.setName("Juan");
        customerDTO.setSurname("Pérez");
        customerDTO.setMail("juan@example.com");
        customerDTO.setId("123");
        customerDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        return customerDTO;
    }

}
