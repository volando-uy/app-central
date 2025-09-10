/*
 * Created by JFormDesigner on Thu Sep 04 17:28:58 UYT 2025
 */

package gui.reservations.bookFlight;

import controllers.booking.IBookingController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.luggage.EnumCategoria;
import domain.models.luggage.EnumEquipajeBasico;
import gui.reservations.addPassenger.AddPassenger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author AparicioQuian
 */
public class BookFlightPanel extends JPanel {

    private IUserController userController;
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;
    private IBookingController bookingController;

    private List<FlightRouteDTO> currentRoutes = java.util.Collections.emptyList();
    private boolean areAirlinesLoading = false;

    // formatos de fecha admitidos en el textfield
    private static final DateTimeFormatter DT_WITH_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter DT_DATE_ONLY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BookFlightPanel(IUserController userController,
                           IFlightRouteController flightRouteController,
                           IFlightController flightController,
                           IBookingController bookingController) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.flightController = flightController;
        this.bookingController = bookingController;

        initComponents();
        initPassengersTable();
        initRoutesTable();
        setDefaultReservationDate();     // <-- fecha local por defecto
        loadAirlines();
        loadClients();
        setupListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadAirlines() {
        areAirlinesLoading = true;
        airlineComboBox.removeAllItems();
        try {
            List<BaseAirlineDTO> airlines = userController.getAllAirlinesSimpleDetails();
            if (airlines != null && !airlines.isEmpty()) {
                for (BaseAirlineDTO a : airlines) {
                    airlineComboBox.addItem(a.getNickname());
                }
            } else {
                updateFlightRouteTable(java.util.Collections.emptyList());
                loadSeatTypes(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar aerolíneas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            areAirlinesLoading = false;
        }
        if (airlineComboBox.getItemCount() > 0) {
            airlineComboBox.setSelectedIndex(0);
            loadRoutesForSelectedAirline();
        }
    }

    private void loadRoutesForSelectedAirline() {
        String airline = (String) airlineComboBox.getSelectedItem();
        if (airline == null || airline.isBlank()) {
            updateFlightRouteTable(java.util.Collections.emptyList());
            loadSeatTypes(null);
            return;
        }
        try {
            List<FlightRouteDTO> routes =
                    flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airline);
            currentRoutes = (routes != null) ? routes : java.util.Collections.emptyList();
            updateFlightRouteTable(currentRoutes);
            if (!currentRoutes.isEmpty()) {
                flightRouteTable.setRowSelectionInterval(0, 0);
                loadSeatTypes(currentRoutes.get(0));
            } else {
                loadSeatTypes(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar rutas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            currentRoutes = java.util.Collections.emptyList();
            updateFlightRouteTable(currentRoutes);
            loadSeatTypes(null);
        }
    }

    private void setupListeners() {
        airlineComboBox.addActionListener(e -> {
            if (areAirlinesLoading) return;
            loadRoutesForSelectedAirline();
        });

        numTicketsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });
        airlineLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadAirlines(); }
        });

        flightRouteLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadRoutesForSelectedAirline(); }
        });

        clientLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadClients(); }
        });

        addPassengerLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { openAddPassengerDialog(); }
        });

        flightRouteTable.getSelectionModel().addListSelectionListener((ListSelectionListener) e -> {
            if (e.getValueIsAdjusting()) return;
            loadSeatTypes(getSelectedRoute());
        });

        createFlightBtn.addActionListener(e -> onReserve());
    }

    private void openAddPassengerDialog() {
        Window owner = SwingUtilities.getWindowAncestor(this);
        final JDialog d = new JDialog(owner, "Agregar pasajero", Dialog.ModalityType.APPLICATION_MODAL);

        AddPassenger form = new AddPassenger();
        d.setContentPane(form);
        d.pack();
        d.setResizable(false);
        d.setLocationRelativeTo(owner);

        // Botón por defecto (Enter)
        d.getRootPane().setDefaultButton(form.getConfirmButton());

        // >>>> REEMPLAZAR el listener anterior por ESTE <<<<
        form.getConfirmButton().addActionListener(ev -> {
            Object tipo     = form.getTipoDoc();
            String doc      = form.getDocumento();
            String nombre   = form.getNombre();
            String apellido = form.getApellido();

            if (tipo == null || doc == null || doc.trim().isEmpty()
                    || nombre == null || nombre.trim().isEmpty()
                    || apellido == null || apellido.trim().isEmpty()) {
                JOptionPane.showMessageDialog(d,
                        "Completa Tipo Doc, Documento, Nombre y Apellido.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Regla 1: si es CI -> documento de 8 dígitos
            if (isCI(tipo) && !isEightDigits(doc)) {
                JOptionPane.showMessageDialog(d,
                        "Para Tipo Doc = CI, el documento debe tener exactamente 8 dígitos.",
                        "Documento inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Regla 2: no exceder la cantidad declarada en "Cantidad Pasajes"
            int desired = getDesiredTickets();
            if (desired <= 0) {
                JOptionPane.showMessageDialog(d,
                        "Ingresá primero la 'Cantidad Pasajes' (un número mayor a 0).",
                        "Falta cantidad", JOptionPane.WARNING_MESSAGE);
                return;
            }

            javax.swing.table.DefaultTableModel m =
                    (javax.swing.table.DefaultTableModel) passsengerTable.getModel();

            if (m.getRowCount() >= desired) {
                JOptionPane.showMessageDialog(d,
                        "Ya cargaste " + desired + " pasajero(s). No podés agregar más.",
                        "Límite alcanzado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            m.addRow(new Object[] {
                    String.valueOf(tipo),
                    doc.trim(),
                    nombre.trim(),
                    apellido.trim()
            });

            if (m.getRowCount() == desired) {
                JOptionPane.showMessageDialog(d,
                        "Se alcanzó la cantidad de pasajes (" + desired + ").",
                        "OK", JOptionPane.INFORMATION_MESSAGE);
            }

            d.dispose();
        });

        // Cerrar con ESC
        KeyStroke esc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        d.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(esc, "close");
        d.getRootPane().getActionMap().put("close", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { d.dispose(); }
        });

        d.setVisible(true);
    }


    private void updateFlightRouteTable(List<FlightRouteDTO> routes) {
        String[] cols = {"Nombre", "Descripción", "Origen", "Destino",
                "Precio Turista", "Precio Business", "Precio Extra", "Creada"};
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Object[][] data = new Object[routes.size()][cols.length];
        for (int i = 0; i < routes.size(); i++) {
            FlightRouteDTO fr = routes.get(i);
            data[i][0] = nz(fr.getName());
            data[i][1] = nz(fr.getDescription());
            data[i][2] = nz(fr.getOriginCityName());
            data[i][3] = nz(fr.getDestinationCityName());
            data[i][4] = money(fr.getPriceTouristClass());
            data[i][5] = money(fr.getPriceBusinessClass());
            data[i][6] = money(fr.getPriceExtraUnitBaggage());
            data[i][7] = (fr.getCreatedAt() != null) ? fr.getCreatedAt().format(df) : "";
        }

        flightRouteTable.setModel(new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        flightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadClients() {
        customerComboBox2.removeAllItems();
        try {
            List<BaseCustomerDTO> customers = userController.getAllCustomersSimpleDetails();
            if (customers == null || customers.isEmpty()) return;
            for (BaseCustomerDTO c : customers) {
                customerComboBox2.addItem(c.getName() + " " + c.getSurname() + " (" + c.getNickname() + ")");
            }
            if (customerComboBox2.getItemCount() > 0) customerComboBox2.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSeatTypes(FlightRouteDTO route) {
        seatTypeComboBox.removeAllItems();
        if (route == null) return;

        List<String> cats;
        try { cats = route.getCategories(); } catch (Throwable t) { cats = null; }

        if (cats != null && !cats.isEmpty()) {
            for (String c : cats) seatTypeComboBox.addItem(c);
        } else {
            if (route.getPriceTouristClass() != null) seatTypeComboBox.addItem("Económica");
            if (route.getPriceBusinessClass() != null) seatTypeComboBox.addItem("Business");
        }
        if (seatTypeComboBox.getItemCount() == 0) seatTypeComboBox.addItem("Económica");
        seatTypeComboBox.setSelectedIndex(0);
    }

    private void onReserve() {
        try {
            // cliente
            String customerNickname = parseNickname((String) customerComboBox2.getSelectedItem());
            if (customerNickname == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un cliente", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ruta
            FlightRouteDTO route = getSelectedRoute();
            if (route == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una ruta de vuelo", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // vuelo (simple: primero disponible)
            List<BaseFlightDTO> flights = flightController.getAllFlightsSimpleDetailsByRouteName(route.getName());
            if (flights == null || flights.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay vuelos disponibles para la ruta seleccionada", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String flightName = flights.get(0).getName();

            // === Pasajeros / Validaciones ===
            DefaultTableModel m = (DefaultTableModel) passsengerTable.getModel();
            int passengerCount = m.getRowCount();
            if (passengerCount <= 0) {
                JOptionPane.showMessageDialog(this, "Agrega al menos un pasajero", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int desired = getDesiredTickets();
            if (desired <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Ingresá una 'Cantidad Pasajes' válida (número > 0).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (passengerCount != desired) {
                JOptionPane.showMessageDialog(this,
                        "Ingresaste " + desired + " pasaje(s) pero hay " + passengerCount + " pasajero(s) cargado(s).\n" +
                                "La cantidad debe coincidir.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar cada fila: CI => documento 8 dígitos y nombre/apellido obligatorios
            for (int i = 0; i < passengerCount; i++) {
                Object tipo   = m.getValueAt(i, 0);
                String doc    = String.valueOf(m.getValueAt(i, 1));
                String nombre = String.valueOf(m.getValueAt(i, 2));
                String ape    = String.valueOf(m.getValueAt(i, 3));

                if (nombre == null || nombre.isBlank() || ape == null || ape.isBlank()) {
                    JOptionPane.showMessageDialog(this,
                            "Fila " + (i + 1) + ": Nombre y Apellido son obligatorios.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (isCI(tipo) && !isEightDigits(doc)) {
                    JOptionPane.showMessageDialog(this,
                            "Fila " + (i + 1) + ": para Tipo Doc = CI el documento debe tener exactamente 8 dígitos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Equipaje extra por pasajero (opcional, clamp >= 0)
            int extraUnits = 0;
            try { extraUnits = Integer.parseInt(extraLuggageTextField.getText().trim()); } catch (Exception ignored) {}
            if (extraUnits < 0) extraUnits = 0;

            // Armar ticketMap
            Map<BaseTicketDTO, java.util.List<LuggageDTO>> ticketMap = new LinkedHashMap<>();
            for (int i = 0; i < passengerCount; i++) {
                String doc    = String.valueOf(m.getValueAt(i, 1));
                String nombre = String.valueOf(m.getValueAt(i, 2));
                String ape    = String.valueOf(m.getValueAt(i, 3));

                BaseTicketDTO t = new BaseTicketDTO();
                t.setName(nombre);
                t.setSurname(ape);
                t.setNumDoc(doc);

                java.util.List<LuggageDTO> l = new LinkedList<>();
                BasicLuggageDTO basic = new BasicLuggageDTO();
                basic.setWeight(8.0);
                basic.setCategory(EnumEquipajeBasico.BOLSO);
                l.add(basic);

                for (int k = 0; k < extraUnits; k++) {
                    ExtraLuggageDTO ex = new ExtraLuggageDTO();
                    ex.setWeight(10.0);
                    ex.setCategory(EnumCategoria.MALETA);
                    l.add(ex);
                }
                ticketMap.put(t, l);
            }

            // Precio total
            String seatType = (seatTypeComboBox.getSelectedItem() != null)
                    ? seatTypeComboBox.getSelectedItem().toString()
                    : "Económica";

            double unitPrice = "Business".equalsIgnoreCase(seatType)
                    ? (route.getPriceBusinessClass() != null ? route.getPriceBusinessClass() : 0.0)
                    : (route.getPriceTouristClass()  != null ? route.getPriceTouristClass()  : 0.0);

            double extraPrice = (route.getPriceExtraUnitBaggage() != null ? route.getPriceExtraUnitBaggage() : 0.0);
            double total = (unitPrice * passengerCount) + (extraUnits * passengerCount * extraPrice);

            // === DTO de reserva ===
            BaseBookFlightDTO booking = new BaseBookFlightDTO();
            booking.setTotalPrice(total);

            // 👉 Seteamos createdAt usando el campo de fecha del formulario
            // (parseReservationDate() ya maneja ambos formatos y fallback a now())
            booking.setCreatedAt(parseReservationDate());

            // Crear
            BaseBookFlightDTO created = bookingController.createBooking(
                    booking, ticketMap, customerNickname, flightName
            );

            JOptionPane.showMessageDialog(this,
                    "Reserva registrada con éxito\nTotal: $" + String.format("%.2f", created.getTotalPrice()),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar pantalla
            resetForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar la reserva: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void initPassengersTable() {
        passsengerTable.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"Tipo Doc", "Documento", "Nombre", "Apellido"}) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
    }
    private boolean isCI(Object tipoDoc) {
        return tipoDoc != null && String.valueOf(tipoDoc).equalsIgnoreCase("CI");
    }
    private boolean isEightDigits(String s) {
        return s != null && s.matches("\\d{8}");
    }
    private int getDesiredTickets() {
        try {
            String txt = numTicketsTextField.getText();
            return (txt == null || txt.isBlank()) ? -1 : Integer.parseInt(txt.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private void initRoutesTable() {
        flightRouteTable.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"Nombre", "Descripción", "Origen", "Destino",
                "Precio Turista", "Precio Business", "Precio Extra", "Creada"}) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        flightRouteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setDefaultReservationDate() {
        reservationdateTextField.setText(LocalDateTime.now().format(DT_WITH_TIME));
    }

    private LocalDateTime parseReservationDate() {
        String txt = reservationdateTextField.getText();
        if (txt == null || txt.isBlank()) return LocalDateTime.now();
        try {
            return LocalDateTime.parse(txt.trim(), DT_WITH_TIME);
        } catch (DateTimeParseException e1) {
            try {
                // si viene solo fecha, la tomamos a las 00:00
                return LocalDateTime.from(DT_DATE_ONLY.parse(txt.trim())).withHour(0).withMinute(0).withSecond(0).withNano(0);
            } catch (Exception e2) {
                return LocalDateTime.now();
            }
        }
    }

    private void resetForm() {
        // pasajeros
        DefaultTableModel m = (DefaultTableModel) passsengerTable.getModel();
        for (int i = m.getRowCount() - 1; i >= 0; i--) m.removeRow(i);
        ((DefaultTableModel) passsengerTable.getModel()).setRowCount(0);
        numTicketsTextField.setText("");
        extraLuggageTextField.setText("");
        // campos
        numTicketsTextField.setText("");
        extraLuggageTextField.setText("");
        setDefaultReservationDate();

        // asiento
        seatTypeComboBox.removeAllItems();

        // rutas (recargar para refrescar precios/stock)
        loadRoutesForSelectedAirline();

        // cliente (mantengo seleccionado el actual)
        if (customerComboBox2.getItemCount() > 0 && customerComboBox2.getSelectedIndex() < 0) {
            customerComboBox2.setSelectedIndex(0);
        }
    }

    private FlightRouteDTO getSelectedRoute() {
        int row = flightRouteTable.getSelectedRow();
        if (row < 0 || currentRoutes == null || row >= currentRoutes.size()) return null;
        return currentRoutes.get(row);
    }

    private String parseNickname(String comboItem) {
        if (comboItem == null) return null;
        int lp = comboItem.lastIndexOf('(');
        int rp = comboItem.lastIndexOf(')');
        if (lp >= 0 && rp > lp) return comboItem.substring(lp + 1, rp);
        return null;
    }

    private String nz(String s) { return (s == null) ? "" : s; }
    private String money(Double d) { return (d == null) ? "" : String.format(java.util.Locale.US, "$ %.2f", d); }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
        InfoFlightPanel = new JPanel();
        titleLabel = new JLabel();
        vSpacer1 = new JPanel(null);
        hSpacer5 = new JPanel(null);
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        hSpacer6 = new JPanel(null);
        firstRowPanel2 = new JPanel();
        flightRouteLabel = new JLabel();
        flightRouteScrollPane = new JScrollPane();
        flightRouteTable = new JTable();
        vSpacer2 = new JPanel(null);
        hSpacer9 = new JPanel(null);
        selectClientPanel = new JPanel();
        clientLabel = new JLabel();
        customerComboBox2 = new JComboBox<>();
        hSpacer10 = new JPanel(null);
        hSpacer7 = new JPanel(null);
        firstRowPanel = new JPanel();
        seatLabel = new JLabel();
        seatTypeComboBox = new JComboBox();
        numTicketsLabel = new JLabel();
        numTicketsTextField = new JTextField();
        hSpacer8 = new JPanel(null);
        secondRowPanel = new JPanel();
        extraLuggageLabel = new JLabel();
        extraLuggageTextField = new JTextField();
        reservationdateTypeLabel = new JLabel();
        reservationdateTextField = new JTextField();
        firstRowPanel3 = new JPanel();
        addPassengerLabel = new JLabel();
        passengerScrollPane = new JScrollPane();
        passsengerTable = new JTable();
        createBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        createFlightBtn = new JButton();
        hSpacer2 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.
        border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER
        ,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font
        .BOLD,12),java.awt.Color.red), getBorder())); addPropertyChangeListener(
        new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order"
        .equals(e.getPropertyName()))throw new RuntimeException();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

        //======== InfoFlightPanel ========
        {
            InfoFlightPanel.setOpaque(false);
            InfoFlightPanel.setLayout(new GridBagLayout());
            ((GridBagLayout)InfoFlightPanel.getLayout()).columnWidths = new int[] {0, 0, 450, 0, 0, 0};
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowHeights = new int[] {0, 0, 43, 107, 0, 43, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- titleLabel ----
            titleLabel.setText("Crear Reserva");
            titleLabel.setFont(new Font("JetBrains Mono ExtraBold", Font.PLAIN, 20));
            InfoFlightPanel.add(titleLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(10, 0, 5, 0), 0, 0));
            InfoFlightPanel.add(vSpacer1, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer5 ----
            hSpacer5.setPreferredSize(new Dimension(40, 10));
            hSpacer5.setOpaque(false);
            InfoFlightPanel.add(hSpacer5, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== selectAirlinePanel ========
            {
                selectAirlinePanel.setOpaque(false);
                selectAirlinePanel.setLayout(new GridLayout(1, 3, 10, 0));

                //---- airlineLabel ----
                airlineLabel.setText("\ud83d\udd04 Selecciona la Aerolinea:");
                airlineLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                selectAirlinePanel.add(airlineLabel);

                //---- airlineComboBox ----
                airlineComboBox.setMinimumSize(new Dimension(100, 30));
                airlineComboBox.setPreferredSize(new Dimension(100, 30));
                airlineComboBox.setOpaque(false);
                selectAirlinePanel.add(airlineComboBox);
            }
            InfoFlightPanel.add(selectAirlinePanel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer6 ----
            hSpacer6.setPreferredSize(new Dimension(40, 10));
            hSpacer6.setOpaque(false);
            InfoFlightPanel.add(hSpacer6, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel2 ========
            {
                firstRowPanel2.setPreferredSize(new Dimension(510, 100));
                firstRowPanel2.setMinimumSize(new Dimension(510, 100));
                firstRowPanel2.setMaximumSize(new Dimension(510, 510));
                firstRowPanel2.setOpaque(false);
                firstRowPanel2.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel2.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)firstRowPanel2.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)firstRowPanel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- flightRouteLabel ----
                flightRouteLabel.setText("Rutas de vuelo:");
                flightRouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                flightRouteLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                flightRouteLabel.setPreferredSize(new Dimension(120, 30));
                flightRouteLabel.setMaximumSize(new Dimension(120, 30));
                flightRouteLabel.setMinimumSize(new Dimension(120, 30));
                firstRowPanel2.add(flightRouteLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== flightRouteScrollPane ========
                {
                    flightRouteScrollPane.setPreferredSize(new Dimension(300, 100));
                    flightRouteScrollPane.setMinimumSize(new Dimension(300, 100));
                    flightRouteScrollPane.setMaximumSize(new Dimension(300, 100));
                    flightRouteScrollPane.setEnabled(false);
                    flightRouteScrollPane.setOpaque(false);

                    //---- flightRouteTable ----
                    flightRouteTable.setOpaque(false);
                    flightRouteScrollPane.setViewportView(flightRouteTable);
                }
                firstRowPanel2.add(flightRouteScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowPanel2, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
            InfoFlightPanel.add(vSpacer2, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer9 ----
            hSpacer9.setPreferredSize(new Dimension(40, 10));
            hSpacer9.setOpaque(false);
            InfoFlightPanel.add(hSpacer9, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== selectClientPanel ========
            {
                selectClientPanel.setOpaque(false);
                selectClientPanel.setLayout(new GridLayout(1, 3, 10, 0));

                //---- clientLabel ----
                clientLabel.setText("\ud83d\udd04 Selecciona el Cliente:");
                clientLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                selectClientPanel.add(clientLabel);

                //---- customerComboBox2 ----
                customerComboBox2.setMinimumSize(new Dimension(100, 30));
                customerComboBox2.setPreferredSize(new Dimension(100, 30));
                customerComboBox2.setOpaque(false);
                selectClientPanel.add(customerComboBox2);
            }
            InfoFlightPanel.add(selectClientPanel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer10 ----
            hSpacer10.setPreferredSize(new Dimension(40, 10));
            hSpacer10.setOpaque(false);
            InfoFlightPanel.add(hSpacer10, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer7 ----
            hSpacer7.setPreferredSize(new Dimension(40, 10));
            hSpacer7.setOpaque(false);
            InfoFlightPanel.add(hSpacer7, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel ========
            {
                firstRowPanel.setPreferredSize(new Dimension(510, 30));
                firstRowPanel.setMinimumSize(new Dimension(510, 30));
                firstRowPanel.setOpaque(false);
                firstRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel.getLayout()).columnWidths = new int[] {130, 0, 130, 110, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- seatLabel ----
                seatLabel.setText("Tipo asiento:");
                seatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                seatLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                seatLabel.setPreferredSize(new Dimension(120, 30));
                seatLabel.setMaximumSize(new Dimension(70, 15));
                seatLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(seatLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- seatTypeComboBox ----
                seatTypeComboBox.setMinimumSize(new Dimension(100, 30));
                seatTypeComboBox.setPreferredSize(new Dimension(120, 30));
                firstRowPanel.add(seatTypeComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- numTicketsLabel ----
                numTicketsLabel.setText("Cantidad Pasajes:");
                numTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                numTicketsLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                numTicketsLabel.setPreferredSize(new Dimension(120, 30));
                numTicketsLabel.setMaximumSize(new Dimension(70, 15));
                numTicketsLabel.setMinimumSize(new Dimension(70, 15));
                firstRowPanel.add(numTicketsLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- numTicketsTextField ----
                numTicketsTextField.setPreferredSize(new Dimension(120, 30));
                numTicketsTextField.setMinimumSize(new Dimension(100, 30));
                numTicketsTextField.setMaximumSize(new Dimension(100, 30));
                numTicketsTextField.setOpaque(false);
                firstRowPanel.add(numTicketsTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowPanel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            hSpacer8.setOpaque(false);
            InfoFlightPanel.add(hSpacer8, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowPanel ========
            {
                secondRowPanel.setPreferredSize(new Dimension(510, 30));
                secondRowPanel.setMinimumSize(new Dimension(510, 30));
                secondRowPanel.setMaximumSize(new Dimension(510, 510));
                secondRowPanel.setOpaque(false);
                secondRowPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- extraLuggageLabel ----
                extraLuggageLabel.setText("Equipaje extra:");
                extraLuggageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                extraLuggageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                extraLuggageLabel.setPreferredSize(new Dimension(120, 30));
                extraLuggageLabel.setMaximumSize(new Dimension(70, 15));
                extraLuggageLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(extraLuggageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageTextField ----
                extraLuggageTextField.setPreferredSize(new Dimension(120, 30));
                extraLuggageTextField.setMinimumSize(new Dimension(100, 30));
                extraLuggageTextField.setMaximumSize(new Dimension(100, 30));
                extraLuggageTextField.setOpaque(false);
                secondRowPanel.add(extraLuggageTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTypeLabel ----
                reservationdateTypeLabel.setText("Fecha Reserva:");
                reservationdateTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                reservationdateTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                reservationdateTypeLabel.setPreferredSize(new Dimension(120, 30));
                reservationdateTypeLabel.setMaximumSize(new Dimension(70, 15));
                reservationdateTypeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowPanel.add(reservationdateTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTextField ----
                reservationdateTextField.setPreferredSize(new Dimension(120, 30));
                reservationdateTextField.setMinimumSize(new Dimension(100, 30));
                reservationdateTextField.setMaximumSize(new Dimension(100, 30));
                reservationdateTextField.setOpaque(false);
                secondRowPanel.add(reservationdateTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(secondRowPanel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== firstRowPanel3 ========
            {
                firstRowPanel3.setPreferredSize(new Dimension(510, 100));
                firstRowPanel3.setMinimumSize(new Dimension(510, 100));
                firstRowPanel3.setMaximumSize(new Dimension(510, 510));
                firstRowPanel3.setOpaque(false);
                firstRowPanel3.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowPanel3.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)firstRowPanel3.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)firstRowPanel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowPanel3.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- addPassengerLabel ----
                addPassengerLabel.setText("+ Pasajeros:");
                addPassengerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                addPassengerLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                addPassengerLabel.setPreferredSize(new Dimension(120, 30));
                addPassengerLabel.setMaximumSize(new Dimension(120, 30));
                addPassengerLabel.setMinimumSize(new Dimension(120, 30));
                addPassengerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                firstRowPanel3.add(addPassengerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== passengerScrollPane ========
                {
                    passengerScrollPane.setPreferredSize(new Dimension(300, 100));
                    passengerScrollPane.setMinimumSize(new Dimension(300, 100));
                    passengerScrollPane.setMaximumSize(new Dimension(300, 100));
                    passengerScrollPane.setEnabled(false);
                    passengerScrollPane.setOpaque(false);

                    //---- passsengerTable ----
                    passsengerTable.setOpaque(false);
                    passengerScrollPane.setViewportView(passsengerTable);
                }
                firstRowPanel3.add(passengerScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowPanel3, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== createBtnPanel ========
            {
                createBtnPanel.setOpaque(false);
                createBtnPanel.setLayout(new BorderLayout());

                //---- hSpacer1 ----
                hSpacer1.setPreferredSize(new Dimension(200, 10));
                hSpacer1.setOpaque(false);
                createBtnPanel.add(hSpacer1, BorderLayout.WEST);

                //---- createFlightBtn ----
                createFlightBtn.setText("+ Reservar");
                createFlightBtn.setOpaque(false);
                createBtnPanel.add(createFlightBtn, BorderLayout.CENTER);

                //---- hSpacer2 ----
                hSpacer2.setPreferredSize(new Dimension(200, 10));
                hSpacer2.setOpaque(false);
                createBtnPanel.add(hSpacer2, BorderLayout.EAST);
            }
            InfoFlightPanel.add(createBtnPanel, new GridBagConstraints(2, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));
        }
        add(InfoFlightPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
    private JPanel InfoFlightPanel;
    private JLabel titleLabel;
    private JPanel vSpacer1;
    private JPanel hSpacer5;
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JPanel hSpacer6;
    private JPanel firstRowPanel2;
    private JLabel flightRouteLabel;
    private JScrollPane flightRouteScrollPane;
    private JTable flightRouteTable;
    private JPanel vSpacer2;
    private JPanel hSpacer9;
    private JPanel selectClientPanel;
    private JLabel clientLabel;
    private JComboBox<String> customerComboBox2;
    private JPanel hSpacer10;
    private JPanel hSpacer7;
    private JPanel firstRowPanel;
    private JLabel seatLabel;
    private JComboBox seatTypeComboBox;
    private JLabel numTicketsLabel;
    private JTextField numTicketsTextField;
    private JPanel hSpacer8;
    private JPanel secondRowPanel;
    private JLabel extraLuggageLabel;
    private JTextField extraLuggageTextField;
    private JLabel reservationdateTypeLabel;
    private JTextField reservationdateTextField;
    private JPanel firstRowPanel3;
    private JLabel addPassengerLabel;
    private JScrollPane passengerScrollPane;
    private JTable passsengerTable;
    private JPanel createBtnPanel;
    private JPanel hSpacer1;
    private JButton createFlightBtn;
    private JPanel hSpacer2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
