package domain.services.flightRoute;

import domain.dtos.category.CategoryDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.flightRoute.FlightRoute;
import domain.services.category.ICategoryService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightRouteService implements IFlightRouteService {
    List<FlightRoute> flightRouteList = new ArrayList<>();

    private ICategoryService categoryService = ControllerFactory.getCategoryService();
    private ModelMapper modelMapper = ControllerFactory.getModelMapper();
    private List<FlightRouteDTO> flightRouteDTOList = new ArrayList<>();
    private IUserService userService = ControllerFactory.getUserService();

    @Override
    public boolean existFlightRoute(String name) {
        return this.flightRouteList.stream().anyMatch(flightRoute -> flightRoute.getName().equalsIgnoreCase(name));
    }

    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO, String airlineNickname) {
        if (existFlightRoute(flightRouteDTO.getName())) {
            throw new UnsupportedOperationException("Ya existe una ruta con el nombre: " + flightRouteDTO.getName());
        }
        //Asignar a la aereolinea, la ruta del vuelo
        AirlineDTO airlineDTO = userService.getAirlineByNickname(airlineNickname);
        if (airlineDTO == null) {
            throw new IllegalArgumentException("No existe la aerolínea: " + airlineNickname);
        }
        FlightRoute flightRoute = modelMapper.map(flightRouteDTO, FlightRoute.class);
        this.flightRouteList.add(flightRoute);
        // Asignar las categorías a la ruta de vuelo
        if (flightRouteDTO.getCategory() != null) {
            flightRouteDTO.getCategory().forEach(categoryDTO -> {
                // Verificar si la categoría existe
                if (categoryService.existsCategory(categoryDTO.getName())) {
                    // Si existe, agregarla a la ruta de vuelo
                    flightRoute.addCategory(modelMapper.map(categoryDTO, Category.class));
                } else {
                    // Si no existe, lanzar una excepción
                    throw new IllegalArgumentException("La categoría " + categoryDTO.getName() + " no existe.");
                }
            });
        }
        // Asignar la ruta de vuelo a la aerolínea
        airlineDTO.addFlightRoute(flightRouteDTO);

        // Guardar la aerolínea actualizada
        userService.updateUser(airlineNickname, airlineDTO);

        // Mapear la ruta de vuelo a FlightRouteDTO
        flightRouteDTO = modelMapper.map(flightRoute, FlightRouteDTO.class);
        // Agregar la ruta de vuelo a la lista de rutas de vuelo

        this.flightRouteDTOList.add(flightRouteDTO);

        return flightRouteDTO;
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutes() {
        return this.flightRouteDTOList = this.flightRouteList.stream()
                .map(flightRoute -> modelMapper.map(flightRoute, FlightRouteDTO.class))
                .collect(Collectors.toList());

    }

    //con el nickname de la aerolínea, devuelvo todas sus rutas
    public List<FlightRouteDTO> getFlightRoutesByAirline(String airlineNickname) {
//        Hay que agregar el userService si lo quieren hacer así
//        Se puede hacer sin usar el userService, filtrando por la aerolinea.
//
        AirlineDTO airline = userService.getAirlineByNickname(airlineNickname);
        System.out.println("Aereolinea: " + airline);
        if (airline == null) {
            throw new IllegalArgumentException("No existe la aerolínea: " + airlineNickname);
        }
        System.out.println(airline.getFlightRoutes());
        return airline.getFlightRoutes();
}

    // busca una ruta especifica en la lista de rutas y devuelve todo el detalle como un FlightRouteDTO
    public FlightRouteDTO getFlightRouteByName(String routeName) {
        return this.flightRouteList.stream()
                .filter(route -> route.getName().equalsIgnoreCase(routeName))
                .findFirst()
                .map(route -> modelMapper.map(route, FlightRouteDTO.class))
                .orElse(null);
    }

}
