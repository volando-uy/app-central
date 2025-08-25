/*
 * Created by JFormDesigner on Mon Aug 18 23:06:29 UYT 2025
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import lombok.Setter;

/**
 * @author ignac
 */
@Setter
public class SideBar extends JPanel {

    private MouseListener userManagementBtnListener;
    private MouseListener flightRouteManagementBtnListener;
    private MouseListener othersManagementBtnListener;
    private MouseListener flightRoutePackageManagementBtnListener;
    private MouseListener flightManagementBtnListener;

    public SideBar(MouseListener userManagementBtnListener,
                   MouseListener flightRouteManagementBtnListener,
                   MouseListener othersManagementBtnListener,
                   MouseListener flightRoutePackageManagementBtnListener, MouseListener flightManagementBtnListener) {
        this.userManagementBtnListener = userManagementBtnListener;
        this.flightRouteManagementBtnListener = flightRouteManagementBtnListener;
        this.othersManagementBtnListener = othersManagementBtnListener;
        this.flightRoutePackageManagementBtnListener = flightRoutePackageManagementBtnListener;
        this.flightManagementBtnListener = flightManagementBtnListener;

        initComponents();
        initListeners();
        try {
//            setBorder(new CompoundBorder(
//                    new BevelBorder(BevelBorder.LOWERED),
//                    new BevelBorder(BevelBorder.RAISED)
//            ));
            setBorder(null);
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        UserManagementBtn.addMouseListener(userManagementBtnListener);
        FlightRouteManagementBtn.addMouseListener(flightRouteManagementBtnListener);
        FlightRoutePackageManagementBtn.addMouseListener(flightRoutePackageManagementBtnListener);
        OthersManagementBtn.addMouseListener(othersManagementBtnListener);
        FlightManagementBtn.addMouseListener(flightManagementBtnListener);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        MenuPanel = new JPanel();
        invisibleLabel = new JLabel();
        Icon = new JLabel();
        UserManagementBtn = new JButton();
        FlightManagementBtn = new JButton();
        FlightRouteManagementBtn = new JButton();
        FlightRoutePackageManagementBtn = new JButton();
        ReservationsManagementBtn = new JButton();
        OthersManagementBtn = new JButton();

        //======== this ========
        setBackground(new Color(0x14213d));
        setPreferredSize(new Dimension(160, 600));
        setMinimumSize(new Dimension(80, 300));
        setMaximumSize(new Dimension(160, 600));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
        javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax
        . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
        .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans.
        PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .
        equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
        setLayout(new BorderLayout());

        //======== MenuPanel ========
        {
            MenuPanel.setMaximumSize(new Dimension(160, 470));
            MenuPanel.setMinimumSize(new Dimension(160, 470));
            MenuPanel.setBackground(new Color(0x154d71));
            MenuPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)MenuPanel.getLayout()).columnWidths = new int[] {160, 0};
            ((GridBagLayout)MenuPanel.getLayout()).rowHeights = new int[] {0, 30, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)MenuPanel.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
            ((GridBagLayout)MenuPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            MenuPanel.add(invisibleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- Icon ----
            Icon.setIcon(new ImageIcon(getClass().getResource("/volando_uy_logo_resized_3.png")));
            Icon.setBackground(new Color(0x1c6ea4));
            MenuPanel.add(Icon, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- UserManagementBtn ----
            UserManagementBtn.setText("Usuarios");
            UserManagementBtn.setForeground(new Color(0x3b82f6));
            UserManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            UserManagementBtn.setPreferredSize(new Dimension(140, 34));
            UserManagementBtn.setMinimumSize(new Dimension(140, 34));
            UserManagementBtn.setMaximumSize(new Dimension(140, 34));
            UserManagementBtn.setOpaque(false);
            MenuPanel.add(UserManagementBtn, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- FlightManagementBtn ----
            FlightManagementBtn.setText("Vuelos");
            FlightManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightManagementBtn.setForeground(new Color(0x3b82f6));
            FlightManagementBtn.setPreferredSize(new Dimension(140, 34));
            FlightManagementBtn.setMinimumSize(new Dimension(140, 34));
            FlightManagementBtn.setMaximumSize(new Dimension(140, 34));
            FlightManagementBtn.setOpaque(false);
            MenuPanel.add(FlightManagementBtn, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- FlightRouteManagementBtn ----
            FlightRouteManagementBtn.setText("Rutas de Vuelos");
            FlightRouteManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightRouteManagementBtn.setForeground(new Color(0x3b82f6));
            FlightRouteManagementBtn.setPreferredSize(new Dimension(140, 34));
            FlightRouteManagementBtn.setMinimumSize(new Dimension(140, 34));
            FlightRouteManagementBtn.setMaximumSize(new Dimension(140, 34));
            FlightRouteManagementBtn.setOpaque(false);
            MenuPanel.add(FlightRouteManagementBtn, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- FlightRoutePackageManagementBtn ----
            FlightRoutePackageManagementBtn.setText("Paquetes de RV");
            FlightRoutePackageManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            FlightRoutePackageManagementBtn.setForeground(new Color(0x3b82f6));
            FlightRoutePackageManagementBtn.setPreferredSize(new Dimension(140, 34));
            FlightRoutePackageManagementBtn.setMinimumSize(new Dimension(140, 34));
            FlightRoutePackageManagementBtn.setMaximumSize(new Dimension(140, 34));
            FlightRoutePackageManagementBtn.setOpaque(false);
            MenuPanel.add(FlightRoutePackageManagementBtn, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- ReservationsManagementBtn ----
            ReservationsManagementBtn.setText("Reservas");
            ReservationsManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            ReservationsManagementBtn.setForeground(new Color(0x3b82f6));
            ReservationsManagementBtn.setPreferredSize(new Dimension(140, 34));
            ReservationsManagementBtn.setMinimumSize(new Dimension(140, 34));
            ReservationsManagementBtn.setMaximumSize(new Dimension(140, 34));
            ReservationsManagementBtn.setOpaque(false);
            MenuPanel.add(ReservationsManagementBtn, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));

            //---- OthersManagementBtn ----
            OthersManagementBtn.setText("Otros (Cat., Ciud.)");
            OthersManagementBtn.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
            OthersManagementBtn.setForeground(new Color(0x3b82f6));
            OthersManagementBtn.setOpaque(false);
            OthersManagementBtn.setPreferredSize(new Dimension(140, 34));
            OthersManagementBtn.setMinimumSize(new Dimension(140, 34));
            OthersManagementBtn.setMaximumSize(new Dimension(140, 34));
            MenuPanel.add(OthersManagementBtn, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 20, 0), 0, 0));
        }
        add(MenuPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel MenuPanel;
    private JLabel invisibleLabel;
    private JLabel Icon;
    private JButton UserManagementBtn;
    private JButton FlightManagementBtn;
    private JButton FlightRouteManagementBtn;
    private JButton FlightRoutePackageManagementBtn;
    private JButton ReservationsManagementBtn;
    private JButton OthersManagementBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
