/*
 * Created by JFormDesigner on Mon Aug 25 17:17:14 UYT 2025
 */

package gui.others.getCategories;

import controllers.category.ICategoryController;
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
public class GetCategoriesPanel extends JPanel {
    ICategoryController categoryController;

    // el JTable 'categoryTable' y el JScrollPane 'categoryScrollPane'
    // los genera JFormDesigner en initComponents()

    public GetCategoriesPanel(ICategoryController categoryController) {
        this.categoryController = categoryController;
        initComponents();          // generado por JFormDesigner (debe crear categoryTable)
        loadCategoriesTable();     // misma idea que en GetUsersPanel
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadCategoriesTable() {
        // 1) Traer datos del controller
        List<String> allCategories = categoryController.getAllCategoriesNames();

        // 2) Crear modelo y columnas
        String[] columnNames = {"Categoría"};
        NonEditableTableModel categoryTableModel = new NonEditableTableModel(columnNames, 0);
        categoryTableModel.setColumnIdentifiers(columnNames);

        // 3) Agregar filas
        for (String catName : allCategories) {
            Object[] rowData = { catName };
            categoryTableModel.addRow(rowData);
        }

        // 4) Config de tabla (igual que en GetUsersPanel)
        categoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 5) Ajustes de ancho/alto con la MISMA lógica que tu panel de usuarios
        adjustDynamicWidthAndHeightToTable(categoryTable, categoryTableModel);

        // 6) Modo de selección
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // --- Ajuste dinámico (misma lógica/orden que tu código de usuarios) ---
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
        // Listener to reload categories
        categoryLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCategoriesTable();
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        vSpacer17 = new JPanel(null);
        categoryInfoPanel = new JPanel();
        categoryLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        categoryTablePanel = new JPanel();
        categoryScrollPane = new JScrollPane();
        categoryTable = new JTable();
        hSpacer6 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border
        .EmptyBorder(0,0,0,0), "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",javax.swing.border.TitledBorder.CENTER,javax
        .swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dialo\u0067",java.awt.Font.BOLD,
        12),java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans
        .PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("borde\u0072".equals(e.
        getPropertyName()))throw new RuntimeException();}});
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

        //======== categoryInfoPanel ========
        {
            categoryInfoPanel.setPreferredSize(new Dimension(640, 180));
            categoryInfoPanel.setMinimumSize(new Dimension(640, 180));
            categoryInfoPanel.setMaximumSize(new Dimension(640, 180));
            categoryInfoPanel.setOpaque(false);
            categoryInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)categoryInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)categoryInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)categoryInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)categoryInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- categoryLabel ----
            categoryLabel.setText("Categorias (\u21bb)");
            categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
            categoryLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            categoryLabel.setPreferredSize(new Dimension(120, 30));
            categoryLabel.setMaximumSize(new Dimension(120, 30));
            categoryLabel.setMinimumSize(new Dimension(120, 30));
            categoryLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            categoryLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            categoryInfoPanel.add(categoryLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            categoryInfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== categoryTablePanel ========
            {
                categoryTablePanel.setPreferredSize(new Dimension(560, 150));
                categoryTablePanel.setMinimumSize(new Dimension(560, 150));
                categoryTablePanel.setMaximumSize(new Dimension(560, 150));
                categoryTablePanel.setOpaque(false);
                categoryTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)categoryTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)categoryTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)categoryTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)categoryTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== categoryScrollPane ========
                {
                    categoryScrollPane.setPreferredSize(new Dimension(560, 150));
                    categoryScrollPane.setMinimumSize(new Dimension(560, 150));
                    categoryScrollPane.setMaximumSize(new Dimension(560, 150));
                    categoryScrollPane.setEnabled(false);
                    categoryScrollPane.setOpaque(false);

                    //---- categoryTable ----
                    categoryTable.setPreferredSize(new Dimension(560, 150));
                    categoryTable.setMaximumSize(new Dimension(560, 150));
                    categoryTable.setMinimumSize(new Dimension(560, 150));
                    categoryTable.setOpaque(false);
                    categoryScrollPane.setViewportView(categoryTable);
                }
                categoryTablePanel.add(categoryScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            categoryInfoPanel.add(categoryTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            categoryInfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(categoryInfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
    private JPanel categoryInfoPanel;
    private JLabel categoryLabel;
    private JPanel hSpacer5;
    private JPanel categoryTablePanel;
    private JScrollPane categoryScrollPane;
    private JTable categoryTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
