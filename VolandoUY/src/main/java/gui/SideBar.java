/*
 * Created by JFormDesigner on Mon Aug 18 23:06:29 UYT 2025
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

import lombok.Setter;
import org.postgresql.util.ReaderInputStream;

/**
 * @author ignac
 */
@Setter
public class SideBar extends JPanel {

    private MouseListener userManagementBtnListener;
    private MouseListener flightRouteManagementBtnListener;
    private MouseListener othersManagementBtnListener;
    private MouseListener flightRoutePackageManagementBtnListener;

    public SideBar(MouseListener userManagementBtnListener,
                   MouseListener flightRouteManagementBtnListener,
                   MouseListener othersManagementBtnListener,
                   MouseListener flightRoutePackageManagementBtnListener) {
        this.userManagementBtnListener = userManagementBtnListener;
        this.flightRouteManagementBtnListener = flightRouteManagementBtnListener;
        this.othersManagementBtnListener = othersManagementBtnListener;
        this.flightRoutePackageManagementBtnListener = flightRoutePackageManagementBtnListener;
        initComponents();
        initListeners();
        try {
            setBorder(new CompoundBorder(
                    new BevelBorder(BevelBorder.LOWERED),
                    new BevelBorder(BevelBorder.RAISED)
            ));
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        UserManagementBtn.addMouseListener(userManagementBtnListener);
        FlightRouteManagementBtn.addMouseListener(flightRouteManagementBtnListener);
        FlightRoutePackageManagementBtn.addMouseListener(flightRoutePackageManagementBtnListener);
        OthersManagementBtn.addMouseListener(othersManagementBtnListener);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        Icon = new JLabel();
        MenuPanel = new JPanel();
        UserManagementBtn = new JButton();
        FlightManagementBtn = new JButton();
        FlightRoutePackageManagementBtn = new JButton();
        ReservationsManagementBtn = new JButton();
        FlightRouteManagementBtn = new JButton();
        OthersManagementBtn = new JButton();

        //======== this ========
        setBackground(new Color(0x14213d));
        setPreferredSize(new Dimension(160, 600));
        setMinimumSize(new Dimension(80, 300));
        setMaximumSize(new Dimension(160, 600));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
        EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing
        .border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),
        java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))
        throw new RuntimeException();}});
        setLayout(new BorderLayout());

        //---- Icon ----
        Icon.setIcon(new ImageIcon(getClass().getResource("/volando_uy_logo_resized.png")));
        add(Icon, BorderLayout.PAGE_START);

        //======== MenuPanel ========
        {
            MenuPanel.setBackground(new Color(0x14213d));
            MenuPanel.setMaximumSize(new Dimension(160, 470));
            MenuPanel.setMinimumSize(new Dimension(160, 470));

            //---- UserManagementBtn ----
            UserManagementBtn.setText("Usuarios");
            UserManagementBtn.setForeground(new Color(0x3b82f6));
            UserManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));

            //---- FlightManagementBtn ----
            FlightManagementBtn.setText("Vuelos");
            FlightManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightManagementBtn.setForeground(new Color(0x3b82f6));

            //---- FlightRoutePackageManagementBtn ----
            FlightRoutePackageManagementBtn.setText("Paquetes de RV");
            FlightRoutePackageManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightRoutePackageManagementBtn.setForeground(new Color(0x3b82f6));

            //---- ReservationsManagementBtn ----
            ReservationsManagementBtn.setText("Reservas");
            ReservationsManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            ReservationsManagementBtn.setForeground(new Color(0x3b82f6));

            //---- FlightRouteManagementBtn ----
            FlightRouteManagementBtn.setText("Rutas de Vuelos");
            FlightRouteManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightRouteManagementBtn.setForeground(new Color(0x3b82f6));

            //---- OthersManagementBtn ----
            OthersManagementBtn.setText("Otros (Cat., Ciud.)");
            OthersManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            OthersManagementBtn.setForeground(new Color(0x3b82f6));

            GroupLayout MenuPanelLayout = new GroupLayout(MenuPanel);
            MenuPanel.setLayout(MenuPanelLayout);
            MenuPanelLayout.setHorizontalGroup(
                MenuPanelLayout.createParallelGroup()
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGroup(MenuPanelLayout.createParallelGroup()
                            .addComponent(FlightRouteManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(FlightManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(FlightRoutePackageManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ReservationsManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addComponent(OthersManagementBtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
            );
            MenuPanelLayout.setVerticalGroup(
                MenuPanelLayout.createParallelGroup()
                    .addGroup(MenuPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(UserManagementBtn)
                        .addGap(30, 30, 30)
                        .addComponent(FlightManagementBtn)
                        .addGap(30, 30, 30)
                        .addComponent(FlightRouteManagementBtn)
                        .addGap(30, 30, 30)
                        .addComponent(FlightRoutePackageManagementBtn)
                        .addGap(30, 30, 30)
                        .addComponent(ReservationsManagementBtn)
                        .addGap(36, 36, 36)
                        .addComponent(OthersManagementBtn)
                        .addContainerGap(80, Short.MAX_VALUE))
            );
        }
        add(MenuPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JLabel Icon;
    private JPanel MenuPanel;
    private JButton UserManagementBtn;
    private JButton FlightManagementBtn;
    private JButton FlightRoutePackageManagementBtn;
    private JButton ReservationsManagementBtn;
    private JButton FlightRouteManagementBtn;
    private JButton OthersManagementBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
