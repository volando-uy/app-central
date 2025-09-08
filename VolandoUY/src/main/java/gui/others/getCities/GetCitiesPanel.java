/*
 * Created by JFormDesigner on Mon Aug 25 17:16:09 UYT 2025
 */

package gui.others.getCities;

import controllers.city.ICityController;
import shared.utils.NonEditableTableModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
/**
 * @author AparicioQuian
 */
public class GetCitiesPanel extends JPanel {
    ICityController cityController;
    public GetCitiesPanel(ICityController cityController) {
        if (cityController == null) {
            throw new IllegalArgumentException("ICityController es null");
        }
        this.cityController = cityController;
        initComponents();
        loadCitiesTable();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadCitiesTable() {
        // 1) Traer datos del controller
        List<String> allCities = cityController.getAllCities();

        // 2) Crear modelo y columnas
        String[] columnNames = {"Ciudad"};
        NonEditableTableModel cityTableModel = new NonEditableTableModel(columnNames, 0);
        cityTableModel.setColumnIdentifiers(columnNames);

        // 3) Agregar filas
        for (String cityName : allCities) {
            Object[] rowData = { cityName };
            cityTableModel.addRow(rowData);
        }

        // 4) Config de tabla (igual que en GetUsersPanel)
        cityTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 5) Ajustes de ancho/alto con la MISMA lógica que tu panel de usuarios
        adjustDynamicWidthAndHeightToTable(cityTable, cityTableModel);

        // 6) Modo de selección
        cityTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void adjustDynamicWidthAndHeightToTable(JTable table, DefaultTableModel tableModel) {
        // Dynamic width
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
            column.setPreferredWidth(preferredWidth + 10); // padding
        }

        table.setModel(tableModel);

        // Dynamic height
        int maxRows = 5;
        int visibleRows = Math.max(table.getRowCount(), maxRows);
        table.setPreferredSize(
                new Dimension(table.getPreferredSize().width, visibleRows * table.getRowHeight())
        );
    }
    
    private void initListeners() {
        // Listener to reload table
        cityLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCitiesTable();
            }
        });
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        vSpacer17 = new JPanel(null);
        cityInfoPanel = new JPanel();
        cityLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        cityTablePanel = new JPanel();
        cityScrollPane = new JScrollPane();
        cityTable = new JTable();
        hSpacer6 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
        . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax
        . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,
        12 ), java. awt. Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans
        . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .
        getPropertyName () )) throw new RuntimeException( ); }} );
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

        //======== cityInfoPanel ========
        {
            cityInfoPanel.setPreferredSize(new Dimension(640, 180));
            cityInfoPanel.setMinimumSize(new Dimension(640, 180));
            cityInfoPanel.setMaximumSize(new Dimension(640, 180));
            cityInfoPanel.setOpaque(false);
            cityInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)cityInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)cityInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)cityInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)cityInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- cityLabel ----
            cityLabel.setText("Ciudades (\u21bb)");
            cityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cityLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            cityLabel.setPreferredSize(new Dimension(120, 30));
            cityLabel.setMaximumSize(new Dimension(120, 30));
            cityLabel.setMinimumSize(new Dimension(120, 30));
            cityLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            cityLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cityInfoPanel.add(cityLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            cityInfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== cityTablePanel ========
            {
                cityTablePanel.setPreferredSize(new Dimension(560, 150));
                cityTablePanel.setMinimumSize(new Dimension(560, 150));
                cityTablePanel.setMaximumSize(new Dimension(560, 150));
                cityTablePanel.setOpaque(false);
                cityTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)cityTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)cityTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)cityTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)cityTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== cityScrollPane ========
                {
                    cityScrollPane.setPreferredSize(new Dimension(560, 150));
                    cityScrollPane.setMinimumSize(new Dimension(560, 150));
                    cityScrollPane.setMaximumSize(new Dimension(560, 150));
                    cityScrollPane.setEnabled(false);
                    cityScrollPane.setOpaque(false);

                    //---- cityTable ----
                    cityTable.setPreferredSize(new Dimension(560, 150));
                    cityTable.setMaximumSize(new Dimension(560, 150));
                    cityTable.setMinimumSize(new Dimension(560, 150));
                    cityTable.setOpaque(false);
                    cityScrollPane.setViewportView(cityTable);
                }
                cityTablePanel.add(cityScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            cityInfoPanel.add(cityTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            cityInfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(cityInfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 20));
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
    private JPanel cityInfoPanel;
    private JLabel cityLabel;
    private JPanel hSpacer5;
    private JPanel cityTablePanel;
    private JScrollPane cityScrollPane;
    private JTable cityTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
