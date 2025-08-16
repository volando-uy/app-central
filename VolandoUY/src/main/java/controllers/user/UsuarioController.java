package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.mapper.UsuarioMapper;
import domain.services.user.IUsuarioService;
import domain.services.user.UsuarioService;
import factory.UsuarioFactoryMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import shared.utils.AnnotationValidator;
import shared.utils.ValidationUtils;

import java.util.List;

@AllArgsConstructor
public class UsuarioController  implements IUsuarioController{
    IUsuarioService usuarioService;
    ModelMapper modelMapper;
    UsuarioMapper usuarioMapper;
    UsuarioFactoryMapper usuarioFactoryMapper;

    @Override
    public void altaCliente(ClienteDTO clienteDTO) {
        AnnotationValidator.validateRequiredFields(clienteDTO);
        Cliente cliente=modelMapper.map(clienteDTO, Cliente.class);
        usuarioService.altaCliente(cliente);
    }

    @Override
    public void altaAerolinea(AerolineaDTO aerolineaDTO) {
        AnnotationValidator.validateRequiredFields(aerolineaDTO);
        Aerolinea aerolinea = modelMapper.map(aerolineaDTO, Aerolinea.class);
        usuarioService.altaAerolinea(aerolinea);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @Override
    public List<String> obtenerTodosLosNicknames() {
        return usuarioService.obtenerTodosLosNicknames();
    }



    @Override
    public UsuarioDTO obtenerUsuarioPorNickname(String nickname) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNickname(nickname);
        return usuarioMapper.toDTO(usuario);
    }



    @Override
    public void modificarDatosUsuario(String nickname, UsuarioDTO dto) {
        AnnotationValidator.validateRequiredFields(dto);
        Usuario usuario = usuarioFactoryMapper.desdeDTO(dto);
        usuarioService.modificarDatosUsuario(nickname, usuario);
    }

    @Override
    public UsuarioDTO modificarDatosUsuarioTemporal(UsuarioDTO dto) {
        AnnotationValidator.validateRequiredFields(dto);
        Usuario usuario = usuarioFactoryMapper.desdeDTO(dto);
        Usuario modificado = usuarioService.modificarDatosUsuarioTemporal(usuario);
        return usuarioMapper.toDTO(modificado);
    }

}
