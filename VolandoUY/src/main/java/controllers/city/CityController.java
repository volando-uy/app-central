package controllers.city;

import domain.dtos.city.CityDTO;
import domain.services.city.ICityService;

public class CityController implements ICityController {
    private ICityService cityService;

    public CityController(ICityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public CityDTO createCity(CityDTO city) {
        return this.cityService.createCity(city);
    }

    @Override
    public void updateCity(CityDTO city) {
        this.cityService.updateCity(city);
    }

    @Override
    public void deleteCity(String cityName) {
        this.cityService.deleteCity(cityName);
    }

    @Override
    public CityDTO getCity(String cityName) {
        return this.cityService.getCity(cityName);
    }

    @Override
    public boolean cityExists(String cityName) {
        return this.cityService.cityExists(cityName);
    }

    @Override
    public void addAirportToCity(String cityName, String airportName, String airportCode) {
        this.cityService.addAirportToCity(cityName, airportName, airportCode);
    }

    @Override
    public void removeAirportFromCity(String cityName, String airportName) {
        this.cityService.removeAirportFromCity(cityName, airportName);
    }

    @Override
    public void updateAirportInCity(String cityName, String airportName, String newAirportName, String newAirportCode) {
        this.cityService.updateAirportInCity(cityName, airportName, newAirportName, newAirportCode);
    }

    @Override
    public CityDTO getCityWithAirports(String cityName) {
        return this.cityService.getCityWithAirports(cityName);
    }

    @Override
    public boolean isAirportInCity(String cityName, String airportName) {
        return this.cityService.isAirportInCity(cityName, airportName);
    }
}
