/*
 * Created by JFormDesigner on Mon Aug 25 17:16:09 UYT 2025
 */

package gui.others.getairports;

import controllers.airport.IAirportController;
import domain.dtos.airport.AirportDTO;
import shared.utils.GUIUtils;
import shared.utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
/**
 * @author AparicioQuian
 */
public class GetAirportsPanel extends JPanel {
    IAirportController airportController;

    public GetAirportsPanel(IAirportController airportController) {
        this.airportController = airportController;
        initComponents();
        loadAirportsTable();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadAirportsTable() {
        // 1) Traer datos del controller
        List<AirportDTO> allAirports = airportController.getAllAirportsDetails();

        // 2) Crear modelo y columnas
        String[] columnNames = {"Codigo", "Nombre", "Ciudad"};
        NonEditableTableModel airportTableModel = new NonEditableTableModel(columnNames, 0);
        airportTableModel.setColumnIdentifiers(columnNames);

        // 3) Agregar filas
        for (AirportDTO airport : allAirports) {
            Object[] rowData = { airport.getCode(), airport.getName(), airport.getCityName() };
            airportTableModel.addRow(rowData);
        }

        // 4) Config de tabla (igual que en GetUsersPanel)
        airportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // 6) Modo de selección
        airportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        GUIUtils.adjustDynamicWidthAndHeightToTable(airportTable);
        airportTable.setModel(airportTableModel);
    }
    
    private void initListeners() {
        // Listener to reload table
        airportLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadAirportsTable();
            }
        });
    }
    
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        vSpacer17 = new JPanel(null);
        airportInfoPanel = new JPanel();
        airportLabel = new JLabel();
        hSpacer5 = new JPanel(null);
        airportTablePanel = new JPanel();
        airportScrollPane = new JScrollPane();
        airportTable = new JTable();
        hSpacer6 = new JPanel(null);
        vSpacer19 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing
        . border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder
        . CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .
        awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder( )) )
        ;  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
        ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} )
        ;
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

        //======== airportInfoPanel ========
        {
            airportInfoPanel.setPreferredSize(new Dimension(640, 180));
            airportInfoPanel.setMinimumSize(new Dimension(640, 180));
            airportInfoPanel.setMaximumSize(new Dimension(640, 180));
            airportInfoPanel.setOpaque(false);
            airportInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)airportInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)airportInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)airportInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)airportInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- airportLabel ----
            airportLabel.setText("Aeropuertos (\u21bb)");
            airportLabel.setHorizontalAlignment(SwingConstants.CENTER);
            airportLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            airportLabel.setPreferredSize(new Dimension(120, 30));
            airportLabel.setMaximumSize(new Dimension(120, 30));
            airportLabel.setMinimumSize(new Dimension(120, 30));
            airportLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            airportLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            airportInfoPanel.add(airportLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            airportInfoPanel.add(hSpacer5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== airportTablePanel ========
            {
                airportTablePanel.setPreferredSize(new Dimension(560, 150));
                airportTablePanel.setMinimumSize(new Dimension(560, 150));
                airportTablePanel.setMaximumSize(new Dimension(560, 150));
                airportTablePanel.setOpaque(false);
                airportTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)airportTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)airportTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)airportTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)airportTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== airportScrollPane ========
                {
                    airportScrollPane.setPreferredSize(new Dimension(560, 150));
                    airportScrollPane.setMinimumSize(new Dimension(560, 150));
                    airportScrollPane.setMaximumSize(new Dimension(560, 150));
                    airportScrollPane.setEnabled(false);
                    airportScrollPane.setOpaque(false);

                    //---- airportTable ----
                    airportTable.setPreferredSize(new Dimension(560, 150));
                    airportTable.setMaximumSize(new Dimension(560, 150));
                    airportTable.setMinimumSize(new Dimension(560, 150));
                    airportTable.setOpaque(false);
                    airportScrollPane.setViewportView(airportTable);
                }
                airportTablePanel.add(airportScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            airportInfoPanel.add(airportTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            airportInfoPanel.add(hSpacer6, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(airportInfoPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
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
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel vSpacer17;
    private JPanel airportInfoPanel;
    private JLabel airportLabel;
    private JPanel hSpacer5;
    private JPanel airportTablePanel;
    private JScrollPane airportScrollPane;
    private JTable airportTable;
    private JPanel hSpacer6;
    private JPanel vSpacer19;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
