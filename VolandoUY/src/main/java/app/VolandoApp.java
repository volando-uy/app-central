package app;

import app.adapters.soap.SoapServicePublisher;
import app.config.ConfigProperties;
import app.config.DBInitThread;
import factory.ControllerFactory;
import gui.MainFrame;


import javax.swing.*;

public class VolandoApp {



    public static void main(String[] args) {
        System.out.println("SOAP URL: " + ConfigProperties.get("soap.ip") + ":" +
                ConfigProperties.getInt("soap.port") +
                ConfigProperties.get("soap.path"));

        SoapServicePublisher.publicarTodos();

        // Inicializar DB con JPA
//        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

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
