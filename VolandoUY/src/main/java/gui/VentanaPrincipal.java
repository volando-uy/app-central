package gui;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.dtos.user.ClienteDTO;
import domain.models.user.enums.EnumTipoDocumento;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private IUsuarioController usuarioController;

    public VentanaPrincipal(IUsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        initUI();
    }

    private void initUI() {
        setTitle("Volando App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new java.awt.GridLayout(3, 1)); // 3 rows, 1 column

        JLabel label = new JLabel("Bienvenido a Volando App");
        add(label);

        JButton crearClienteButton = new JButton("Crear Cliente");
        crearClienteButton.addActionListener(e -> {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNickname("cliente" + System.currentTimeMillis());
            clienteDTO.setNombre("Nombre");
            clienteDTO.setMail("asd@gmail.com");
            clienteDTO.setApellido("Apellido");
            clienteDTO.setFechaNacimiento(LocalDate.now());
            clienteDTO.setTipoDocumento(EnumTipoDocumento.CI);
            clienteDTO.setNumDocumento("12345678");
            clienteDTO.setNacionalidad("Uruguay");

            usuarioController.altaCliente(clienteDTO);
        });
        add(crearClienteButton);

        JButton mostrarClientesButton = new JButton("Mostrar Clientes");
        mostrarClientesButton.addActionListener(e -> {
            List<String> clientes = usuarioController.obtenerTodosLosNicknames();
            StringBuilder todosLosClientes = new StringBuilder("Clientes registrados:\n");
            for (String cliente : clientes) {
                todosLosClientes.append(cliente).append("\n");
            }
            JOptionPane.showMessageDialog(this, todosLosClientes);
        });
        add(mostrarClientesButton);
    }
}
