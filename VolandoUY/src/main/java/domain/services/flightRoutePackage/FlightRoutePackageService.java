package domain.services.flightRoutePackage;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoute.IFlightRouteService;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;


public class FlightRoutePackageService implements IFlightRoutePackageService {
    private List<FlightRoutePackage> flightRoutePackages;

    private IFlightRouteService flightRouteService;
    private ModelMapper modelMapper;

    public FlightRoutePackageService(IFlightRouteService flightRouteService, ModelMapper modelMapper) {
        flightRoutePackages = new ArrayList<>();
        this.flightRouteService = flightRouteService;
        this.modelMapper = modelMapper;
    }

    @Override
    public FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO) {
        FlightRoutePackage pack = modelMapper.map(flightRoutePackageDTO, FlightRoutePackage.class);
        if (flightRoutePackageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete ya existe");
        }
        ValidatorUtil.validate(pack);

        pack.setFlightRoutes(new ArrayList<>());
        flightRoutePackages.add(pack);
        return modelMapper.map(pack, FlightRoutePackageDTO.class);
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackageByName(String flightRoutePackageName) {
        return flightRoutePackages.stream()
                .filter(pack -> pack.getName().equalsIgnoreCase(flightRoutePackageName))
                .findFirst()
                .map(pack -> modelMapper.map(pack, FlightRoutePackageDTO.class))
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, flightRoutePackageName)));
    }

    @Override
    public boolean flightRoutePackageExists(String packageName) {
        return flightRoutePackages.stream()
                .anyMatch(pack -> pack.getName().equalsIgnoreCase(packageName));
    }

    @Override
    public List<String> getAllNotBoughtFlightRoutePackagesNames() {
        // We have no way of know if a package has been bought for now.
        // So we return all the packages names.
        return flightRoutePackages.stream()
                .map(FlightRoutePackage::getName)
                .toList();
    }

    @Override
    public void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity) {
        FlightRoutePackage pack = flightRoutePackages.stream()
                .filter(p -> p.getName().equalsIgnoreCase(packageName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, packageName)));

        // Throw exception if the flight route does not exist
        FlightRoute flightRoute = flightRouteService.getFlightRouteByName(flightRouteName);

        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        
        for (int i = 0; i < quantity; i++) {
            pack.getFlightRoutes().add(flightRoute);
        }

    }
}