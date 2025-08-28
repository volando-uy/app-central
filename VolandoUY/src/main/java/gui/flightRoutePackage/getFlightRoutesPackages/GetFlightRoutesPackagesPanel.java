/*
 * Created by JFormDesigner on Tue Aug 26 18:34:36 UYT 2025
 */

package gui.flightRoutePackage.getFlightRoutesPackages;

import controllers.flightRoutePackage.IFlightRoutePackageController;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;

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
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public GetFlightRoutesPackagesPanel(IFlightRoutePackageController flightRoutePackageController ) {
        this.flightRoutePackageController = flightRoutePackageController;
        initComponents();     // TU UI generada
        loadPackagesTable();  // Carga de datos -> misma lógica que UsersPanel
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadPackagesTable() {
        // 1) Traer nombres (según tu controller)
        List<String> names = flightRoutePackageController.getAllNotBoughtFlightRoutePackagesNames();

        // 2) Modelo/columnas
        DefaultTableModel model = new DefaultTableModel();
        String[] cols = {"Nombre", "Descripción", "Validez (días)", "Descuento", "Creado", "Tipo asiento"};
        model.setColumnIdentifiers(cols);

        // 3) Por cada nombre, obtener DTO y armar fila
        for (String name : names) {
            try {
                FlightRoutePackageDTO p = flightRoutePackageController.getFlightRoutePackageByName(name);
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
        PackageTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        PackageTable.setModel(model);

        // 5) Ajuste dinámico
        adjustDynamicWidthAndHeightToTable(PackageTable, model);

        // 6) Selección
        PackageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private String nz(String s) { return s == null ? "" : s; }

    // Si el backend guarda 0.15 => 15%, o 15 => 15%
    private String formatDiscount(double d) {
        double value = (d <= 1.0 && d >= 0.0) ? d * 100.0 : d;
        return (Math.round(value * 100.0) / 100.0) + "%";
    }

    // MISMA lógica que venís usando
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

        // Alto (mínimo 5 filas)
        int minRows = 5;
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(new Dimension(
                table.getPreferredSize().width,
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
        
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        vSpacer17 = new JPanel(null);
        PackageinfoPanel = new JPanel();
        packageLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        PackageTablePanel = new JPanel();
        PackageScrollPane = new JScrollPane();
        PackageTable = new JTable();
        hSpacer6 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
        new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn"
        , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
        , new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 )
        ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener(
        new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
        ;} } );
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

                    //---- PackageTable ----
                    PackageTable.setPreferredSize(new Dimension(560, 150));
                    PackageTable.setMaximumSize(new Dimension(560, 150));
                    PackageTable.setMinimumSize(new Dimension(560, 150));
                    PackageTable.setEnabled(false);
                    PackageScrollPane.setViewportView(PackageTable);
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
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel vSpacer17;
    private JPanel PackageinfoPanel;
    private JLabel packageLabel;
    private JPanel hSpacer5;
    private JPanel PackageTablePanel;
    private JScrollPane PackageScrollPane;
    private JTable PackageTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
