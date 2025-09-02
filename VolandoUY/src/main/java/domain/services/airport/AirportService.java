package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import domain.services.city.ICityService;
import infra.repository.airport.AirportRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;


public class AirportService implements IAirportService {
    private AirportRepository airportRepository;
    private ModelMapper modelMapper;

    @Setter
    private ICityService cityService;

    public AirportService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.airportRepository = new AirportRepository();
    }

    public AirportDTO createAirport(AirportDTO dto, String cityName) {
        City city = cityService.getCityByName(cityName);
        System.out.println("City found: " + city);
        if (city.getAirports() == null) {
            city.setAirports(new ArrayList<>()); // 🔧 Previene la excepción
        }

        if (airportExists(dto.getCode())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_CODE_ALREADY_EXISTS, dto.getCode()));
        }

        Airport airport = modelMapper.map(dto, Airport.class);
//        System.out.println("Airportes before adding: " + airports);
        airport.setCity(city);
        ValidatorUtil.validate(airport);
//        airports.add(airport);
        airportRepository.save(airport);
//        System.out.println("Airport after setting city: " + airports);
        city.getAirports().add(airport);

        return modelMapper.map(airport, AirportDTO.class);
    }

//    @Override
//    public void removeAirport(String code) {
//        Airport toRemove = null;
//
//        for (Airport airport : airports) {
//            if (airport.getCode().equals(code)) {
//                City city = airport.getCities();
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
//                City oldCity = airport.getCities();
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
        Airport airport = airportRepository.getAirportByCode(code);
        if(airport == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRPORT_NOT_FOUND, code));
        }
        return airport;

    }

    @Override
    public AirportDTO getAirportDetailsByCode(String code) {
        return modelMapper.map(getAirportByCode(code), AirportDTO.class);
    }

    @Override
    public boolean airportExists(String code) {
        return airportRepository.existsAirportByCode(code);
    }
}
