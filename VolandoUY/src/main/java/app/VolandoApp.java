package app;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import factory.ControllerFactory;

import java.time.LocalDate;
import java.util.List;

public class VolandoApp {

    public static void main(String[] args) {

        System.out.println("Punto de inicio");
        LocalDate fechaNacimiento = LocalDate.now();

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Jose");
        clienteDTO.setApellido("Gabriel");
        clienteDTO.setFechaNacimiento(fechaNacimiento);
        clienteDTO.setNacionalidad("Uruguay");
        clienteDTO.setMail("josecitohernandez5@gmail.com");
        clienteDTO.setTipoDocumento(EnumTipoDocumento.CI);
        clienteDTO.setNumDocumento("55906938");
        clienteDTO.setNickname("gyabisito");

        IUsuarioController usuarioController = ControllerFactory.getUsuarioController();
        usuarioController.altaCliente(clienteDTO);

        AerolineaDTO aerolineaDTO = new AerolineaDTO();
        aerolineaDTO.setNombre("Nombre");
        aerolineaDTO.setNickname("gyabisito");
        aerolineaDTO.setMail("mail@a.com");
        aerolineaDTO.setDescripcion("Descrip");
        aerolineaDTO.setWeb("Web");

        usuarioController.altaAerolinea(aerolineaDTO);
        usuarioController.obtenerTodosLosUsuarios().stream().forEach(usuario -> System.out.println(usuario.getNickname()));
    }
}
