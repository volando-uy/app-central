package gui.flightRoute;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import gui.flightRoute.createFlightRoute.CreateFlightRoutePanel;
import gui.flightRoute.getFlightRoute.GetFlightRoutePanel;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;
import gui.user.updateUser.UpdateUserPanel;

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

    private MouseListener createFlightRoutePanelListener;
    private MouseListener listFlightRoutePanelListener;
    private IFlightRouteController flightRouteController;
    private IUserController userController;
    private ICategoryController categoryController;

    private JPanel contentPanel;

    public FlightRoutePanel(IFlightRouteController flightRouteController, IUserController userController, ICategoryController categoryController) {
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.categoryController = categoryController;
        initComponents();
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        // Listener para el botón de creacion de ruta de vuelo
        createFlightRoutePanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo CreateFlightRoutePanel
                    if (contentPanel instanceof CreateFlightRoutePanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Create Flight Route button clicked");
                // Crear el nuevo contentPanel con el contenido de creacoion de ruta de vuelo
                contentPanel = new CreateFlightRoutePanel(flightRouteController, userController, categoryController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };
        listFlightRoutePanelListener = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    if (contentPanel instanceof GetFlightRoutePanel) return;
                    remove(contentPanel);
                }
                System.out.println("List Flight Routes button clicked");
                contentPanel = new GetFlightRoutePanel(flightRouteController, userController);
                add(contentPanel, BorderLayout.CENTER);      // <-- CENTER
                revalidate();
                repaint();
            }
        };
        getFlightRouteBtn.addMouseListener(listFlightRoutePanelListener);
        createFlightRouteBtn.addMouseListener(createFlightRoutePanelListener);
        //button3.addMouseListener(getUserListener);
    }
  
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel NavPanel;
    private JButton createFlightRouteBtn;
    private JButton getFlightRouteBtn;
    private JButton updateUserBtn;
    private JButton getUserBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        NavPanel = new JPanel();
        createFlightRouteBtn = new JButton();
        getFlightRouteBtn = new JButton();
        updateUserBtn = new JButton();
        getUserBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
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
            createFlightRouteBtn.setText("Crear Ruta de Vuelo");
            NavPanel.add(createFlightRouteBtn);

            //---- getFlightRouteBtn ----
            getFlightRouteBtn.setText("Listar Rutas de vuelo");
            NavPanel.add(getFlightRouteBtn);

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
}

