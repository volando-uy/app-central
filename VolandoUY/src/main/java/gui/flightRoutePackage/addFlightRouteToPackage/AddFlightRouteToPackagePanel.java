package gui.flightRoutePackage.addFlightRouteToPackage;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
public class AddFlightRouteToPackagePanel extends JPanel {

    IFlightRoutePackageController flightRoutePackageController;
    IFlightRouteController flightRouteController;
    IUserController userController;

    AirlineDTO selectedAirline;
    FlightRoutePackageDTO selectedPackage;

    public AddFlightRouteToPackagePanel(IFlightRoutePackageController flightRoutePackageController, IFlightRouteController flightRouteController, IUserController userController) {
        this.flightRoutePackageController = flightRoutePackageController;
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        initComponents();
        initListeners();
        initNotBoughtPackagesList();
        initAirlineList();
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initNotBoughtPackagesList() {
        List<String> notBoughtPackagesNames = flightRoutePackageController.getAllNotBoughtFlightRoutePackagesNames();
        for (String name : notBoughtPackagesNames) {
            packageComboBox.addItem(name);
        }
    }

    private void initAirlineList() {
        List<String> airlinesNickanmes = userController.getAllAirlinesNicknames();
        for (String nickname : airlinesNickanmes) {
            airlineComboBox.addItem(nickname);
        }
    }

    private void loadFlightRouteList(String airlineNickname) {
        List<FlightRouteDTO> flightRoutes = flightRouteController.getAllFlightRoutesByAirlineNickname(airlineNickname);

        // Create table model and set column names
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"Nombre", "Descripcion", "Ciudad Origen", "Ciudad Destino", "Precio Turista", "Precio Ejecutivo", "Precio Equipaje Extra", "Fecha de Creación"};
        tableModel.setColumnIdentifiers(columnNames);

        // Add rows to the table model
        for (FlightRouteDTO flightRoute : flightRoutes) {
            Object[] rowData = {
                    flightRoute.getName(),
                    flightRoute.getDescription(),
                    flightRoute.getOriginCityName(),
                    flightRoute.getDestinationCityName(),
                    "$" + flightRoute.getPriceTouristClass(),
                    "$" + flightRoute.getPriceBusinessClass(),
                    "$" + flightRoute.getPriceExtraUnitBaggage(),
                    flightRoute.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            };
            tableModel.addRow(rowData);
        }

        flightRouteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Adjust column widths based on content
        for (int col = 0; col < flightRouteTable.getColumnCount(); col++) {
            TableColumn column = flightRouteTable.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = flightRouteTable.getRowCount();

            TableCellRenderer headerRenderer = flightRouteTable.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(flightRouteTable, column.getHeaderValue(), false, false, 0, col);
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = flightRouteTable.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(flightRouteTable, flightRouteTable.getValueAt(row, col), false, false, row, col);
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10); // add some padding
        }

        // Set the model to the table
        flightRouteTable.setModel(tableModel);

        // Dynamic height
        int maxRows = 4;
        int visibleRows = Math.max(flightRouteTable.getRowCount(), maxRows);
        flightRouteTable.setPreferredSize(
                new Dimension(flightRouteTable.getPreferredSize().width, visibleRows * flightRouteTable.getRowHeight())
        );

        flightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    private void initListeners() {
        loadPackageBtn.addActionListener(e -> {
            try {
                String selectedPackageName = (String) packageComboBox.getSelectedItem();
                // If package doesn't exist, throw exception
                selectedPackage = flightRoutePackageController.getFlightRoutePackageByName(selectedPackageName);

                // Enable the buttons and list for arline selection
                loadAirlineBtn.setEnabled(true);
                airlineComboBox.setEnabled(true);

                JOptionPane.showMessageDialog(this, "Paquete seleccionado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al seleccionar un paquete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loadAirlineBtn.addActionListener(e -> {
            try {
                String selectedNickname = (String) airlineComboBox.getSelectedItem();
                // If ariline doesn't exist, throw exception
                selectedAirline = userController.getAirlineByNickname(selectedNickname);
                loadFlightRouteList(selectedAirline.getNickname());

                // Enable the flight route list, quantity text field and add button
                flightRouteTable.setEnabled(true);
                flightRouteScrollPane.setEnabled(true);
                quantityTextField.setEnabled(true);
                addFlightRouteToPackageBtn.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la aerolinea: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addFlightRouteToPackageBtn.addActionListener(e -> {
            try {
                // Get selected flight route from table
                int selectedRow = flightRouteTable.getSelectedRow();
                if (selectedRow == -1) {
                    throw new IllegalArgumentException("Debe seleccionar una ruta de vuelo de la tabla.");
                }
                String selectedFlightRouteName = (String) flightRouteTable.getValueAt(selectedRow, 0);
                FlightRouteDTO selectedFlightRoute = flightRouteController.getFlightRouteByName(selectedFlightRouteName);
                Integer quantity = Integer.valueOf(quantityTextField.getText());

                // Add flight route to package
                flightRoutePackageController.addFlightRouteToPackage(
                        selectedPackage.getName(), selectedFlightRoute.getName(), quantity
                );

                JOptionPane.showMessageDialog(this, "Ruta de vuelo añadida al paquete correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al añadir la ruta de vuelo al paquete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        selectPackagePanel = new JPanel();
        packageLabel = new JLabel();
        packageComboBox = new JComboBox<>();
        loadPackageBtn = new JButton();
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        loadAirlineBtn = new JButton();
        InfoUserPanel = new JPanel();
        hSpacer5 = new JPanel(null);
        vSpacer17 = new JPanel(null);
        hSpacer6 = new JPanel(null);
        firstRowPanel = new JPanel();
        flightRouteLabel = new JLabel();
        flightRouteScrollPane = new JScrollPane();
        flightRouteTable = new JTable();
        secondRowPanel = new JPanel();
        quantityLabel = new JLabel();
        quantityTextField = new JTextField();
        addBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        addFlightRouteToPackageBtn = new JButton();
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 31, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //======== selectPackagePanel ========
        {
            selectPackagePanel.setLayout(new GridLayout(1, 3));

            //---- packageLabel ----
            packageLabel.setText("Selecciona el Paquete de R.V.:");
            selectPackagePanel.add(packageLabel);

            //---- packageComboBox ----
            packageComboBox.setMinimumSize(new Dimension(100, 30));
            packageComboBox.setPreferredSize(new Dimension(100, 30));
            selectPackagePanel.add(packageComboBox);

            //---- loadPackageBtn ----
            loadPackageBtn.setText("Cargar paquete");
            selectPackagePanel.add(loadPackageBtn);
        }
        add(selectPackagePanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== selectAirlinePanel ========
        {
            selectAirlinePanel.setLayout(new GridLayout(1, 3));

            //---- airlineLabel ----
            airlineLabel.setText("Selecciona la Aerolinea:");
            selectAirlinePanel.add(airlineLabel);

            //---- airlineComboBox ----
            airlineComboBox.setMinimumSize(new Dimension(100, 30));
            airlineComboBox.setPreferredSize(new Dimension(100, 30));
            airlineComboBox.setEnabled(false);
            selectAirlinePanel.add(airlineComboBox);

            //---- loadAirlineBtn ----
            loadAirlineBtn.setText("Cargar aerolinea");
            loadAirlineBtn.setEnabled(false);
            selectAirlinePanel.add(loadAirlineBtn);
        }
        add(selectAirlinePanel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== InfoUserPanel ========
        {
            InfoUserPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowHeights = new int[] {0, 25, 38, 0};
            ((GridBagLayout)InfoUserPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoUserPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- vSpacer17 ----
            vSpacer17.setMinimumSize(new Dimension(12, 70));
            vSpacer17.setPreferredSize(new Dimension(10, 100));
            InfoUserPanel.add(vSpacer17, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            InfoUserPanel.add(hSpacer6, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 100));
                firstRowPanel.setMinimumSize(new Dimension(510, 100));
                firstRowPanel.setMaximumSize(new Dimension(510, 510));
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- flightRouteLabel ----
                flightRouteLabel.setText("Rutas de vuelo:");
                flightRouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                flightRouteLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                flightRouteLabel.setPreferredSize(new Dimension(120, 30));
                flightRouteLabel.setMaximumSize(new Dimension(120, 30));
                flightRouteLabel.setMinimumSize(new Dimension(120, 30));
                firstRowPanel.add(flightRouteLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== flightRouteScrollPane ========
                {
                    flightRouteScrollPane.setPreferredSize(new Dimension(300, 100));
                    flightRouteScrollPane.setMinimumSize(new Dimension(300, 100));
                    flightRouteScrollPane.setMaximumSize(new Dimension(300, 100));
                    flightRouteScrollPane.setEnabled(false);
                    flightRouteScrollPane.setViewportView(flightRouteTable);
                }
                firstRowPanel.add(flightRouteScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoUserPanel.add(firstRowPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- quantityLabel ----
                quantityLabel.setText("Cantidad a a\u00f1adir:");
                quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                quantityLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                quantityLabel.setPreferredSize(new Dimension(120, 30));
                quantityLabel.setMaximumSize(new Dimension(70, 15));
                quantityLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(quantityLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- quantityTextField ----
                quantityTextField.setPreferredSize(new Dimension(120, 30));
                quantityTextField.setMinimumSize(new Dimension(100, 30));
                quantityTextField.setMaximumSize(new Dimension(100, 30));
                quantityTextField.setEnabled(false);
                secondRowPanel.add(quantityTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));
            }
            InfoUserPanel.add(secondRowPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoUserPanel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== addBtnPanel ========
        {
            addBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            addBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            addBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- addFlightRouteToPackageBtn ----
            addFlightRouteToPackageBtn.setText("Aa\u00f1adir R.V. a Paquete");
            addFlightRouteToPackageBtn.setEnabled(false);
            addBtnPanel.add(addFlightRouteToPackageBtn, BorderLayout.CENTER);
        }
        add(addBtnPanel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        add(vSpacer19, new GridBagConstraints(0, 6, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel selectPackagePanel;
    private JLabel packageLabel;
    private JComboBox<String> packageComboBox;
    private JButton loadPackageBtn;
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JButton loadAirlineBtn;
    private JPanel InfoUserPanel;
    private JPanel hSpacer5;
    private JPanel vSpacer17;
    private JPanel hSpacer6;
    private JPanel firstRowPanel;
    private JLabel flightRouteLabel;
    private JScrollPane flightRouteScrollPane;
    private JTable flightRouteTable;
    private JPanel secondRowPanel;
    private JLabel quantityLabel;
    private JTextField quantityTextField;
    private JPanel addBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton addFlightRouteToPackageBtn;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
