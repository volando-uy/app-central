package domain.models.user.mapper;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
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
            return modelMapper.map(usuario, AerolineaDTO.class);
        }
        throw new IllegalArgumentException("Tipo de usuario desconocido: " + usuario.getClass());
    }
}
