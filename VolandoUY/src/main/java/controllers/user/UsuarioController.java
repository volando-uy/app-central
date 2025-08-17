package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
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
        usuarioService.altaCliente(clienteDTO);
    }

    @Override
    public void altaAerolinea(AerolineaDTO aerolineaDTO) {
        AnnotationValidator.validateRequiredFields(aerolineaDTO);
        usuarioService.altaAerolinea(aerolineaDTO);
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @Override
    public List<String> obtenerTodosLosNicknames() {
        return usuarioService.obtenerTodosLosNicknames();
    }



    @Override
    public UsuarioDTO obtenerUsuarioPorNickname(String nickname) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorNickname(nickname);
        return usuarioDTO;
    }



    @Override
    public void modificarDatosUsuario(String nickname, UsuarioDTO usuarioDTO) {
        AnnotationValidator.validateRequiredFields(usuarioDTO);
        usuarioService.modificarDatosUsuario(nickname, usuarioDTO);
    }

    @Override
    public UsuarioDTO modificarDatosUsuarioTemporal(UsuarioDTO usuarioDTO) {
        AnnotationValidator.validateRequiredFields(usuarioDTO);
        UsuarioDTO modificado = usuarioService.modificarDatosUsuarioTemporal(usuarioDTO);
        return modificado;
    }

}
