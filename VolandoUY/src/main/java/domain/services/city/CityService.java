package domain.services.city;

import domain.dtos.city.CityDTO;

import domain.models.city.City;
import infra.repository.city.CityRepository;
import infra.repository.city.ICityRepository;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CityService implements ICityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.cityRepository = new CityRepository();
    }

    @Override
    public CityDTO createCity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        if (cityExists(city.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_ALREADY_EXISTS, city.getName()));
        }
        ValidatorUtil.validate(city);

        if (city.getAirports() == null) {
            city.setAirports(new ArrayList<>());
        }

        cityRepository.save(city);

        return modelMapper.map(city, CityDTO.class);
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.getCityByName(cityName);
    }

    @Override
    public CityDTO getCityDetailsByName(String cityName) {
        City city = cityRepository.getCityByName(cityName);
        if (city == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName));
        }

        // Map manual de airportNames
        CityDTO dto = modelMapper.map(city, CityDTO.class);
        if (city.getAirports() != null) {
            dto.setAirportNames(
                    city.getAirports().stream()
                            .map(airport -> airport.getName())
                            .toList()
            );
        } else {
            dto.setAirportNames(List.of()); // evitar null
        }

        return dto;
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
        return cities.stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }
}
