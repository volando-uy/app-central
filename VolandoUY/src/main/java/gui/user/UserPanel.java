package gui.user;

import javax.swing.border.*;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;
import gui.user.updateUser.UpdateUserPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
                contentPanel = new RegisterCustomerPanel(userController);
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
                    // Verificar si el contentPanel ya es de tipo CreateCategoryPanel
                    if (contentPanel instanceof RegisterAirlinePanel) {
                        return;
                    }
                    remove(contentPanel);
                }
                System.out.println("Register Airline button clicked");
                // Crear el nuevo contentPanel con el contenido de registro de aerolínea
                contentPanel = new RegisterAirlinePanel(userController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        // Listener para el botón de actualización de usuario
        updateUserPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo UpdateUserPanel
                    if (contentPanel instanceof UpdateUserPanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Update User button clicked");
                // Crear el nuevo contentPanel con el contenido de actualización de usuario
                contentPanel = new UpdateUserPanel(userController);
                add(contentPanel);
                revalidate();
                repaint();
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
        updateUserBtn.addMouseListener(updateUserPanelListener);
        //button3.addMouseListener(getUserListener);
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

