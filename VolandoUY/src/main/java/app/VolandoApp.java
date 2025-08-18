package app;

import controllers.user.IUserController;
import factory.ControllerFactory;
import gui.MainFrame1;

import javax.swing.*;

public class VolandoApp {

    public static void main(String[] args) {
        IUserController usuarioController = ControllerFactory.crearUsuarioController();

        SwingUtilities.invokeLater(() -> {
            MainFrame1 mainFrame1 = new MainFrame1();
            mainFrame1.setVisible(true);
        });
    }
}
