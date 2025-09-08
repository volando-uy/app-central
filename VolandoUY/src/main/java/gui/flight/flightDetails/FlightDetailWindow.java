/*
 * Created by JFormDesigner on Sun Sep 07 17:18:11 UYT 2025
 */

package gui.flight.flightDetails;

import controllers.flight.IFlightController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import shared.utils.NonEditableTableModel;

import java.awt.*;
import java.util.List;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 * @author Nahu
 */
public class FlightDetailWindow extends JFrame {
    public FlightDetailWindow(FlightDTO flight, List<BookFlightDTO> reservations) {
        initComponents();
        setTitle("Detalle del Vuelo: " + flight.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(getOwner());

        // Cargar datos en la tabla
        loadFlightDetails(flight, reservations);
    }
    private void loadFlightDetails(FlightDTO flight, List<BookFlightDTO> reservations) {
        // Ahora solo hasta "Cliente"
        String[] cols = {"Nombre", "Ruta", "Aerolínea", "Salida", "Duración",
                "Econ. máx", "Bus. máx", "Creado", "Cliente"};
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);

        // Fila de vuelo
        model.addRow(new Object[] {
                safe(flight.getName()),
                safe(flight.getFlightRouteName()),
                safe(flight.getAirlineNickname()),
                flight.getDepartureTime() != null ? flight.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "",
                formatDuration(flight.getDuration()),
                flight.getMaxEconomySeats(),
                flight.getMaxBusinessSeats(),
                flight.getCreatedAt() != null ? flight.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "",
                "-" // Cliente vacío en la fila de vuelo
        });

        // Filas de reservas
        if (reservations != null) {
            for (BookFlightDTO r : reservations) {
                model.addRow(new Object[] {
                        "-", "-", "-", "-", "-", "-", "-", "-",
                        safe(r.getCustomerNickname()) // directo del DTO
                });
            }
        } else {
            // Si no hay reservas, agregar una fila indicándolo
            model.addRow(new Object[] {
                    "-", "-", "-", "-", "-", "-", "-", "-",
                    "No hay reservas"
            });
        }

        tablaRutaVuelo.setModel(model);
        tablaRutaVuelo.setRowHeight(28);
        tablaRutaVuelo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String formatDuration(Long minutes) {
        if (minutes == null || minutes < 0) return "";
        long h = minutes / 60;
        long m = minutes % 60;
        return String.format("%02d:%02d", h, m);
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
