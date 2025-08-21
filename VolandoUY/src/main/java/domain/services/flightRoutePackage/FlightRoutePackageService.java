package domain.services.flightRoutePackage;


import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;


public class FlightRoutePackageService implements IFlightRoutePackageService {

    private List<FlightRoutePackage> flightRoutePackages;
    private ModelMapper modelMapper;


    public FlightRoutePackageService(ModelMapper modelMapper) {
        flightRoutePackages = new ArrayList<>();
        this.modelMapper = modelMapper;
    }

    @Override
    public FlightRoutePackageDTO createFlightRoutePackage(FlightRoutePackageDTO flightRoutePackageDTO) {
        FlightRoutePackage pack = modelMapper.map(flightRoutePackageDTO, FlightRoutePackage.class);
        if (flightRoutePackageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete ya existe");
        }
        ValidatorUtil.validate(pack);
      
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
    public List<FlightRoutePackageDTO> getAllFlightRoutePackage() {
         // Convertimos los elementos a DTO y todo eso a una lista.
         return flightRoutePackages.stream()
                    .map(pack -> modelMapper.map(pack, FlightRoutePackageDTO.class))
                    .toList();

        }
}
