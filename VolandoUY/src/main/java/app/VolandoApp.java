package app;

import app.adapters.soap.SoapServicePublisher;
import app.config.ConfigProperties;
import app.config.DBInitThread;
import factory.ControllerFactory;
import gui.MainFrame;


import javax.swing.*;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;

public class VolandoApp {
    static {
        try {
            PrintStream ps = new PrintStream(new File("volando.log"));
            System.setOut(ps);
            System.setErr(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            System.out.println("SOAP URL: " + ConfigProperties.get("soap.ip") + ":" +
                    ConfigProperties.getInt("soap.port") +
                    ConfigProperties.get("soap.path"));

            SoapServicePublisher.publicarTodos();

            new DBInitThread().start();

            SwingUtilities.invokeLater(() -> {
                try {
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
                } catch (Exception e) {
                    logException(e);
                }
            });

        } catch (Throwable t) {
            logException(t);
        }
    }

    private static void logException(Throwable t) {
        try (PrintWriter out = new PrintWriter("error.log")) {
            t.printStackTrace(out);
        } catch (Exception e) {
            // Worst case: still try to print to std err
            e.printStackTrace();
        }
    }

}
