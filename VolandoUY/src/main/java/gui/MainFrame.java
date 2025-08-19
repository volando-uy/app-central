package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import gui.user.UserPanel;

/**
 * Ventana principal de la aplicación Volando Uy
 * @author Nahu
 */
public class MainFrame extends JFrame {

    private IUserController userController;
    private IFlightRouteController flightRouteController;

    private SideBar sideBar;
    private JPanel mainPanel;
    // 0: Default, 1: UserPanel, 2: FlightManagementPanel, 3: PackageManagementPanel, 4: AirlineManagementPanel, 5: ReservationsPanel
    private Integer mainPanelType;

    public MainFrame(IUserController uController, IFlightRouteController frController) {
        userController = uController;
        flightRouteController = frController;
        sideBar = createSideBar();
        mainPanel = new JPanel(); // Añadir un panel default
        mainPanelType = 0;
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Volando Uy");
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(); // Default panel to initialize

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
        MouseListener userListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateMainPanel(new UserPanel(userController), 1);
            }
        };
        return new SideBar(userListener);
    }

}
