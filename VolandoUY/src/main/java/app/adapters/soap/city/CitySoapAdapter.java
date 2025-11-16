package app.adapters.soap.city;

import app.adapters.soap.BaseSoapAdapter;
import controllers.city.ICityController;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class CitySoapAdapter extends BaseSoapAdapter implements ICityController {

    private final ICityController controller;

    public CitySoapAdapter(ICityController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "cityService";
    }

    @Override
    @WebMethod
    public BaseCityDTO createCity(BaseCityDTO baseCity) {
        return controller.createCity(baseCity);
    }

    @Override
    @WebMethod
    public boolean cityExists(String cityName) {
        return controller.cityExists(cityName);
    }

    @Override
    @WebMethod
    public boolean isAirportInCity(String name, String airportName) {
        return controller.isAirportInCity(name, airportName);
    }

    @Override
    @WebMethod
    public BaseCityDTO getCitySimpleDetailsByName(String cityName) {
        return controller.getCitySimpleDetailsByName(cityName);
    }

    @Override
    @WebMethod
    public CityDTO getCityDetailsByName(String cityName) {
        return controller.getCityDetailsByName(cityName);
    }

    @Override
    @WebMethod
    public List<BaseCityDTO> getAllCitiesSimpleDetails() {
        return controller.getAllCitiesSimpleDetails();
    }

    @Override
    @WebMethod
    public List<String> getAllCitiesNames() {
        return controller.getAllCitiesNames();
    }
}
