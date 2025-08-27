/*
 * Created by JFormDesigner on Tue Aug 26 18:34:01 UYT 2025
 */

package gui.flightRoute.getFlightRoutes;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.util.ArrayList;
import java.util.List;
/**
 * @author AparicioQuian
 */
public class GetFlightRoutesPanel extends JPanel {
    private  IFlightRouteController flightRouteController;
    private  IUserController userController;

    // --- Datos auxiliares ---
    private  List<AirlineDTO> airlines = new ArrayList<>();
    private static  DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public GetFlightRoutesPanel(IFlightRouteController flightRouteController,
                                IUserController userController)
    {
        if (flightRouteController == null) throw new IllegalArgumentException("IFlightRouteController es null");
        if (userController == null)         throw new IllegalArgumentException("IUserController es null");
        this.flightRouteController = flightRouteController;
        this.userController = userController;

        initComponents();
        initListeners();
        loadAirlinesIntoCombo();      // llena el combo al iniciar
        clearTable();                 // deja la tabla vacía hasta que elijas
        try { setBorder(null); } catch (Exception ignored) {}
    }

    private void initListeners() {
        loadAirlineBtn.addActionListener(e -> {
            String nickname = getSelectedAirlineNickname();
            if (nickname == null || nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecciona una aerolínea.", "Atención",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            loadFlightRoutesTable(nickname);
        });
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
            String display = safe(a.getName()) + " (" + safe(a.getNickname()) + ")";
            airlineComboBox.addItem(display);
        }

        if (!airlines.isEmpty()) airlineComboBox.setSelectedIndex(0);
    }

    private String getSelectedAirlineNickname() {
        int idx = airlineComboBox.getSelectedIndex();
        if (idx < 0 || idx >= airlines.size()) return null;
        return airlines.get(idx).getNickname(); // lo que necesita tu método
    }

