/*
 * Created by JFormDesigner on Tue Aug 26 18:34:01 UYT 2025
 */

package gui.flightroute.getflightroutes;

import controllers.flight.IFlightController;
import controllers.flightroute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.enums.EnumEstatusRuta;
import gui.flightroute.details.FlightRouteDetailWindow;
import shared.utils.GUIUtils;
import shared.utils.NonEditableTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AparicioQuian
 */
public class GetFlightRoutesPanel extends JPanel {
    private IFlightRouteController flightRouteController;
    private IUserController userController;
    private IFlightController flightController;
    private List<FlightRouteDTO> flightRoutes = new ArrayList<>();
    private boolean areAirlinesLoading = false;

    private JPopupMenu flightRoutePopupMenu = new JPopupMenu();
    JMenuItem confirmItem = new JMenuItem("Confirmar");
    JMenuItem rejectItem = new JMenuItem("Rechazar");
    JMenuItem finalizeItem = new JMenuItem("Finalizar");

    // --- Datos auxiliares ---
    private List<BaseAirlineDTO> airlines = new ArrayList<>();
    private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public GetFlightRoutesPanel(IFlightRouteController flightRouteController,
                                IUserController userController,
                                IFlightController flightController) {
        if (flightController == null) throw new IllegalArgumentException("IFlightController es null");
        if (flightRouteController == null) throw new IllegalArgumentException("IFlightRouteController es null");
        if (userController == null) throw new IllegalArgumentException("IUserController es null");
        this.flightRouteController = flightRouteController;
        this.userController = userController;
        this.flightController = flightController;

        // Add menu items to popup menu
        flightRoutePopupMenu.add(confirmItem);
        flightRoutePopupMenu.add(rejectItem);
        flightRoutePopupMenu.add(finalizeItem);

        initComponents();
        initListeners();
        loadAirlinesIntoCombo();      // llena el combo al iniciar
        clearTable();                 // deja la tabla vacía hasta que elijas
        try {
            setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        } catch (Exception ignored) {
        }
    }

