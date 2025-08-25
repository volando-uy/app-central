package factory;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

public class UserFactoryMapper {

    private final ModelMapper modelMapper;

    public UserFactoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User desdeDTO(UserDTO dto) {
        if (dto instanceof CustomerDTO) {
            return modelMapper.map(dto, Customer.class);
        } else if (dto instanceof AirlineDTO) {
            return modelMapper.map(dto, Airline.class);
        }
        throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
    }
}
