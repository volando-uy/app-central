package gui;

import java.awt.*;
import javax.swing.*;
import gui.user.UserFrame;

/**
 * Ventana principal de la aplicación Volando Uy
 * @author Nahu
 */
public class MainFrame1 extends JFrame {
    private CardLayout cardLayout; // << para manejar las vistas

    public MainFrame1() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Volando Uy");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(Jpanel1);

        // Inicializar CardLayout
        cardLayout = new CardLayout();
        RightSection.setLayout(cardLayout);

        // Registrar pantallas
        RightSection.add(new JLabel("Bienvenido a Volando Uy!", SwingConstants.CENTER), "MAIN"); // pantalla inicial
        RightSection.add(new UserFrame(), "USER"); // pantalla usuario

        // Mostrar MAIN al inicio
        cardLayout.show(RightSection, "MAIN");

        // Acción del botón usuario
        UserButton.addActionListener(e -> mostrarPanel("USER"));
    }

    private void mostrarPanel(String name) {
        cardLayout.show(RightSection, name);
    }
    private void mostrarUserPanel() {
        RightSection.removeAll();
        RightSection.add(new UserFrame(), BorderLayout.CENTER);
        RightSection.revalidate();
        RightSection.repaint();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        Jpanel1 = new JPanel();
        MainPanel = new JPanel();
        UserButton = new JButton();
        FlightManagementButton = new JButton();
        PackageManagementButton = new JButton();
        AirlineManagementButton = new JButton();
        ReservationsButton = new JButton();
        label1 = new JLabel();
        RightSection = new JPanel();

        //======== Jpanel1 ========
        {
            Jpanel1.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
            EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing
            .border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),
            java.awt.Color.red),Jpanel1. getBorder()));Jpanel1. addPropertyChangeListener(new java.beans.PropertyChangeListener()
            {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))
            throw new RuntimeException();}});
            Jpanel1.setLayout(null);

            //======== MainPanel ========
            {
                MainPanel.setBackground(new Color(0x14213d));
                MainPanel.setLayout(null);

                //---- UserButton ----
                UserButton.setText("Usuario");
                UserButton.setForeground(new Color(0x3b82f6));
                UserButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
                MainPanel.add(UserButton);
                UserButton.setBounds(0, 265, 170, UserButton.getPreferredSize().height);

                //---- FlightManagementButton ----
                FlightManagementButton.setText("Gesti\u00f3n de Vuelos");
                FlightManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
                FlightManagementButton.setForeground(new Color(0x3b82f6));
                MainPanel.add(FlightManagementButton);
                FlightManagementButton.setBounds(0, 315, 170, 34);

                //---- PackageManagementButton ----
                PackageManagementButton.setText("Gesti\u00f3n de Paquetes");
                PackageManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
                PackageManagementButton.setForeground(new Color(0x3b82f6));
                MainPanel.add(PackageManagementButton);
                PackageManagementButton.setBounds(0, 365, 170, 34);

                //---- AirlineManagementButton ----
                AirlineManagementButton.setText("Gesti\u00f3n de Aerol\u00ednea");
                AirlineManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
                AirlineManagementButton.setForeground(new Color(0x3b82f6));
                MainPanel.add(AirlineManagementButton);
                AirlineManagementButton.setBounds(0, 415, 170, 34);

                //---- ReservationsButton ----
                ReservationsButton.setText("Reservas");
                ReservationsButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
                ReservationsButton.setForeground(new Color(0x3b82f6));
                MainPanel.add(ReservationsButton);
                ReservationsButton.setBounds(0, 465, 170, 34);

                //---- label1 ----
                label1.setIcon(new ImageIcon(getClass().getResource("/volando_uy_logo_resized.png")));
                MainPanel.add(label1);
                label1.setBounds(5, 15, 160, 130);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < MainPanel.getComponentCount(); i++) {
                        Rectangle bounds = MainPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = MainPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    MainPanel.setMinimumSize(preferredSize);
                    MainPanel.setPreferredSize(preferredSize);
                }
            }
            Jpanel1.add(MainPanel);
            MainPanel.setBounds(0, 0, MainPanel.getPreferredSize().width, 600);

            //======== RightSection ========
            {

                GroupLayout RightSectionLayout = new GroupLayout(RightSection);
                RightSection.setLayout(RightSectionLayout);
                RightSectionLayout.setHorizontalGroup(
                    RightSectionLayout.createParallelGroup()
                        .addGap(0, 630, Short.MAX_VALUE)
                );
                RightSectionLayout.setVerticalGroup(
                    RightSectionLayout.createParallelGroup()
                        .addGap(0, 600, Short.MAX_VALUE)
                );
            }
            Jpanel1.add(RightSection);
            RightSection.setBounds(170, 0, 630, 600);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < Jpanel1.getComponentCount(); i++) {
                    Rectangle bounds = Jpanel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = Jpanel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                Jpanel1.setMinimumSize(preferredSize);
                Jpanel1.setPreferredSize(preferredSize);
            }
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JPanel Jpanel1;
    private JPanel MainPanel;
    private JButton UserButton;
    private JButton FlightManagementButton;
    private JButton PackageManagementButton;
    private JButton AirlineManagementButton;
    private JButton ReservationsButton;
    private JLabel label1;
    private JPanel RightSection;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
