/*
 * Created by JFormDesigner on Tue Aug 26 17:55:40 UYT 2025
 */

package gui.flight.getFlight;

import controllers.flight.IFlightController;
import domain.dtos.flight.FlightDTO;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.List;
/**
 * @author AparicioQuian
 */
public class GetFlightsPanel extends JPanel {

    private IFlightController flightController;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public GetFlightsPanel(IFlightController flightController) {
        if (flightController == null) throw new IllegalArgumentException("IFlightController es null");
        this.flightController = flightController;
        initComponents();
        loadFlightsTable();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}

    }

    private void loadFlightsTable() {
        // 1) Traer datos
        List<FlightDTO> flights = flightController.getAllFlights(); // usa tu método real

        // 2) Modelo y columnas
        DefaultTableModel model = new DefaultTableModel();
        String[] cols = {"Nombre", "Ruta", "Aerolínea", "Salida", "Duración", "Econ. máx", "Bus. máx", "Creado"};
        model.setColumnIdentifiers(cols);

        // 3) Filas
        for (FlightDTO f : flights) {
            String name        = safeStr(f.getName());
            String route       = safeStr(f.getFlightRouteName());
            String airline     = safeStr(f.getAirlineNickname());
            String departure   = f.getDepartureTime() != null ? f.getDepartureTime().format(DTF) : "";
            String created     = f.getCreatedAt() != null ? f.getCreatedAt().format(DTF) : "";
            String durationStr = formatDuration(f.getDuration()); // minutos -> HH:mm
            Integer econMax    = f.getMaxEconomySeats();
            Integer busMax     = f.getMaxBusinessSeats();

            model.addRow(new Object[] {
                    name, route, airline, departure, durationStr, econMax, busMax, created
            });
        }

        // 4) Config tabla
        FlightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FlightTable.setModel(model);

        // 5) Ajuste dinámico (igual que venís usando)
        adjustDynamicWidthAndHeightToTable(FlightTable, model);

        // 6) Selección
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
        // Listener to reload the flights table
        flightLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                loadFlightsTable();
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        vSpacer17 = new JPanel(null);
        FlightInfoPanel = new JPanel();
        flightLabel = new JLabel();
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
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder( 0
        , 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
        , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,
         getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
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
            ((GridBagLayout)FlightInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)FlightInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)FlightInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

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

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            FlightInfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
                ((GridBagLayout)FlightTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)FlightTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)FlightTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

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
                    FlightTable.setEnabled(false);
                    FlightTable.setOpaque(false);
                    FlightScrollPane.setViewportView(FlightTable);
                }
                FlightTablePanel.add(FlightScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            FlightInfoPanel.add(FlightTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            FlightInfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
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
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel vSpacer17;
    private JPanel FlightInfoPanel;
    private JLabel flightLabel;
    private JPanel hSpacer5;
    private JPanel FlightTablePanel;
    private JScrollPane FlightScrollPane;
    private JTable FlightTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
