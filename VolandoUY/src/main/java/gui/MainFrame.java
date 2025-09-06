package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controllers.booking.IBookingController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.flight.FlightPanel;
import gui.flightRoute.FlightRoutePanel;
import gui.flightRoutePackage.FlightRoutePackagePanel;
import gui.others.OtherPanel;
import gui.reservations.ReservationPanel;
import gui.user.UserPanel;

/**
 * Ventana principal de la aplicación Volando Uy
 * @author Nahu
 */
public class MainFrame extends JFrame {

    private IUserController userController;
    private IFlightRouteController flightRouteController;
    private ICategoryController categoryController;
    private ICityController cityController;
    private IFlightRoutePackageController flightRoutePackageController;
    private IFlightController flightController;
    private IBookingController bookingController;

    private JPanel userPanel;
    private JPanel flightRoutePanel;
    private JPanel flightRoutePackagePanel;
    private JPanel flightPanel;
    private JPanel otherPanel;
    private JPanel reservationPanel;

    private SideBar sideBar;
    private JPanel mainPanel;

    // 0: Default, 1: OtherPanel, 2: FlightPanel, 3: FlightRoutePanel, 4: FlightRoutePackagePanel, 5: ReservationsPanel, 6: OtherPanel
    private Integer mainPanelType;

    public MainFrame(IUserController userController, IFlightRouteController flightRouteController,
                     ICategoryController categoryController, ICityController cityController,
                     IFlightRoutePackageController flightRoutePackageController , IFlightController flightController , IBookingController bookingController) {
        this.flightController = flightController;
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.categoryController = categoryController;
        this.cityController = cityController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.bookingController = bookingController;
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        // create UI here...
        initUI();
    }

    private void initUI() {

        // Obtener el splash. Se tiene que pasar por comando
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Volando Uy");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(43,45,48));

        mainPanel = new JPanel(); // Default panel to initialize
        mainPanelType = 0;

        sideBar = createSideBar();

        // I want to use a BorderLayout to place the sidebar on the left and the main content on the right
        setLayout(new BorderLayout());
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
        toFront();
    }

    private void updateMainPanel(JPanel panel, Integer panelType) {
        // Delete the current main panel if it exists
        if (mainPanelType.equals(panelType)) {
            return; // Same panel, no need to update
        }
        remove(mainPanel);
        mainPanel = panel; // Update the main panel with the new content
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
        mainPanelType = panelType; // Update the type of the main panel
    }


    private SideBar createSideBar() {
        userPanel = new UserPanel(userController);
        flightRoutePanel = new FlightRoutePanel(flightRouteController, userController, categoryController);
        flightRoutePackagePanel = new FlightRoutePackagePanel(flightRoutePackageController, flightRouteController, userController);
        flightPanel = new FlightPanel(flightController, flightRouteController, userController);
        otherPanel = new OtherPanel(categoryController, cityController);
        reservationPanel = new ReservationPanel(userController, flightRoutePackageController , flightRouteController , flightController , bookingController);

        MouseListener userManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("User Management button clicked");
                updateMainPanel(userPanel, 1);
            }
        };

        MouseListener othersManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Others Management button clicked");
                updateMainPanel(otherPanel, 6);
            }
        };

        MouseListener flightRoutesManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Flight Routes Management button clicked");
                updateMainPanel(flightRoutePanel, 3);
            }
        };

        MouseListener flightRoutePackagesManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Flight Route Packages Management button clicked");
                updateMainPanel(flightRoutePackagePanel, 4);
            }
        };

        MouseListener flightManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Flight Management button clicked");
                updateMainPanel(flightPanel, 2);
            }
        };
        MouseListener reservationsManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Reservations Management button clicked");
                updateMainPanel(reservationPanel, 5);
            }
        };
        return new SideBar(userManagementBtnListener, flightRoutesManagementBtnListener, othersManagementBtnListener, flightRoutePackagesManagementBtnListener, flightManagementBtnListener, reservationsManagementBtnListener);
    }

}
