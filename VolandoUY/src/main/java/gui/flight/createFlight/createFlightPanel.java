/*
 * Created by JFormDesigner on Sun Aug 24 14:32:55 UYT 2025
 */

package gui.flight.createFlight;

import controllers.category.ICategoryController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.flight.FlightDTO;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author AparicioQuian
 */
public class createFlightPanel extends JPanel {
     IFlightController flightController;
     IFlightRouteController flightRouteController;
     IUserController userController;
     private static final String PH_CREATED_AT = "dd/MM/yyyy HH:mm";
     private static final String PH_DEPARTURE  = "dd/MM/yyyy HH:mm";

     List<AirlineDTO> airlines = new ArrayList<>();

    public createFlightPanel(IFlightController flightController, IFlightRouteController flightRouteController , IUserController userController) {
        this.flightController = flightController;
        this.flightRouteController = flightRouteController ;
        this.userController = userController;
        initComponents();
        loadAirlinesIntoCombo();
        initPlaceholders();
        initListeners();
        try {
            setBorder(null);
        } catch (Exception ignored) {
        }
    }

    private void loadAirlinesIntoCombo() {
        airlines.clear();
        airlineComboBox.removeAllItems();

        // asumo que tu IUserController tiene getAllAirlines()
        List<AirlineDTO> list = userController.getAllAirlines();
        if (list == null) return;

        airlines.addAll(list);
        for (AirlineDTO a : airlines) {
            // Muestra “Nombre (nickname)”
            String display = a.getName() + " (" + a.getNickname() + ")";
            airlineComboBox.addItem(display);
        }

        if (!airlines.isEmpty()) airlineComboBox.setSelectedIndex(0);
    }

