package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.CityDTO;
import domain.services.airport.IAirportService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AirportController implements IAirportController {
    private IAirportService airportService;

    @Override
    public BaseAirportDTO createAirport(BaseAirportDTO baseAirportDTO, String cityName) {
        return airportService.createAirport(baseAirportDTO, cityName);
    }

    @Override
    public AirportDTO getAirportDetailsByCode(String code) {
        return airportService.getAirportDetailsByCode(code, true);
    }

    @Override
    public BaseAirportDTO getAirportSimpleDetailsByCode(String code) {
        return airportService.getAirportDetailsByCode(code, false);
    }

    @Override
    public boolean airportExists(String code) {
        return airportService.airportExists(code);
    }
}
