package gui.user;

import javax.swing.border.*;
import controllers.user.IUserController;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.user.enums.EnumTipoDocumento;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.Objects;

public class UserPanel extends JPanel {

    private MouseListener registerCustomerPanelListener;
    private MouseListener registerAirlinePanelListener;
    private MouseListener updateUserPanelListener;
    private MouseListener getUserPanelListener;

    private IUserController userController;
    
    private JPanel contentPanel;

    public UserPanel(IUserController uController) {
        userController = uController;
        initComponents();
        initListeners();
    }

    private void initListeners() {
        // Listener para el botón de registro de cliente
        registerCustomerPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo RegisterCustomerPanel
                    if (contentPanel instanceof RegisterCustomerPanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Register User button clicked");
                // Crear el nuevo contentPanel con el contenido de registro de usuario
                contentPanel = createRegisterConsumerPanel();
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        // Listener para el botón de registro de aerolínea
        registerAirlinePanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo RegisterAirlinePanel
                    if (contentPanel instanceof RegisterAirlinePanel) {
                        return;
                    }
                    remove(contentPanel);
                }
                System.out.println("Register Airline button clicked");
                // Crear el nuevo contentPanel con el contenido de registro de aerolínea
                contentPanel = createRegisterAirplinePanel();
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        // Listener para el botón de actualización de usuario
        updateUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Update User button clicked");
                // Implement the logic to update a user
            }
        };

        getUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Get User button clicked");
                // Implement the logic to get a user
            }
        };

        registerCustomerBtn.addMouseListener(registerCustomerPanelListener);
        registerAirlineBtn.addMouseListener(registerAirlinePanelListener);
        //button2.addMouseListener(updateUserPanelListener);
        //button3.addMouseListener(getUserListener);
    }

    private void registerUserListener() {

    }

    // Crea el JPanel que que se encarga de los registros del consumidor
    private JPanel createRegisterConsumerPanel() {
        RegisterCustomerPanel registerCustomerPanel = new RegisterCustomerPanel();
        // Crea e implementa la única función del listener de registro de usuario
        registerCustomerPanel.setCustomerFormListener(e -> {

            // Get all the info from the form and create a CustomerDTO
            String nickname = e.getNickname();
            String name = e.getName();
            String surname = e.getSurname();
            String mail = e.getMail();
            String citizenship = e.getCitizenship();
            LocalDate birthDate = e.getBirthDate();
            String id = e.getId();
            EnumTipoDocumento idType = e.getIdType();

            CustomerDTO customerDTO = new CustomerDTO(nickname, name, mail,
                    surname, citizenship, birthDate, id, idType);


            // Try to register the user using the userController
            CustomerDTO userCreated;
            try {
                userCreated = userController.registerCustomer(customerDTO);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show the new user in a JDialog
            JOptionPane.showMessageDialog(this,
                    "Nickname: " + userCreated.getNickname() +
                            "\nName: " + userCreated.getName() +
                            "\nSurname: " + userCreated.getSurname() +
                            "\nMail: " + userCreated.getMail() +
                            "\nCitizenship: " + userCreated.getCitizenship() +
                            "\nBirth Date: " + userCreated.getBirthDate() +
                            "\nID: " + userCreated.getId() +
                            "\nID Type: " + userCreated.getIdType(),
                    "User Registered",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });
        return registerCustomerPanel;
    }

    // Crea el JPanel que que se encarga de los registros de la aerolínea
    private JPanel createRegisterAirplinePanel() {
        RegisterAirlinePanel registerAirlinePanel = new RegisterAirlinePanel();
        // Crea e implementa la única función del listener de registro de aerolínea
        registerAirlinePanel.setAirlineFormListener(e -> {
            // Get all the info from the form and create an AirlineDTO
            String nickname = e.getNickname();
            String name = e.getName();
            String description = e.getDescription();
            String email = e.getMail();
            String web = e.getWeb();

            // Create the AirlineDTO based on the provided information
            AirlineDTO airlineDTO = null;
            if (Objects.equals(web, "")) {
                airlineDTO = new AirlineDTO(nickname, name, email, description);
            } else {
                airlineDTO = new AirlineDTO(nickname, name, email, description, web);
            }

            // Try to register the airline using the userController
            try {
                userController.registerAirline(airlineDTO);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar la aerolínea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the airline information string
            // If web is empty, it will not be included in the information string
            String airlineInfo = "Nickname: " + airlineDTO.getNickname() +
                    "\nName: " + airlineDTO.getName() +
                    "\nDescription: " + airlineDTO.getDescription() +
                    "\nEmail: " + airlineDTO.getMail();
            if (Objects.equals(web, "")) {
                airlineInfo += "\nWeb: " + airlineDTO.getWeb();
            }

            // Show the new airline in a JDialog
            JOptionPane.showMessageDialog(this, airlineInfo, "Aerolínea Registrada", JOptionPane.INFORMATION_MESSAGE);
        });
        return registerAirlinePanel;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton registerCustomerBtn;
    private JButton registerAirlineBtn;
    private JButton updateUserBtn;
    private JButton getUserBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        registerCustomerBtn = new JButton();
        registerAirlineBtn = new JButton();
        updateUserBtn = new JButton();
        getUserBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing
        .border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder
        .CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.
        awt.Font.BOLD,12),java.awt.Color.red), getBorder()))
        ; addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException();}})
        ;
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- registerCustomerBtn ----
            registerCustomerBtn.setText("Registrar Cliente");
            NavPanel.add(registerCustomerBtn);

            //---- registerAirlineBtn ----
            registerAirlineBtn.setText("Registrar Aerolinea");
            NavPanel.add(registerAirlineBtn);

            //---- updateUserBtn ----
            updateUserBtn.setText("Modificar Usuario");
            NavPanel.add(updateUserBtn);

            //---- getUserBtn ----
            getUserBtn.setText("Listar Clientes");
            NavPanel.add(getUserBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

