/*
 * Created by JFormDesigner on Sun Aug 24 14:31:59 UYT 2025
 */

package gui.flight;

import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.flight.createFlight.createFlightPanel;
import gui.flightRoute.createFlightRoute.CreateFlightRoutePanel;

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
    private MouseListener createFlightListener;
    private IFlightController flightController;
    private IFlightRouteController flightRouteController;
    private IUserController userController;

    private JPanel contentPanel;

    public FlightPanel( IFlightController flightController, IFlightRouteController flightRouteController, IUserController userController) {
        this.flightController = flightController;
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        initComponents();
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }
    private void initListeners() {
        // Listener para el botón de creacion de ruta de vuelo
        createFlightListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo CreateFlightRoutePanel
                    if (contentPanel instanceof createFlightPanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Create Flight button clicked");
                // Crear el nuevo contentPanel con el contenido de creacoion de ruta de vuelo
                contentPanel = new createFlightPanel(flightController, flightRouteController, userController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };
        createFlightBtn.addMouseListener(createFlightListener); 
     }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        NavPanel = new JPanel();
        createFlightBtn = new JButton();
        registerAirlineBtn = new JButton();
        updateUserBtn = new JButton();
        getUserBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );
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
            createFlightBtn.setText("Crear Vuelo");
            NavPanel.add(createFlightBtn);

            //---- registerAirlineBtn ----
            registerAirlineBtn.setText("---");
            NavPanel.add(registerAirlineBtn);

            //---- updateUserBtn ----
            updateUserBtn.setText("---");
            NavPanel.add(updateUserBtn);

            //---- getUserBtn ----
            getUserBtn.setText("---");
            NavPanel.add(getUserBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel NavPanel;
    private JButton createFlightBtn;
    private JButton registerAirlineBtn;
    private JButton updateUserBtn;
    private JButton getUserBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
