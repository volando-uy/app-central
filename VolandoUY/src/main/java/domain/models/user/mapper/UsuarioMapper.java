package domain.models.user.mapper;

import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aereolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import org.modelmapper.ModelMapper;

public class UsuarioMapper {
    private final ModelMapper modelMapper;

    public UsuarioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario instanceof Cliente) {
            return modelMapper.map(usuario, ClienteDTO.class);
        } else if (usuario instanceof Aereolinea) {
            return modelMapper.map(usuario, AereolineaDTO.class);
        }
        throw new IllegalArgumentException("Tipo de usuario desconocido: " + usuario.getClass());
    }
}
