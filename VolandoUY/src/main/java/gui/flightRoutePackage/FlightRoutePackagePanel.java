package gui.flightRoutePackage;

import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.flightRoutePackage.addFlightRouteToPackage.AddFlightRouteToPackagePanel;
import gui.flightRoutePackage.createFlightRoutePackage.CreateFlightRoutePackagePanel;
import gui.flightRoutePackage.getFlightRoutesPackages.GetFlightRoutesPackagesPanel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlightRoutePackagePanel extends JPanel {

    private IFlightRoutePackageController flightRoutePackageController;
    private IFlightRouteController flightRouteController;
    private IUserController userController;
    
    private JPanel createFlightRoutePackagePanel;
    private JPanel addFlightRouteToPackagePanel;
    private JPanel getFlightRoutePackagePanel;

    private JPanel contentPanel;

    public FlightRoutePackagePanel(
            IFlightRoutePackageController flightRoutePackageController,
            IFlightRouteController flightRouteController,
            IUserController userController

    ) {
        this.flightRoutePackageController = flightRoutePackageController;
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        initComponents();
        initPanels();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch ( Exception ignored ) {
        }
    }
    
    private void initPanels() {
        createFlightRoutePackagePanel = new CreateFlightRoutePackagePanel(flightRoutePackageController);
        addFlightRouteToPackagePanel = new AddFlightRouteToPackagePanel(flightRoutePackageController, flightRouteController, userController);
        getFlightRoutePackagePanel = new GetFlightRoutesPackagesPanel(flightRoutePackageController);
    }

    private void initListeners() {
        createFlightRoutePackageBtn.addMouseListener(createListener(createFlightRoutePackagePanel));
        addFlightRouteToPackageBtn.addMouseListener(createListener(addFlightRouteToPackagePanel));
        getFlightRoutePackagesBtn.addMouseListener(createListener(getFlightRoutePackagePanel));
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
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel NavPanel;
    private JButton createFlightRoutePackageBtn;
    private JButton addFlightRouteToPackageBtn;
    private JButton getFlightRoutePackagesBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        NavPanel = new JPanel();
        createFlightRoutePackageBtn = new JButton();
        addFlightRouteToPackageBtn = new JButton();
        getFlightRoutePackagesBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xeeeeee));
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );
        setLayout(new BorderLayout());

        //======== NavPanel ========
        {
            NavPanel.setMinimumSize(new Dimension(640, 60));
            NavPanel.setPreferredSize(new Dimension(640, 60));
            NavPanel.setMaximumSize(new Dimension(640, 60));
            NavPanel.setBackground(new Color(0x386aac));
            NavPanel.setBorder(new EtchedBorder());
            NavPanel.setLayout(new GridLayout());

            //---- createFlightRoutePackageBtn ----
            createFlightRoutePackageBtn.setText("+ Crear Paquete");
            NavPanel.add(createFlightRoutePackageBtn);

            //---- addFlightRouteToPackageBtn ----
            addFlightRouteToPackageBtn.setText("\u279c A\u00f1adir R.V. a Paquete");
            NavPanel.add(addFlightRouteToPackageBtn);

            //---- getFlightRoutePackagesBtn ----
            getFlightRoutePackagesBtn.setText("\ud83d\udcc4 Listar Paquetes");
            NavPanel.add(getFlightRoutePackagesBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