    // ------------------ TABLA ------------------
    private void loadFlightRoutesTable(String airlineNickname) {
        List<FlightRouteDTO> routes =
                flightRouteController.getAllFlightRoutesByAirlineNickname(airlineNickname);

        DefaultTableModel model = new DefaultTableModel();
        String[] cols = {
                "Nombre", "Descripción", "Creado",
                "Origen", "Destino", "Aerolínea",
                "$ Turista", "$ Business", "$ Extra Bulto",
                "Categorías", "Vuelos"
        };
        model.setColumnIdentifiers(cols);

        for (FlightRouteDTO r : routes) {
            String created = r.getCreatedAt() != null ? r.getCreatedAt().format(DTF) : "";
            String cats    = r.getCategories()   != null ? String.join(", ", r.getCategories())   : "";
            String flights = r.getFlightsNames() != null ? String.join(", ", r.getFlightsNames()) : "";

            // nombre bonito de aerolínea: buscamos en la lista ya cargada
            String airlineName = r.getAirlineNickname();
            for (AirlineDTO a : airlines) {
                if (a.getNickname() != null && a.getNickname().equalsIgnoreCase(r.getAirlineNickname())) {
                    if (a.getName() != null) airlineName = a.getName();
                    break;
                }
            }

            model.addRow(new Object[]{
                    safe(r.getName()),
                    safe(r.getDescription()),
                    created,
                    safe(r.getOriginCityName()),
                    safe(r.getDestinationCityName()),
                    airlineName,
                    fmtMoney(r.getPriceTouristClass()),
                    fmtMoney(r.getPriceBusinessClass()),
                    fmtMoney(r.getPriceExtraUnitBaggage()),
                    cats,
                    flights
            });
        }

        FlightRouteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FlightRouteTable.setModel(model);
        adjustDynamicWidthAndHeightToTable(FlightRouteTable, model);
        FlightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void clearTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Nombre", "Descripción", "Creado",
                "Origen", "Destino", "Aerolínea",
                "$ Turista", "$ Business", "$ Extra Bulto",
                "Categorías", "Vuelos"
        });
        FlightRouteTable.setModel(model);
    }

    private String safe(String s) { return s == null ? "" : s; }
    private String fmtMoney(Double d) { return d == null ? "" : String.format("$ %.2f", d); }

    private void adjustDynamicWidthAndHeightToTable(JTable table, DefaultTableModel tableModel) {
        // Ancho
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = table.getRowCount();

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, col
            );
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col
                );
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10);
        }

        // Alto
        int minRows = 5;
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(new Dimension(
                table.getPreferredSize().width,
                visibleRows * table.getRowHeight()
        ));
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        selectPackagePanel2 = new JPanel();
        packageLabel3 = new JLabel();
        airlineComboBox = new JComboBox<>();
        loadAirlineBtn = new JButton();
        FlightRouteinfoPanel2 = new JPanel();
        FlightRouteLabel2 = new JLabel();
        hSpacer7 = new JPanel(null);
        FlightRouteTablePanel = new JPanel();
        FlightRouteScrollPane = new JScrollPane();
        FlightRouteTable = new JTable();
        hSpacer8 = new JPanel(null);
        vSpacer20 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
        0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
        .BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt.Color.
        red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
        beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //======== selectPackagePanel2 ========
        {
            selectPackagePanel2.setLayout(new GridLayout(1, 3));

            //---- packageLabel3 ----
            packageLabel3.setText("Selecciona el Aerolinea:");
            selectPackagePanel2.add(packageLabel3);

            //---- airlineComboBox ----
            airlineComboBox.setMinimumSize(new Dimension(100, 30));
            airlineComboBox.setPreferredSize(new Dimension(100, 30));
            selectPackagePanel2.add(airlineComboBox);

            //---- loadAirlineBtn ----
            loadAirlineBtn.setText("Cargar Aerolinea");
            selectPackagePanel2.add(loadAirlineBtn);
        }
        add(selectPackagePanel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== FlightRouteinfoPanel2 ========
        {
            FlightRouteinfoPanel2.setPreferredSize(new Dimension(640, 180));
            FlightRouteinfoPanel2.setMinimumSize(new Dimension(640, 180));
            FlightRouteinfoPanel2.setMaximumSize(new Dimension(640, 180));
            FlightRouteinfoPanel2.setLayout(new GridBagLayout());
            ((GridBagLayout)FlightRouteinfoPanel2.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)FlightRouteinfoPanel2.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)FlightRouteinfoPanel2.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)FlightRouteinfoPanel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- FlightRouteLabel2 ----
            FlightRouteLabel2.setText("Rutas de vuelo");
            FlightRouteLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            FlightRouteLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
            FlightRouteLabel2.setPreferredSize(new Dimension(120, 30));
            FlightRouteLabel2.setMaximumSize(new Dimension(120, 30));
            FlightRouteLabel2.setMinimumSize(new Dimension(120, 30));
            FlightRouteLabel2.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            FlightRouteinfoPanel2.add(FlightRouteLabel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer7 ----
            hSpacer7.setPreferredSize(new Dimension(40, 10));
            FlightRouteinfoPanel2.add(hSpacer7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== FlightRouteTablePanel ========
            {
                FlightRouteTablePanel.setPreferredSize(new Dimension(560, 150));
                FlightRouteTablePanel.setMinimumSize(new Dimension(560, 150));
                FlightRouteTablePanel.setMaximumSize(new Dimension(560, 150));
                FlightRouteTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== FlightRouteScrollPane ========
                {
                    FlightRouteScrollPane.setPreferredSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setMinimumSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setMaximumSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setEnabled(false);

                    //---- FlightRouteTable ----
                    FlightRouteTable.setPreferredSize(new Dimension(560, 150));
                    FlightRouteTable.setMaximumSize(new Dimension(560, 150));
                    FlightRouteTable.setMinimumSize(new Dimension(560, 150));
                    FlightRouteTable.setEnabled(false);
                    FlightRouteScrollPane.setViewportView(FlightRouteTable);
                }
                FlightRouteTablePanel.add(FlightRouteScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            FlightRouteinfoPanel2.add(FlightRouteTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            FlightRouteinfoPanel2.add(hSpacer8, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(FlightRouteinfoPanel2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer20 ----
        vSpacer20.setPreferredSize(new Dimension(10, 14));
        vSpacer20.setMinimumSize(new Dimension(12, 20));
        add(vSpacer20, new GridBagConstraints(0, 6, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel selectPackagePanel2;
    private JLabel packageLabel3;
    private JComboBox<String> airlineComboBox;
    private JButton loadAirlineBtn;
    private JPanel FlightRouteinfoPanel2;
    private JLabel FlightRouteLabel2;
    private JPanel hSpacer7;
    private JPanel FlightRouteTablePanel;
    private JScrollPane FlightRouteScrollPane;
    private JTable FlightRouteTable;
    private JPanel hSpacer8;
    private JPanel vSpacer20;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
