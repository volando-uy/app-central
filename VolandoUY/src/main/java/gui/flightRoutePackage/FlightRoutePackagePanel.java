package gui.flightRoutePackage;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import gui.flightRoutePackage.addFlightRouteToPackage.AddFlightRouteToPackagePanel;
import gui.flightRoutePackage.createFlightRoutePackage.CreateFlightRoutePackagePanel;
import gui.flightRoutePackage.getFlightRoutePackage.GetFlightRoutePackagePanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlightRoutePackagePanel extends JPanel {

    private MouseListener createFlightRoutePackagePanelListener;
    private MouseListener addFlightRouteToPackagePanelListener;
    private MouseListener listPackagesPanelListener;
    private IFlightRoutePackageController flightRoutePackageController;
    private IFlightRouteController flightRouteController;
    private IUserController userController;

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
        initListeners();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        // Listener para el botón de creacion de ruta de vuelo
        createFlightRoutePackagePanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo CreateFlightRoutePanel
                    if (contentPanel instanceof CreateFlightRoutePackagePanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Create Flight Route button clicked");
                // Crear el nuevo contentPanel con el contenido de creacoion de ruta de vuelo
                contentPanel = new CreateFlightRoutePackagePanel(flightRoutePackageController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        listPackagesPanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contentPanel != null) {
                    if (contentPanel instanceof GetFlightRoutePackagePanel) return;
                    remove(contentPanel);
                }
                System.out.println("List Flight Route Packages button clicked");
                contentPanel = new GetFlightRoutePackagePanel(flightRoutePackageController);
                add(contentPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        };
        addFlightRouteToPackagePanelListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remover solo si ya existe un contentPanel
                if (contentPanel != null) {
                    // Verificar si el contentPanel ya es de tipo AddFlightRouteToPackagePanel
                    if (contentPanel instanceof AddFlightRouteToPackagePanel) {
                        return;
                    }
                    remove(contentPanel);
                }

                System.out.println("Add Flight Route to Package button clicked");
                // Crear el nuevo contentPanel con el contenido de adición de ruta de vuelo a paquete
                contentPanel = new AddFlightRouteToPackagePanel(flightRoutePackageController, flightRouteController, userController);
                add(contentPanel);
                revalidate();
                repaint();
            }
        };

        createFlightRoutePackageBtn.addMouseListener(createFlightRoutePackagePanelListener);
        addFlightRouteToPackageBtn.addMouseListener(addFlightRouteToPackagePanelListener);
        getPackageBtn.addMouseListener(listPackagesPanelListener);
        //button3.addMouseListener(getUserListener);
    }
  
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel NavPanel;
    private JButton createFlightRoutePackageBtn;
    private JButton addFlightRouteToPackageBtn;
    private JButton getPackageBtn;
    private JButton getUserBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        NavPanel = new JPanel();
        createFlightRoutePackageBtn = new JButton();
        addFlightRouteToPackageBtn = new JButton();
        getPackageBtn = new JButton();
        getUserBtn = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 600));
        setMinimumSize(new Dimension(640, 600));
        setMaximumSize(new Dimension(640, 600));
        setBackground(new Color(0xcccccc));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
        new javax.swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion"
        ,javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM
        ,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12)
        ,java.awt.Color.red), getBorder())); addPropertyChangeListener(
        new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});
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
            createFlightRoutePackageBtn.setText("Crear Paquete");
            NavPanel.add(createFlightRoutePackageBtn);

            //---- addFlightRouteToPackageBtn ----
            addFlightRouteToPackageBtn.setText("A\u00f1adir R.V. a Paquete");
            NavPanel.add(addFlightRouteToPackageBtn);

            //---- getPackageBtn ----
            getPackageBtn.setText("Listar Paquetes");
            NavPanel.add(getPackageBtn);

            //---- getUserBtn ----
            getUserBtn.setText("---");
            NavPanel.add(getUserBtn);
        }
        add(NavPanel, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}

