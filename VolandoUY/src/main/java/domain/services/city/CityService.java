package domain.services.city;

import domain.dtos.city.CityDTO;
import domain.models.city.City;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;


public class CityService implements ICityService {
    private List<City> cities;
    private ModelMapper modelMapper;

    public CityService(ModelMapper modelMapper) {
        this.cities = new ArrayList<>();
        this.modelMapper = modelMapper;
    }

    @Override
    public CityDTO createCity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        if (cityExists(city.getName())) {
            throw new IllegalArgumentException("City already exists");
        }
        ValidatorUtil.validate(city);

        cities.add(city);

        if (city.getAirports() == null) {
            city.setAirports(List.of());
        }

        return modelMapper.map(city, CityDTO.class);
    }

    @Override
    public City getCityByName(String cityName) {
        return cities.stream()
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName)));
    }

    @Override
    public CityDTO getCityDetailsByName(String cityName) {
        return cities.stream()
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, cityName)));
    }

    @Override
    public boolean cityExists(String cityName) {
        return cities.stream()
                .anyMatch(city -> city.getName().equalsIgnoreCase(cityName));
    }


    @Override
    public boolean isAirportInCity(String cityName, String airportName) {
        City city = this.getCityByName(cityName);
        return city.getAirports().stream()
                .anyMatch(airport -> airport.getName().equalsIgnoreCase(airportName));
    }

}
