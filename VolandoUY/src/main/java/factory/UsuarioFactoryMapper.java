package factory;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aerolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import org.modelmapper.ModelMapper;

public class UsuarioFactoryMapper {

    private final ModelMapper modelMapper;

    public UsuarioFactoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Usuario desdeDTO(UsuarioDTO dto) {
        if (dto instanceof ClienteDTO) {
            return modelMapper.map(dto, Cliente.class);
        } else if (dto instanceof AerolineaDTO) {
            return modelMapper.map(dto, Aerolinea.class);
        }
        throw new IllegalArgumentException("Tipo de usuario no soportado: " + dto.getClass());
    }
}
