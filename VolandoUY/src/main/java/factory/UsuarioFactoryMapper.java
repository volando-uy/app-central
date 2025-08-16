package factory;

import domain.dtos.user.AereolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.dtos.user.UsuarioDTO;
import domain.models.user.Aereolinea;
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
        } else if (dto instanceof AereolineaDTO) {
            return modelMapper.map(dto, Aereolinea.class);
        }
        throw new IllegalArgumentException("Tipo de usuario no soportado: " + dto.getClass());
    }
}
