package domain.services.flightRoutePackage;

import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.flightRoutePackage.FlightRoutePackage;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class FlightRoutePackageService implements IFlightRoutePackageService {

    private List<FlightRoutePackage> flightRoutePackages = new ArrayList<>();

    private ModelMapper modelMapper  = ControllerFactory.getModelMapper();

    public FlightRoutePackageService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addFlightRoutePackage(FlightRoutePackageDTO packageDTO) {
        FlightRoutePackage pack = modelMapper.map(packageDTO, FlightRoutePackage.class);
        if (flightRoutePackageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete ya existe");
        }
        flightRoutePackages.add(pack);
    }

    @Override
    public void updateFlightRoutePackage(FlightRoutePackageDTO packageDTO) {
        FlightRoutePackage pack = modelMapper.map(packageDTO, FlightRoutePackage.class);
        if (!flightRoutePackageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        for (int i = 0; i < flightRoutePackages.size(); i++) {
            if (flightRoutePackages.get(i).getName().equalsIgnoreCase(pack.getName())) {
                flightRoutePackages.set(i, pack);
                return;
            }
        }
        throw new IllegalArgumentException("Paquete no encontrado en la lista");
    }

    @Override
    public void deleteFlightRoutePackage(String packageName) {
        if (!flightRoutePackageExists(packageName)) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        flightRoutePackages.removeIf(pack -> pack.getName().equalsIgnoreCase(packageName));
    }

    @Override
    public FlightRoutePackageDTO getFlightRoutePackage(String packageName) {
        for (FlightRoutePackage pack : flightRoutePackages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return modelMapper.map(pack, FlightRoutePackageDTO.class);
            }
        }
        throw new IllegalArgumentException("Paquete no encontrado");
    }

    @Override
    public boolean flightRoutePackageExists(String packageName) {
        for (FlightRoutePackage pack : flightRoutePackages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<FlightRoutePackageDTO> getAllFlightRoutePackage() {
         // Convertimos los elementos a DTO y todo eso a una lista.
         return flightRoutePackages.stream()
                    .map(pack -> modelMapper.map(pack, FlightRoutePackageDTO.class))
                    .toList();

        }
}
