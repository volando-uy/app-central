package app.adapters.soap.airport;

import app.adapters.soap.BaseSoapAdapter;
import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import controllers.airport.IAirportController;
import jakarta.xml.ws.Endpoint;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class AirportSoapAdapter extends BaseSoapAdapter implements IAirportController{

    private final IAirportController controller;

    public AirportSoapAdapter(IAirportController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "airportService";
    }

    @Override
    @WebMethod
    public boolean airportExists(String code) {
        return controller.airportExists(code);
    }

    @Override
    @WebMethod
    public BaseAirportDTO createAirport(BaseAirportDTO baseAirportDTO, String cityName) {
        return controller.createAirport(baseAirportDTO, cityName);
    }

    @Override
    @WebMethod
    public BaseAirportDTO getAirportSimpleDetailsByCode(String code) {
        return controller.getAirportSimpleDetailsByCode(code);
    }

    @Override
    @WebMethod
    public AirportDTO getAirportDetailsByCode(String code) {
        return controller.getAirportDetailsByCode(code);
    }

    @Override
    @WebMethod
    public List<BaseAirportDTO> getAllAirportsSimpleDetails() {
        return controller.getAllAirportsSimpleDetails();
    }

    @Override
    @WebMethod
    public List<AirportDTO> getAllAirportsDetails() {
        return controller.getAllAirportsDetails();
    }


}
