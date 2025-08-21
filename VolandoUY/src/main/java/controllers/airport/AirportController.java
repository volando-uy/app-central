package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.services.airport.IAirportService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AirportController implements IAirportController {
    private IAirportService airportService;

    @Override
    public AirportDTO createAirport(AirportDTO airportDTO, String cityName) {
        return airportService.createAirport(airportDTO, cityName);
    }

    @Override
    public AirportDTO getAirportByCode(String code) {
        return airportService.getAirportDetailsByCode(code);
    }

    @Override
    public boolean airportExists(String code) {
        return airportService.airportExists(code);
    }
}
