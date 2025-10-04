package domain.services.flightRoute;

import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.enums.EnumEstatusRuta;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.RepositoryFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import infra.repository.flightroute.FlightRouteRepository;
import infra.repository.flightroute.IFlightRouteRepository;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.constants.Images;
import shared.utils.CustomModelMapper;
import shared.utils.ImageProcessor;
import shared.utils.ValidatorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightRouteService implements IFlightRouteService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    private final IFlightRouteRepository flightRouteRepository;

    @Setter
    private ICategoryService categoryService;

    @Setter
    private ICityService cityService;

    @Setter
    private IUserService userService;

    public FlightRouteService() {
        this.flightRouteRepository = RepositoryFactory.getFlightRouteRepository();
    }


    @Override
    public BaseFlightRouteDTO createFlightRoute(
            BaseFlightRouteDTO baseFlightRouteDTO,
            String originCityName,
            String destinationCityName,
            String airlineNickname,
            List<String> categoriesNames,
            File imageFile
    ) {
        // Comprobar que la ruta de vuelo no exista
        if (existFlightRoute(baseFlightRouteDTO.getName())) {
            throw new UnsupportedOperationException(
                    String.format(ErrorMessages.ERR_FLIGHT_ROUTE_ALREADY_EXISTS, baseFlightRouteDTO.getName()));
        }

        // Obtener todos las entidades relacionadas
        // Tira throw si ya existe
        Airline airline = userService.getAirlineByNickname(airlineNickname, true);
        City originCity = cityService.getCityByName(originCityName);
        if (originCity == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, originCityName));
        }
        City destinationCity = cityService.getCityByName(destinationCityName);
        if (destinationCity == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CITY_NOT_FOUND, destinationCityName));
        }

        // Crear la lista de las categorias (si es que hay)
        List<Category> categories = new ArrayList<>();
        if (categoriesNames != null) {
            for (String categoryName : categoriesNames) {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category == null) {
                    throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName));
                }
                categories.add(category);
            }
        }

        // Crear la nueva ruta de vuelo
        FlightRoute flightRoute = customModelMapper.map(baseFlightRouteDTO, FlightRoute.class);
        flightRoute.setOriginCity(originCity);
        flightRoute.setDestinationCity(destinationCity);
        flightRoute.setAirline(airline);
        flightRoute.setCategories(categories);
        flightRoute.setFlights(new ArrayList<>());
        flightRoute.setStatus(EnumEstatusRuta.SIN_ESTADO);


        // Validar antes del primer save
        ValidatorUtil.validate(flightRoute);

        // Cargar la imagen al servidor si tiene
        if (imageFile != null) {
            String imagePath = Images.AIRLINES_PATH + airline.getNickname() + Images.FORMAT_DEFAULT;
            String uploadedImagePath = ImageProcessor.uploadImage(imageFile, imagePath);
            flightRoute.setImage(uploadedImagePath);
        } else {
            flightRoute.setImage(Images.FLIGHT_ROUTE_DEFAULT);
        }

        // Guardar la ruta de vuelo
        flightRouteRepository.createFlightRoute(flightRoute, airline);

        // Devolver el DTO mapeado
        return customModelMapper.map(flightRoute, BaseFlightRouteDTO.class);
    }

    @Override
    public boolean existFlightRoute(String name) {
        return flightRouteRepository.existsByName(name);
    }

    @Override
    public List<FlightRouteDTO> getFlightRoutesDetailsByAirlineNickname(String airlineNickname, boolean full) {
        List<FlightRoute> fr = (List<FlightRoute>) (full ? flightRouteRepository.getFullAllByAirlineNickname(airlineNickname) : flightRouteRepository.getAllByAirlineNickname(airlineNickname));
        if (fr == null) {
            return null;
        }

        return fr.stream()
                .map(route -> full ? customModelMapper.mapFullFlightRoute(route) : customModelMapper.map(route, FlightRouteDTO.class))
                .toList();
    }

    @Override
    public List<FlightRouteDTO> getFlightRoutesDetailsByPackageName(String packageName, boolean full) {
        List<FlightRoute> fr = full ? flightRouteRepository.getFullAllByPackageName(packageName) : flightRouteRepository.getAllByPackageName(packageName);
        if (fr == null) {
            return List.of();
        }

        return fr.stream()
                .map(route -> full ? customModelMapper.mapFullFlightRoute(route) : customModelMapper.map(route, FlightRouteDTO.class))
                .toList();
    }


    @Override
    public FlightRoute getFlightRouteByName(String routeName, boolean full) {
        // Comprobar que la ruta de vuelo exista
        return full ? flightRouteRepository.getFullByName(routeName) : flightRouteRepository.getByName(routeName);
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName, boolean full) {
        // Comprobar que la ruta de vuelo exista
        // Tira throw si no existe
        FlightRoute flightRoute = this.getFlightRouteByName(routeName, full);

        if (full){
            return customModelMapper.mapFullFlightRoute(flightRoute);
        } else {
            return customModelMapper.mapBaseFlightRoute(flightRoute);
        }
    }

    @Override
    public void setStatusFlightRouteByName(String routeName, boolean confirmed) {
        FlightRoute flightRoute = this.getFlightRouteByName(routeName, false);
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName));
        }

        EnumEstatusRuta status = confirmed ? EnumEstatusRuta.CONFIRMADA : EnumEstatusRuta.RECHAZADA;

        flightRoute.setStatus(status);
        flightRouteRepository.update(flightRoute);
    }
}


