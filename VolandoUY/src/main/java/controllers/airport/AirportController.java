package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.services.airport.IAirportService;

public class AirportController implements IAirportController {

    private IAirportService airportService;

    public AirportController(IAirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    public void addAirport(CityDTO city, String name, String code) {
        airportService.addAirport(city, name, code);
    }

    @Override
    public void removeAirport(String code) {
        airportService.removeAirport(code);
    }

    @Override
    public void updateAirport(String code, CityDTO city) {
        airportService.updateAirport(code, city);
    }

    @Override
    public AirportDTO getAirportByCode(String code) {
        return airportService.getAirportByCode(code);
    }

    @Override
    public AirportDTO getAirportByName(String name) {
        return airportService.getAirportByName(name);
    }

    @Override
    public boolean airportExists(String code) {
        return airportService.airportExists(code);
    }
}
