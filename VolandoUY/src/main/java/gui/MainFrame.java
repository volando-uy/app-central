package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import gui.user.UserFrame;

/**
 * Ventana principal de la aplicación Volando Uy
 * @author Nahu
 */
public class MainFrame extends JFrame {

    private IUserController userController;
    private IFlightRouteController flightRouteController;

    private SideBar sideBar;
    private JPanel mainPanel;

    public MainFrame(IUserController userController, IFlightRouteController flightRouteController) {
        sideBar = createSideBar();
        mainPanel = new JPanel();
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

    private void updateMainPanel(JPanel panel) {
        // Delete the current main panel if it exists
        remove(mainPanel);
        mainPanel = panel; // Update the main panel with the new content
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private SideBar createSideBar() {
        MouseListener userListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("User button clicked");
                // Clear the main panel and add the UserFrame
                updateMainPanel(new UserFrame());
            }
        };
        return new SideBar(userListener);
    }

}
