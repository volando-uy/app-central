package controllers.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;

import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.services.user.IUsuarioService;
import domain.services.user.UsuarioService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import shared.utils.AnnotationValidator;
import shared.utils.ValidationUtils;

import java.util.List;

@AllArgsConstructor
public class UsuarioController  implements IUsuarioController{
    IUsuarioService usuarioService;
    ModelMapper modelMapper;

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
}
