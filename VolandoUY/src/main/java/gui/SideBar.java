/*
 * Created by JFormDesigner on Mon Aug 18 23:06:29 UYT 2025
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

import lombok.Setter;

/**
 * @author ignac
 */
public class SideBar extends JPanel {

    private MouseListener userButtonListener;

    public SideBar(MouseListener userListener) {
        userButtonListener = userListener;
        initComponents();
        initListeners();
    }

    private void initListeners() {
        UserButton.addMouseListener(userButtonListener);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        Icon = new JLabel();
        MenuPanel = new JPanel();
        UserButton = new JButton();
        FlightManagementButton = new JButton();
        PackageManagementButton = new JButton();
        AirlineManagementButton = new JButton();
        ReservationsButton = new JButton();

        //======== this ========
        setBackground(new Color(0x14213d));
        setPreferredSize(new Dimension(160, 600));
        setMinimumSize(new Dimension(80, 300));
        setMaximumSize(new Dimension(160, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
        . swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing
        . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
        Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
        ) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
        public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName (
        ) )) throw new RuntimeException( ); }} );
        setLayout(new BorderLayout());

        //---- Icon ----
        Icon.setIcon(new ImageIcon(getClass().getResource("/volando_uy_logo_resized.png")));
        add(Icon, BorderLayout.PAGE_START);

        //======== MenuPanel ========
        {
            MenuPanel.setBackground(new Color(0x14213d));
            MenuPanel.setMaximumSize(new Dimension(160, 470));
            MenuPanel.setMinimumSize(new Dimension(160, 470));

            //---- UserButton ----
            UserButton.setText("Usuario");
            UserButton.setForeground(new Color(0x3b82f6));
            UserButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));

            //---- FlightManagementButton ----
            FlightManagementButton.setText("Gesti\u00f3n de Vuelos");
            FlightManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightManagementButton.setForeground(new Color(0x3b82f6));

            //---- PackageManagementButton ----
            PackageManagementButton.setText("Gesti\u00f3n de Paquetes");
            PackageManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            PackageManagementButton.setForeground(new Color(0x3b82f6));

            //---- AirlineManagementButton ----
            AirlineManagementButton.setText("Gesti\u00f3n de Aerol\u00ednea");
            AirlineManagementButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            AirlineManagementButton.setForeground(new Color(0x3b82f6));

            //---- ReservationsButton ----
            ReservationsButton.setText("Reservas");
            ReservationsButton.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            ReservationsButton.setForeground(new Color(0x3b82f6));

            GroupLayout MenuPanelLayout = new GroupLayout(MenuPanel);
            MenuPanel.setLayout(MenuPanelLayout);
            MenuPanelLayout.setHorizontalGroup(
                MenuPanelLayout.createParallelGroup()
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup()
                            .addComponent(PackageManagementButton)
                            .addComponent(FlightManagementButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirlineManagementButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReservationsButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
            );
            MenuPanelLayout.setVerticalGroup(
                MenuPanelLayout.createParallelGroup()
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(UserButton)
                        .addGap(30, 30, 30)
                        .addComponent(FlightManagementButton)
                        .addGap(30, 30, 30)
                        .addComponent(PackageManagementButton)
                        .addGap(30, 30, 30)
                        .addComponent(AirlineManagementButton)
                        .addGap(30, 30, 30)
                        .addComponent(ReservationsButton)
                        .addContainerGap(150, Short.MAX_VALUE))
            );
        }
        add(MenuPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JLabel Icon;
    private JPanel MenuPanel;
    private JButton UserButton;
    private JButton FlightManagementButton;
    private JButton PackageManagementButton;
    private JButton AirlineManagementButton;
    private JButton ReservationsButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
