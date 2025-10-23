/*
 * Created by JFormDesigner on Thu Sep 04 14:52:43 UYT 2025
 */

package gui.flightroute.details;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import controllers.flight.IFlightController;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import shared.constants.Images;
import shared.utils.ImageProcessor;
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
        flightRouteNameLabel.setText("Nombre. " + safe(route.getName()));
        descriptionLabel.setToolTipText(safe(route.getDescription()));
        descriptionLabel.setText("Descripción ...");
        createdAtLabel.setText("Creada. " +
                (route.getCreatedAt() != null
                        ? route.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : ""));
        statusLabel.setText("Estado. " + route.getStatus().toString());
        originAeroLabel.setText("Desde. " + route.getOriginAeroCode());
        destinationAeroLabel.setText("Hasta. " + route.getDestinationAeroCode());
        
        ImageIcon image = ImageProcessor.makeRoundedIcon(!route.getImage().equals(Images.FLIGHT_ROUTE_DEFAULT) ? route.getImage() : Images.FLIGHT_ROUTE_DEFAULT, 50);
        imageLabel.setIcon(image);

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
        // Generated using JFormDesigner Evaluation license - asd
        infoPanel = new JPanel();
        flightRouteNameLabel = new JLabel();
        originAeroLabel = new JLabel();
        imageLabel = new JLabel();
        descriptionLabel = new JLabel();
        destinationAeroLabel = new JLabel();
        createdAtLabel = new JLabel();
        statusLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        tablaVuelos = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 1.0E-4};

        //======== infoPanel ========
        {
            infoPanel.setMaximumSize(new Dimension(480, 61));
            infoPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder(
            0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder
            . BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color.
            red) ,infoPanel. getBorder( )) ); infoPanel. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .
            beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            infoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)infoPanel.getLayout()).columnWidths = new int[] {200, 200, 72, 0};
            ((GridBagLayout)infoPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)infoPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)infoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- flightRouteNameLabel ----
            flightRouteNameLabel.setText("Nombre. ");
            flightRouteNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            flightRouteNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(flightRouteNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- originAeroLabel ----
            originAeroLabel.setText("Desde:");
            originAeroLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            originAeroLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(originAeroLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));
            infoPanel.add(imageLabel, new GridBagConstraints(2, 0, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- descriptionLabel ----
            descriptionLabel.setText("Desc.");
            descriptionLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
            descriptionLabel.setDoubleBuffered(true);
            infoPanel.add(descriptionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- destinationAeroLabel ----
            destinationAeroLabel.setText("Hasta:");
            destinationAeroLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            destinationAeroLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(destinationAeroLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- createdAtLabel ----
            createdAtLabel.setText("Creada.");
            createdAtLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            createdAtLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(createdAtLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- statusLabel ----
            statusLabel.setText("Estado:");
            statusLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
            infoPanel.add(statusLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(infoPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 0, 10), 0, 0));

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
    // Generated using JFormDesigner Evaluation license - asd
    private JPanel infoPanel;
    private JLabel flightRouteNameLabel;
    private JLabel originAeroLabel;
    private JLabel imageLabel;
    private JLabel descriptionLabel;
    private JLabel destinationAeroLabel;
    private JLabel createdAtLabel;
    private JLabel statusLabel;
    private JScrollPane scrollPane1;
    private JTable tablaVuelos;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}