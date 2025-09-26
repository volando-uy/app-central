/*
 * Created by JFormDesigner on Thu Sep 04 14:52:43 UYT 2025
 */

package gui.flightRoute.details;

import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import controllers.flight.FlightController;
import controllers.flight.IFlightController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import gui.flight.flightDetails.FlightDetailWindow;
import shared.utils.NonEditableTableModel;

/**
 * @author Nahu
 */
public class FlightRouteDetailWindow extends JFrame {
    private final IFlightController flightController;
    private final FlightRouteDTO route;
    public FlightRouteDetailWindow(FlightRouteDTO route, IFlightController flightController) {
        this.flightController = flightController;
        this.route = route;
        initComponents();

        setResizable(false);

        setTitle("Detalle de Ruta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getOwner());

        try { infoPanel.setBorder(null); } catch (Exception ignored) {}

        // Llenar labels con datos de la rutaiin
        label1.setText("Nombre: " + safe(route.getName()));
        label2.setText("Descripción: " + safe(route.getDescription()));
        label3.setText("Creación: " +
                (route.getCreatedAt() != null
                        ? route.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : ""));

        // Configurar tabla SOLO para vuelos
        String[] columnNames = {
                "Nombre vuelo", "Fecha salida", "Duración (min)",
                "Asientos turista", "Asientos ejecutivo", "Creado"
        };
        NonEditableTableModel model = new NonEditableTableModel(columnNames, 0);

        if (route.getFlightsNames() != null && !route.getFlightsNames().isEmpty()) {
            for (String vuelo : route.getFlightsNames()) {
                try {
                    FlightDTO flight = flightController.getFlightDetailsByName(vuelo);
                    model.addRow(new Object[]{
                            flight.getName(),
                            flight.getDepartureTime() != null ? flight.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "",
                            flight.getDuration(),
                            flight.getMaxEconomySeats(),
                            flight.getMaxBusinessSeats(),
                            flight.getCreatedAt() != null ? flight.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
                    });
                } catch (Exception ex) {
                    model.addRow(new Object[]{vuelo, "Error al cargar", "-", "-", "-", "-"});
                }
            }
        }
        tablaVuelos.setModel(model);
        tablaVuelos.setRowHeight(28);
        tablaVuelos.setFillsViewportHeight(true);
        tablaVuelos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] columnWidths = {150, 150, 120, 140, 150, 120};
        for (int i = 0; i < columnWidths.length; i++) {
            tablaVuelos.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    private String safe(String text) {
        return text != null ? text : "";
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        infoPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        tablaVuelos = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

        //======== infoPanel ========
        {
            infoPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
            . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing
            . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
            Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
            ) ,infoPanel. getBorder( )) ); infoPanel. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
            public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName (
            ) )) throw new RuntimeException( ); }} );
            infoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)infoPanel.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)infoPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)infoPanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)infoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- label1 ----
            label1.setText("Nombre de la ruta de vuelo:");
            infoPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- label2 ----
            label2.setText("Creaci\u00f3n:");
            infoPanel.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- label3 ----
            label3.setText("Aerol\u00ednea:");
            infoPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(infoPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 0, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setPreferredSize(new Dimension(450, 300));
            scrollPane1.setMinimumSize(new Dimension(450, 300));
            scrollPane1.setMaximumSize(new Dimension(450, 300));

            //---- tablaVuelos ----
            tablaVuelos.setPreferredSize(new Dimension(450, 300));
            tablaVuelos.setMinimumSize(new Dimension(450, 300));
            tablaVuelos.setMaximumSize(new Dimension(500000, 500000));
            tablaVuelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(tablaVuelos);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 25, 25, 25), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JPanel infoPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTable tablaVuelos;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}