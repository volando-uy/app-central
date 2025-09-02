package app;

import app.config.DBInitThread;
import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import controllers.flight.IFlightController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VolandoApp {


    public static void main(String[] args) {

        new DBInitThread().start();

        SwingUtilities.invokeLater(() -> {
            new MainFrame(
                ControllerFactory.getUserController(),
                ControllerFactory.getFlightRouteController(),
                ControllerFactory.getCategoryController(),
                ControllerFactory.getCityController(),
                ControllerFactory.getFlightRoutePackageController(),
                ControllerFactory.getFlightController()
            );
        });

    }
}
