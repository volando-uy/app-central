package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Usuario;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUsuarioController {
    void altaCliente(ClienteDTO dto);

    void altaAerolinea(AerolineaDTO dto);

    List<Usuario> obtenerTodosLosUsuarios();

    List<String> obtenerTodosLosNicknames();
}