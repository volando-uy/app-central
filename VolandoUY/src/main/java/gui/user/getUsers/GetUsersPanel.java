package gui.user.getUsers;

import controllers.category.ICategoryController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Setter
public class GetUsersPanel extends JPanel {

    IUserController userController;

    public GetUsersPanel(IUserController userController) {
        this.userController = userController;
        initComponents();
        loadUsersTables();
        try {
            setBorder(null);
        } catch (Exception ignored) {
        }
    }

    private void loadUsersTables() {
        List<CustomerDTO> getAllCustomers = userController.getAllCustomers();
        List<AirlineDTO> getAllAirlines = userController.getAllAirlines();

        // Create table model and set column names
        DefaultTableModel customerTableModel = new DefaultTableModel();
        String[] columnNames = {"Nickname", "Nombre", "Apellido", "Email", "Nacionalidad", "Fecha de nacimiento", "Tipo de identificación", "Identificación"};
        customerTableModel.setColumnIdentifiers(columnNames);
        
        DefaultTableModel airlineTableModel = new DefaultTableModel();
        String[] airlineColumnNames = {"Nickname", "Nombre", "Email", "Descripción", "Web"};
        airlineTableModel.setColumnIdentifiers(airlineColumnNames);
        
        // Add rows to the table model
        for (CustomerDTO customerDTO : getAllCustomers) {
            Object[] rowData = {
                customerDTO.getNickname(),
                customerDTO.getName(),
                customerDTO.getSurname(),
                customerDTO.getMail(),
                customerDTO.getCitizenship(),
                customerDTO.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                customerDTO.getIdType(),
                customerDTO.getId()
            };
            customerTableModel.addRow(rowData);
        }
        
        for (AirlineDTO airlineDTO : getAllAirlines) {
            Object[] rowData = {
                airlineDTO.getNickname(),
                airlineDTO.getName(),
                airlineDTO.getMail(),
                airlineDTO.getDescription(),
                airlineDTO.getWeb()
            };
            airlineTableModel.addRow(rowData);
        }

        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        airlineTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        adjustDynamicWidthAndHeightToTable(customerTable, customerTableModel);
        adjustDynamicWidthAndHeightToTable(airlineTable, airlineTableModel);

        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        airlineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void adjustDynamicWidthAndHeightToTable(JTable table, DefaultTableModel tableModel) {
        // Dynamic width
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = table.getRowCount();

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, col);
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(table, table.getValueAt(row, col), false, false, row, col);
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10); // add some padding
        }

        table.setModel(tableModel);

        // Dynamic height
        int maxRows = 5;
        int visibleRows = Math.max(table.getRowCount(), maxRows);
        table.setPreferredSize(
                new Dimension(table.getPreferredSize().width, visibleRows * table.getRowHeight())
        );
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - dotto
        vSpacer17 = new JPanel(null);
        CustomerInfoPanel = new JPanel();
        customerLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        CustomerTablePanel = new JPanel();
        customerScrollPane = new JScrollPane();
        customerTable = new JTable();
        hSpacer6 = new JPanel(null);
        AirlineInfoPanel = new JPanel();
        airlineLabel = new JLabel();
        hSpacer7 = new JPanel(null);
        AirlineTablePanel = new JPanel();
        airlineScrollPane = new JScrollPane();
        airlineTable = new JTable();
        hSpacer8 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0
        ,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM
        ,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.red),
         getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
        ){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException();}});
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

        //======== CustomerInfoPanel ========
        {
            CustomerInfoPanel.setPreferredSize(new Dimension(640, 180));
            CustomerInfoPanel.setMinimumSize(new Dimension(640, 180));
            CustomerInfoPanel.setMaximumSize(new Dimension(640, 180));
            CustomerInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)CustomerInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)CustomerInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)CustomerInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)CustomerInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- customerLabel ----
            customerLabel.setText("Clientes");
            customerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            customerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            customerLabel.setPreferredSize(new Dimension(120, 30));
            customerLabel.setMaximumSize(new Dimension(120, 30));
            customerLabel.setMinimumSize(new Dimension(120, 30));
            customerLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            CustomerInfoPanel.add(customerLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            CustomerInfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== CustomerTablePanel ========
            {
                CustomerTablePanel.setPreferredSize(new Dimension(560, 150));
                CustomerTablePanel.setMinimumSize(new Dimension(560, 150));
                CustomerTablePanel.setMaximumSize(new Dimension(560, 150));
                CustomerTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)CustomerTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)CustomerTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)CustomerTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)CustomerTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== customerScrollPane ========
                {
                    customerScrollPane.setPreferredSize(new Dimension(560, 150));
                    customerScrollPane.setMinimumSize(new Dimension(560, 150));
                    customerScrollPane.setMaximumSize(new Dimension(560, 150));
                    customerScrollPane.setEnabled(false);

                    //---- customerTable ----
                    customerTable.setPreferredSize(new Dimension(560, 150));
                    customerTable.setMaximumSize(new Dimension(560, 150));
                    customerTable.setMinimumSize(new Dimension(560, 150));
                    customerTable.setEnabled(false);
                    customerScrollPane.setViewportView(customerTable);
                }
                CustomerTablePanel.add(customerScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            CustomerInfoPanel.add(CustomerTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            CustomerInfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(CustomerInfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== AirlineInfoPanel ========
        {
            AirlineInfoPanel.setPreferredSize(new Dimension(640, 180));
            AirlineInfoPanel.setMinimumSize(new Dimension(640, 180));
            AirlineInfoPanel.setMaximumSize(new Dimension(640, 180));
            AirlineInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)AirlineInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)AirlineInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)AirlineInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)AirlineInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- airlineLabel ----
            airlineLabel.setText("Aerolineas");
            airlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            airlineLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            airlineLabel.setPreferredSize(new Dimension(120, 30));
            airlineLabel.setMaximumSize(new Dimension(120, 30));
            airlineLabel.setMinimumSize(new Dimension(120, 30));
            airlineLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            AirlineInfoPanel.add(airlineLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer7 ----
            hSpacer7.setPreferredSize(new Dimension(40, 10));
            AirlineInfoPanel.add(hSpacer7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== AirlineTablePanel ========
            {
                AirlineTablePanel.setPreferredSize(new Dimension(560, 150));
                AirlineTablePanel.setMinimumSize(new Dimension(560, 150));
                AirlineTablePanel.setMaximumSize(new Dimension(560, 150));
                AirlineTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)AirlineTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)AirlineTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)AirlineTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)AirlineTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== airlineScrollPane ========
                {
                    airlineScrollPane.setPreferredSize(new Dimension(560, 150));
                    airlineScrollPane.setMinimumSize(new Dimension(560, 150));
                    airlineScrollPane.setMaximumSize(new Dimension(560, 150));
                    airlineScrollPane.setEnabled(false);

                    //---- airlineTable ----
                    airlineTable.setPreferredSize(new Dimension(560, 150));
                    airlineTable.setMaximumSize(new Dimension(560, 150));
                    airlineTable.setMinimumSize(new Dimension(560, 150));
                    airlineTable.setEnabled(false);
                    airlineScrollPane.setViewportView(airlineTable);
                }
                AirlineTablePanel.add(airlineScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            AirlineInfoPanel.add(AirlineTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            AirlineInfoPanel.add(hSpacer8, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(AirlineInfoPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer19 ----
        vSpacer19.setPreferredSize(new Dimension(10, 20));
        vSpacer19.setMinimumSize(new Dimension(12, 20));
        add(vSpacer19, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - dotto
    private JPanel vSpacer17;
    private JPanel CustomerInfoPanel;
    private JLabel customerLabel;
    private JPanel hSpacer5;
    private JPanel CustomerTablePanel;
    private JScrollPane customerScrollPane;
    private JTable customerTable;
    private JPanel hSpacer6;
    private JPanel AirlineInfoPanel;
    private JLabel airlineLabel;
    private JPanel hSpacer7;
    private JPanel AirlineTablePanel;
    private JScrollPane airlineScrollPane;
    private JTable airlineTable;
    private JPanel hSpacer8;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
