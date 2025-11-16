package app.adapters.soap.flight;

import app.adapters.soap.BaseSoapAdapter;
import controllers.flight.IFlightController;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class FlightSoapAdapter extends BaseSoapAdapter implements IFlightController {

    private final IFlightController controller;

    public FlightSoapAdapter(IFlightController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "flightService";
    }

    @Override
    @WebMethod
    public BaseFlightDTO createFlight(BaseFlightDTO flight, String airlineNickname, String flightRouteName, File imageFile) {
        return controller.createFlight(flight, airlineNickname, flightRouteName, imageFile);
    }

    @Override
    @WebMethod
    public List<FlightDTO> getAllFlightsDetails() {
        return controller.getAllFlightsDetails();
    }

    @Override
    @WebMethod
    public List<BaseFlightDTO> getAllFlightsSimpleDetails() {
        return controller.getAllFlightsSimpleDetails();
    }

    @Override
    @WebMethod
    public FlightDTO getFlightDetailsByName(String name) {
        return controller.getFlightDetailsByName(name);
    }

    @Override
    @WebMethod
    public BaseFlightDTO getFlightSimpleDetailsByName(String name) {
        return controller.getFlightSimpleDetailsByName(name);
    }

    @Override
    @WebMethod
    public List<FlightDTO> getAllFlightsDetailsByAirline(String airlineNickname) {
        return controller.getAllFlightsDetailsByAirline(airlineNickname);
    }

    @Override
    @WebMethod
    public List<BaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname) {
        return controller.getAllFlightsSimpleDetailsByAirline(airlineNickname);
    }

    @Override
    @WebMethod
    public List<FlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName) {
        return controller.getAllFlightsDetailsByRouteName(flightRouteName);
    }

    @Override
    @WebMethod
    public List<BaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName) {
        return controller.getAllFlightsSimpleDetailsByRouteName(flightRouteName);
    }
}
