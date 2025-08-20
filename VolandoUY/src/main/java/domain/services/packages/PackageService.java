package domain.services.packages;

import domain.dtos.packages.PackageDTO;
import domain.models.packages.Package;
import factory.ControllerFactory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class PackageService implements IPackageService {
    private List<Package> packages = new ArrayList<>();
    private ModelMapper modelMapper  = ControllerFactory.getModelMapper();

    public PackageService (ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addPackage(PackageDTO packageDTO) {
        Package pack = modelMapper.map(packageDTO, Package.class);
        if (packageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete ya existe");
        }
        packages.add(pack);
    }

    @Override
    public void updatePackage(PackageDTO packageDTO) {
        Package pack = modelMapper.map(packageDTO, Package.class);
        if (!packageExists(pack.getName())) {
            throw new IllegalArgumentException("El paquete no existe");
        }
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).getName().equalsIgnoreCase(pack.getName())) {
                packages.set(i, pack);
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
        packages.removeIf(pack -> pack.getName().equalsIgnoreCase(packageName));
    }

    @Override
    public PackageDTO getPackage(String packageName) {
        for (Package pack : packages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return modelMapper.map(pack, PackageDTO.class);
            }
        }
        throw new IllegalArgumentException("Paquete no encontrado");
    }

    @Override
    public boolean packageExists(String packageName) {
        for (Package pack : packages) {
            if (pack.getName().equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }
}