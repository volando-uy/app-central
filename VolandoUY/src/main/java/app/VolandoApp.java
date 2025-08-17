package app;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.ClienteDTO;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import domain.models.user.enums.EnumTipoDocumento;
import factory.ControllerFactory;
import gui.VentanaPrincipal;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VolandoApp {

    public static void main(String[] args) {
        IUsuarioController usuarioController = ControllerFactory.crearUsuarioController();

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(usuarioController);
            ventanaPrincipal.setVisible(true);
        });
    }
}
