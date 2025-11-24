package app.adapters.soap.flightroutepackage;

import app.adapters.soap.BaseSoapAdapter;
import controllers.flightroute.IFlightRouteController;
import controllers.flightroutepackage.IFlightRoutePackageController;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.models.flightroutepackage.FlightRoutePackage;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.io.File;
import java.util.List;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class FlightRoutePackageSoapAdapter extends BaseSoapAdapter implements IFlightRoutePackageController {

    private final IFlightRoutePackageController controller;

    public FlightRoutePackageSoapAdapter(IFlightRoutePackageController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "flightRoutePackageService";
    }

    @Override
    @WebMethod
    public BaseFlightRoutePackageDTO createFlightRoutePackage(BaseFlightRoutePackageDTO flightRoutePackageDTO) {
        return controller.createFlightRoutePackage(flightRoutePackageDTO);
    }

    @Override
    @WebMethod
    public FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String packageName) {
        return controller.getFlightRoutePackageDetailsByName(packageName);
    }

    @Override
    @WebMethod
    public BaseFlightRoutePackageDTO getFlightRoutePackageSimpleDetailsByName(String packageName) {
        return controller.getFlightRoutePackageSimpleDetailsByName(packageName);
    }

    @Override
    @WebMethod
    public List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes() {
        return controller.getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes();
    }

    @Override
    @WebMethod
    public List<FlightRoutePackage> getAllFlightRoutesPackages() {
        return controller.getAllFlightRoutesPackages();
    }

    @Override
    @WebMethod
    public List<FlightRoutePackageDTO> getAllFlightRoutesPackagesDetails() {
        return controller.getAllFlightRoutesPackagesDetails();
    }

    @Override
    @WebMethod
    public List<BaseFlightRoutePackageDTO> getAllFlightRoutesPackagesSimpleDetails() {
        return controller.getAllFlightRoutesPackagesSimpleDetails();
    }

    @Override
    @WebMethod
    public List<String> getAllNotBoughtFlightRoutesPackagesNames() {
        return controller.getAllNotBoughtFlightRoutesPackagesNames();
    }

    @Override
    @WebMethod
    public void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity) {
        controller.addFlightRouteToPackage(packageName, flightRouteName, quantity);
    }

    @Override
    @WebMethod
    public boolean flightRoutePackageExists(String packageName) {
        return controller.flightRoutePackageExists(packageName);
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackageDetailsById(Long id) {
        return controller.getFlightRoutePackageDetailsById(id);
    }
}
