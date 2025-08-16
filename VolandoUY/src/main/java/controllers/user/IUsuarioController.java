package controllers.user;

import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Usuario;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUsuarioController {
    void altaCliente(ClienteDTO dto);

    void altaAereolinea(AereolineaDTO dto);

    List<Usuario> obtenerTodosLosUsuarios();

    List<String> obtenerTodosLosNicknames();
}