    private void initListeners() {
        airlineComboBox.addActionListener(e -> {
            if (areAirlinesLoading) return;
            try {
                String nickname = getSelectedAirlineNickname();
                if (nickname == null || nickname.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Selecciona una aerolínea.", "Atención",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                loadFlightRoutesTable(nickname);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error cargando las rutas de vuelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        flightRouteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Recarga las aerolíneas y la tabla
                loadAirlinesIntoCombo();
                String nickname = getSelectedAirlineNickname();
                if (nickname != null && !nickname.isEmpty()) {
                    loadFlightRoutesTable(nickname);
                } else {
                    clearTable();
                }
            }
        });
        FlightRouteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                int row = FlightRouteTable.rowAtPoint(e.getPoint());
                if (row >= 0 && row < FlightRouteTable.getRowCount()) {
                    FlightRouteTable.setRowSelectionInterval(row, row);
                    flightRoutePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && FlightRouteTable.getSelectedRow() != -1) {
                    int fila = FlightRouteTable.getSelectedRow();
                    FlightRouteDTO route = flightRoutes.get(fila);
                    new FlightRouteDetailWindow(route, flightController).setVisible(true);
                }
            }
        });

        // Add action listeners for menu items
        confirmItem.addActionListener(e -> {
            int row = FlightRouteTable.getSelectedRow();
            if (row >= 0) {
                FlightRouteDTO route = flightRoutes.get(row);
                flightRouteController.setFlightRouteStatusByName(
                        route.getName(),
                        EnumEstatusRuta.CONFIRMADA.name()
                );
                loadFlightRoutesTable(route.getAirlineNickname());
            }
        });

        rejectItem.addActionListener(e -> {
            int row = FlightRouteTable.getSelectedRow();
            if (row >= 0) {
                FlightRouteDTO route = flightRoutes.get(row);
                flightRouteController.setFlightRouteStatusByName(
                        route.getName(),
                        EnumEstatusRuta.RECHAZADA.name()
                );
                loadFlightRoutesTable(route.getAirlineNickname());
            }
        });

        finalizeItem.addActionListener(e -> {
            int row = FlightRouteTable.getSelectedRow();
            if (row >= 0) {
                FlightRouteDTO route = flightRoutes.get(row);
                flightRouteController.setFlightRouteStatusByName(
                        route.getName(),
                        EnumEstatusRuta.FINALIZADA.name()
                );
                loadFlightRoutesTable(route.getAirlineNickname());
            }
        });

    }

    private void loadAirlinesIntoCombo() {
        areAirlinesLoading = true;

        // Guardar la seleccion si ya existe
        String currentSelection = null;
        if (airlineComboBox.getSelectedItem() != null) {
            currentSelection = airlineComboBox.getSelectedItem().toString();
            System.out.println("Recargando aerolíneas, selección actual: " + currentSelection);
        }

        // Borrar la lista y la info del comboBox
        airlines.clear();
        airlineComboBox.removeAllItems();

        // Obtener las aerolineas
        List<BaseAirlineDTO> list = userController.getAllAirlinesSimpleDetails();
        if (list == null) return;

        airlines.addAll(list);

        // Iterar por las aerolineas de la lista
        Integer selectedIndex = null;
        for (BaseAirlineDTO a : airlines) {
            // Muestra “Nombre (nickname)”
            String display = safe(a.getName()) + " (" + safe(a.getNickname()) + ")";

            // Buscar si es la seleccion anterior
            if (currentSelection != null && currentSelection.equalsIgnoreCase(display)) {
                selectedIndex = airlineComboBox.getItemCount();
            }

            // Añadir al combo
            airlineComboBox.addItem(display);
        }

        areAirlinesLoading = false;

        // Asignarle un valor inicial
        if (!airlines.isEmpty()) {
            // Si ya tenía un valor inicial y lo encontramos nuevamente, asignarle ese
            // Si no, asignarle el primero
            airlineComboBox.setSelectedIndex(
                    selectedIndex != null ? selectedIndex : 0
            );
        }
    }

    private String getSelectedAirlineNickname() {
        int idx = airlineComboBox.getSelectedIndex();
        if (idx < 0 || idx >= airlines.size()) return null;
        return airlines.get(idx).getNickname(); // lo que necesita tu método
    }

    // ------------------ TABLA ------------------
    private void loadFlightRoutesTable(String airlineNickname) {
        List<FlightRouteDTO> routes =
                flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);

        flightRoutes = routes;

        String[] cols = {
                "Estado", "Nombre", "Descripción", "Creado",
                "Origen", "Destino", "Aerolínea",
                "$ Turista", "$ Business", "$ Extra Bulto",
                "Categorías"
        };
        NonEditableTableModel model = new NonEditableTableModel(cols, 0);
        model.setColumnIdentifiers(cols);

        for (FlightRouteDTO r : routes) {
            String created = r.getCreatedAt() != null ? r.getCreatedAt().format(DTF) : "";
            String cats = r.getCategoriesNames() != null ? String.join(", ", r.getCategoriesNames()) : "";

            // nombre bonito de aerolínea: buscamos en la lista ya cargada
            String airlineName = r.getAirlineNickname();
            for (BaseAirlineDTO a : airlines) {
                if (a.getNickname() != null && a.getNickname().equalsIgnoreCase(r.getAirlineNickname())) {
                    if (a.getName() != null) airlineName = a.getName();
                    break;
                }
            }
            //this.flightRoutes = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
            model.addRow(new Object[]{
                    r.getStatus().toString(),
                    safe(r.getName()),
                    safe(r.getDescription()),
                    created,
                    safe(r.getOriginAeroCode()),
                    safe(r.getDestinationAeroCode()),
                    airlineName,
                    fmtMoney(r.getPriceTouristClass()),
                    fmtMoney(r.getPriceBusinessClass()),
                    fmtMoney(r.getPriceExtraUnitBaggage()),
                    cats
            });
        }

        FlightRouteTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        FlightRouteTable.setModel(model);
        GUIUtils.adjustDynamicWidthAndHeightToTable(FlightRouteTable);
        FlightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void clearTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Nombre", "Descripción", "Creado",
                "Origen", "Destino", "Aerolínea",
                "$ Turista", "$ Business", "$ Extra Bulto",
                "Categorías"
        });
        FlightRouteTable.setModel(model);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String fmtMoney(Double d) {
        return d == null ? "" : String.format("$ %.2f", d);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nahuel
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        vSpacer14 = new JPanel(null);
        FlightRouteInfoPanel = new JPanel();
        flightRouteLabel = new JLabel();
        hSpacer7 = new JPanel(null);
        FlightRouteTablePanel = new JPanel();
        FlightRouteScrollPane = new JScrollPane();
        FlightRouteTable = new JTable();
        hSpacer8 = new JPanel(null);
        vSpacer20 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax
        .swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing
        .border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.
        Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.red
        ), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override
        public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName(
        )))throw new RuntimeException();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};

        //======== selectAirlinePanel ========
        {
            selectAirlinePanel.setOpaque(false);
            selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

            //---- airlineLabel ----
            airlineLabel.setText("Selecciona la Aerolinea:");
            selectAirlinePanel.add(airlineLabel);

            //---- airlineComboBox ----
            airlineComboBox.setMinimumSize(new Dimension(150, 30));
            airlineComboBox.setPreferredSize(new Dimension(150, 30));
            airlineComboBox.setOpaque(false);
            selectAirlinePanel.add(airlineComboBox);
        }
        add(selectAirlinePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
            new Insets(10, 0, 0, 0), 0, 0));

        //---- vSpacer14 ----
        vSpacer14.setPreferredSize(new Dimension(10, 40));
        vSpacer14.setMinimumSize(new Dimension(12, 40));
        vSpacer14.setOpaque(false);
        add(vSpacer14, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== FlightRouteInfoPanel ========
        {
            FlightRouteInfoPanel.setPreferredSize(new Dimension(640, 180));
            FlightRouteInfoPanel.setMinimumSize(new Dimension(640, 180));
            FlightRouteInfoPanel.setMaximumSize(new Dimension(640, 180));
            FlightRouteInfoPanel.setOpaque(false);
            FlightRouteInfoPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)FlightRouteInfoPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
            ((GridBagLayout)FlightRouteInfoPanel.getLayout()).rowHeights = new int[] {35, 0, 0};
            ((GridBagLayout)FlightRouteInfoPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
            ((GridBagLayout)FlightRouteInfoPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

            //---- flightRouteLabel ----
            flightRouteLabel.setText("Rutas de vuelo (\u21bb)");
            flightRouteLabel.setHorizontalAlignment(SwingConstants.CENTER);
            flightRouteLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            flightRouteLabel.setPreferredSize(new Dimension(120, 30));
            flightRouteLabel.setMaximumSize(new Dimension(120, 30));
            flightRouteLabel.setMinimumSize(new Dimension(120, 30));
            flightRouteLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 20));
            flightRouteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            FlightRouteInfoPanel.add(flightRouteLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer7 ----
            hSpacer7.setPreferredSize(new Dimension(40, 10));
            hSpacer7.setOpaque(false);
            FlightRouteInfoPanel.add(hSpacer7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

            //======== FlightRouteTablePanel ========
            {
                FlightRouteTablePanel.setPreferredSize(new Dimension(560, 150));
                FlightRouteTablePanel.setMinimumSize(new Dimension(560, 150));
                FlightRouteTablePanel.setMaximumSize(new Dimension(560, 150));
                FlightRouteTablePanel.setOpaque(false);
                FlightRouteTablePanel.setLayout(new GridBagLayout());
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).columnWidths = new int[] {612, 0};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)FlightRouteTablePanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //======== FlightRouteScrollPane ========
                {
                    FlightRouteScrollPane.setPreferredSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setMinimumSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setMaximumSize(new Dimension(560, 150));
                    FlightRouteScrollPane.setEnabled(false);
                    FlightRouteScrollPane.setOpaque(false);

                    //---- FlightRouteTable ----
                    FlightRouteTable.setPreferredSize(new Dimension(560, 150));
                    FlightRouteTable.setMaximumSize(new Dimension(560, 150));
                    FlightRouteTable.setMinimumSize(new Dimension(560, 150));
                    FlightRouteTable.setOpaque(false);
                    FlightRouteScrollPane.setViewportView(FlightRouteTable);
                }
                FlightRouteTablePanel.add(FlightRouteScrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            FlightRouteInfoPanel.add(FlightRouteTablePanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            hSpacer8.setOpaque(false);
            FlightRouteInfoPanel.add(hSpacer8, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(FlightRouteInfoPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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
    // Generated using JFormDesigner Evaluation license - Nahuel
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JPanel vSpacer14;
    private JPanel FlightRouteInfoPanel;
    private JLabel flightRouteLabel;
    private JPanel hSpacer7;
    private JPanel FlightRouteTablePanel;
    private JScrollPane FlightRouteScrollPane;
    private JTable FlightRouteTable;
    private JPanel hSpacer8;
    private JPanel vSpacer20;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
