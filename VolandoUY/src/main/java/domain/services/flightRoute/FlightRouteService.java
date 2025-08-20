package domain.services.flightRoute;


import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flightRoute.FlightRoute;
import domain.services.category.ICategoryService;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightRouteService implements IFlightRouteService {
    List<FlightRoute> flightRouteList = new ArrayList<>();

    private ICategoryService categoryService = ControllerFactory.getCategoryService();
    private ModelMapper modelMapper = ControllerFactory.getModelMapper();
    private List<FlightRouteDTO> flightRouteDTOList = new ArrayList<>();

    @Override
    public boolean existFlightRoute(String name) {
        return this.flightRouteList.stream().anyMatch(flightRoute -> flightRoute.getName().equalsIgnoreCase(name));
    }

    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO) {
        if (existFlightRoute(flightRouteDTO.getName())) {
            throw new UnsupportedOperationException("no podes meter un bicho que ya existe");
        }
        FlightRoute flightRoute = modelMapper.map(flightRouteDTO, FlightRoute.class);
        this.flightRouteList.add(flightRoute);
        return flightRouteDTO;
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutes() {
        return this.flightRouteDTOList = this.flightRouteList.stream()
                .map(flightRoute -> modelMapper.map(flightRoute, FlightRouteDTO.class))
                .collect(Collectors.toList());

    }
}

