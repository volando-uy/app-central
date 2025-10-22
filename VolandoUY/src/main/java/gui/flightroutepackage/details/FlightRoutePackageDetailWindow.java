/*
 * Created by JFormDesigner on Sun Sep 07 17:18:11 UYT 2025
 */

package gui.flightroutepackage.details;

import controllers.flightroute.IFlightRouteController;
import controllers.flightroutepackage.IFlightRoutePackageController;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import shared.utils.GUIUtils;
import shared.utils.NonEditableTableModel;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Nahu
 */
public class FlightRoutePackageDetailWindow extends JFrame {

    private IFlightRoutePackageController flightRoutePackageController;
    private IFlightRouteController flightRouteController;
    private FlightRoutePackageDTO flightRoutePackageDTO;

    private static  DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public FlightRoutePackageDetailWindow(String packageName, IFlightRoutePackageController flightRoutePackageController, IFlightRouteController flightRouteController) {
        this.flightRoutePackageController = flightRoutePackageController;
        this.flightRouteController = flightRouteController;
        this.flightRoutePackageDTO = flightRoutePackageController.getFlightRoutePackageDetailsByName(packageName);
        if (flightRoutePackageDTO == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró el paquete de rutas aéreas: " + packageName,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            dispose();
            return;
        }

        initComponents();
        setTitle("Detalle del Paquete: " + packageName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getOwner());

        setResizable(false);

        // Cargar datos en la tabla
        loadFlightRoutePackageDetails();
        loadFlightRoutesTable();

    }

    private void loadFlightRoutesTable() {
        List<FlightRouteDTO> packRoutes = flightRouteController.getAllFlightRoutesDetailsByPackageName(flightRoutePackageDTO.getName());

        String[] cols = {
                "Nombre", "Descripción", "Creado",
                "Origen", "Destino", "Aerolínea",
                "$ Turista", "$ Business", "$ Extra Bulto",
                "Categorías"
        };
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);
        model.setColumnIdentifiers(cols);

        for (FlightRouteDTO r : packRoutes) {
            String created = r.getCreatedAt() != null ? r.getCreatedAt().format(DTF) : "";
            String cats    = r.getCategoriesNames()   != null ? String.join(", ", r.getCategoriesNames())   : "";

            model.addRow(new Object[]{
                    safe(r.getName()),
                    safe(r.getDescription()),
                    created,
                    safe(r.getOriginAeroCode()),
                    safe(r.getDestinationAeroCode()),
                    r.getAirlineNickname(),
                    fmtMoney(r.getPriceTouristClass()),
                    fmtMoney(r.getPriceBusinessClass()),
                    fmtMoney(r.getPriceExtraUnitBaggage()),
                    cats
            });
        }

        flightRoutesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightRoutesTable.setModel(model);
        GUIUtils.adjustDynamicWidthAndHeightToTable(flightRoutesTable);
        flightRoutesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadFlightRoutePackageDetails() {
        try { infoPanel.setBorder(null); } catch (Exception e) {}
        flightRoutePackageNameLabel.setText("Nombre del paquete: " + safe(flightRoutePackageDTO.getName()));
        descriptionLabel.setText("Descripción ...");
        descriptionLabel.setToolTipText(safe(flightRoutePackageDTO.getDescription()));
        validityPeriodDaysLabel.setText( "Validez en días: " + flightRoutePackageDTO.getValidityPeriodDays());
        createdAtLabel.setText("Fecha creación: " + (flightRoutePackageDTO.getCreationDate() != null ? flightRoutePackageDTO.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
        seatTypeLabel.setText("Tipo de asiento: " + (flightRoutePackageDTO.getSeatType() != null ? flightRoutePackageDTO.getSeatType().toString() : "N/A"));
        priceLabel.setText("Precio: "
                + (flightRoutePackageDTO.getTotalPrice() != null ? String.format("$ %.2f", flightRoutePackageDTO.getTotalPrice())
                + " | Descuento: " + String.format("%.1f%%", flightRoutePackageDTO.getDiscount())  : "N/A"));
    }


    private String safe(String s) { return s == null ? "" : s; }
    private String fmtMoney(Double d) { return d == null ? "" : String.format("$ %.2f", d); }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        infoPanel = new JPanel();
        flightRoutePackageNameLabel = new JLabel();
        createdAtLabel = new JLabel();
        descriptionLabel = new JLabel();
        validityPeriodDaysLabel = new JLabel();
        priceLabel = new JLabel();
        seatTypeLabel = new JLabel();
        scrollPane2 = new JScrollPane();
        flightRoutesTable = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

        //======== infoPanel ========
        {
            infoPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder( 0
            , 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM
            , new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,
            infoPanel. getBorder( )) ); infoPanel. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e
            ) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            infoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)infoPanel.getLayout()).columnWidths = new int[] {300, 175, 0};
            ((GridBagLayout)infoPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
            ((GridBagLayout)infoPanel.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
            ((GridBagLayout)infoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

            //---- flightRoutePackageNameLabel ----
            flightRoutePackageNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            flightRoutePackageNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            flightRoutePackageNameLabel.setText("flightRoutePackageNameLabel");
            infoPanel.add(flightRoutePackageNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- createdAtLabel ----
            createdAtLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            createdAtLabel.setHorizontalAlignment(SwingConstants.LEFT);
            createdAtLabel.setText("createdAtLabel");
            infoPanel.add(createdAtLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- descriptionLabel ----
            descriptionLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
            descriptionLabel.setText("descriptionLabel");
            infoPanel.add(descriptionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- validityPeriodDaysLabel ----
            validityPeriodDaysLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            validityPeriodDaysLabel.setHorizontalAlignment(SwingConstants.LEFT);
            validityPeriodDaysLabel.setText("validityPeriodDaysLabel");
            infoPanel.add(validityPeriodDaysLabel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- priceLabel ----
            priceLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
            priceLabel.setText("priceLabel");
            infoPanel.add(priceLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- seatTypeLabel ----
            seatTypeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            seatTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            seatTypeLabel.setText("seatTypeLabel");
            infoPanel.add(seatTypeLabel, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        contentPane.add(infoPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.NONE,
            new Insets(0, 10, 0, 10), 0, 0));

        //======== scrollPane2 ========
        {
            scrollPane2.setPreferredSize(new Dimension(450, 250));
            scrollPane2.setMinimumSize(new Dimension(450, 250));
            scrollPane2.setMaximumSize(new Dimension(450, 250));

            //---- flightRoutesTable ----
            flightRoutesTable.setPreferredSize(new Dimension(450, 500));
            flightRoutesTable.setMinimumSize(new Dimension(450, 250));
            flightRoutesTable.setMaximumSize(new Dimension(500000, 500000));
            flightRoutesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane2.setViewportView(flightRoutesTable);
        }
        contentPane.add(scrollPane2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.VERTICAL,
            new Insets(0, 25, 25, 25), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JPanel infoPanel;
    private JLabel flightRoutePackageNameLabel;
    private JLabel createdAtLabel;
    private JLabel descriptionLabel;
    private JLabel validityPeriodDaysLabel;
    private JLabel priceLabel;
    private JLabel seatTypeLabel;
    private JScrollPane scrollPane2;
    private JTable flightRoutesTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
