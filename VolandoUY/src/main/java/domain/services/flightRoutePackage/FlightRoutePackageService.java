package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoute.IFlightRouteService;
import infra.repository.flightroutepackage.FlightRoutePackageRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;


public class FlightRoutePackageService implements IFlightRoutePackageService {

    private FlightRoutePackageRepository flightRoutePackageRepository;

    private IFlightRouteService flightRouteService;
    private ModelMapper modelMapper;

    public FlightRoutePackageService(IFlightRouteService flightRouteService, ModelMapper modelMapper) {
        flightRoutePackageRepository = new FlightRoutePackageRepository();
        this.flightRouteService = flightRouteService;
        this.modelMapper = modelMapper;
    }

    @Override
    public FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO) {
        FlightRoutePackage pack = modelMapper.map(flightRoutePackageDTO, FlightRoutePackage.class);
        if (flightRoutePackageExists(pack.getName())) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_PACKAGE_ALREADY_EXISTS, pack.getName()));
        }
        /**
         * Si flightRouteNames viene con una lista vacia, inicializar flightRoutes como una lista vacía. Sino mapear
         * flightRouteNames a flightRoutes.
         */
        //Si viene con cosas
        List<FlightRoute> flightRoutes = new ArrayList<>();
        if (flightRoutePackageDTO.getFlightRouteNames() != null && !flightRoutePackageDTO.getFlightRouteNames().isEmpty()) {
            System.out.println("Flight route names: " + flightRoutePackageDTO.getFlightRouteNames());
            for (String routeName : flightRoutePackageDTO.getFlightRouteNames()) {
                FlightRoute route = flightRouteService.getFlightRouteByName(routeName);
                if (route == null) {
                    throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName));
                }
                flightRoutes.add(route);
            }
        }
        pack.setFlightRoutes(flightRoutes);
        ValidatorUtil.validate(pack);


        flightRoutePackageRepository.save(pack);
        return modelMapper.map(pack, FlightRoutePackageDTO.class);
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackageByName(String flightRoutePackageName) {
        FlightRoutePackage pack = flightRoutePackageRepository.getFlightRoutePackageByName(flightRoutePackageName);
        if (pack == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, flightRoutePackageName));
        }
        FlightRoutePackageDTO flightRoutePackageDTO = modelMapper.map(pack, FlightRoutePackageDTO.class);
        flightRoutePackageDTO.setFlightRouteNames(pack.getFlightRoutes().stream().map(FlightRoute::getName).toList());
        return flightRoutePackageDTO;

    }

    @Override
    public boolean flightRoutePackageExists(String packageName) {
        return flightRoutePackageRepository.existsByName(packageName);
    }

    @Override
    public List<String> getAllNotBoughtFlightRoutePackagesNames() {

        return flightRoutePackageRepository.findAll().stream().map(FlightRoutePackage::getName).toList();
    }

    @Override
    public void addFlightRouteToPackage(String packageName, String flightRouteName, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(ErrorMessages.ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO);
        }

        FlightRoutePackage flightRoutePackage = flightRoutePackageRepository.getFlightRoutePackageByName(packageName);
        if (flightRoutePackage == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, packageName));
        }
        // Throw exception if the flight route does not exist
        FlightRoute flightRoute = flightRouteService.getFlightRouteByName(flightRouteName);
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, flightRouteName));
        }

        for (int i = 0; i < quantity; i++) {
            flightRoutePackage.getFlightRoutes().add(flightRoute);
            System.out.println("Added flight route " + flightRouteName + " to package " + packageName);
        }

        flightRoutePackageRepository.update(flightRoutePackage);
    }

    @Override
    public List<FlightRoutePackageDTO> getPackagesWithFlightRoutes() {
        return flightRoutePackageRepository.findAll()
                .stream()
                .filter(pack -> pack.getFlightRoutes() != null && !pack.getFlightRoutes().isEmpty())
                .map(pack -> {
                    FlightRoutePackageDTO dto = modelMapper.map(pack, FlightRoutePackageDTO.class);
                    dto.setFlightRouteNames(pack.getFlightRoutes()
                            .stream()
                            .map(FlightRoute::getName)
                            .toList());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<FlightRoutePackage> getAllFlightRoutePackages() {
        return flightRoutePackageRepository.findAll();
    }
}