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
        String[] cols = {"ID Reserva", "Cliente", "Tickets"};
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);

        if (reservations != null && !reservations.isEmpty()) {
            for (BookFlightDTO r : reservations) {
                model.addRow(new Object[]{
                        r.getId(),
                        safe(r.getCustomerNickname()),
                        r.getTicketIds() != null ? r.getTicketIds().toString() : "-"
                });
            }
        } else {
            model.addRow(new Object[]{"-", "No hay reservas", "-"});
        }

        tablaRutaVuelo.setModel(model);
        tablaRutaVuelo.setRowHeight(28);
        tablaRutaVuelo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] columnWidths = {100, 200, 300};
        for (int i = 0; i < columnWidths.length; i++) {
            tablaRutaVuelo.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        label1.setText("Nombre del vuelo: " + safe(flight.getName()));
        label2.setText("Ruta: " + safe(flight.getFlightRouteName()) + " | Aerolínea: " + safe(flight.getAirlineNickname()));
        label3.setText("Salida: " +
                (flight.getDepartureTime() != null ? flight.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A")
                + " | Duración: " + formatDuration(flight.getDuration()));
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
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tablaRutaVuelo);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(20, 110, 460, 215);

        //---- label1 ----
        label1.setText("Nombre del vuelo:");
        contentPane.add(label1);
        label1.setBounds(25, 5, 650, label1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Otro dato del vuelo:");
        contentPane.add(label2);
        label2.setBounds(25, 25, 650, label2.getPreferredSize().height);

        //---- label3 ----
        label3.setText("Un dato m\u00e1s del vuelo:");
        contentPane.add(label3);
        label3.setBounds(25, 45, 650, label3.getPreferredSize().height);

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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
