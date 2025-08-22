package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import domain.services.city.ICityService;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;


public class AirportService implements IAirportService {
    private List<Airport> airports;
    private ModelMapper modelMapper;
    private ICityService cityService;

    public AirportService(ModelMapper modelMapper, ICityService cityService) {
        this.modelMapper = modelMapper;
        this.cityService = cityService;
        this.airports = new ArrayList<>();
    }

    public AirportDTO createAirport(AirportDTO dto, String cityName) {
        City city = cityService.getCityByName(cityName);
        System.out.println("City found: " + city);
        if (city.getAirports() == null) {
            city.setAirports(new ArrayList<>()); // 🔧 Previene la excepción
        }

        if (airportExists(dto.getCode())) {
            throw new IllegalArgumentException("Ya existe un aeropuerto con el código " + dto.getCode());
        }

        Airport airport = modelMapper.map(dto, Airport.class);
        System.out.println("Airportes before adding: " + airports);
        airport.setCity(city);
        ValidatorUtil.validate(airport);
        airports.add(airport);
        System.out.println("Airport after setting city: " + airports);
        city.getAirports().add(airport);

        return modelMapper.map(airport, AirportDTO.class);
    }

//    @Override
//    public void removeAirport(String code) {
//        Airport toRemove = null;
//
//        for (Airport airport : airports) {
//            if (airport.getCode().equals(code)) {
//                City city = airport.getCity();
//                if (city != null && city.getAirports() != null) {
//                    city.getAirports().removeIf(a -> a.getCode().equals(code));
//                }
//                toRemove = airport;
//                break;
//            }
//        }
//
//        if (toRemove == null) {
//            throw new IllegalArgumentException("Airport does not exist");
//        }
//
//        airports.remove(toRemove);
//    }
//    @Override
//    public void updateAirport(String code, CityDTO newCityDTO) {
//        if (!airportExists(code)) {
//            throw new IllegalArgumentException("Airport does not exist");
//        }
//        City newCity = modelMapper.map(newCityDTO, City.class);
//        for (Airport airport : airports) {
//            if (airport.getCode().equals(code)) {
//                // Remover de la ciudad anterior
//                City oldCity = airport.getCity();
//                if (oldCity != null && oldCity.getAirports() != null) {
//                    oldCity.getAirports().remove(airport);
//                }
//
//                // Asignar nueva ciudad
//                airport.setCity(newCity);
//
//                // Agregar a nueva ciudad
//                if (newCity.getAirports() == null) {
//                    newCity.setAirports(new ArrayList<>());
//                }
//                if (!newCity.getAirports().contains(airport)) {
//                    newCity.getAirports().add(airport);
//                }
//                return;
//            }
//        }
//
//        throw new IllegalArgumentException("Airport does not exist");
//    }

    @Override
    public Airport getAirportByCode(String code) {
        System.out.println("airports: " + airports);
        return airports.stream()
                .filter(airport -> airport.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_NOT_FOUND, code)));
    }

    @Override
    public AirportDTO getAirportDetailsByCode(String code) {
        return modelMapper.map(getAirportByCode(code), AirportDTO.class);
    }

    @Override
    public boolean airportExists(String code) {
        return airports.stream().anyMatch(airport -> airport.getCode().equals(code));
    }
}
