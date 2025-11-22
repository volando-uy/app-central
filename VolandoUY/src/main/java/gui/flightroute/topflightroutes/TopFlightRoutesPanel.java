/*
 * Created by JFormDesigner on Tue Aug 26 18:34:01 UYT 2025
 */

package gui.flightroute.topflightroutes;

import controllers.flight.IFlightController;
import controllers.flightroute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.user.BaseAirlineDTO;
import gui.flightroute.details.FlightRouteDetailWindow;
import shared.utils.GUIUtils;
import shared.utils.NonEditableTableModel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * @author AparicioQuian
 */
public class TopFlightRoutesPanel extends JPanel {
    private  IFlightRouteController flightRouteController;

    public TopFlightRoutesPanel(IFlightRouteController flightRouteController) {
        this.flightRouteController = flightRouteController;

        initComponents();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void initListeners() {

        topFlightRoutesLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadFlightRoutesTable();
            }
        });
    }

    // ------------------ TABLA ------------------
    private void loadFlightRoutesTable() {
        List<FlightRouteDTO> topRoutes =
                flightRouteController.getTopFlightRoutesDetailsByVisitCount();

        String[] cols = {
                "Nombre", "Aerolínea", "Aero origen", "Aero destino",
                "Cant. Visitas"
        };
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);
        model.setColumnIdentifiers(cols);

        for (FlightRouteDTO r : topRoutes) {
            model.addRow(new Object[]{
                    r.getName(),
                    r.getAirlineNickname(),
                    r.getOriginAeroCode(),
                    r.getDestinationAeroCode(),
                    r.getVisitCount()
            });
        }

        flightRoutesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightRoutesTable.setModel(model);
        GUIUtils.adjustDynamicWidthAndHeightToTable(flightRoutesTable);
        flightRoutesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        vSpacer14 = new JPanel(null);
        topFlightRoutesPanel = new JPanel();
        topFlightRoutesLabel = new JLabel();
        hSpacer7 = new JPanel(null);
        topFlightRouteTablePanel = new JPanel();
        flightRoutesScrollPane = new JScrollPane();
        flightRoutesTable = new JTable();
        hSpacer8 = new JPanel(null);
        vSpacer20 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
        swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax. swing. border
        . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069al\u006fg"
        ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) , getBorder
        ( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
        .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () )) throw new RuntimeException
        ( ); }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- vSpacer14 ----
        vSpacer14.setPreferredSize(new Dimension(10, 40));
        vSpacer14.setMinimumSize(new Dimension(12, 40));
        vSpacer14.setOpaque(false);
        add(vSpacer14, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== topFlightRoutesPanel ========
        {
            topFlightRoutesPanel.setPreferredSize(new Dimension(640, 180));
            topFlightRoutesPanel.setMinimumSize(new Dimension(640, 180));
            topFlightRoutesPanel.setMaximumSize(new Dimension(640, 180));
            topFlightRoutesPanel.setOpaque(false);
            topFlightRoutesPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)topFlightRoutesPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)topFlightRoutesPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)topFlightRoutesPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)topFlightRoutesPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- topFlightRoutesLabel ----
            topFlightRoutesLabel.setText("Top Rutas de vuelo (\u21bb)");
            topFlightRoutesLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topFlightRoutesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            topFlightRoutesLabel.setPreferredSize(new Dimension(120, 30));
            topFlightRoutesLabel.setMaximumSize(new Dimension(120, 30));
            topFlightRoutesLabel.setMinimumSize(new Dimension(120, 30));
            topFlightRoutesLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            topFlightRoutesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            topFlightRoutesPanel.add(topFlightRoutesLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer7 ----
            hSpacer7.setPreferredSize(new Dimension(40, 10));
            hSpacer7.setOpaque(false);
            topFlightRoutesPanel.add(hSpacer7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== topFlightRouteTablePanel ========
            {
                topFlightRouteTablePanel.setPreferredSize(new Dimension(560, 150));
                topFlightRouteTablePanel.setMinimumSize(new Dimension(560, 150));
                topFlightRouteTablePanel.setMaximumSize(new Dimension(560, 150));
                topFlightRouteTablePanel.setOpaque(false);
                topFlightRouteTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)topFlightRouteTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)topFlightRouteTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)topFlightRouteTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)topFlightRouteTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== flightRoutesScrollPane ========
                {
                    flightRoutesScrollPane.setPreferredSize(new Dimension(560, 150));
                    flightRoutesScrollPane.setMinimumSize(new Dimension(560, 150));
                    flightRoutesScrollPane.setMaximumSize(new Dimension(560, 150));
                    flightRoutesScrollPane.setEnabled(false);
                    flightRoutesScrollPane.setOpaque(false);

                    //---- flightRoutesTable ----
                    flightRoutesTable.setPreferredSize(new Dimension(560, 150));
                    flightRoutesTable.setMaximumSize(new Dimension(560, 150));
                    flightRoutesTable.setMinimumSize(new Dimension(560, 150));
                    flightRoutesTable.setOpaque(false);
                    flightRoutesScrollPane.setViewportView(flightRoutesTable);
                }
                topFlightRouteTablePanel.add(flightRoutesScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            topFlightRoutesPanel.add(topFlightRouteTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            hSpacer8.setOpaque(false);
            topFlightRoutesPanel.add(hSpacer8, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(topFlightRoutesPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- vSpacer20 ----
        vSpacer20.setPreferredSize(new Dimension(10, 300));
        vSpacer20.setMinimumSize(new Dimension(12, 300));
        vSpacer20.setOpaque(false);
        add(vSpacer20, new GridBagConstraints(0, 3, 1, 1, 0.0, 2.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel vSpacer14;
    private JPanel topFlightRoutesPanel;
    private JLabel topFlightRoutesLabel;
    private JPanel hSpacer7;
    private JPanel topFlightRouteTablePanel;
    private JScrollPane flightRoutesScrollPane;
    private JTable flightRoutesTable;
    private JPanel hSpacer8;
    private JPanel vSpacer20;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
