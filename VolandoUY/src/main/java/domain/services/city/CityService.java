package domain.services.city;

import domain.dtos.city.CityDTO;
import domain.models.city.City;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;

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
        cities.add(city);

        if (city.getAirports() == null) {
            city.setAirports(List.of());
        }

        return cityDTO;
    }

    @Override
    public void updateCity(CityDTO cityDTO) {
        City city = modelMapper.map(cityDTO, City.class);
        if (!cityExists(city.getName())) {
            throw new IllegalArgumentException("City does not exist");
        }
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(city.getName())) {
                cities.set(i, city);
                return;
            }
        }
        throw new IllegalArgumentException("City not found in the list");
    }

    @Override
    public void deleteCity(String cityName) {
        if (!cityExists(cityName)) {
            throw new IllegalArgumentException("City does not exist");
        }
        cities.removeIf(city -> city.getName().equalsIgnoreCase(cityName));


    }

    @Override
    public CityDTO getCity(String cityName) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                return modelMapper.map(city, CityDTO.class);
            }
        }
        throw new IllegalArgumentException("City not found");
    }

    @Override
    public boolean cityExists(String cityName) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAirportToCity(String cityName, String airportName, String airportCode) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                if (city.isAirportInCity(airportName)) {
                    throw new IllegalArgumentException("Airport already exists in this city");
                }
                city.addAirport(airportName, airportCode);
                return;
            }
        }
        throw new IllegalArgumentException("City does not exist");
    }

    @Override
    public void removeAirportFromCity(String cityName, String airportName) {
        City city = getCityEntity(cityName);

        if (!city.isAirportInCity(airportName)) {
            throw new IllegalArgumentException("Airport does not exist in this city");
        }

        // Eliminar aeropuerto
        city.getAirports().removeIf(airport -> airport.getName().equalsIgnoreCase(airportName));
    }


    @Override
    public void updateAirportInCity(String cityName, String airportName, String newAirportName, String newAirportCode) {
        CityDTO cityDTO = getCity(cityName);
        City city = modelMapper.map(cityDTO, City.class);
        if (city == null) {
            throw new IllegalArgumentException("City does not exist");
        }
        if (!city.isAirportInCity(airportName)) {
            throw new IllegalArgumentException("Airport does not exist in this city");
        }
        for (int i = 0; i < city.getAirports().size(); i++) {
            if (city.getAirports().get(i).getName().equalsIgnoreCase(airportName)) {
                city.getAirports().get(i).setName(newAirportName);
                city.getAirports().get(i).setCode(newAirportCode);
                return;
            }
        }
        throw new IllegalArgumentException("Airport not found in the city");
    }

    @Override
    public CityDTO getCityWithAirports(String cityName) {
        City city = getCityEntity(cityName);
        return modelMapper.map(city, CityDTO.class);
    }


    @Override
    public boolean isAirportInCity(String cityName, String airportName) {
        City city = getCityEntity(cityName);

        return city.isAirportInCity(airportName);
    }

    private City getCityEntity(String cityName) {
        return cities.stream()
                .filter(c -> c.getName().equalsIgnoreCase(cityName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("City does not exist"));
    }

}
