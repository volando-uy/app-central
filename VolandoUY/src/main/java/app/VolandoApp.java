package app;

import controllers.user.IUserController;
import factory.ControllerFactory;
import gui.MainFrame;

import javax.swing.*;

public class VolandoApp {

    public static void main(String[] args) {
        IUserController usuarioController = ControllerFactory.crearUsuarioController();

        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(usuarioController);
            mainFrame.setVisible(true);
        });
    }
}