    private String getSelectedAirlineNickname() {
        int idx = airlineComboBox.getSelectedIndex();
        if (idx < 0 || idx >= airlines.size()) return null;
        return airlines.get(idx).getNickname(); // lo que necesita tu método
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

        flightRouteTable.setModel(tableModel);
        flightRouteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        loadAirlineBtn.addActionListener(e -> {
            try {
                String selectedAirlineNickname = getSelectedAirlineNickname();
                if (selectedAirlineNickname == null || selectedAirlineNickname.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una aerolínea.");
                }

                loadFlightRouteList(selectedAirlineNickname);
                JOptionPane.showMessageDialog(this, "Aerolínea cargada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar la aerolínea: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        createFlightBtn.addActionListener(e -> {
            try {
                // Validar selección de ruta
                int selectedRow = flightRouteTable.getSelectedRow();
                if (selectedRow == -1) {
                    throw new IllegalArgumentException("Debe seleccionar una ruta de vuelo de la tabla.");
                }
                String selectedFlightRouteName = (String) flightRouteTable.getValueAt(selectedRow, 0);

                String selectedAirlineNickname = getSelectedAirlineNickname();
                if (selectedAirlineNickname == null || selectedAirlineNickname.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una aerolínea.");
                }

                // Validar campos básicos
                String flightName = nameTextField.getText().trim();
                if (flightName.isEmpty()) {
                    throw new IllegalArgumentException("El nombre del vuelo no puede estar vacío.");
                }

                long durationMinutes;
                int seatsBusiness;
                int seatsEconomy;

                try {
                    durationMinutes = Long.parseLong(durationTextField.getText().trim());
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Duración inválida. Debe ser un número (minutos).");
                }
                try {
                    seatsBusiness = Integer.parseInt(ejecutiveTextField.getText().trim());
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Asientos ejecutivos inválidos. Debe ser un entero.");
                }
                try {
                    seatsEconomy = Integer.parseInt(turistTextField.getText().trim());
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Asientos turista inválidos. Debe ser un entero.");
                }


                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


                LocalDateTime departureTime;
                if (isPlaceholderOrEmpty(dateFlightTextField, PH_DEPARTURE)) {
                    throw new IllegalArgumentException("La fecha de vuelo es obligatoria (formato: dd/MM/yyyy HH:mm).");
                }
                try {
                    departureTime = LocalDateTime.parse(dateFlightTextField.getText().trim(), fmt);
                } catch (Exception pe) {
                    throw new IllegalArgumentException("Fecha de vuelo inválida. Formato esperado: dd/MM/yyyy HH:mm");
                }

                 LocalDateTime createdAt;
                if (isPlaceholderOrEmpty(createdDateAtTextField, PH_CREATED_AT)) {
                    createdAt = LocalDateTime.now();
                } else {
                    try {
                        createdAt = LocalDateTime.parse(createdDateAtTextField.getText().trim(), fmt);
                    } catch (Exception pe) {
                        throw new IllegalArgumentException("Fecha de alta inválida. Formato esperado: dd/MM/yyyy HH:mm");
                    }
                }


                // Construcción del DTO
                FlightDTO flightDTO = new FlightDTO(
                        flightName,
                        departureTime,
                        durationMinutes,
                        seatsEconomy,
                        seatsBusiness,
                        createdAt,
                        selectedAirlineNickname,
                        selectedFlightRouteName
                );

                // Llamada al controlador
                flightController.createFlight(flightDTO);

                JOptionPane.showMessageDialog(this,
                        "Vuelo creado con éxito.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear el vuelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void initPlaceholders() {
        setPlaceholder(createdDateAtTextField, PH_CREATED_AT);
        setPlaceholder(dateFlightTextField, PH_DEPARTURE);
    }
    private void setPlaceholder(JTextField textField, String placeholder) {
        // estado inicial
        textField.setForeground(new Color(150,150,150)); // gris un poco más visible
        textField.setText(placeholder);
        textField.setCaretPosition(0);                   // <-- clave: mostrar desde el inicio

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setCaretPosition(0);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(150,150,150));
                    textField.setText(placeholder);
                    textField.setCaretPosition(0);       // <-- reponer al inicio
                }
            }
        });
    }


    private boolean isPlaceholderOrEmpty(JTextField tf, String placeholder) {
        String t = tf.getText().trim();
        return t.isEmpty() || t.equals(placeholder);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        vSpacer19 = new JPanel(null);
        InfoFlightRoutePackagePanel = new JPanel();
        hSpacer5 = new JPanel(null);
        selectPackagePanel = new JPanel();
        packageLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        loadAirlineBtn = new JButton();
        hSpacer6 = new JPanel(null);
        firstRowPanel2 = new JPanel();
        flightRouteLabel = new JLabel();
        flightRouteScrollPane = new JScrollPane();
        flightRouteTable = new JTable();
        firstRowPanel = new JPanel();
        nameLabel = new JLabel();
        nameTextField = new JTextField();
        durationLabel = new JLabel();
        durationTextField = new JTextField();
        secondRowPanel = new JPanel();
        ejecutiveLabel = new JLabel();
        ejecutiveTextField = new JTextField();
        turistTypeLabel = new JLabel();
        turistTextField = new JTextField();
        thirdRowPanel = new JPanel();
        createdDateAtLabel = new JLabel();
        createdDateAtTextField = new JTextField();
        dateFlightLabel = new JLabel();
        dateFlightTextField = new JTextField();
        createBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        hSpacer2 = new JPanel(null);
        createFlightBtn = new JButton();
        vSpacer20 = new JPanel(null);

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

        //======== InfoFlightRoutePackagePanel ========
        {
            InfoFlightRoutePackagePanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).rowHeights = new int[] {0, 0, 25, 43, 0, 0, 0, 35, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightRoutePackagePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            InfoFlightRoutePackagePanel.add(hSpacer5, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== selectPackagePanel ========
            {
                selectPackagePanel.setLayout(new GridLayout(1, 3));

                //---- packageLabel ----
                packageLabel.setText("Selecciona el Aerolinea:");
                selectPackagePanel.add(packageLabel);

                //---- airlineComboBox ----
                airlineComboBox.setMinimumSize(new Dimension(100, 30));
                airlineComboBox.setPreferredSize(new Dimension(100, 30));
                selectPackagePanel.add(airlineComboBox);

                //---- loadAirlineBtn ----
                loadAirlineBtn.setText("Cargar Aerolinea");
                selectPackagePanel.add(loadAirlineBtn);
            }
            InfoFlightRoutePackagePanel.add(selectPackagePanel, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            InfoFlightRoutePackagePanel.add(hSpacer6, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel2 ========
            {
                firstRowPanel2.setPreferredSize(new Dimension(510, 100));
                firstRowPanel2.setMinimumSize(new Dimension(510, 100));
                firstRowPanel2.setMaximumSize(new Dimension(510, 510));
                firstRowPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel2.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)firstRowPanel2.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)firstRowPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- flightRouteLabel ----
                flightRouteLabel.setText("Rutas de vuelo:");
                flightRouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                flightRouteLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                flightRouteLabel.setPreferredSize(new Dimension(120, 30));
                flightRouteLabel.setMaximumSize(new Dimension(120, 30));
                flightRouteLabel.setMinimumSize(new Dimension(120, 30));
                firstRowPanel2.add(flightRouteLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
                firstRowPanel2.add(flightRouteScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(firstRowPanel2, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 0, 130, 110, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- nameLabel ----
                nameLabel.setText("Nombre:");
                nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                nameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                nameLabel.setPreferredSize(new Dimension(120, 30));
                nameLabel.setMaximumSize(new Dimension(70, 15));
                nameLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- nameTextField ----
                nameTextField.setPreferredSize(new Dimension(120, 30));
                nameTextField.setMinimumSize(new Dimension(100, 30));
                nameTextField.setMaximumSize(new Dimension(100, 30));
                firstRowPanel.add(nameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- durationLabel ----
                durationLabel.setText("Duraci\u00f3n:");
                durationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                durationLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                durationLabel.setPreferredSize(new Dimension(120, 30));
                durationLabel.setMaximumSize(new Dimension(70, 15));
                durationLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(durationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- durationTextField ----
                durationTextField.setPreferredSize(new Dimension(120, 30));
                durationTextField.setMinimumSize(new Dimension(100, 30));
                durationTextField.setMaximumSize(new Dimension(100, 30));
                firstRowPanel.add(durationTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(firstRowPanel, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- ejecutiveLabel ----
                ejecutiveLabel.setText("Asientos ejecutivo:");
                ejecutiveLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                ejecutiveLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                ejecutiveLabel.setPreferredSize(new Dimension(120, 30));
                ejecutiveLabel.setMaximumSize(new Dimension(70, 15));
                ejecutiveLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(ejecutiveLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- ejecutiveTextField ----
                ejecutiveTextField.setPreferredSize(new Dimension(120, 30));
                ejecutiveTextField.setMinimumSize(new Dimension(100, 30));
                ejecutiveTextField.setMaximumSize(new Dimension(100, 30));
                secondRowPanel.add(ejecutiveTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- turistTypeLabel ----
                turistTypeLabel.setText("Asientos Turista");
                turistTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                turistTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                turistTypeLabel.setPreferredSize(new Dimension(120, 30));
                turistTypeLabel.setMaximumSize(new Dimension(70, 15));
                turistTypeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(turistTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- turistTextField ----
                turistTextField.setPreferredSize(new Dimension(120, 30));
                turistTextField.setMinimumSize(new Dimension(100, 30));
                turistTextField.setMaximumSize(new Dimension(100, 30));
                secondRowPanel.add(turistTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(secondRowPanel, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWidths = new int[] {130, 130, 124, 0, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)thirdRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)thirdRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- createdDateAtLabel ----
                createdDateAtLabel.setText("Fecha de alta:");
                createdDateAtLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                createdDateAtLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                createdDateAtLabel.setPreferredSize(new Dimension(120, 30));
                createdDateAtLabel.setMaximumSize(new Dimension(70, 15));
                createdDateAtLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(createdDateAtLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- createdDateAtTextField ----
                createdDateAtTextField.setPreferredSize(new Dimension(120, 30));
                createdDateAtTextField.setMinimumSize(new Dimension(100, 30));
                createdDateAtTextField.setMaximumSize(new Dimension(100, 30));
                thirdRowPanel.add(createdDateAtTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- dateFlightLabel ----
                dateFlightLabel.setText("Fecha vuelo:");
                dateFlightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                dateFlightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                dateFlightLabel.setPreferredSize(new Dimension(120, 30));
                dateFlightLabel.setMaximumSize(new Dimension(70, 15));
                dateFlightLabel.setMinimumSize(new Dimension(70, 15));
                thirdRowPanel.add(dateFlightLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- dateFlightTextField ----
                dateFlightTextField.setPreferredSize(new Dimension(120, 30));
                dateFlightTextField.setMinimumSize(new Dimension(100, 30));
                dateFlightTextField.setMaximumSize(new Dimension(100, 30));
                thirdRowPanel.add(dateFlightTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightRoutePackagePanel.add(thirdRowPanel, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(InfoFlightRoutePackagePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== createBtnPanel ========
        {
            createBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            createBtnPanel.add(hSpacer1, BorderLayout.LINE_START);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            createBtnPanel.add(hSpacer2, BorderLayout.LINE_END);

            //---- createFlightBtn ----
            createFlightBtn.setText("Crear Vuelo.");
            createBtnPanel.add(createFlightBtn, BorderLayout.CENTER);
        }
        add(createBtnPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        add(vSpacer20, new GridBagConstraints(0, 2, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel vSpacer19;
    private JPanel InfoFlightRoutePackagePanel;
    private JPanel hSpacer5;
    private JPanel selectPackagePanel;
    private JLabel packageLabel;
    private JComboBox<String> airlineComboBox;
    private JButton loadAirlineBtn;
    private JPanel hSpacer6;
    private JPanel firstRowPanel2;
    private JLabel flightRouteLabel;
    private JScrollPane flightRouteScrollPane;
    private JTable flightRouteTable;
    private JPanel firstRowPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel durationLabel;
    private JTextField durationTextField;
    private JPanel secondRowPanel;
    private JLabel ejecutiveLabel;
    private JTextField ejecutiveTextField;
    private JLabel turistTypeLabel;
    private JTextField turistTextField;
    private JPanel thirdRowPanel;
    private JLabel createdDateAtLabel;
    private JTextField createdDateAtTextField;
    private JLabel dateFlightLabel;
    private JTextField dateFlightTextField;
    private JPanel createBtnPanel;
    private JPanel hSpacer1;
    private JPanel hSpacer2;
    private JButton createFlightBtn;
    private JPanel vSpacer20;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
