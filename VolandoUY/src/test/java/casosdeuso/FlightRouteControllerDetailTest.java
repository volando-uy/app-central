package casosdeuso;

import controllers.flightRoute.FlightRouteController;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.services.flightRoute.IFlightRouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FlightRouteControllerDetailTest {

    private IFlightRouteService flightRouteService;
    private FlightRouteController controller;

    @BeforeEach
    void setUp() {
        flightRouteService = Mockito.mock(IFlightRouteService.class);
        controller = new FlightRouteController(flightRouteService);
    }

    @Test
    void getFlightsFromRoute_printsFlightDetails() {
        // given
        String routeName = "Montevideo - Madrid";

        FlightRouteDTO route = new FlightRouteDTO();
        route.setName(routeName);
        route.setDescription("Ruta directa entre Montevideo y Madrid");
        route.setCreatedAt(LocalDate.now());
        route.setPriceTouristClass(750.0);
        route.setPriceBusinessClass(1200.0);
        route.setPriceExtraUnitBaggage(100.0);
        route.setOriginCityName("Montevideo");
        route.setDestinationCityName("Madrid");
        route.setAirlineNickname("Iberia");
        route.setCategories(List.of("Internacional", "Directo"));

        FlightDTO flight1 = new FlightDTO();
        flight1.setName("Vuelo001");
        flight1.setName(routeName);
        flight1.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight1.setCreatedAt(LocalDateTime.now().plusDays(1).plusHours(12));

        FlightDTO flight2 = new FlightDTO();
        flight2.setName("Vuelo002");
        flight2.setName(routeName);
        flight2.setDepartureTime(LocalDateTime.now().plusDays(2));
        flight2.setCreatedAt(LocalDateTime.now().plusDays(2).plusHours(12));

        List<FlightDTO> mockFlights = List.of(flight1, flight2);

        when(flightRouteService.getFlightsByRouteName(routeName))
                .thenReturn(mockFlights);

        // when
        List<FlightDTO> result = controller.getFlightsFromRoute(routeName);

        // then
        assertEquals(2, result.size());

        System.out.println("=== Detalles de la ruta ===");
        System.out.println("Nombre: " + route.getName());
        System.out.println("Descripción: " + route.getDescription());
        System.out.println("Origen: " + route.getOriginCityName());
        System.out.println("Destino: " + route.getDestinationCityName());
        System.out.println("Aerolínea: " + route.getAirlineNickname());
        System.out.println("Categorías: " + route.getCategories());
        System.out.println("Precio turista: " + route.getPriceTouristClass());
        System.out.println("Precio business: " + route.getPriceBusinessClass());
        System.out.println("Precio extra equipaje: " + route.getPriceExtraUnitBaggage());

        System.out.println("\n=== Vuelos disponibles ===");
        result.forEach(f ->
                System.out.println("Vuelo: " + f.getName() +
                        ", Salida: " + f.getDepartureTime() +
                        ", Llegada: " + f.getCreatedAt())
        );

        verify(flightRouteService, times(1)).getFlightsByRouteName(routeName);
    }
}
