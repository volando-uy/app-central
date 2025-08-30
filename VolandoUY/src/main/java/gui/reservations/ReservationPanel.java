/*
 * Created by JFormDesigner on Sat Aug 30 16:14:51 UYT 2025
 */

package gui.reservations;

import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.packagePurchase.IPackagePurchaseController;
import controllers.user.IUserController;
import gui.reservations.packagePurchase.PackagePurchasePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Nahu
 */
public class ReservationPanel extends JPanel {
    private IUserController userController;
    private IFlightRoutePackageController flightRoutePackageController;
    private IPackagePurchaseController packagePurchaseController;
    private JPanel packagePurchasePanel;
    private JPanel contentPanel;
    public ReservationPanel(IUserController userController,IFlightRoutePackageController flightRoutePackageController,IPackagePurchaseController packagePurchaseController) {
        this.userController = userController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.packagePurchaseController = packagePurchaseController;
        initComponents();
        initPanels();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    private void initPanels() {
        packagePurchasePanel = new PackagePurchasePanel(userController, flightRoutePackageController, packagePurchaseController);
    }
    private void initListeners() {
        packagePurchaseBtn.addMouseListener(createListener(packagePurchasePanel));
        // más adelante podés asignar nullBtn y null01Btn a otros paneles reales
    }

    private MouseAdapter createListener(JPanel panel) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    if (contentPanel.getClass().equals(panel.getClass())) {
                        return;
                    }
                    remove(contentPanel);
                }

                contentPanel = panel;
                add(contentPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        };
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        NavPanel = new JPanel();
        createReservationBtn = new JButton();
        packagePurchaseBtn = new JButton();
        nullBtn = new JButton();
        null01Btn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
        new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn"
        , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
        , new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 )
        ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- createReservationBtn ----
            createReservationBtn.setText("+ Reserva Vuelo");
            NavPanel.add(createReservationBtn);

            //---- packagePurchaseBtn ----
            packagePurchaseBtn.setText("+ Comprar Paquete");
            NavPanel.add(packagePurchaseBtn);

            //---- nullBtn ----
            nullBtn.setText("...");
            NavPanel.add(nullBtn);

            //---- null01Btn ----
            null01Btn.setText("...");
            NavPanel.add(null01Btn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JPanel NavPanel;
    private JButton createReservationBtn;
    private JButton packagePurchaseBtn;
    private JButton nullBtn;
    private JButton null01Btn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
