package domain.models.user.mapper;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
    }

    public User fromDTO(UserDTO updatedUserDTO) {
        if (updatedUserDTO instanceof CustomerDTO customerDTO) {
            return modelMapper.map(customerDTO, Customer.class);
        } else if (updatedUserDTO instanceof AirlineDTO airlineDTO) {
            return modelMapper.map(airlineDTO, Airline.class);
        }
        throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
    }
    public AirlineDTO toAirlineDTO(Airline airline) {
        AirlineDTO dto = new AirlineDTO();
        dto.setNickname(airline.getNickname());
        dto.setName(airline.getName());
        dto.setDescription(airline.getDescription());
        dto.setWeb(airline.getWeb());
        dto.setMail(airline.getMail());

        return dto;
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setNickname(customer.getNickname());
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());
        dto.setMail(customer.getMail());
        dto.setDocType(customer.getDocType());
        dto.setNumDoc(customer.getNumDoc());
        dto.setBirthDate(customer.getBirthDate());
        dto.setCitizenship(customer.getCitizenship());

        return dto;
    }
}
