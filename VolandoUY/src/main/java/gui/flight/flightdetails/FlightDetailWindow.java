/*
 * Created by JFormDesigner on Sun Sep 07 17:18:11 UYT 2025
 */

package gui.flight.flightdetails;

import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import shared.constants.Images;
import shared.utils.ImageProcessor;
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
        setLocationRelativeTo(getOwner());

        setResizable(false);

        // Cargar datos en la tabla
        loadFlightDetails(flight);
        loadReservationsTable(reservations);

    }
    
    private void loadReservationsTable(List<BookFlightDTO> reservations) {
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

        reservationsTable.setModel(model);
        reservationsTable.setRowHeight(28);
        reservationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] columnWidths = {100, 200, 300};
        for (int i = 0; i < columnWidths.length; i++) {
            reservationsTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    private void loadFlightDetails(FlightDTO flight) {
        try { infoPanel.setBorder(null); } catch (Exception e) {}
        flightNameLabel.setText("Nombre. " + safe(flight.getName()));
        flightRouteNameLabel.setText("Rut. " + safe(flight.getFlightRouteName()));
        flightAirlineNameLabel.setText( "Aero. " + safe(flight.getAirlineNickname()));
        departureLabel.setText("Sal. " + (flight.getDepartureTime() != null ? flight.getDepartureTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/A"));
        durationLabel.setText("Dur. " + formatDuration(flight.getDuration()));
        ImageIcon image = ImageProcessor.makeRoundedIcon(!flight.getImage().equals(Images.FLIGHT_DEFAULT) ? flight.getImage() : Images.FLIGHT_DEFAULT, 50);
        imageLabel.setIcon(image);

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
        // Generated using JFormDesigner Evaluation license - asd
        infoPanel = new JPanel();
        flightNameLabel = new JLabel();
        departureLabel = new JLabel();
        imageLabel = new JLabel();
        flightRouteNameLabel = new JLabel();
        durationLabel = new JLabel();
        flightAirlineNameLabel = new JLabel();
        scrollPane2 = new JScrollPane();
        reservationsTable = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

        //======== infoPanel ========
        {
            infoPanel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.
            EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing
            .border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),
            java.awt.Color.red),infoPanel. getBorder()));infoPanel. addPropertyChangeListener(new java.beans.PropertyChangeListener()
            {@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))
            throw new RuntimeException();}});
            infoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)infoPanel.getLayout()).columnWidths = new int[] {208, 137, 72, 0};
            ((GridBagLayout)infoPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)infoPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)infoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- flightNameLabel ----
            flightNameLabel.setText("Nombre. ");
            flightNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            flightNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(flightNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- departureLabel ----
            departureLabel.setText("Sal.");
            departureLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            departureLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(departureLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));
            infoPanel.add(imageLabel, new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- flightRouteNameLabel ----
            flightRouteNameLabel.setText("Rut. ");
            flightRouteNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            flightRouteNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(flightRouteNameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- durationLabel ----
            durationLabel.setText("Dur.");
            durationLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            durationLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(durationLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- flightAirlineNameLabel ----
            flightAirlineNameLabel.setText("Aero.");
            flightAirlineNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            flightAirlineNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(flightAirlineNameLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(infoPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 0, 10), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setPreferredSize(new Dimension(450, 300));
            scrollPane2.setMinimumSize(new Dimension(450, 300));
            scrollPane2.setMaximumSize(new Dimension(450, 300));

            //---- reservationsTable ----
            reservationsTable.setPreferredSize(new Dimension(450, 300));
            reservationsTable.setMinimumSize(new Dimension(450, 300));
            reservationsTable.setMaximumSize(new Dimension(500000, 500000));
            reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane2.setViewportView(reservationsTable);
        }
        contentPane.add(scrollPane2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
            new Insets(0, 25, 25, 25), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel infoPanel;
    private JLabel flightNameLabel;
    private JLabel departureLabel;
    private JLabel imageLabel;
    private JLabel flightRouteNameLabel;
    private JLabel durationLabel;
    private JLabel flightAirlineNameLabel;
    private JScrollPane scrollPane2;
    private JTable reservationsTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
