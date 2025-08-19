package domain.models.user.mapper;

import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDTO(User user) {
        if (user instanceof Customer) {
            return modelMapper.map(user, CustomerDTO.class);
        } else if (user instanceof Airline) {
            return modelMapper.map(user, AirlineDTO.class);
        }
        throw new IllegalArgumentException("Tipo de user desconocido: " + user.getClass());
    }
}
