package app.adapters.soap.flightroute;

import app.adapters.mappers.ImageMapper;
import app.adapters.soap.BaseSoapAdapter;
import controllers.flight.IFlightController;
import controllers.flightroute.IFlightRouteController;
import controllers.flightroute.IFlightRouteSoapController;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class FlightRouteSoapAdapter extends BaseSoapAdapter implements IFlightRouteSoapController {

    private final IFlightRouteController controller;

    public FlightRouteSoapAdapter(IFlightRouteController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "flightRouteService";
    }

    @Override
    @WebMethod
    public boolean existFlightRoute(String name) {
        return controller.existFlightRoute(name);
    }

    @Override
    @WebMethod
    public BaseFlightRouteDTO createFlightRoute(BaseFlightRouteDTO baseFlightRouteDTO, String originAeroCode, String destinationAeroCode, String airlineNickname, List<String> categoriesNames, String imageBase64) {
        File imageFile = null;
        try {
            imageFile = ImageMapper.fromBase64(imageBase64, ".tmp");
            return controller.createFlightRoute(
                    baseFlightRouteDTO,
                    originAeroCode,
                    destinationAeroCode,
                    airlineNickname,
                    categoriesNames,
                    imageFile
            );
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar la imagen base64", e);
        } finally {
            if (imageFile != null && imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    @Override
    @WebMethod
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        return controller.getFlightRouteDetailsByName(routeName);
    }

    @Override
    @WebMethod
    public BaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName) {
        return controller.getFlightRouteSimpleDetailsByName(routeName);
    }

    @Override
    @WebMethod
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        return controller.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
    }

    @Override
    @WebMethod
    public List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname) {
        return controller.getAllFlightRoutesSimpleDetailsByAirlineNickname(airlineNickname);
    }

    @Override
    @WebMethod
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByPackageName(String packageName) {
        return controller.getAllFlightRoutesDetailsByPackageName(packageName);
    }

    @Override
    @WebMethod
    public List<BaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByPackageName(String packageName) {
        return controller.getAllFlightRoutesSimpleDetailsByPackageName(packageName);
    }

    @Override
    public void setFlightRouteStatusByName(String routeName, String status) {
        controller.setFlightRouteStatusByName(routeName, status);
    }
}
