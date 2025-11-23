package controllers.flight;

import app.adapters.dto.flights.BaseFlightSoapViewDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;

import java.io.File;
import java.util.List;

public interface IFlightController extends IBaseFlightController {
    List<BaseFlightDTO> getAllFlightsSimpleDetails();

}
