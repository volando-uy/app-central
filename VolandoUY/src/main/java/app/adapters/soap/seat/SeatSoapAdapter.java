package app.adapters.soap.seat;

import app.adapters.soap.BaseSoapAdapter;
import controllers.flightroutepackage.IFlightRoutePackageController;
import controllers.seat.ISeatController;
import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.flightroutepackage.FlightRoutePackage;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SeatSoapAdapter extends BaseSoapAdapter implements ISeatController {

    private final ISeatController controller;

    public SeatSoapAdapter(ISeatController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "seatService";
    }

    @Override
    @WebMethod
    public SeatDTO getSeatDetailsById(Long id) {
        return controller.getSeatDetailsById(id);
    }

    @Override
    @WebMethod
    public BaseSeatDTO getSeatSimpleDetailsById(Long id) {
        return controller.getSeatSimpleDetailsById(id);
    }

    @Override
    @WebMethod
    public BaseSeatDTO getSeatSimpleDetailsByTicketId(Long ticketId) {
        return controller.getSeatSimpleDetailsByTicketId(ticketId);
    }

    @Override
    @WebMethod
    public SeatDTO getSeatDetailsByTicketId(Long ticketId) {
        return controller.getSeatDetailsByTicketId(ticketId);
    }
}
