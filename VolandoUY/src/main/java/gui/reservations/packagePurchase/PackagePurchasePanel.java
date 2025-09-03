/*
 * Created by JFormDesigner on Sat Aug 30 16:16:07 UYT 2025
 */

package gui.reservations.packagePurchase;

import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.services.user.IUserService;

import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Nahu
 */
public class PackagePurchasePanel extends JPanel {
    private IUserController userController;
    private IFlightRoutePackageController flightRoutePackageController;
    private List<FlightRoutePackageDTO> availablePackages;
    public PackagePurchasePanel(IUserController userController, IFlightRoutePackageController flightRoutePackageController) {
        this.userController = userController;
        this.flightRoutePackageController = flightRoutePackageController;
        initComponents();
        loadPackagesWithRoutes();
        loadClients();
        setupListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    private void loadPackagesWithRoutes() {
        listPackageComboBox.removeAllItems();
        availablePackages = flightRoutePackageController.getPackagesWithFlightRoutes();

        if (availablePackages == null || availablePackages.isEmpty()) {
            return;
        }

        for (FlightRoutePackageDTO dto : availablePackages) {
            listPackageComboBox.addItem(dto.getName()); // muestra solo el nombre
        }
    }
    private void setupListeners() {
        listPackageComboBox.addActionListener(e -> {
            int selectedIndex = listPackageComboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                FlightRoutePackageDTO selectedPackage = availablePackages.get(selectedIndex);
                updatePackageTable(selectedPackage);
                registerPurchaseBtn.setEnabled(true); // habilita botón cuando hay selección
            }
        });
        registerPurchaseBtn.addActionListener(e -> {
            try {
                int packageIndex = listPackageComboBox.getSelectedIndex();
                int clientIndex = listClientComboBox.getSelectedIndex();

                if (packageIndex < 0 || clientIndex < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Debes seleccionar un paquete y un cliente",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // obtener paquete seleccionado
                String selectedPackageName = availablePackages.get(packageIndex).getName();

                // obtener nickname del cliente desde el combo
                String clientText = (String) listClientComboBox.getSelectedItem();
                String nickname = clientText.substring(clientText.lastIndexOf("(") + 1, clientText.length() - 1);

                // llamar al controlador
                userController.addFlightRoutePackageToCustomer(nickname, selectedPackageName);

                JOptionPane.showMessageDialog(this,
                        "Compra registrada con éxito",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar la compra: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void updatePackageTable(FlightRoutePackageDTO selectedPackage) {
        String[] columnNames = {"Rutas incluidas"};
        List<String> routes = selectedPackage.getFlightRouteNames();
        Object[][] data = new Object[routes.size()][1];

        for (int i = 0; i < routes.size(); i++) {
            data[i][0] = routes.get(i);
        }

        packageTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                columnNames
        ));
    }
    private void loadClients() {
        listClientComboBox.removeAllItems();

        try {
            List<domain.dtos.user.UserDTO> allUsers = userController.getAllUsers();

            if (allUsers == null || allUsers.isEmpty()) {
                return;
            }

            for (domain.dtos.user.UserDTO user : allUsers) {
                if (user instanceof domain.dtos.user.CustomerDTO customer) {
                    listClientComboBox.addItem(customer.getName() + " " + customer.getSurname() + " (" + customer.getNickname() + ")");
                }
            }

            if (listClientComboBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron usuarios de tipo cliente", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        titleLabel = new JLabel();
        vSpacer18 = new JPanel(null);
        selectPackagePanel = new JPanel();
        packageLabel = new JLabel();
        listPackageComboBox = new JComboBox<>();
        vSpacer20 = new JPanel(null);
        selectAirlinePanel = new JPanel();
        clientLabel = new JLabel();
        listClientComboBox = new JComboBox<>();
        InfoPackagePanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        infoPackageLabel = new JLabel();
        infoPackageScrollPane = new JScrollPane();
        packageTable = new JTable();
        addBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        registerPurchaseBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (
        new javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e"
        , javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
        , new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 )
        , java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (
        new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 31, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- titleLabel ----
        titleLabel.setText("Registrar compra de paquete");
        titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //---- vSpacer18 ----
        vSpacer18.setMinimumSize(new Dimension(12, 70));
        vSpacer18.setPreferredSize(new Dimension(10, 100));
        vSpacer18.setOpaque(false);
        add(vSpacer18, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== selectPackagePanel ========
        {
            selectPackagePanel.setOpaque(false);
            selectPackagePanel.setLayout(new GridLayout(1, 3, 10, 0));

            //---- packageLabel ----
            packageLabel.setText("Selecciona el Paquete:");
            packageLabel.setHorizontalAlignment(SwingConstants.TRAILING);
            selectPackagePanel.add(packageLabel);

            //---- listPackageComboBox ----
            listPackageComboBox.setMinimumSize(new Dimension(150, 30));
            listPackageComboBox.setPreferredSize(new Dimension(150, 30));
            listPackageComboBox.setOpaque(false);
            selectPackagePanel.add(listPackageComboBox);
        }
        add(selectPackagePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer20 ----
        vSpacer20.setMinimumSize(new Dimension(12, 5));
        vSpacer20.setPreferredSize(new Dimension(10, 5));
        vSpacer20.setOpaque(false);
        add(vSpacer20, new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== selectAirlinePanel ========
        {
            selectAirlinePanel.setOpaque(false);
            selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

            //---- clientLabel ----
            clientLabel.setText("Selecciona el cliente:");
            clientLabel.setHorizontalAlignment(SwingConstants.TRAILING);
            selectAirlinePanel.add(clientLabel);

            //---- listClientComboBox ----
            listClientComboBox.setMinimumSize(new Dimension(150, 30));
            listClientComboBox.setPreferredSize(new Dimension(150, 30));
            listClientComboBox.setOpaque(false);
            selectAirlinePanel.add(listClientComboBox);
        }
        add(selectAirlinePanel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== InfoPackagePanel ========
        {
            InfoPackagePanel.setOpaque(false);
            InfoPackagePanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoPackagePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoPackagePanel.getLayout()).rowHeights = new int[] {0, 25, 43, 0, 0};
            ((GridBagLayout)InfoPackagePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoPackagePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoPackagePanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 20));
            vSpacer17.setPreferredSize(new Dimension(10, 20));
            vSpacer17.setOpaque(false);
            InfoPackagePanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoPackagePanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 100));
                firstRowPanel.setMinimumSize(new Dimension(510, 100));
                firstRowPanel.setMaximumSize(new Dimension(510, 510));
                firstRowPanel.setOpaque(false);
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- infoPackageLabel ----
                infoPackageLabel.setText("Info paquete:");
                infoPackageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                infoPackageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                infoPackageLabel.setPreferredSize(new Dimension(120, 30));
                infoPackageLabel.setMaximumSize(new Dimension(120, 30));
                infoPackageLabel.setMinimumSize(new Dimension(120, 30));
                firstRowPanel.add(infoPackageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== infoPackageScrollPane ========
                {
                    infoPackageScrollPane.setPreferredSize(new Dimension(300, 100));
                    infoPackageScrollPane.setMinimumSize(new Dimension(300, 100));
                    infoPackageScrollPane.setMaximumSize(new Dimension(300, 100));
                    infoPackageScrollPane.setEnabled(false);
                    infoPackageScrollPane.setOpaque(false);

                    //---- packageTable ----
                    packageTable.setOpaque(false);
                    infoPackageScrollPane.setViewportView(packageTable);
                }
                firstRowPanel.add(infoPackageScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoPackagePanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(InfoPackagePanel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== addBtnPanel ========
        {
            addBtnPanel.setOpaque(false);
            addBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            addBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            addBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- registerPurchaseBtn ----
            registerPurchaseBtn.setText("Registrar compra");
            registerPurchaseBtn.setEnabled(false);
            registerPurchaseBtn.setOpaque(false);
            addBtnPanel.add(registerPurchaseBtn, BorderLayout.CENTER);
        }
        add(addBtnPanel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        vSpacer19.setOpaque(false);
        add(vSpacer19, new GridBagConstraints(0, 7, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JLabel titleLabel;
    private JPanel vSpacer18;
    private JPanel selectPackagePanel;
    private JLabel packageLabel;
    private JComboBox<String> listPackageComboBox;
    private JPanel vSpacer20;
    private JPanel selectAirlinePanel;
    private JLabel clientLabel;
    private JComboBox<String> listClientComboBox;
    private JPanel InfoPackagePanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel infoPackageLabel;
    private JScrollPane infoPackageScrollPane;
    private JTable packageTable;
    private JPanel addBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton registerPurchaseBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
