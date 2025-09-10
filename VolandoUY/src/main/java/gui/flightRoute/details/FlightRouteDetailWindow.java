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

        setTitle("Detalle de Ruta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(getOwner());

        // Llenar labels con datos de la ruta
        label1.setText("Nombre: " + safe(route.getName()));
        label2.setText("Descripción: " + safe(route.getDescription()));
        label3.setText("Creación: " +
                (route.getCreatedAt() != null
                        ? route.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : ""));
        //labelAirline.setText("Aerolínea: " + safe(route.getAirlineName()));
        //labelPriceTourist.setText("Turista: $" + route.getPriceTouristClass());
        //labelPriceBusiness.setText("Ejecutivo: $" + route.getPriceBusinessClass());
        //labelPriceExtra.setText("Equipaje extra: $" + route.getPriceExtraUnitBaggage());

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
        tablaVuelos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 🚀 evita que se achiquen
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
        // Generated using JFormDesigner Evaluation license - Nahuel
        scrollPane1 = new JScrollPane();
        tablaVuelos = new JTable();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tablaVuelos);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(25, 115, 460, 260);

        //---- label1 ----
        label1.setText("Nombre de la ruta de vuelo:");
        contentPane.add(label1);
        label1.setBounds(35, 15, 450, 20);

        //---- label2 ----
        label2.setText("Creaci\u00f3n:");
        contentPane.add(label2);
        label2.setBounds(35, 35, 450, 20);

        //---- label3 ----
        label3.setText("Aerol\u00ednea:");
        contentPane.add(label3);
        label3.setBounds(35, 55, 445, 20);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JScrollPane scrollPane1;
    private JTable tablaVuelos;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}