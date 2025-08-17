package domain.services.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;

import java.util.List;

//Aca no entran los DTO, porque se trabaja directamente con el dominio
public interface IUsuarioService {
    ClienteDTO altaCliente(ClienteDTO clienteDTO);
    AerolineaDTO altaAerolinea(AerolineaDTO aerolineaDTO);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    List<String> obtenerTodosLosNicknames();
    UsuarioDTO obtenerUsuarioPorNickname(String nickname);
    UsuarioDTO modificarDatosUsuarioTemporal(UsuarioDTO usuarioDTO);
    void modificarDatosUsuario(String nickname, UsuarioDTO usuarioDTO);

}
