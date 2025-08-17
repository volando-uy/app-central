package domain.services.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import org.modelmapper.ModelMapper;
import domain.models.user.mapper.UsuarioMapper;
import shared.constants.ErrorMessages;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioService implements IUsuarioService {
    //Supongamos que tenemos el UserRepository
    private LinkedList<Usuario> usuarios = new LinkedList<>();
    private Map<String, Usuario> usuariosTemporales = new HashMap<>();
    private ModelMapper modelMapper = new ModelMapper();
    private UsuarioMapper usuarioMapper = new UsuarioMapper(modelMapper);



    // Les puse _ a las funciones internas, que luego van a ser parte del repository.
    private Usuario _obtenerUsuarioPorNickname(String nickname){
        return this.usuarios.stream()
                .filter(u -> u.getNickname().equalsIgnoreCase(nickname))
                .findFirst()
                .orElse(null);
    }

    // Funcion para saber si existe un usuario con el mismo nickname
    private boolean existeUsuario(Usuario usuario) {
        // TODO: usar acá el userRepository
        return usuarios.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(usuario.getNickname()));
    }


    @Override
    public ClienteDTO altaCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        if (existeUsuario(cliente)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USUARIO_YA_EXISTE, cliente.getNickname()));
        }
        usuarios.add(cliente);
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    public AerolineaDTO altaAerolinea(AerolineaDTO aerolineaDTO) {
        Aerolinea aerolinea = modelMapper.map(aerolineaDTO, Aerolinea.class);
        if (existeUsuario(aerolinea)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_USUARIO_YA_EXISTE, aerolinea.getNickname()));
        }
        usuarios.add(aerolinea);
        return modelMapper.map(aerolinea, AerolineaDTO.class);
    }

    @Override
    public List<String> obtenerTodosLosNicknames() {
        // TODO: Pasar esta responsabilidad al userRepository
        return usuarios.stream().map(Usuario::getNickname).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        // TODO: Pasar esta responsabilidad al userRepository
        List<UsuarioDTO> usuariosDTO = this.usuarios.stream()
                .map(usuario -> usuarioMapper.toDTO(usuario))
                .collect(Collectors.toList());

        // Antes de devolver, asegurarse de agregar atributos Objetos al DTO en caso de que tenga
        return usuariosDTO;
    }


    public UsuarioDTO obtenerUsuarioPorNickname(String nickname) {
        Usuario usuario = _obtenerUsuarioPorNickname(nickname);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + nickname);
        }
        return modelMapper.map(usuario, UsuarioDTO.class);
    }



    @Override
    public UsuarioDTO modificarDatosUsuarioTemporal(UsuarioDTO nuevosDatos) {
        // TODO: Pasarle esta responsabilidad al userRepository
        // Comprobar que estamos modificando un usuario existente
        Usuario usuarioOriginal = _obtenerUsuarioPorNickname(nuevosDatos.getNickname());
        if (usuarioOriginal == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + nuevosDatos.getNickname());
        }

        // Si no es la primera modificación ya existirá un usuario temporal
        Usuario usuarioTemporal = usuariosTemporales.get(nuevosDatos.getNickname());
        // En cambio, si es la primera vez, creamos uno nuevo
        if (usuarioTemporal == null) {
            usuarioTemporal = modelMapper.map(nuevosDatos, Usuario.class);
        } else {
            // Si ya existe, lo actualizamos con los nuevos datos
            usuarioTemporal.actualizarDatosDesde(nuevosDatos);
        }

        // Guardamos/Actualizamos el usuario temporal en el mapa
        usuariosTemporales.put(nuevosDatos.getNickname(), usuarioTemporal);

        // Retornamos el DTO del usuario temporal actualizado
        return modelMapper.map(usuarioTemporal, UsuarioDTO.class);
    }

    @Override
    public void modificarDatosUsuario(String nickname, UsuarioDTO usuarioTempDTO) {
        Usuario usuarioOriginal = _obtenerUsuarioPorNickname(nickname);
        if (usuarioOriginal == null) return;

        usuarioOriginal.actualizarDatosDesde(usuarioTempDTO);

        usuariosTemporales.remove(nickname);
    }





}
