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
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
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
import javax.swing.table.DefaultTableModel;

/**
 * @author AparicioQuian
 */
public class BookFlightPanel extends JPanel {

    private IUserController userController;
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;
    private IBookingController bookingController;

    private List<FlightDTO> currentFlights = new ArrayList<>();
    private List<FlightRouteDTO> currentFlightRoutes = new ArrayList<>();
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
        loadSeatTypes();
        initListeners();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    private void loadAirlines() {
        areAirlinesLoading = true;
        airlineComboBox.removeAllItems();
        try {
            // Obtenemos las aerolineas
            List<BaseAirlineDTO> airlines = userController.getAllAirlinesSimpleDetails();

            // Por cada aerolinea, la agregamos al combo
            for (BaseAirlineDTO a : airlines) {
                airlineComboBox.addItem(a.getNickname());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar aerolíneas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        areAirlinesLoading = false;
        // Si hay aerolíneas, seleccionamos la primera
        // Sus rutas se deberían cargar por el listener
        if (airlineComboBox.getItemCount() > 0) {
            airlineComboBox.setSelectedIndex(0);
        }

    }

    private void loadFlightRoutesForSelectedAirline() {
        // Obtenemos el nombre de la aerolinea
        String airline = (String) airlineComboBox.getSelectedItem();

        // Si la aerolinea es null, cargamos la tabla vacia
        if (airline == null || airline.isBlank()) {
            updateFlightsTable(java.util.Collections.emptyList());
            return;
        }
        try {

            // Obtenermos todos los vuelos de la aerolinea
            List<FlightRouteDTO> flightRoutesDTOs = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airline);
            currentFlightRoutes = flightRoutesDTOs;

            // Actualizamos la tabla
            updateFlightRoutesTable(flightRoutesDTOs);
            if (!currentFlightRoutes.isEmpty()) {
                flightRoutesTable.setRowSelectionInterval(0, 0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar rutas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            currentFlightRoutes = new ArrayList<>();
            updateFlightRoutesTable(currentFlightRoutes);
        }
    }

    private void loadFlightsTableByFlightRouteName(String flightRouteName) {
        try {
            // Obtenermos todos los vuelos de la aerolinea
            List<FlightDTO> flightsDTOs = flightController.getAllFlightsDetailsByRouteName(flightRouteName);
            currentFlights = flightsDTOs;

            // Actualizamos la tabla
            updateFlightsTable(flightsDTOs);
            if (!currentFlights.isEmpty()) {
                flightsTable.setRowSelectionInterval(0, 0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar vuelos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            currentFlights = new ArrayList<>();
            updateFlightsTable(currentFlights);
        }
    }

    private void initListeners() {
        airlineComboBox.addActionListener(e -> {
            if (areAirlinesLoading) return;
            loadFlightRoutesForSelectedAirline();
        });

        numTicketsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });

        flightRoutesTable.addMouseListener(new MouseAdapter() {
           // Cuando se selecciona una ruta, se cargan sus vuelos en FlightsTable
           @Override public void mouseClicked(MouseEvent e) {
               // Primero, obtenemos el nombre de la ruta seleccionada
               String flightRouteName = flightRoutesTable.getValueAt(flightRoutesTable.getSelectedRow(), 0).toString();
               loadFlightsTableByFlightRouteName(flightRouteName);
           }
        });

        airlineLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadAirlines(); }
        });

        clientLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { loadClients(); }
        });

        addPassengerLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { openAddPassengerDialog(); }
        });

        createReservationBtn.addActionListener(e -> onReserve());
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

    private void updateFlightRoutesTable(List<FlightRouteDTO> flightRouteDTOs) {
        String[] cols = {"Nombre", "Descripción", "Origen", "Destino",
                "Precio Turista", "Precio Business", "Precio Extra", "Creada"};
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Object[][] data = new Object[flightRouteDTOs.size()][cols.length];
        for (int i = 0; i < flightRouteDTOs.size(); i++) {
            FlightRouteDTO fr = flightRouteDTOs.get(i);
            data[i][0] = fr.getName();
            data[i][1] = fr.getDescription();
            data[i][2] = fr.getOriginCityName();
            data[i][3] = fr.getDestinationCityName();
            data[i][4] = money(fr.getPriceTouristClass());
            data[i][5] = money(fr.getPriceBusinessClass());
            data[i][6] = money(fr.getPriceExtraUnitBaggage());
            data[i][7] = (fr.getCreatedAt() != null) ? fr.getCreatedAt().format(df) : "";
        }

        flightRoutesTable.setModel(new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        flightRoutesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void updateFlightsTable(List<FlightDTO> flightsDTOs) {

        String[] cols = {"Nombre", "Nombre Ruta de Vuelo", "Fecha Partida", "Duracion",
                "Asientos turista", "Asientos ejecutivo", "Fecha Creación"};
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Object[][] data = new Object[flightsDTOs.size()][cols.length];
        for (int i = 0; i < flightsDTOs.size(); i++) {
            FlightDTO fr = flightsDTOs.get(i);
            data[i][0] = fr.getName();
            data[i][1] = fr.getFlightRouteName();
            data[i][2] = fr.getDepartureTime();
            data[i][3] = fr.getDuration();
            data[i][4] = fr.getMaxBusinessSeats();
            data[i][5] = fr.getMaxEconomySeats();
            data[i][6] = (fr.getCreatedAt() != null) ? fr.getCreatedAt().format(df) : "";
        }

        flightsTable.setModel(new DefaultTableModel(data, cols) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadClients() {
        customerComboBox.removeAllItems();
        try {
            List<BaseCustomerDTO> customers = userController.getAllCustomersSimpleDetails();
            if (customers == null || customers.isEmpty()) return;
            for (BaseCustomerDTO c : customers) {
                customerComboBox.addItem(c.getName() + " " + c.getSurname() + " (" + c.getNickname() + ")");
            }
            if (customerComboBox.getItemCount() > 0) customerComboBox.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSeatTypes() {
        seatTypeComboBox.addItem(EnumTipoAsiento.TURISTA);
        seatTypeComboBox.addItem(EnumTipoAsiento.EJECUTIVO);
        seatTypeComboBox.setSelectedIndex(0);
    }

    private void onReserve() {
        try {
            // cliente
            String customerNickname = parseNickname((String) customerComboBox.getSelectedItem());
            if (customerNickname == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un cliente", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ruta de vuelo
            FlightRouteDTO flightRouteDTO = getSelectedFlightRoute();
            if (flightRouteDTO == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una ruta de vuelo", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vuelo
            FlightDTO flightDTO = getSelectedFlight();
            if (flightDTO == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar un vuelo", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

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
            Map<BaseTicketDTO, List<LuggageDTO>> ticketMap = new LinkedHashMap<>();
            for (int i = 0; i < passengerCount; i++) {
                String doc    = String.valueOf(m.getValueAt(i, 1));
                String nombre = String.valueOf(m.getValueAt(i, 2));
                String ape    = String.valueOf(m.getValueAt(i, 3));

                BaseTicketDTO t = new BaseTicketDTO();
                t.setName(nombre);
                t.setSurname(ape);
                t.setNumDoc(doc);

                List<LuggageDTO> l = new LinkedList<>();
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

            // Agarrar el tipo de asiento
            EnumTipoAsiento seatType = (EnumTipoAsiento) seatTypeComboBox.getSelectedItem();

            double unitPrice = seatType.equals(EnumTipoAsiento.EJECUTIVO)
                    ? (flightRouteDTO.getPriceBusinessClass() != null ? flightRouteDTO.getPriceBusinessClass() : 0.0)
                    : (flightRouteDTO.getPriceTouristClass()  != null ? flightRouteDTO.getPriceTouristClass()  : 0.0);

            double extraPrice = (flightRouteDTO.getPriceExtraUnitBaggage() != null ? flightRouteDTO.getPriceExtraUnitBaggage() : 0.0);
            double total = (unitPrice * passengerCount) + (extraUnits * passengerCount * extraPrice);

            // === DTO de reserva ===
            BaseBookFlightDTO booking = new BaseBookFlightDTO();
            booking.setTotalPrice(total);
            booking.setSeatType(seatType);

            // 👉 Seteamos createdAt usando el campo de fecha del formulario
            // (parseReservationDate() ya maneja ambos formatos y fallback a now())
            booking.setCreatedAt(parseReservationDate());

            // Crear
            BaseBookFlightDTO created = bookingController.createBooking(
                    booking, ticketMap, customerNickname, flightDTO.getName()
            );

            JOptionPane.showMessageDialog(this,
                    "Reserva registrada con éxito\nTotal: $" + String.format("%.2f", created.getTotalPrice()),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar pantalla
            resetForm();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar la reserva: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            resetForm();
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
        flightsTable.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"Nombre", "Descripción", "Origen", "Destino",
                "Precio Turista", "Precio Business", "Precio Extra", "Creada"}) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        // cliente (mantengo seleccionado el actual)
        if (customerComboBox.getItemCount() > 0 && customerComboBox.getSelectedIndex() < 0) {
            customerComboBox.setSelectedIndex(0);
        }
    }

    private FlightDTO getSelectedFlight() {
        int row = flightsTable.getSelectedRow();
        if (row < 0 || currentFlights == null || row >= currentFlights.size()) return null;
        return currentFlights.get(row);
    }

    private FlightRouteDTO getSelectedFlightRoute() {
        int row = flightRoutesTable.getSelectedRow();
        if (row < 0 || currentFlightRoutes == null || row >= currentFlightRoutes.size()) return null;
        return currentFlightRoutes.get(row);
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
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        InfoFlightPanel = new JPanel();
        vSpacer1 = new JPanel(null);
        hSpacer5 = new JPanel(null);
        selectAirlinePanel = new JPanel();
        airlineLabel = new JLabel();
        airlineComboBox = new JComboBox<>();
        hSpacer6 = new JPanel(null);
        flightRoutesPanel = new JPanel();
        flightRouteLabel = new JLabel();
        flightRoutesTableScrollPane2 = new JScrollPane();
        flightRoutesTable = new JTable();
        flightsPanel = new JPanel();
        flightLabel = new JLabel();
        flightsTableScrollPane = new JScrollPane();
        flightsTable = new JTable();
        vSpacer2 = new JPanel(null);
        hSpacer9 = new JPanel(null);
        selectCustomerPanel = new JPanel();
        clientLabel = new JLabel();
        customerComboBox = new JComboBox<>();
        hSpacer10 = new JPanel(null);
        hSpacer7 = new JPanel(null);
        firstRowCustomerPanel = new JPanel();
        seatLabel = new JLabel();
        seatTypeComboBox = new JComboBox<>();
        numTicketsLabel = new JLabel();
        numTicketsTextField = new JTextField();
        hSpacer8 = new JPanel(null);
        secondRowCustomerPanel = new JPanel();
        extraLuggageLabel = new JLabel();
        extraLuggageTextField = new JTextField();
        reservationdateTypeLabel = new JLabel();
        reservationdateTextField = new JTextField();
        passengersPanel = new JPanel();
        addPassengerLabel = new JLabel();
        passengerScrollPane = new JScrollPane();
        passsengerTable = new JTable();
        createBtnPanel = new JPanel();
        hSpacer1 = new JPanel(null);
        createReservationBtn = new JButton();
        hSpacer2 = new JPanel(null);

        //======== this ========
        setPreferredSize(new Dimension(640, 540));
        setMinimumSize(new Dimension(640, 540));
        setMaximumSize(new Dimension(640, 540));
        setBackground(new Color(0x517ed6));
        setBorder(new EtchedBorder());
        setOpaque(false);
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing
        . border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder
        . CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .
        awt . Font. BOLD ,12 ) ,java . awt. Color .red ) , getBorder () ) )
        ;  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
        ) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } )
        ;
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
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowHeights = new int[] {0, 0, 43, 107, 0, 43, 0, 0, 0, 0, 0};
            ((GridBagLayout)InfoFlightPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)InfoFlightPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
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

            //======== flightRoutesPanel ========
            {
                flightRoutesPanel.setPreferredSize(new Dimension(510, 100));
                flightRoutesPanel.setMinimumSize(new Dimension(510, 100));
                flightRoutesPanel.setMaximumSize(new Dimension(510, 510));
                flightRoutesPanel.setOpaque(false);
                flightRoutesPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)flightRoutesPanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)flightRoutesPanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)flightRoutesPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)flightRoutesPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- flightRouteLabel ----
                flightRouteLabel.setText("Rutas de Vuelo:");
                flightRouteLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                flightRouteLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                flightRouteLabel.setPreferredSize(new Dimension(120, 30));
                flightRouteLabel.setMaximumSize(new Dimension(120, 30));
                flightRouteLabel.setMinimumSize(new Dimension(120, 30));
                flightRoutesPanel.add(flightRouteLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== flightRoutesTableScrollPane2 ========
                {
                    flightRoutesTableScrollPane2.setPreferredSize(new Dimension(300, 100));
                    flightRoutesTableScrollPane2.setMinimumSize(new Dimension(300, 100));
                    flightRoutesTableScrollPane2.setMaximumSize(new Dimension(300, 100));
                    flightRoutesTableScrollPane2.setEnabled(false);
                    flightRoutesTableScrollPane2.setOpaque(false);

                    //---- flightRoutesTable ----
                    flightRoutesTable.setOpaque(false);
                    flightRoutesTableScrollPane2.setViewportView(flightRoutesTable);
                }
                flightRoutesPanel.add(flightRoutesTableScrollPane2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(flightRoutesPanel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== flightsPanel ========
            {
                flightsPanel.setPreferredSize(new Dimension(510, 100));
                flightsPanel.setMinimumSize(new Dimension(510, 100));
                flightsPanel.setMaximumSize(new Dimension(510, 510));
                flightsPanel.setOpaque(false);
                flightsPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)flightsPanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)flightsPanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)flightsPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)flightsPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- flightLabel ----
                flightLabel.setText("Vuelos:");
                flightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                flightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                flightLabel.setPreferredSize(new Dimension(120, 30));
                flightLabel.setMaximumSize(new Dimension(120, 30));
                flightLabel.setMinimumSize(new Dimension(120, 30));
                flightsPanel.add(flightLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== flightsTableScrollPane ========
                {
                    flightsTableScrollPane.setPreferredSize(new Dimension(300, 100));
                    flightsTableScrollPane.setMinimumSize(new Dimension(300, 100));
                    flightsTableScrollPane.setMaximumSize(new Dimension(300, 100));
                    flightsTableScrollPane.setEnabled(false);
                    flightsTableScrollPane.setOpaque(false);

                    //---- flightsTable ----
                    flightsTable.setOpaque(false);
                    flightsTableScrollPane.setViewportView(flightsTable);
                }
                flightsPanel.add(flightsTableScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(flightsPanel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
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

            //======== selectCustomerPanel ========
            {
                selectCustomerPanel.setOpaque(false);
                selectCustomerPanel.setLayout(new GridLayout(1, 3, 10, 0));

                //---- clientLabel ----
                clientLabel.setText("\ud83d\udd04 Selecciona el Cliente:");
                clientLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                selectCustomerPanel.add(clientLabel);

                //---- customerComboBox ----
                customerComboBox.setMinimumSize(new Dimension(100, 30));
                customerComboBox.setPreferredSize(new Dimension(100, 30));
                customerComboBox.setOpaque(false);
                selectCustomerPanel.add(customerComboBox);
            }
            InfoFlightPanel.add(selectCustomerPanel, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
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

            //======== firstRowCustomerPanel ========
            {
                firstRowCustomerPanel.setPreferredSize(new Dimension(510, 30));
                firstRowCustomerPanel.setMinimumSize(new Dimension(510, 30));
                firstRowCustomerPanel.setOpaque(false);
                firstRowCustomerPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)firstRowCustomerPanel.getLayout()).columnWidths = new int[] {130, 0, 130, 110, 0};
                ((GridBagLayout)firstRowCustomerPanel.getLayout()).rowHeights = new int[] {10, 0};
                ((GridBagLayout)firstRowCustomerPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)firstRowCustomerPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- seatLabel ----
                seatLabel.setText("Tipo asiento:");
                seatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                seatLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                seatLabel.setPreferredSize(new Dimension(120, 30));
                seatLabel.setMaximumSize(new Dimension(70, 15));
                seatLabel.setMinimumSize(new Dimension(70, 15));
                firstRowCustomerPanel.add(seatLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- seatTypeComboBox ----
                seatTypeComboBox.setMinimumSize(new Dimension(100, 30));
                seatTypeComboBox.setPreferredSize(new Dimension(120, 30));
                firstRowCustomerPanel.add(seatTypeComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- numTicketsLabel ----
                numTicketsLabel.setText("Cantidad Pasajes:");
                numTicketsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                numTicketsLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                numTicketsLabel.setPreferredSize(new Dimension(120, 30));
                numTicketsLabel.setMaximumSize(new Dimension(70, 15));
                numTicketsLabel.setMinimumSize(new Dimension(70, 15));
                firstRowCustomerPanel.add(numTicketsLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- numTicketsTextField ----
                numTicketsTextField.setPreferredSize(new Dimension(120, 30));
                numTicketsTextField.setMinimumSize(new Dimension(100, 30));
                numTicketsTextField.setMaximumSize(new Dimension(100, 30));
                numTicketsTextField.setOpaque(false);
                firstRowCustomerPanel.add(numTicketsTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(firstRowCustomerPanel, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- hSpacer8 ----
            hSpacer8.setPreferredSize(new Dimension(40, 10));
            hSpacer8.setOpaque(false);
            InfoFlightPanel.add(hSpacer8, new GridBagConstraints(4, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== secondRowCustomerPanel ========
            {
                secondRowCustomerPanel.setPreferredSize(new Dimension(510, 30));
                secondRowCustomerPanel.setMinimumSize(new Dimension(510, 30));
                secondRowCustomerPanel.setMaximumSize(new Dimension(510, 510));
                secondRowCustomerPanel.setOpaque(false);
                secondRowCustomerPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).columnWidths = new int[] {130, 130, 130, 120, 0};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).rowHeights = new int[] {30, 0};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)secondRowCustomerPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- extraLuggageLabel ----
                extraLuggageLabel.setText("Equipaje extra:");
                extraLuggageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                extraLuggageLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                extraLuggageLabel.setPreferredSize(new Dimension(120, 30));
                extraLuggageLabel.setMaximumSize(new Dimension(70, 15));
                extraLuggageLabel.setMinimumSize(new Dimension(70, 15));
                secondRowCustomerPanel.add(extraLuggageLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- extraLuggageTextField ----
                extraLuggageTextField.setPreferredSize(new Dimension(120, 30));
                extraLuggageTextField.setMinimumSize(new Dimension(100, 30));
                extraLuggageTextField.setMaximumSize(new Dimension(100, 30));
                extraLuggageTextField.setOpaque(false);
                secondRowCustomerPanel.add(extraLuggageTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTypeLabel ----
                reservationdateTypeLabel.setText("Fecha Reserva:");
                reservationdateTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                reservationdateTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                reservationdateTypeLabel.setPreferredSize(new Dimension(120, 30));
                reservationdateTypeLabel.setMaximumSize(new Dimension(70, 15));
                reservationdateTypeLabel.setMinimumSize(new Dimension(70, 15));
                secondRowCustomerPanel.add(reservationdateTypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //---- reservationdateTextField ----
                reservationdateTextField.setPreferredSize(new Dimension(120, 30));
                reservationdateTextField.setMinimumSize(new Dimension(100, 30));
                reservationdateTextField.setMaximumSize(new Dimension(100, 30));
                reservationdateTextField.setOpaque(false);
                secondRowCustomerPanel.add(reservationdateTextField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(secondRowCustomerPanel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== passengersPanel ========
            {
                passengersPanel.setPreferredSize(new Dimension(510, 80));
                passengersPanel.setMinimumSize(new Dimension(510, 80));
                passengersPanel.setMaximumSize(new Dimension(510, 510));
                passengersPanel.setOpaque(false);
                passengersPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)passengersPanel.getLayout()).columnWidths = new int[] {130, 376, 0};
                ((GridBagLayout)passengersPanel.getLayout()).rowHeights = new int[] {67, 0};
                ((GridBagLayout)passengersPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)passengersPanel.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- addPassengerLabel ----
                addPassengerLabel.setText("+ Pasajeros:");
                addPassengerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                addPassengerLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                addPassengerLabel.setPreferredSize(new Dimension(120, 30));
                addPassengerLabel.setMaximumSize(new Dimension(120, 30));
                addPassengerLabel.setMinimumSize(new Dimension(120, 30));
                addPassengerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                passengersPanel.add(addPassengerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 10), 0, 0));

                //======== passengerScrollPane ========
                {
                    passengerScrollPane.setPreferredSize(new Dimension(300, 80));
                    passengerScrollPane.setMinimumSize(new Dimension(300, 80));
                    passengerScrollPane.setMaximumSize(new Dimension(300, 80));
                    passengerScrollPane.setEnabled(false);
                    passengerScrollPane.setOpaque(false);

                    //---- passsengerTable ----
                    passsengerTable.setOpaque(false);
                    passsengerTable.setMinimumSize(new Dimension(30, 80));
                    passsengerTable.setPreferredSize(new Dimension(150, 80));
                    passengerScrollPane.setViewportView(passsengerTable);
                }
                passengersPanel.add(passengerScrollPane, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            InfoFlightPanel.add(passengersPanel, new GridBagConstraints(2, 8, 1, 1, 0.0, 0.0,
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

                //---- createReservationBtn ----
                createReservationBtn.setText("+ Reservar");
                createReservationBtn.setOpaque(false);
                createBtnPanel.add(createReservationBtn, BorderLayout.CENTER);

                //---- hSpacer2 ----
                hSpacer2.setPreferredSize(new Dimension(200, 10));
                hSpacer2.setOpaque(false);
                createBtnPanel.add(hSpacer2, BorderLayout.EAST);
            }
            InfoFlightPanel.add(createBtnPanel, new GridBagConstraints(2, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        }
        add(InfoFlightPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JPanel InfoFlightPanel;
    private JPanel vSpacer1;
    private JPanel hSpacer5;
    private JPanel selectAirlinePanel;
    private JLabel airlineLabel;
    private JComboBox<String> airlineComboBox;
    private JPanel hSpacer6;
    private JPanel flightRoutesPanel;
    private JLabel flightRouteLabel;
    private JScrollPane flightRoutesTableScrollPane2;
    private JTable flightRoutesTable;
    private JPanel flightsPanel;
    private JLabel flightLabel;
    private JScrollPane flightsTableScrollPane;
    private JTable flightsTable;
    private JPanel vSpacer2;
    private JPanel hSpacer9;
    private JPanel selectCustomerPanel;
    private JLabel clientLabel;
    private JComboBox<String> customerComboBox;
    private JPanel hSpacer10;
    private JPanel hSpacer7;
    private JPanel firstRowCustomerPanel;
    private JLabel seatLabel;
    private JComboBox<EnumTipoAsiento> seatTypeComboBox;
    private JLabel numTicketsLabel;
    private JTextField numTicketsTextField;
    private JPanel hSpacer8;
    private JPanel secondRowCustomerPanel;
    private JLabel extraLuggageLabel;
    private JTextField extraLuggageTextField;
    private JLabel reservationdateTypeLabel;
    private JTextField reservationdateTextField;
    private JPanel passengersPanel;
    private JLabel addPassengerLabel;
    private JScrollPane passengerScrollPane;
    private JTable passsengerTable;
    private JPanel createBtnPanel;
    private JPanel hSpacer1;
    private JButton createReservationBtn;
    private JPanel hSpacer2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
