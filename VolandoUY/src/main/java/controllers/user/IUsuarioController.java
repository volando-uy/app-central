package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Usuario;

import java.util.List;

//Aca al no trabajarse directamente en el dominio, para mayor abstraccion se usan los DTO
public interface IUsuarioController {
    void altaCliente(ClienteDTO dto);

    void altaAerolinea(AerolineaDTO dto);

    List<Usuario> obtenerTodosLosUsuarios();

    UsuarioDTO obtenerUsuarioPorNickname(String nickname);


    List<String> obtenerTodosLosNicknames();

    UsuarioDTO modificarDatosUsuarioTemporal(UsuarioDTO usuario);
    void modificarDatosUsuario(String nickname, UsuarioDTO usuario);
}