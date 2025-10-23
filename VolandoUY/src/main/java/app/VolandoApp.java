package app;

import app.config.DBInitThread;
import factory.ControllerFactory;
import gui.MainFrame;

import javax.swing.*;

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
                ControllerFactory.getFlightController(),
                ControllerFactory.getBookingController(),
                ControllerFactory.getBuyPackageController(),
                ControllerFactory.getTicketController(),
                ControllerFactory.getSeatController(),
                ControllerFactory.getAirportController()
            );
        });

    }
}
