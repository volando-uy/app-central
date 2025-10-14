package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import domain.services.city.ICityService;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import factory.ServiceFactory;
import infra.repository.airport.AirportRepository;
import infra.repository.airport.IAirportRepository;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.List;


public class AirportService implements IAirportService {
    private final IAirportRepository airportRepository;

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    @Setter
    private ICityService cityService;

    public AirportService() {
        this.airportRepository = RepositoryFactory.getAirportRepository();
    }

    @Override
    public BaseAirportDTO createAirport(BaseAirportDTO dto, String cityName) {
        // Buscamos la ciudad a la que se agrega el aeropuerto
        City city = cityService.getCityByName(cityName);
        if (city == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName));
        }

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
        airportRepository.saveAirportAndAddToCity(airport, city);

        // Retornamos el DTO del aeropuerto creado
        return customModelMapper.map(airport, BaseAirportDTO.class);
    }

    @Override
    public Airport getAirportByCode(String code, boolean full) {
        Airport airport = full ? airportRepository.getFullAirportByCode(code) : airportRepository.getAirportByCode(code);
        if(airport == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_NOT_FOUND, code));
        }
        return airport;
    }

    @Override
    public AirportDTO getAirportDetailsByCode(String code, boolean full) {
        // Check if the airport exists
        // Throws an exception if it doesn't
        Airport airport = getAirportByCode(code, full);

        return full ? customModelMapper.mapFullAirport(airport) : customModelMapper.map(airport, AirportDTO.class);
    }

    @Override
    public boolean airportExists(String code) {
        return airportRepository.existsAirportByCode(code);
    }

    @Override
    public List<AirportDTO> getAllAirportsDetails(boolean full) {
        List<Airport> airports = full ? airportRepository.getAllFullAirports() : airportRepository.getAllAirports();

        return airports.stream()
                .map(air -> full ? customModelMapper.mapFullAirport(air) : customModelMapper.map(air, AirportDTO.class))
                .toList();
    }
}
