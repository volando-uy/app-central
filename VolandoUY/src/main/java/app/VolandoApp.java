package app;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import factory.ControllerFactory;
import gui.MainFrame;

import javax.swing.*;

public class VolandoApp {

    public static void main(String[] args) {
        IUserController usuarioController = ControllerFactory.createUserController();
        IFlightRouteController flightRouteController = ControllerFactory.createFlightRouteController();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(usuarioController, flightRouteController);
            mainFrame.setVisible(true);
        });
    }
}
