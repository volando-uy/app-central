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
    // listar todas las aerolíneas con formato airlinedto
    public List<AirlineDTO> getAllAirlines() {
        return airlines.stream()
        .map(a -> modelMapper.map(a, AirlineDTO.class))
        .toList();
    }
    //con el nickname de la aerolínea, devuelvo todas sus rutas
    public List<FlightRouteDTO> getRoutesByAirline(String airlineNickname) {
        AirlineDTO airline = (AirlineDTO) userService.getUserByNickname(airlineNickname);
        if (airline == null) {
            throw new IllegalArgumentException("No existe la aerolínea: " + airlineNickname);
        }
        return airline.getFlightRoutes();
    }

    // busca una ruta especifica en la lista de rutas y devuelve todo el detalle como un FlightRouteDTO
    public FlightRouteDTO getRouteDetail(String routeName) {
        for (FlightRoute route : flightRouteList) {
            if (route.getName().equalsIgnoreCase(routeName)) {
                return modelMapper.map(route, FlightRouteDTO.class);
            }
        }
        return null;
    }

}
