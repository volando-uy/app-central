/*
 * Created by JFormDesigner on Tue Aug 26 18:34:36 UYT 2025
 */

package gui.flightRoutePackage.getFlightRoutesPackages;

import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import gui.flightRoute.details.FlightRouteDetailWindow;
import gui.flightRoutePackage.details.FlightRoutePackageDetailWindow;
import shared.utils.NonEditableTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class GetFlightRoutesPackagesPanel extends JPanel {
    private IFlightRoutePackageController flightRoutePackageController;
    private IFlightRouteController flightRouteController;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public GetFlightRoutesPackagesPanel(
            IFlightRoutePackageController flightRoutePackageController,
            IFlightRouteController flightRouteController
    ) {
        this.flightRoutePackageController = flightRoutePackageController;
        this.flightRouteController = flightRouteController;
        initComponents();     // TU UI generada
        loadPackagesTable();  // Carga de datos -> misma lógica que UsersPanel
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadPackagesTable() {
        // 1) Traer nombres (según tu controller)
        List<String> names = flightRoutePackageController.getAllNotBoughtFlightRoutesPackagesNames();

        // 2) Modelo/columnas
        String[] cols = {"Nombre", "Descripción", "Validez (días)", "Descuento", "Creado", "Tipo asiento"};
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);
        model.setColumnIdentifiers(cols);

        // 3) Por cada nombre, obtener DTO y armar fila
        for (String name : names) {
            try {
                BaseFlightRoutePackageDTO p = flightRoutePackageController.getFlightRoutePackageSimpleDetailsByName(name);
                String desc = nz(p.getDescription());
                Integer days = p.getValidityPeriodDays();
                String disc = formatDiscount(p.getDiscount());  // 0.15 => 15%
                String created = p.getCreationDate() != null ? p.getCreationDate().format(DTF) : "";
                String seat = p.getSeatType() != null ? p.getSeatType().toString() : "";
                model.addRow(new Object[]{ name, desc, days, disc, created, seat });
            } catch (Exception ex) {
                // Si algún paquete falla al traer detalles, igual mostramos el nombre
                model.addRow(new Object[]{ name, "", "", "", "", "" });
            }
        }

        // 4) Tabla (igual que en GetUsersPanel)
        packageTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        packageTable.setModel(model);

        // 5) Ajuste dinámico
        adjustDynamicWidthAndHeightToTable(packageTable);

        // 6) Selección
        packageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private String nz(String s) { return s == null ? "" : s; }

    // Si el backend guarda 0.15 => 15%, o 15 => 15%
    private String formatDiscount(double d) {
        double value = (d <= 1.0 && d >= 0.0) ? d * 100.0 : d;
        return (Math.round(value * 100.0) / 100.0) + "%";
    }

    // MISMA lógica que venís usando
    private void adjustDynamicWidthAndHeightToTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int tableWidth = 0;
        // Ancho
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = table.getRowCount();

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, col
            );
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width) + 50;

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col
                );
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10);
            tableWidth += preferredWidth;
        }

        // Alto
        int minRows = 5;
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(new Dimension(
                tableWidth,
                visibleRows * table.getRowHeight()
        ));
    }

    private void initListeners() {
        // Listener to reload packages table
        packageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadPackagesTable();
            }
        });

        packageTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && packageTable.getSelectedRow() != -1) {
                    int fila = packageTable.getSelectedRow();
                    String packageName = (String) packageTable.getValueAt(fila, 0);
                    new FlightRoutePackageDetailWindow(packageName, flightRoutePackageController, flightRouteController).setVisible(true);
                }
            }
        });
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        vSpacer17 = new JPanel(null);
        PackageinfoPanel = new JPanel();
        packageLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        PackageTablePanel = new JPanel();
        PackageScrollPane = new JScrollPane();
        packageTable = new JTable();
        hSpacer6 = new JPanel(null);
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
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- vSpacer17 ----
        vSpacer17.setMinimumSize(new Dimension(12, 20));
        vSpacer17.setPreferredSize(new Dimension(10, 20));
        add(vSpacer17, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== PackageinfoPanel ========
        {
            PackageinfoPanel.setPreferredSize(new Dimension(640, 180));
            PackageinfoPanel.setMinimumSize(new Dimension(640, 180));
            PackageinfoPanel.setMaximumSize(new Dimension(640, 180));
            PackageinfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)PackageinfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)PackageinfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)PackageinfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)PackageinfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- packageLabel ----
            packageLabel.setText("Paquetes (\u21bb)");
            packageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            packageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            packageLabel.setPreferredSize(new Dimension(120, 30));
            packageLabel.setMaximumSize(new Dimension(120, 30));
            packageLabel.setMinimumSize(new Dimension(120, 30));
            packageLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            packageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            PackageinfoPanel.add(packageLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            PackageinfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== PackageTablePanel ========
            {
                PackageTablePanel.setPreferredSize(new Dimension(560, 150));
                PackageTablePanel.setMinimumSize(new Dimension(560, 150));
                PackageTablePanel.setMaximumSize(new Dimension(560, 150));
                PackageTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)PackageTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)PackageTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)PackageTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)PackageTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== PackageScrollPane ========
                {
                    PackageScrollPane.setPreferredSize(new Dimension(560, 150));
                    PackageScrollPane.setMinimumSize(new Dimension(560, 150));
                    PackageScrollPane.setMaximumSize(new Dimension(560, 150));
                    PackageScrollPane.setEnabled(false);

                    //---- packageTable ----
                    packageTable.setPreferredSize(new Dimension(560, 150));
                    packageTable.setMaximumSize(new Dimension(560, 150));
                    packageTable.setMinimumSize(new Dimension(560, 150));
                    PackageScrollPane.setViewportView(packageTable);
                }
                PackageTablePanel.add(PackageScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            PackageinfoPanel.add(PackageTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            PackageinfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(PackageinfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 14));
        vSpacer19.setMinimumSize(new Dimension(12, 20));
        add(vSpacer19, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JPanel vSpacer17;
    private JPanel PackageinfoPanel;
    private JLabel packageLabel;
    private JPanel hSpacer5;
    private JPanel PackageTablePanel;
    private JScrollPane PackageScrollPane;
    private JTable packageTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
