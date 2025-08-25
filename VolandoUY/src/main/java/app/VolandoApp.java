package app;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import controllers.flight.IFlightController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import factory.ControllerFactory;
import gui.MainFrame;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VolandoApp {

    public static void main(String[] args) {


        IUserController usuarioController = ControllerFactory.getUserController();
        IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();
        IFlightController flightController = ControllerFactory.getFlightController();
        ICategoryController categoryController = ControllerFactory.getCategoryController();
        ICityController cityController = ControllerFactory.getCityController();
        IFlightRoutePackageController flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();

        /* Creates users: customers and airlines */
//        usuarioController.registerCustomer(new CustomerDTO(
//                "customer1", "Aparicio", "waza@gmail.com", "Quian", "uruguayo", LocalDate.of(2004, 12, 12), "123123123", EnumTipoDocumento.CI
//        ));
//        usuarioController.registerCustomer(new CustomerDTO(
//                "customer2", "Nahuel", "waza2@gmail.com", "Gonzalez", "uruguayo", LocalDate.of(2003, 1, 1), "123123124", EnumTipoDocumento.CI
//        ));
//        usuarioController.registerAirline(new AirlineDTO(
//                "airline1", "Aerolíneas Argentinas", "aa@mail.com", "Aerolíneas Argentinas S.A.", "www.aerolineas.com.ar"
//        ));
//        usuarioController.registerAirline(new AirlineDTO(
//                "airline2", "LATAM Airlines", "ltm@gmail.com", "LATAM Airlines Group S.A.", "www.latam.com"
//        ));

        /* Creates flight route packages */
        flightRoutePackageController.createFlightRoutePackage(new FlightRoutePackageDTO(
                "package1", "Paquete de vuelo 1", 10, 50.0, LocalDate.now(), EnumTipoAsiento.TURISTA
        ));
        flightRoutePackageController.createFlightRoutePackage(new FlightRoutePackageDTO(
                "package2", "Paquete de vuelo 2", 20, 100.0, LocalDate.now().plusDays(1), EnumTipoAsiento.EJECUTIVO
        ));

        /* Creates categories y ciudad*/
        categoryController.createCategory(new CategoryDTO("category1"));
        categoryController.createCategory(new CategoryDTO("category2"));
        cityController.createCity(new CityDTO("San José", "Uruguay", 50.0, 50.0, new ArrayList<>()));
        cityController.createCity(new CityDTO("Montevideo", "Uruguay", 60.0, 60.0, new ArrayList<>()));

        /* Creates flight routes */
//        flightRouteController.createFlightRoute(new FlightRouteDTO(
//                "route1", "Ruta de vuelo 1", LocalDate.now(), 100.0, 200.0, 50.0, "San José", "Montevideo", "airline1", List.of("category1", "category2")
//        ));
//        flightRouteController.createFlightRoute(new FlightRouteDTO(
//                "route2", "Ruta de vuelo 2", LocalDate.now().plusDays(1), 150.0, 250.0, 75.0, "Montevideo", "San José", "airline2", List.of("category1")
//        ));

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(usuarioController, flightRouteController, categoryController, cityController, flightRoutePackageController , flightController);
            mainFrame.setVisible(true);
        });

        // Forzar conexión inicial
        System.out.println("Estableciendo conexión inicial con la base de datos...");
        EntityManager em = DBConnection.getEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SELECT 1").getSingleResult();
        em.getTransaction().commit();
        em.close();
        System.out.println("Conexión establecida.");
    }
}
