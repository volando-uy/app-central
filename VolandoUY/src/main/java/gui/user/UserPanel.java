package gui.user;

import javax.swing.border.*;

import controllers.booking.IBookingController;
import controllers.buyPackage.IBuyPackageController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.seat.ISeatController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import controllers.utils.IUtilsController;
import gui.user.getUser.GetUserPanel;
import gui.user.registerAirline.RegisterAirlinePanel;
import gui.user.registerCustomer.RegisterCustomerPanel;
import gui.user.updateUser.UpdateUserPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

public class UserPanel extends JPanel {

    private IUserController userController;
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;
    private IFlightRoutePackageController flightRoutePackageController;
    private IBookingController bookingController;
    private IBuyPackageController buyPackageController;
    private ITicketController ticketController;
    private ISeatController seatController;
    private IUtilsController utilsController;


    private JPanel registerCustomerPanel;
    private JPanel registerAirlinePanel;
    private JPanel updateUserPanel;
    private JPanel getUsersPanel;
    
    private JPanel contentPanel;

    public UserPanel(
            IUserController userController,
            IFlightRouteController flightRouteController,
            IFlightController flightController,
            IFlightRoutePackageController flightRoutePackageController,
            IBookingController bookingController,
            IBuyPackageController buyPackageController,
            ITicketController ticketController,
            ISeatController seatController,
            IUtilsController utilsController
    ) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.flightController = flightController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.bookingController = bookingController;
        this.buyPackageController = buyPackageController;
        this.ticketController = ticketController;
        this.seatController = seatController;
        this.utilsController = utilsController;
        initComponents();
        initPanels();
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initPanels() {
        registerCustomerPanel = new RegisterCustomerPanel(userController, utilsController);
        registerAirlinePanel = new RegisterAirlinePanel(userController, utilsController);
        updateUserPanel = new UpdateUserPanel(userController);
        getUsersPanel = new GetUserPanel(userController, flightRouteController, flightController, flightRoutePackageController, bookingController, buyPackageController, ticketController, seatController);

    }

    private void initListeners() {
        registerCustomerBtn.addMouseListener(createListener(registerCustomerPanel));
        registerAirlineBtn.addMouseListener(createListener(registerAirlinePanel));
        updateUserBtn.addMouseListener(createListener(updateUserPanel));
        getUsersBtn.addMouseListener(createListener(getUsersPanel));
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
    private JButton registerCustomerBtn;
    private JButton registerAirlineBtn;
    private JButton updateUserBtn;
    private JButton getUsersBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        NavPanel = new JPanel();
        registerCustomerBtn = new JButton();
        registerAirlineBtn = new JButton();
        updateUserBtn = new JButton();
        getUsersBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax
        . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax. swing
        .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .
        Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 ) ,java . awt. Color .red
        ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override
        public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er" .equals ( e. getPropertyName (
        ) ) )throw new RuntimeException( ) ;} } );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- registerCustomerBtn ----
            registerCustomerBtn.setText("+ Registrar Cliente");
            NavPanel.add(registerCustomerBtn);

            //---- registerAirlineBtn ----
            registerAirlineBtn.setText("+ Registrar Aerolinea");
            NavPanel.add(registerAirlineBtn);

            //---- updateUserBtn ----
            updateUserBtn.setText("\u27f3 Modificar Usuario");
            NavPanel.add(updateUserBtn);

            //---- getUsersBtn ----
            getUsersBtn.setText("\ud83d\udcc4 Consultar Usuarios");
            NavPanel.add(getUsersBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

