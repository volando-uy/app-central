package controllers.flight;

import app.adapters.dto.flights.BaseFlightSoapViewDTO;

import java.util.List;

public interface ISoapFlightController extends IBaseFlightController {
    List<BaseFlightSoapViewDTO> getAllFlightsSimpleDetails();
}
