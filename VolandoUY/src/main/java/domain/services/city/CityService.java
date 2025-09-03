package domain.services.city;

import domain.dtos.city.CityDTO;

import domain.models.city.City;
import factory.ControllerFactory;
import infra.repository.city.CityRepository;
import infra.repository.city.ICityRepository;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CityService implements ICityService {

    private CityRepository cityRepository;
    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public CityService() {
        this.cityRepository = new CityRepository();
    }

    @Override
    public CityDTO createCity(CityDTO cityDTO) {
        // Cheackeamos que la ciudad no exista
        City city = customModelMapper.map(cityDTO, City.class);
        if (cityExists(city.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_ALREADY_EXISTS, city.getName()));
        }
        // Inicializamos la lista de aeropuertos
        city.setAirports(new ArrayList<>());

        // Validamos la ciudad
        ValidatorUtil.validate(city);

        // Guardamos la nueva ciudad
        cityRepository.save(city);

        // Devolvemos el DTO mapeado
        return customModelMapper.mapCity(city);
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.getCityByName(cityName);
    }

    @Override
    public CityDTO getCityDetailsByName(String cityName) {
        // Cheackemos que la ciudad exista
        City city = cityRepository.getCityByName(cityName);
        if (city == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName));
        }

        // Devolvemos el DTO mapeado
        return customModelMapper.mapCity(city);
    }


    @Override
    public boolean cityExists(String cityName) {
        return cityRepository.existsByName(cityName);
    }


    @Override
    public boolean isAirportInCity(String cityName, String airportName) {
        return cityRepository.existsAirportInCity(cityName, airportName);
    }


    @Override
    public List<String> getAllCities() {
        return cityRepository.findAll().stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }
}
