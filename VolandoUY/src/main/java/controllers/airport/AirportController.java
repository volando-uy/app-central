package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.services.airport.IAirportService;
import lombok.AllArgsConstructor;

import java.util.List;

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

    @Override
    public List<BaseAirportDTO> getAllAirportsSimpleDetails() {
        return airportService.getAllAirportsDetails(false).stream()
                .map(airDTO -> (BaseAirportDTO) airDTO)
                .toList();
    }

    @Override
    public List<AirportDTO> getAllAirportsDetails() {
        return airportService.getAllAirportsDetails(true);
    }
}
