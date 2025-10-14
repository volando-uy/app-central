package gui.flightRoute;

import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import gui.flightRoute.createFlightRoute.CreateFlightRoutePanel;
import gui.flightRoute.getFlightRoutes.GetFlightRoutesPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlightRoutePanel extends JPanel {
 

    private IFlightRouteController flightRouteController;
    private IUserController userController;
    private IFlightController flightController;
    private ICategoryController categoryController;
    private IAirportController airportController;

    private JPanel createFlightRoutePanel;
    private JPanel getFlightsRoutesPanel;

    private JPanel contentPanel;

    public FlightRoutePanel(
            IFlightRouteController flightRouteController,
            IUserController userController,
            ICategoryController categoryController,
            IFlightController flightController,
            IAirportController airportController
    ) {
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.categoryController = categoryController;
        this.flightController = flightController;
        this.airportController = airportController;
        initComponents();
        initPanels();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch ( Exception ignored ) {}
    }

    private void initPanels() {
        createFlightRoutePanel = new CreateFlightRoutePanel(flightRouteController, userController, categoryController, airportController);
        getFlightsRoutesPanel = new GetFlightRoutesPanel(flightRouteController, userController, flightController);
    }

    private void initListeners() {
        createFlightRouteBtn.addMouseListener(createListener(createFlightRoutePanel));
        getFlightRouteBtn.addMouseListener(createListener(getFlightsRoutesPanel));
    }

    private MouseAdapter createListener(JPanel panel) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es del mismo tipo que el nuevo panel
                    if (contentPanel.getClass().equals(panel.getClass())) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println(panel.getClass().getSimpleName() + " button clicked");
                // Crear el nuevo contentPanel con el contenido del panel proporcionado
                contentPanel = panel;
                add(contentPanel);
                revalidate();
                repaint();
            }
        };
    }
  
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel NavPanel;
    private JButton createFlightRouteBtn;
    private JButton getFlightRouteBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        NavPanel = new JPanel();
        createFlightRouteBtn = new JButton();
        getFlightRouteBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
        0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
        . BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
        red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
        beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- createFlightRouteBtn ----
            createFlightRouteBtn.setText("+ Crear Ruta de Vuelo");
            NavPanel.add(createFlightRouteBtn);

            //---- getFlightRouteBtn ----
            getFlightRouteBtn.setText("\ud83d\udcc4 Listar Rutas de vuelo");
            NavPanel.add(getFlightRouteBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

