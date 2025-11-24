package app.adapters.soap.flightroute;

import app.adapters.dto.flightroute.SoapBaseFlightRouteDTO;
import app.adapters.dto.flightroute.SoapFlightRouteDTO;
import app.adapters.dto.flights.SoapFlightDTO;
import app.adapters.mappers.FlightRouteMapper;
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
    public SoapBaseFlightRouteDTO createFlightRoute(SoapBaseFlightRouteDTO soapBaseFlightRouteDTO, String originAeroCode, String destinationAeroCode, String airlineNickname, List<String> categoriesNames, String imageBase64) {
        File imageFile = null;
        BaseFlightRouteDTO domainDto = FlightRouteMapper.toBaseFlightRouteDTO(soapBaseFlightRouteDTO);

        try {
            imageFile = ImageMapper.fromBase64(imageBase64, ".tmp");
            BaseFlightRouteDTO baseFlightRouteDTO= controller.createFlightRoute(
                    domainDto,
                    originAeroCode,
                    destinationAeroCode,
                    airlineNickname,
                    categoriesNames,
                    imageFile
            );
            return FlightRouteMapper.toSoapBaseFlightRouteDTO(baseFlightRouteDTO);
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
    public SoapFlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        FlightRouteDTO soapFlightRouteDTO = controller.getFlightRouteDetailsByName(routeName);
        return FlightRouteMapper.toSoapFlightRouteDTO(soapFlightRouteDTO);
    }

    @Override
    @WebMethod
    public SoapBaseFlightRouteDTO getFlightRouteSimpleDetailsByName(String routeName) {
        BaseFlightRouteDTO baseFlightRouteDTO = controller.getFlightRouteSimpleDetailsByName(routeName);
        return FlightRouteMapper.toSoapBaseFlightRouteDTO(baseFlightRouteDTO);
    }

    @Override
    @WebMethod
    public List<SoapFlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        List<FlightRouteDTO> flightRouteDTOS= controller.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
        return flightRouteDTOS.stream()
                .map(FlightRouteMapper::toSoapFlightRouteDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByAirlineNickname(String airlineNickname) {
        List<BaseFlightRouteDTO> soapBaseFlightRouteDTOS = controller.getAllFlightRoutesSimpleDetailsByAirlineNickname(airlineNickname);
        return soapBaseFlightRouteDTOS.stream()
                .map(FlightRouteMapper::toSoapBaseFlightRouteDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapFlightRouteDTO> getAllFlightRoutesDetailsByPackageName(String packageName) {
        List<FlightRouteDTO> flightRouteDTOS = controller.getAllFlightRoutesDetailsByPackageName(packageName);
        return flightRouteDTOS.stream()
                .map(FlightRouteMapper::toSoapFlightRouteDTO)
                .toList();
    }

    @Override
    @WebMethod
    public List<SoapBaseFlightRouteDTO> getAllFlightRoutesSimpleDetailsByPackageName(String packageName) {
        List<BaseFlightRouteDTO> baseFlightRouteDTOS= controller.getAllFlightRoutesSimpleDetailsByPackageName(packageName);
        return baseFlightRouteDTOS.stream()
                .map(FlightRouteMapper::toSoapBaseFlightRouteDTO)
                .toList();
    }

    @Override
    public void setFlightRouteStatusByName(String routeName, String status) {
        controller.setFlightRouteStatusByName(routeName, status);
    }
}
