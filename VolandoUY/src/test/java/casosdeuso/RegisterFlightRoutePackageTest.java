package casosdeuso;

import controllers.flightRoutePackage.FlightRoutePackageController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.UserController;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class RegisterFlightRoutePackageTest {
    IFlightRoutePackageController  flightRoutePackageController;
    IFlightRoutePackageService flightRoutePackageService;

    @BeforeEach
    void setUp() {

         ModelMapper modelMapper = ControllerFactory.getModelMapper();
         flightRoutePackageService =  new FlightRoutePackageService(modelMapper);
         flightRoutePackageController = new FlightRoutePackageController(flightRoutePackageService);

    }

        @Test
        public void registerFlightRoutePackage() {
            FlightRoutePackageDTO flightRoutePackageDTO = new FlightRoutePackageDTO();
            flightRoutePackageDTO.setName("Test Package");
            flightRoutePackageDTO.setDescription("This is a test package for flight routes.");
            flightRoutePackageDTO.setDiscount(40);
            flightRoutePackageDTO.setValidityPeriodDays(4);
            flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO);
            System.out.println("Flight Route Package created successfully: " + flightRoutePackageDTO.getName());
            FlightRoutePackageDTO createFlightRoutePackage = flightRoutePackageController.getFlightRoutePackage(flightRoutePackageDTO.getName());
            assertEquals(flightRoutePackageDTO.getName() , createFlightRoutePackage.getName());




            // Assert
            // Here you would typically verify that the package was created successfully,
            // but for this example, we will just print a message.
        }


    @Test
    public void registerFlightRoutePackageFalse() {
        FlightRoutePackageDTO flightRoutePackageDTO1 = new FlightRoutePackageDTO();
        flightRoutePackageDTO1.setName("Test Package");
        flightRoutePackageDTO1.setDescription("This is a test package for flight routes.");
        flightRoutePackageDTO1.setDiscount(40);
        flightRoutePackageDTO1.setValidityPeriodDays(4);
        flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO1);
        System.out.println("Flight Route Package created successfully: " + flightRoutePackageDTO1.getName());
        FlightRoutePackageDTO createFlightRoutePackage1 = flightRoutePackageController.getFlightRoutePackage(flightRoutePackageDTO1.getName());

        FlightRoutePackageDTO flightRoutePackageDTO2 = new FlightRoutePackageDTO();
        flightRoutePackageDTO2.setName("Test No");
        flightRoutePackageDTO2.setDescription("This is a test package for flight routes.");
        flightRoutePackageDTO2.setDiscount(40);
        flightRoutePackageDTO2.setValidityPeriodDays(4);
        flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO2);
        System.out.println("Flight Route Package created successfully: " + flightRoutePackageDTO2.getName());
        FlightRoutePackageDTO createFlightRoutePackage2 = flightRoutePackageController.getFlightRoutePackage(flightRoutePackageDTO2.getName());
        assertNotEquals( createFlightRoutePackage1.getName() , createFlightRoutePackage2.getName());




        // Assert
        // Here you would typically verify that the package was created successfully,
        // but for this example, we will just print a message.
    }
    }
