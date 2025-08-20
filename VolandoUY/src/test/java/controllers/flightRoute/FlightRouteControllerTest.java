//package controllers.flightRoute;
//
//import domain.services.flightRoute.IFlightRouteService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import domain.dtos.flightRoute.CategoryDTO;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//public class FlightRouteControllerTest {
//
//    @Mock
//    private IFlightRouteService flightRouteService;
//
//    @InjectMocks
//    private IFlightRouteController flightRouteController;
//
//    @BeforeEach
//    void setUp() {
//        flightRouteService = mock(IFlightRouteService.class);
//        flightRouteController = new FlightRouteController(flightRouteService);
//    }
//
//    @Test
//    @DisplayName("Debe llamar a createCategory correctamente")
//    void createCategory_shouldCallTheServiceCorrectly() {
//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setName("test");
//
//        // Act
//        flightRouteController.createCategory(categoryDTO);
//
//        // Assert
//        verify(flightRouteService).createCategory(categoryDTO);
//    }
//}
