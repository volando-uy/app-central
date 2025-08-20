package app;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.user.enums.EnumTipoDocumento;
import factory.ControllerFactory;
import gui.MainFrame;

import javax.swing.*;
import java.time.LocalDate;

public class VolandoApp {

    public static void main(String[] args) {
        IUserController usuarioController = ControllerFactory.getUserController();
        IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();
        ICategoryController categoryController = ControllerFactory.getCategoryController();
        ICityController cityController = ControllerFactory.getCityController();

        /* Creates users */
        usuarioController.registerCustomer(new CustomerDTO(
                "customer1", "Aparicio", "waza@gmail.com", "Quian", "uruguayo", LocalDate.of(2004, 12, 12), "123123123", EnumTipoDocumento.CI
        ));
        usuarioController.registerCustomer(new CustomerDTO(
                "customer2", "Nahuel", "waza2@gmail.com", "Gonzalez", "uruguayo", LocalDate.of(2003, 1, 1), "123123124", EnumTipoDocumento.CI
        ));
        usuarioController.registerAirline(new AirlineDTO(
                "airline1", "Aerolíneas Argentinas", "aa@mail.com", "Aerolíneas Argentinas S.A.", "www.aerolineas.com.ar", null
        ));
        usuarioController.registerAirline(new AirlineDTO(
                "airline2", "LATAM Airlines", "ltm@gmail.com", "LATAM Airlines Group S.A.", "www.latam.com", null
        ));

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(usuarioController, flightRouteController, categoryController, cityController);
            mainFrame.setVisible(true);
        });
    }
}
