/*
 * Created by JFormDesigner on Sun Aug 24 14:31:59 UYT 2025
 */

package gui.flight;

import controllers.booking.IBookingController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import gui.flight.createFlight.createFlightPanel;
import gui.flight.getFlight.GetFlightsPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author AparicioQuian
 *  private MouseListener createFlightRoutePanelListener;
 *         //button3.addMouseListener(getUserListener);
 */
public class FlightPanel extends JPanel {

    private IFlightController flightController;
    private IFlightRouteController flightRouteController;
    private IUserController userController;
    private IBookingController bookingController;

    private JPanel createFlightPanel;
    private JPanel getFlightsPanel;

    private JPanel contentPanel;

    public FlightPanel(
            IFlightController flightController,
            IFlightRouteController flightRouteController,
            IUserController userController,
            IBookingController bookingController
    ) {
        this.flightController = flightController;
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.bookingController = bookingController;
        initComponents();
        initPanels();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch ( Exception ignored ) {
        }
    }

    private void initPanels() {
        createFlightPanel = new createFlightPanel(flightController, flightRouteController, userController);
        getFlightsPanel = new GetFlightsPanel(flightController, userController, bookingController);
    }

    private void initListeners() {
        createFlightBtn.addMouseListener(createListener(createFlightPanel));
        listFlightBtn.addMouseListener(createListener(getFlightsPanel));
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        NavPanel = new JPanel();
        createFlightBtn = new JButton();
        listFlightBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder( 0
        , 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
        , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,
         getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- createFlightBtn ----
            createFlightBtn.setText("+ Crear Vuelo");
            NavPanel.add(createFlightBtn);

            //---- listFlightBtn ----
            listFlightBtn.setText("\ud83d\udcc4 Listar Vuelos");
            NavPanel.add(listFlightBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel NavPanel;
    private JButton createFlightBtn;
    private JButton listFlightBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
