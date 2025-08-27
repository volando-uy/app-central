package gui.user;

import javax.swing.border.*;
import controllers.user.IUserController;
import gui.user.getUsers.GetUsersPanel;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;
import gui.user.updateUser.UpdateUserPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

public class UserPanel extends JPanel {

    private IUserController userController;

    private JPanel registerCustomerPanel;
    private JPanel registerAirlinePanel;
    private JPanel updateUserPanel;
    private JPanel getUsersPanel;
    
    private JPanel contentPanel;

    public UserPanel(IUserController userController) {
        this.userController = userController;
        initComponents();
        initPanels();
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initPanels() {
        registerCustomerPanel = new RegisterCustomerPanel(userController);
        registerAirlinePanel = new RegisterAirlinePanel(userController);
        updateUserPanel = new UpdateUserPanel(userController);
        getUsersPanel = new GetUsersPanel(userController);
    }

    private void initListeners() {
        registerCustomerBtn.addMouseListener(createListener(registerCustomerPanel));
        registerAirlineBtn.addMouseListener(createListener(registerAirlinePanel));
        updateUserBtn.addMouseListener(createListener(updateUserPanel));
        getUsersBtn.addMouseListener(createListener(getUsersPanel));
    }

    private MouseAdapter createListener(JPanel panel) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es del mismo tipo que el nuevo panel
                    if (contentPanel.getClass().equals(panel.getClass())) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println(panel.getClass().getSimpleName() + " button clicked");
                // Crear el nuevo contentPanel con el contenido del panel proporcionado
                contentPanel = panel;
                add(contentPanel);
                revalidate();
                repaint();
            }
        };
    }
  
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton registerCustomerBtn;
    private JButton registerAirlineBtn;
    private JButton updateUserBtn;
    private JButton getUsersBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        registerCustomerBtn = new JButton();
        registerAirlineBtn = new JButton();
        updateUserBtn = new JButton();
        getUsersBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
        new javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e"
        ,javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM
        ,new java.awt.Font("D\u0069al\u006fg",java.awt.Font.BOLD,12)
        ,java.awt.Color.red), getBorder())); addPropertyChangeListener(
        new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("\u0062or\u0064er".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});
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

            //---- getUsersBtn ----
            getUsersBtn.setText("Listar Clientes");
            NavPanel.add(getUsersBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

