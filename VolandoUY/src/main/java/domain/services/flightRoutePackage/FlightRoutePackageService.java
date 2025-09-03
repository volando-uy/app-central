package domain.services.flightRoutePackage;

import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.services.flightRoute.IFlightRouteService;
import factory.ControllerFactory;
import infra.repository.flightroutepackage.FlightRoutePackageRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;


public class FlightRoutePackageService implements IFlightRoutePackageService {

    private FlightRoutePackageRepository flightRoutePackageRepository;

    @Setter
    private IFlightRouteService flightRouteService;

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public FlightRoutePackageService() {
        flightRoutePackageRepository = new FlightRoutePackageRepository();
    }

    @Override
    public FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO) {
        FlightRoutePackage pack = customModelMapper.map(flightRoutePackageDTO, FlightRoutePackage.class);
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
        // Agregamos las rutas
        pack.setFlightRoutes(flightRoutes);

        // Calcular el totalPrice
        Double totalPrice;
        if (pack.getSeatType().equals(EnumTipoAsiento.TURISTA)) {
            totalPrice = flightRoutes.stream().mapToDouble(FlightRoute::getPriceTouristClass).sum();
        } else if (pack.getSeatType().equals(EnumTipoAsiento.EJECUTIVO)) {
            totalPrice = flightRoutes.stream().mapToDouble(FlightRoute::getPriceBusinessClass).sum();
        } else {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_INVALID_SEAT_TYPE, pack.getSeatType()));
        }
        pack.setTotalPrice(totalPrice);

        // Validamos el paquete
        ValidatorUtil.validate(pack);

        // Guardamos el paquete
        flightRoutePackageRepository.save(pack);

        return customModelMapper.mapFlightRoutePackage(pack);
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackageDetailsByName(String flightRoutePackageName) {
        // Comrpobamos que el paquete existe
        FlightRoutePackage pack = flightRoutePackageRepository.getFlightRoutePackageByName(flightRoutePackageName);
        if (pack == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, flightRoutePackageName));
        }

        return customModelMapper.mapFlightRoutePackage(pack);
    }

    @Override
    public FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName) {
        // Comrpobamos que el paquete existe
        FlightRoutePackage pack = flightRoutePackageRepository.getFlightRoutePackageByName(flightRoutePackageName);
        if (pack == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, flightRoutePackageName));
        }

        return pack;
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
        // Nos fijamos si la cantidad es mayor a 0
        if (quantity <= 0) {
            throw new IllegalArgumentException(ErrorMessages.ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO);
        }

        // Tiramos una excepcion si el paquete no existe
        FlightRoutePackage flightRoutePackage = flightRoutePackageRepository.getFlightRoutePackageByName(packageName);
        if (flightRoutePackage == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, packageName));
        }

        // Tiramos una excepcion si la ruta de vuelo no existe
        FlightRoute flightRoute = flightRouteService.getFlightRouteByName(flightRouteName);
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, flightRouteName));
        }

        // La cantidad de veces de la 'cantidad'
        for (int i = 0; i < quantity; i++) {

            // Actualizar el totalPrice del paquete
            Double priceToAdd;
            if (flightRoutePackage.getSeatType().equals(EnumTipoAsiento.TURISTA)) {
                priceToAdd = flightRoute.getPriceTouristClass();
            } else if (flightRoutePackage.getSeatType().equals(EnumTipoAsiento.EJECUTIVO)) {
                priceToAdd = flightRoute.getPriceBusinessClass();
            } else {
                throw new IllegalArgumentException(String.format(ErrorMessages.ERR_INVALID_SEAT_TYPE, flightRoutePackage.getSeatType()));
            }

            // Setteamos el precio nuevo
            flightRoutePackage.setTotalPrice(flightRoutePackage.getTotalPrice() + priceToAdd);

            // Añadimos la ruta de vuelo al paquete
            flightRoutePackage.getFlightRoutes().add(flightRoute);

        }

        // Actualizamos el paquete
        flightRoutePackageRepository.update(flightRoutePackage);
    }

    @Override
    public List<FlightRoutePackageDTO> getPackagesWithFlightRoutes() {
        // Buscamos todos los paquetes en el repository
        // Filtramos entre los paquetes que tienen rutas de vuelo
        // Mapeamos a DTO y retornamos la lista
        return flightRoutePackageRepository.findAll()
                .stream()
                .filter(pack -> pack.getFlightRoutes() != null && !pack.getFlightRoutes().isEmpty())
                .map(customModelMapper::mapFlightRoutePackage)
                .toList();
    }

    @Override
    public void _updateFlightRoutePackage(FlightRoutePackage flightRoutePackage) {
        flightRoutePackageRepository.update(flightRoutePackage);
    }

    @Override
    public List<FlightRoutePackage> getAllFlightRoutePackages() {
        return flightRoutePackageRepository.findAll();
    }
}