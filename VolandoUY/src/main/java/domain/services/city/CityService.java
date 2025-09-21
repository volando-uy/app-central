package domain.services.city;

import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;

import domain.models.city.City;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import infra.repository.city.CityRepository;
import infra.repository.city.ICityRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CityService implements ICityService {

    private final ICityRepository cityRepository;
    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public CityService() {
        this.cityRepository = RepositoryFactory.getCityRepository();
    }

    @Override
    public BaseCityDTO createCity(BaseCityDTO baseCityDTO) {
        // Cheackeamos que la ciudad no exista
        City city = customModelMapper.map(baseCityDTO, City.class);
        if (cityExists(city.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_ALREADY_EXISTS, city.getName()));
        }

        // Validamos la ciudad
        ValidatorUtil.validate(city);

        // Inicializamos la lista de aeropuertos
        city.setAirports(new ArrayList<>());

        // Guardamos la nueva ciudad
        cityRepository.save(city);

        // Devolvemos el DTO mapeado
        return customModelMapper.map(city, BaseCityDTO.class);
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.getCityByName(cityName);
    }

    @Override
    public CityDTO getCityDetailsByName(String cityName, boolean full) {
        // Cheackemos que la ciudad exista
        City city = full ? cityRepository.getFullCityByName(cityName) : cityRepository.getCityByName(cityName);

        if (city == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName));
        }

        // Devolvemos el DTO mapeado
        return full ? customModelMapper.mapFullCity(city) : customModelMapper.map(city, CityDTO.class);
    }


    @Override
    public boolean cityExists(String cityName) {
        return cityRepository.existsByName(cityName);
    }


    @Override
    public boolean isAirportInCity(String cityName, String airportName) {
        if (!cityExists(cityName)) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName));
        }
        return cityRepository.existsAirportInCity(cityName, airportName);
    }



    @Override
    public List<String> getAllCities() {
        return cityRepository.findAll().stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }
}
