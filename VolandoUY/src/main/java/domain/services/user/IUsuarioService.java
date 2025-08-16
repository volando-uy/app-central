package domain.services.user;

import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aereolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;

import java.util.List;

//Aca no entran los DTO, porque se trabaja directamente con el dominio
public interface IUsuarioService {
    void altaCliente(Cliente cliente);
    void altaAereolinea(Aereolinea aereolinea);
    List<Usuario> obtenerTodosLosUsuarios();
    List<String> obtenerTodosLosNicknames();
    Usuario obtenerUsuarioPorNickname(String nickname);
    Usuario modificarDatosUsuarioTemporal(Usuario usuario);

}
