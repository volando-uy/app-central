/*
 * Created by JFormDesigner on Tue Aug 26 17:55:40 UYT 2025
 */

package gui.flight.getFlight;

import controllers.flight.IFlightController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.user.BaseAirlineDTO;
import gui.flight.flightDetails.FlightDetailWindow;
import shared.utils.NonEditableTableModel;

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
public class GetFlightsPanel extends JPanel {
    private IUserController userController;
    private List<BaseAirlineDTO> airlines = new ArrayList<>();
    private List<FlightDTO> flights = new ArrayList<>();
    private boolean areAirlinesLoading = false;

    private IFlightController flightController;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GetFlightsPanel(IFlightController flightController, IUserController userController) {
        this.flightController = flightController;
        this.userController = userController;
        initComponents();
        initListeners();
        loadAirlinesIntoCombo();
        clearTable();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}

    }
    private void clearTable() {
        String[] cols = {"Nombre", "Ruta", "Aerolínea", "Salida", "Duración", "Econ. máx", "Bus. máx", "Creado"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        FlightTable.setModel(model);
    }
    private void loadAirlinesIntoCombo() {
        areAirlinesLoading = true;
        airlineComboBox.removeAllItems();
        airlines.clear();

        List<BaseAirlineDTO> list = userController.getAllAirlinesSimpleDetails();
        if (list == null) return;

        airlines.addAll(list);
        for (BaseAirlineDTO a : airlines) {
            String display = safeStr(a.getName()) + " (" + safeStr(a.getNickname()) + ")";
            airlineComboBox.addItem(display);
        }

        areAirlinesLoading = false;
        if (!airlines.isEmpty()) airlineComboBox.setSelectedIndex(0);
    }
    private String getSelectedAirlineNickname() {
        int idx = airlineComboBox.getSelectedIndex();
        if (idx < 0 || idx >= airlines.size()) return null;
        return airlines.get(idx).getNickname();
    }
    private void loadFlightsTable(String airlineNickname) {
        flights = flightController.getAllFlightsDetailsByAirline(airlineNickname);

        String[] cols = {"Nombre", "Ruta", "Aerolínea", "Salida", "Duración", "Econ. máx", "Bus. máx", "Creado"};
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);
        model.setColumnIdentifiers(cols);

        for (FlightDTO f : flights) {
            String departure   = f.getDepartureTime() != null ? f.getDepartureTime().format(DTF) : "";
            String created     = f.getCreatedAt() != null ? f.getCreatedAt().format(DTF) : "";
            String durationStr = formatDuration(f.getDuration());

            model.addRow(new Object[] {
                    safeStr(f.getName()),
                    safeStr(f.getFlightRouteName()),
                    safeStr(f.getAirlineNickname()),
                    departure,
                    durationStr,
                    f.getMaxEconomySeats(),
                    f.getMaxBusinessSeats(),
                    created
            });
        }

        FlightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FlightTable.setModel(model);
        adjustDynamicWidthAndHeightToTable(FlightTable, model);
        FlightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }


    // ayuda: minutos -> "HH:mm"
    private String formatDuration(Long minutes) {
        if (minutes == null || minutes < 0) return "";
        long h = minutes / 60;
        long m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    // ayuda: null-safe para strings
    private String safeStr(String s) {
        return s == null ? "" : s;
    }

    // ajuste dinámico (podés usar el tuyo; dejo el mismo patrón)
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

    private void initListeners() {
        // Cuando seleccionás una aerolínea
        airlineComboBox.addActionListener(e -> {
            if (areAirlinesLoading) return;
            String nickname = getSelectedAirlineNickname();
            if (nickname != null && !nickname.isEmpty()) {
                loadFlightsTable(nickname);
            } else {
                clearTable();
            }
        });

        // Refrescar aerolíneas y vuelos al hacer clic en el título
        flightLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                loadAirlinesIntoCombo();
                String nickname = getSelectedAirlineNickname();
                if (nickname != null && !nickname.isEmpty()) {
                    loadFlightsTable(nickname);
                } else {
                    clearTable();
                }
            }
        });
        //Listener de doble clic para abrir detalle del vuelo
        FlightTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && FlightTable.getSelectedRow() != -1) {
                    int row = FlightTable.getSelectedRow();
                    FlightDTO flight = flights.get(row);

                    // Lista vacía de reservas
                    List<BookFlightDTO> reservations = new ArrayList<>();

                    SwingUtilities.invokeLater(() -> {
                        FlightDetailWindow detailWin = new FlightDetailWindow(flight, reservations);
                        detailWin.setVisible(true);
                    });
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        vSpacer17 = new JPanel(null);
        FlightInfoPanel = new JPanel();
        flightLabel = new JLabel();
        selectAirlinePanel = new JPanel();
        vSpacer18 = new JPanel(null);
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        vSpacer20 = new JPanel(null);
        hSpacer5 = new JPanel(null);
        FlightTablePanel = new JPanel();
        FlightScrollPane = new JScrollPane();
        FlightTable = new JTable();
        hSpacer6 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder
        ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border
        .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt
        . Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void
        propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- vSpacer17 ----
        vSpacer17.setMinimumSize(new Dimension(12, 20));
        vSpacer17.setPreferredSize(new Dimension(10, 20));
        vSpacer17.setOpaque(false);
        add(vSpacer17, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== FlightInfoPanel ========
        {
            FlightInfoPanel.setPreferredSize(new Dimension(640, 180));
            FlightInfoPanel.setMinimumSize(new Dimension(640, 180));
            FlightInfoPanel.setMaximumSize(new Dimension(640, 180));
            FlightInfoPanel.setOpaque(false);
            FlightInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)FlightInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)FlightInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0, 0};
            ((GridBagLayout)FlightInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)FlightInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- flightLabel ----
            flightLabel.setHorizontalAlignment(SwingConstants.CENTER);
            flightLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            flightLabel.setPreferredSize(new Dimension(120, 30));
            flightLabel.setMaximumSize(new Dimension(120, 30));
            flightLabel.setMinimumSize(new Dimension(120, 30));
            flightLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            flightLabel.setText("Vuelos (\u21bb)");
            flightLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            FlightInfoPanel.add(flightLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== selectAirlinePanel ========
            {
                selectAirlinePanel.setOpaque(false);
                selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

                //---- vSpacer18 ----
                vSpacer18.setMinimumSize(new Dimension(12, 20));
                vSpacer18.setPreferredSize(new Dimension(10, 20));
                vSpacer18.setOpaque(false);
                selectAirlinePanel.add(vSpacer18);

                //---- airlineLabel ----
                airlineLabel.setText("Selecciona la Aerolinea:");
                selectAirlinePanel.add(airlineLabel);

                //---- airlineComboBox ----
                airlineComboBox.setMinimumSize(new Dimension(150, 30));
                airlineComboBox.setPreferredSize(new Dimension(150, 30));
                airlineComboBox.setOpaque(false);
                selectAirlinePanel.add(airlineComboBox);

                //---- vSpacer20 ----
                vSpacer20.setMinimumSize(new Dimension(12, 20));
                vSpacer20.setPreferredSize(new Dimension(10, 20));
                vSpacer20.setOpaque(false);
                selectAirlinePanel.add(vSpacer20);
            }
            FlightInfoPanel.add(selectAirlinePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            FlightInfoPanel.add(hSpacer5, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== FlightTablePanel ========
            {
                FlightTablePanel.setPreferredSize(new Dimension(560, 150));
                FlightTablePanel.setMinimumSize(new Dimension(560, 150));
                FlightTablePanel.setMaximumSize(new Dimension(560, 150));
                FlightTablePanel.setOpaque(false);
                FlightTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)FlightTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)FlightTablePanel.getLayout()).rowHeights = new int[] {0, 0, 67, 0};
                ((GridBagLayout)FlightTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)FlightTablePanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //======== FlightScrollPane ========
                {
                    FlightScrollPane.setPreferredSize(new Dimension(560, 150));
                    FlightScrollPane.setMinimumSize(new Dimension(560, 150));
                    FlightScrollPane.setMaximumSize(new Dimension(560, 150));
                    FlightScrollPane.setEnabled(false);
                    FlightScrollPane.setOpaque(false);

                    //---- FlightTable ----
                    FlightTable.setPreferredSize(new Dimension(560, 150));
                    FlightTable.setMaximumSize(new Dimension(560, 150));
                    FlightTable.setMinimumSize(new Dimension(560, 150));
                    FlightTable.setOpaque(false);
                    FlightScrollPane.setViewportView(FlightTable);
                }
                FlightTablePanel.add(FlightScrollPane, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            FlightInfoPanel.add(FlightTablePanel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            FlightInfoPanel.add(hSpacer6, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(FlightInfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 14));
        vSpacer19.setMinimumSize(new Dimension(12, 20));
        vSpacer19.setOpaque(false);
        add(vSpacer19, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JPanel vSpacer17;
    private JPanel FlightInfoPanel;
    private JLabel flightLabel;
    private JPanel selectAirlinePanel;
    private JPanel vSpacer18;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JPanel vSpacer20;
    private JPanel hSpacer5;
    private JPanel FlightTablePanel;
    private JScrollPane FlightScrollPane;
    private JTable FlightTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
