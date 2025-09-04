/*
 * Created by JFormDesigner on Sun Aug 24 14:32:55 UYT 2025
 */

package gui.flight.createFlight;

import controllers.category.ICategoryController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.flight.FlightDTO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

     List<AirlineDTO> airlines = new ArrayList<>();
    AirlineDTO selectedAirline;
    private static final String PH_CREATED_AT = "dd/MM/yyyy HH:mm";
    private static final String PH_DEPARTURE  = "dd/MM/yyyy HH:mm";

    private boolean areAirlinesLoading = false;

    public createFlightPanel(IFlightController flightController,
                             IFlightRouteController flightRouteController,
                             IUserController userController) {
        this.flightController = flightController;
        this.flightRouteController = flightRouteController;
        this.userController = userController;

        initComponents();
        loadAirlinesIntoCombo();   // carga nicknames
        initPlaceholders();
        initListeners();

        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }
    private void loadAirlinesIntoCombo() {
        areAirlinesLoading = true;
        airlineComboBox.removeAllItems();
        List<String> nicknames = userController.getAllAirlinesNicknames();
        if (nicknames != null) {
            for (String n : nicknames) airlineComboBox.addItem(n);
        }

        areAirlinesLoading = false;
        if (airlineComboBox.getItemCount() > 0) {
            airlineComboBox.setSelectedIndex(0);
        }
    }

    private String getSelectedAirlineNickname() {
        Object sel = airlineComboBox.getSelectedItem();
        return (sel == null) ? null : sel.toString();
    }

    /** Carga la tabla de rutas para la aerolínea seleccionada */
    private void loadFlightRouteList(String airlineNickname) {
        List<FlightRouteDTO> flightRoutes =
                flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {
                "Nombre", "Descripción", "Ciudad Origen", "Ciudad Destino",
                "Precio Turista", "Precio Ejecutivo", "Precio Equipaje Extra", "Fecha de Creación"
        };
        tableModel.setColumnIdentifiers(columnNames);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (flightRoutes != null) {
            for (FlightRouteDTO fr : flightRoutes) {
                Object[] rowData = {
                        nz(fr.getName()),
                        nz(fr.getDescription()),
                        nz(fr.getOriginCityName()),
                        nz(fr.getDestinationCityName()),
                        money(fr.getPriceTouristClass()),
                        money(fr.getPriceBusinessClass()),
                        money(fr.getPriceExtraUnitBaggage()),
                        fr.getCreatedAt() != null ? fr.getCreatedAt().format(df) : ""
                };
                tableModel.addRow(rowData);
            }
        }

        flightRouteTable.setModel(tableModel);
        flightRouteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adjustColumnsToContent(flightRouteTable);
        adjustTableHeight(flightRouteTable, 4);
    }

    /** Listeners de UI */
    private void initListeners() {
        airlineComboBox.addActionListener(e -> {
            if (areAirlinesLoading) return;
            try {
                String selectedAirlineNickname = getSelectedAirlineNickname();
                if (selectedAirlineNickname == null || selectedAirlineNickname.isEmpty()) {
                    throw new IllegalArgumentException("Debe seleccionar una aerolínea.");
                }
                loadFlightRouteList(selectedAirlineNickname);
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

                // Fecha de vuelo (obligatoria)
                LocalDateTime departureTime;
                if (isPlaceholderOrEmpty(dateFlightTextField, PH_DEPARTURE)) {
                    throw new IllegalArgumentException("La fecha de vuelo es obligatoria (formato: dd/MM/yyyy HH:mm).");
                }
                try {
                    departureTime = LocalDateTime.parse(dateFlightTextField.getText().trim(), fmt);
                } catch (Exception pe) {
                    throw new IllegalArgumentException("Fecha de vuelo inválida. Formato esperado: dd/MM/yyyy HH:mm");
                }

                // Fecha de alta (opcional => por defecto ahora)
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
                BaseFlightDTO baseFlightDTO = new BaseFlightDTO(
                        flightName,
                        departureTime,
                        durationMinutes,
                        seatsEconomy,
                        seatsBusiness,
                        createdAt
                );

                // Llamada al controlador
                flightController.createFlight(baseFlightDTO, selectedAirlineNickname, selectedFlightRouteName);

                JOptionPane.showMessageDialog(this,
                        "Vuelo creado con éxito.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al crear el vuelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        airlineLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadAirlinesIntoCombo();
            }
        });
    }

    /** Placeholders */
    private void initPlaceholders() {
        setPlaceholder(createdDateAtTextField, PH_CREATED_AT);
        setPlaceholder(dateFlightTextField, PH_DEPARTURE);
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(new Color(150, 150, 150));
        textField.setText(placeholder);
        textField.setCaretPosition(0);

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
                    textField.setForeground(new Color(150, 150, 150));
                    textField.setText(placeholder);
                    textField.setCaretPosition(0);
                }
            }
        });
    }

    private boolean isPlaceholderOrEmpty(JTextField tf, String placeholder) {
        String t = tf.getText().trim();
        return t.isEmpty() || t.equals(placeholder);
    }

    private String nz(String s) {
        return (s == null) ? "" : s;
    }

    private String money(Double d) {
        return (d == null) ? "" : String.format("$ %.2f", d);
    }

    private void adjustColumnsToContent(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, col
            );
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col
                );
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10);
        }
    }

    private void adjustTableHeight(JTable table, int minRows) {
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(
                new Dimension(table.getPreferredSize().width, visibleRows * table.getRowHeight())
        );
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        vSpacer19 = new JPanel(null);
        InfoFlightPanel = new JPanel();
        titleLabel = new JLabel();
        vSpacer18 = new JPanel(null);
        hSpacer5 = new JPanel(null);
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
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
        vSpacer20 = new JPanel(null);
        createBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        createFlightBtn = new JButton();
        hSpacer2 = new JPanel(null);
        vSpacer14 = new JPanel(null);

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 100));
        vSpacer19.setOpaque(false);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
        EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing
        . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
        java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( )
        { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () ))
        throw new RuntimeException( ); }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //======== InfoFlightPanel ========
        {
            InfoFlightPanel.setOpaque(false);
            InfoFlightPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoFlightPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowHeights = new int[] {0, 0, 43, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- titleLabel ----
            titleLabel.setText("Crear vuelo");
            titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            InfoFlightPanel.add(titleLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(10, 0, 5, 0), 0, 0));

            //---- vSpacer18 ----
            vSpacer18.setPreferredSize(new Dimension(10, 50));
            vSpacer18.setMinimumSize(new Dimension(12, 50));
            vSpacer18.setOpaque(false);
            InfoFlightPanel.add(vSpacer18, new GridBagConstraints(1, 1, 1, 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoFlightPanel.add(hSpacer5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== selectAirlinePanel ========
            {
                selectAirlinePanel.setOpaque(false);
                selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

                //---- airlineLabel ----
                airlineLabel.setText("\ud83d\udd04 Selecciona el Aerolinea:");
                airlineLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                selectAirlinePanel.add(airlineLabel);

                //---- airlineComboBox ----
                airlineComboBox.setMinimumSize(new Dimension(100, 30));
                airlineComboBox.setPreferredSize(new Dimension(100, 30));
                airlineComboBox.setOpaque(false);
                selectAirlinePanel.add(airlineComboBox);
            }
            InfoFlightPanel.add(selectAirlinePanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoFlightPanel.add(hSpacer6, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel2 ========
            {
                firstRowPanel2.setPreferredSize(new Dimension(510, 100));
                firstRowPanel2.setMinimumSize(new Dimension(510, 100));
                firstRowPanel2.setMaximumSize(new Dimension(510, 510));
                firstRowPanel2.setOpaque(false);
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
                    flightRouteScrollPane.setOpaque(false);

                    //---- flightRouteTable ----
                    flightRouteTable.setOpaque(false);
                    flightRouteScrollPane.setViewportView(flightRouteTable);
                }
                firstRowPanel2.add(flightRouteScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowPanel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setOpaque(false);
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
                nameTextField.setOpaque(false);
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
                durationTextField.setOpaque(false);
                firstRowPanel.add(durationTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowPanel, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setOpaque(false);
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
                ejecutiveTextField.setOpaque(false);
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
                turistTextField.setOpaque(false);
                secondRowPanel.add(turistTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(secondRowPanel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== thirdRowPanel ========
            {
                thirdRowPanel.setPreferredSize(new Dimension(510, 30));
                thirdRowPanel.setMinimumSize(new Dimension(510, 30));
                thirdRowPanel.setMaximumSize(new Dimension(510, 510));
                thirdRowPanel.setOpaque(false);
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
                createdDateAtTextField.setOpaque(false);
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
                dateFlightTextField.setOpaque(false);
                thirdRowPanel.add(dateFlightTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(thirdRowPanel, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoFlightPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer20 ----
        vSpacer20.setPreferredSize(new Dimension(10, 20));
        vSpacer20.setMinimumSize(new Dimension(12, 20));
        vSpacer20.setOpaque(false);
        add(vSpacer20, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== createBtnPanel ========
        {
            createBtnPanel.setOpaque(false);
            createBtnPanel.setLayout(new BorderLayout());

            //---- hSpacer1 ----
            hSpacer1.setPreferredSize(new Dimension(200, 10));
            hSpacer1.setOpaque(false);
            createBtnPanel.add(hSpacer1, BorderLayout.WEST);

            //---- createFlightBtn ----
            createFlightBtn.setText("+ Crear Vuelo");
            createFlightBtn.setOpaque(false);
            createBtnPanel.add(createFlightBtn, BorderLayout.CENTER);

            //---- hSpacer2 ----
            hSpacer2.setPreferredSize(new Dimension(200, 10));
            hSpacer2.setOpaque(false);
            createBtnPanel.add(hSpacer2, BorderLayout.EAST);
        }
        add(createBtnPanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer14 ----
        vSpacer14.setOpaque(false);
        add(vSpacer14, new GridBagConstraints(1, 3, 1, 1, 0.0, 200.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel vSpacer19;
    private JPanel InfoFlightPanel;
    private JLabel titleLabel;
    private JPanel vSpacer18;
    private JPanel hSpacer5;
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
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
    private JPanel vSpacer20;
    private JPanel createBtnPanel;
    private JPanel hSpacer1;
    private JButton createFlightBtn;
    private JPanel hSpacer2;
    private JPanel vSpacer14;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
