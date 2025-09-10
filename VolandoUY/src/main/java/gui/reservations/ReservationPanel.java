/*
 * Created by JFormDesigner on Sat Aug 30 16:14:51 UYT 2025
 */

package gui.reservations;

import controllers.booking.IBookingController;
import controllers.buyPackage.IBuyPackageController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.reservations.getUserReservation.GetUserReservationPanel;
import gui.flightRoutePackage.packagePurchase.PackagePurchasePanel;
import gui.reservations.bookFlight.BookFlightPanel;

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
    private IFlightRouteController flightRouteController;
    private IBookingController bookingController;
    private IFlightController flightController;
    private IBuyPackageController buyPackageController;


    private GetUserReservationPanel userReservationPanel;
    private JPanel packagePurchasePanel;
    private JPanel bookFlightPanel;
    private JPanel contentPanel;
    public ReservationPanel(
            IUserController userController,
            IFlightRoutePackageController flightRoutePackageController,
            IFlightRouteController flightRouteController,
            IFlightController flightController,
            IBookingController bookingController,
            IBuyPackageController buyPackageController
    ) {
        this.userController = userController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.flightRouteController = flightRouteController ;
        this.flightController = flightController ;
        this.bookingController = bookingController;
        this.buyPackageController = buyPackageController;
        initPanels();
        initComponents();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    private void initPanels() {
        bookFlightPanel = new BookFlightPanel(userController, flightRouteController  , flightController , bookingController);
        userReservationPanel = new GetUserReservationPanel(userController, flightRouteController, flightController, flightRoutePackageController, bookingController, buyPackageController);
    }

    private void initListeners() {
        createReservationBtn.addMouseListener(createListener(bookFlightPanel));
        getUsersBtn.addMouseListener(createListener(userReservationPanel));
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

                System.out.println(panel.getClass().getSimpleName() + " button clicked");
                contentPanel = panel;
                add(contentPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        };
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        NavPanel = new JPanel();
        createReservationBtn = new JButton();
        getUsersBtn = new JButton();
        null01Btn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
        EmptyBorder( 0, 0, 0, 0) , "JFor\u006dDesi\u0067ner \u0045valu\u0061tion", javax. swing. border. TitledBorder. CENTER, javax. swing
        . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
        java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( )
        { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("bord\u0065r" .equals (e .getPropertyName () ))
        throw new RuntimeException( ); }} );
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

            //---- getUsersBtn ----
            getUsersBtn.setText("\ud83d\udcc4Consultar Usuarios");
            NavPanel.add(getUsersBtn);

            //---- null01Btn ----
            null01Btn.setText("...");
            NavPanel.add(null01Btn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JPanel NavPanel;
    private JButton createReservationBtn;
    private JButton getUsersBtn;
    private JButton null01Btn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
