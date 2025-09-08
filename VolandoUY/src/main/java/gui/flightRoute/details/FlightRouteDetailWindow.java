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
    public FlightRouteDetailWindow(FlightRouteDTO route, IFlightController flightController) {
        this.flightController = flightController;
        initComponents();


        setTitle("Detalle de Ruta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(getOwner());

        String[] columnNames = {
                "Nombre", "Descripción", "Fecha de creación",
                "Precio Turista", "Precio Ejecutivo", "Precio Extra Equipaje", "Vuelo Asociado"
        };
        NonEditableTableModel model = new NonEditableTableModel(columnNames, 0);
        model.setColumnIdentifiers(columnNames);

        model.addRow(new Object[]{
                route.getName(),
                route.getDescription(),
                route.getCreatedAt() != null ? route.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                "$" + route.getPriceTouristClass(),
                "$" + route.getPriceBusinessClass(),
                "$" + route.getPriceExtraUnitBaggage(),
                "-"   // vacío en la fila de ruta
        });

        if (route.getFlightsNames() != null && !route.getFlightsNames().isEmpty()) {
            for (String vuelo : route.getFlightsNames()) {
                model.addRow(new Object[]{
                        "Doble", "click", "para", "detalle", "de vuelo", "", vuelo
                });
            }
        }

        tablaRutaVuelo.setModel(model);
        tablaRutaVuelo.setRowHeight(28);
        tablaRutaVuelo.setFillsViewportHeight(true);
        tablaRutaVuelo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaRutaVuelo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tablaRutaVuelo.getSelectedRow();
                    String vuelo = (String) tablaRutaVuelo.getValueAt(row, 6); // columna de vuelos
                    if (vuelo != null && !vuelo.equals("-")) {
                        try {
                            // Obtener detalles del vuelo desde el controlador
                            FlightDTO flight = flightController.getFlightDetailsByName(vuelo);

                            // Abrir ventana de detalle (sin reservas por ahora)
                            FlightDetailWindow detalle = new FlightDetailWindow(flight, null);
                            detalle.setVisible(true);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(
                                    FlightRouteDetailWindow.this,
                                    "Error al abrir detalle del vuelo: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
            }
        });
    }

    private String safe(String text) {
        return text != null ? text : "";
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        scrollPane1 = new JScrollPane();
        tablaRutaVuelo = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tablaRutaVuelo);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(20, 25, 460, 260);

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
    private JTable tablaRutaVuelo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}