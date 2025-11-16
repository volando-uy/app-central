package app;

import app.adapters.soap.SoapServicePublisher;
import app.adapters.soap.airport.AirportSoapAdapter;
import app.config.ConfigProperties;
import app.config.DBInitThread;
import factory.ControllerFactory;
import gui.MainFrame;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.swing.*;

public class VolandoApp {



    public static void main(String[] args) {

        new DBInitThread().start();

        SoapServicePublisher.publicarTodos();


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
