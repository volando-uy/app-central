package domain.services.flightRoutePackage;

import domain.dtos.packages.PackageDTO;
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
    public void addPackage(PackageDTO packageDTO) {
        FlightRoutePackage pack = modelMapper.map(packageDTO, FlightRoutePackage.class);
        if (packageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete ya existe");
        }
        flightRoutePackages.add(pack);
    }

    @Override
    public void updatePackage(PackageDTO packageDTO) {
        FlightRoutePackage pack = modelMapper.map(packageDTO, FlightRoutePackage.class);
        if (!packageExists(pack.getName())) {
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
    public void deletePackage(String packageName) {
        if (!packageExists(packageName)) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        flightRoutePackages.removeIf(pack -> pack.getName().equalsIgnoreCase(packageName));
    }

    @Override
    public PackageDTO getPackage(String packageName) {
        for (FlightRoutePackage pack : flightRoutePackages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return modelMapper.map(pack, PackageDTO.class);
            }
        }
        throw new IllegalArgumentException("Paquete no encontrado");
    }

    @Override
    public boolean packageExists(String packageName) {
        for (FlightRoutePackage pack : flightRoutePackages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }
}