package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import domain.services.city.ICityService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.airport.AirportRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;


public class AirportService implements IAirportService {
    private AirportRepository airportRepository;
    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    @Setter
    private ICityService cityService;

    public AirportService() {
        this.cityService= ServiceFactory.getCityService();
        this.airportRepository = new AirportRepository();
    }

    public AirportDTO createAirport(AirportDTO dto, String cityName) {
        // Buscamos la ciudad a la que se agrega el aeropuerto
        // Tira throw si no encuentra
        City city = cityService.getCityByName(cityName);

        // Checkeamos que no exista un aeropuerto con el mismo código
        if (airportExists(dto.getCode())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_CODE_ALREADY_EXISTS, dto.getCode()));
        }

        // Creamos la nueva entidad aeropuerto y le agregamos la ciudad
        Airport airport = customModelMapper.map(dto, Airport.class);
        airport.setCity(city);

        // Valudamos el aeropuerto
        ValidatorUtil.validate(airport);

        // Si es válido, guardamos el aeropuerto y lo agregamos a la ciudad
        airportRepository.save(airport);
        city.getAirports().add(airport);

        // Retornamos el DTO del aeropuerto creado
        return customModelMapper.mapAirport(airport);
    }

    @Override
    public Airport getAirportByCode(String code) {
        Airport airport = airportRepository.getAirportByCode(code);
        if(airport == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_NOT_FOUND, code));
        }
        return airport;
    }

    @Override
    public AirportDTO getAirportDetailsByCode(String code) {
        return customModelMapper.mapAirport(this.getAirportByCode(code));
    }

    @Override
    public boolean airportExists(String code) {
        return airportRepository.existsAirportByCode(code);
    }
}
