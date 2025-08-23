package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.flightRoute.FlightRoutePanel;
import gui.flightRoutePackage.FlightRoutePackagePanel;
import gui.others.OtherPanel;
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

    private SideBar sideBar;
    private JPanel mainPanel;

    // 0: Default, 1: OtherPanel, 2: FlightPanel, 3: FlightRoutePanel, 4: FlightRoutePackagePanel, 5: ReservationsPanel, 6: OtherPanel
    private Integer mainPanelType;

    public MainFrame(IUserController userController, IFlightRouteController flightRouteController,
                     ICategoryController categoryController, ICityController cityController,
                     IFlightRoutePackageController flightRoutePackageController) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.categoryController = categoryController;
        this.cityController = cityController;
        this.flightRoutePackageController = flightRoutePackageController;
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

// create UI here...
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Volando Uy");
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(); // Default panel to initialize
        mainPanelType = 0;

        sideBar = createSideBar();

        // I want to use a BorderLayout to place the sidebar on the left and the main content on the right
        setLayout(new BorderLayout());
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
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
        MouseListener userManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("User Management button clicked");
                updateMainPanel(new UserPanel(userController), 1);
            }
        };

        MouseListener othersManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Others Management button clicked");
                updateMainPanel(new OtherPanel(categoryController, cityController), 6);
            }
        };

        MouseListener flightRoutesManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Flight Routes Management button clicked");
                updateMainPanel(new FlightRoutePanel(flightRouteController, userController, categoryController), 3);
            }
        };

        MouseListener flightRoutePackagesManagementBtnListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Flight Route Packages Management button clicked");
                updateMainPanel(new FlightRoutePackagePanel(flightRoutePackageController, flightRouteController, userController), 4);
            }
        };

        return new SideBar(userManagementBtnListener, flightRoutesManagementBtnListener, othersManagementBtnListener, flightRoutePackagesManagementBtnListener);
    }

}
