package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class AirportService implements IAirportService {
    private List<Airport> airports;
    private ModelMapper modelMapper= ControllerFactory.getModelMapper();
    @Override
    public void addAirport(CityDTO cityDTO, String name, String code) {
        if (airportExists(code)) {
            throw new IllegalArgumentException("Airport already exists");
        }
        City city=modelMapper.map(cityDTO, City.class);
        Airport airport = new Airport(city, name, code);
        airports.add(airport);
        if (city.getAirports() == null) {
            city.setAirports(List.of(airport));
        } else {
            city.getAirports().add(airport);
        }
    }
    @Override
    public void removeAirport(String code) {
        Airport toRemove = null;

        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                City city = airport.getCity();
                if (city != null && city.getAirports() != null) {
                    city.getAirports().removeIf(a -> a.getCode().equals(code));
                }
                toRemove = airport;
                break;
            }
        }

        if (toRemove == null) {
            throw new IllegalArgumentException("Airport does not exist");
        }

        airports.remove(toRemove);
    }
    @Override
    public void updateAirport(String code, CityDTO newCityDTO) {
        if (!airportExists(code)) {
            throw new IllegalArgumentException("Airport does not exist");
        }
        City newCity = modelMapper.map(newCityDTO, City.class);
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                // Remover de la ciudad anterior
                City oldCity = airport.getCity();
                if (oldCity != null && oldCity.getAirports() != null) {
                    oldCity.getAirports().remove(airport);
                }

                // Asignar nueva ciudad
                airport.setCity(newCity);

                // Agregar a nueva ciudad
                if (newCity.getAirports() == null) {
                    newCity.setAirports(new ArrayList<>());
                }
                if (!newCity.getAirports().contains(airport)) {
                    newCity.getAirports().add(airport);
                }
                return;
            }
        }

        throw new IllegalArgumentException("Airport does not exist");
    }

    @Override
    public AirportDTO getAirportByCode(String code) {
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                return modelMapper.map(airport, AirportDTO.class);
            }
        }
        return null;
    }

    @Override
    public AirportDTO getAirportByName(String name) {
        for (Airport airport : airports) {
            if (airport.getName().equals(name)) {
                return modelMapper.map(airport, AirportDTO.class);
            }
        }
        return null;
    }

    @Override
    public boolean airportExists(String code) {
        for (Airport airport : airports) {
            if (airport.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
