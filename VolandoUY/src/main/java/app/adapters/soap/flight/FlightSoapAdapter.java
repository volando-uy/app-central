package app.adapters.soap.flight;

import app.adapters.dto.flights.SoapBaseFlightDTO;
import app.adapters.dto.flights.SoapFlightDTO;
import app.adapters.mappers.FlightSoapMapper;
import app.adapters.mappers.ImageMapper;
import app.adapters.soap.BaseSoapAdapter;
import controllers.flight.IFlightController;
import controllers.flight.ISoapFlightController;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class FlightSoapAdapter extends BaseSoapAdapter implements ISoapFlightController {

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
    public SoapBaseFlightDTO createFlight(SoapBaseFlightDTO flight, String airlineNickname, String flightRouteName, String imageBase64File) {
        BaseFlightDTO baseFlightDTO = FlightSoapMapper.mapFromSoapBaseFlightDTO(flight);
        File image=null;
        try {
            image = ImageMapper.fromBase64(imageBase64File, "flightImage");
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error decoding image from base64", e);
        }
        BaseFlightDTO createdFlight = controller.createFlight(baseFlightDTO, airlineNickname, flightRouteName, image);
        return FlightSoapMapper.mapToSoapBaseFlightDTO(createdFlight);

    }


    @Override
    @WebMethod
    public List<SoapFlightDTO> getAllFlightsDetails() {
        return controller.getAllFlightsDetails()
                .stream()
                .map(FlightSoapMapper::mapToSoapFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseFlightDTO> getAllFlightsSimpleDetails() {
        return controller.getAllFlightsSimpleDetails()
                .stream()
                .map(FlightSoapMapper::mapToSoapBaseFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public SoapFlightDTO getFlightDetailsByName(String name) {
        FlightDTO flight = controller.getFlightDetailsByName(name);
        return FlightSoapMapper.mapToSoapFlightDTO(flight);
    }

    @Override
    @WebMethod
    public SoapBaseFlightDTO getFlightSimpleDetailsByName(String name) {
        BaseFlightDTO flight = controller.getFlightSimpleDetailsByName(name);
        return FlightSoapMapper.mapToSoapBaseFlightDTO(flight);
    }

    @Override
    @WebMethod
    public List<SoapFlightDTO> getAllFlightsDetailsByAirline(String airlineNickname) {
        List<FlightDTO> flights = controller.getAllFlightsDetailsByAirline(airlineNickname);
        return flights.stream()
                .map(FlightSoapMapper::mapToSoapFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseFlightDTO> getAllFlightsSimpleDetailsByAirline(String airlineNickname) {

        List<BaseFlightDTO> flights = controller.getAllFlightsSimpleDetailsByAirline(airlineNickname);
        return flights.stream()
                .map(FlightSoapMapper::mapToSoapBaseFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapFlightDTO> getAllFlightsDetailsByRouteName(String flightRouteName) {
        //Mappear a SoapFlightDTO

        List<FlightDTO> flights = controller.getAllFlightsDetailsByRouteName(flightRouteName);
        return flights.stream()
                .map(FlightSoapMapper::mapToSoapFlightDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseFlightDTO> getAllFlightsSimpleDetailsByRouteName(String flightRouteName) {

        List<BaseFlightDTO> flights = controller.getAllFlightsSimpleDetailsByRouteName(flightRouteName);
        return flights.stream()
                .map(FlightSoapMapper::mapToSoapBaseFlightDTO)
                .toList();
    }
}
