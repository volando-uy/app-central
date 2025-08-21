package controllers.flightRoutePackage;


import controllers.flight.IFlightController;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.user.Customer;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.flight.IFlightService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import factory.UserFactoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class FlightRoutePackageControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private IFlightRoutePackageService  flightRoutePackageService;

    @InjectMocks
    private IFlightRoutePackageController flightRoutePackageController;


    @BeforeEach
    void setUp() {
        flightRoutePackageService = mock(FlightRoutePackageService.class);
        modelMapper = mock(ModelMapper.class);
        flightRoutePackageController = new FlightRoutePackageController(flightRoutePackageService);
    }


    @Test
    @DisplayName("Debe llamar a createFlight correctamente")
    void createFlightRoutePackage_shouldCallTheServiceCorrectly() {
        FlightRoutePackageDTO flightRoutePackageDTO = new FlightRoutePackageDTO();
        flightRoutePackageDTO.setName("Apa");

        // Act
        flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO);

        // Assert
        verify(flightRoutePackageService).addFlightRoutePackage(flightRoutePackageDTO);
    }

    @Test
    @DisplayName("Debe llamar a getAllFlights correctamente")
    void getAllFlights_shouldCallTheServiceCorrectly() {
        // Act
        flightRoutePackageController.getAllFlightRoutePackage();

        // Assert
        verify(flightRoutePackageService).getAllFlightRoutePackage();
    }


}

