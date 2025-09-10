/*
 * Created by JFormDesigner on Sun Sep 07 16:17:39 UYT 2025
 */

package gui.reservations.getUserReservation;

import java.awt.*;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import controllers.booking.IBookingController;
import controllers.buyPackage.IBuyPackageController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.bookflight.BookFlight;
import domain.models.ticket.Ticket;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AparicioQuian
 */
public class GetUserReservationPanel extends JPanel {
    private final IUserController userController;
    private final IFlightRouteController flightRouteController;
    private final IFlightController flightController;
    private final IFlightRoutePackageController flightRoutePackageController;
    private final IBookingController bookingController;
    private final IBuyPackageController buyPackageController;

    private static final String CARD_EMPTY   = "EMPTY";
    private static final String CARD_AIRLINE = "AIRLINE";
    private static final String CARD_CLIENT  = "CLIENT";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DefaultTableModel modelUsuarios;
    private DefaultTableModel modelRutas;
    private DefaultTableModel modelReservas;
    private DefaultTableModel modelPaquetes;

    private String usuarioSeleccionadoTipo = null;
    private String usuarioSeleccionadoNick = null;
    private final java.util.Map<String, String[]> routeEndsCache = new java.util.HashMap<>();

    // detalle reserva
    private JPanel detalleReservaPanel;
    private JLabel lblDetTitulo, lblDetVuelo, lblDetRuta, lblDetFechaHora, lblDetTotal;
    private JTable tblSeats;
    private DefaultTableModel modelSeats;

    // detalle ruta
    private JPanel panelDetalleRuta, airlineBody;
    private JLabel rlTitulo, rlRuta, rlOrigen, rlDestino, rlEstado, rlPrecioExtra;
    private JTable tblDetalleRuta;
    private DefaultTableModel modelDetalleRuta;

    public GetUserReservationPanel(
            IUserController userController,
            IFlightRouteController flightRouteController,
            IFlightController flightController,
            IFlightRoutePackageController flightRoutePackageController,
            IBookingController bookingController,
            IBuyPackageController buyPackageController
    ) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.flightController = flightController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.bookingController = bookingController;
        this.buyPackageController = buyPackageController;

        initComponents();

        setLayout(new BorderLayout());
        add(splitPane1, BorderLayout.CENTER);

        postInitWireUp();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    // ---------- wiring ----------
    private void postInitWireUp() {
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"Todos", "Usuario", "Aerolínea"}));
        cmbTipo.setSelectedIndex(0);

        modelUsuarios = new DefaultTableModel(new Object[]{"Nombre", "Documento", "Email", "Tipo", "Nickname"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsuarios.setModel(modelUsuarios);

        modelRutas = new DefaultTableModel(new Object[]{"Ruta", "Origen", "Destino", "Estado" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblRutas.setModel(modelRutas);
        tblRutas.setAutoCreateRowSorter(true);
        tblRutas.getTableHeader().setReorderingAllowed(false);
        scrollPane3.setViewportView(tblRutas);
        scrollPane3.setColumnHeaderView(tblRutas.getTableHeader());

        // contenedor del cuerpo de Aerolínea (tabla + detalle debajo)
        airlineBody = new JPanel(new BorderLayout());
        airline.remove(scrollPane3);
        airlineBody.add(scrollPane3, BorderLayout.CENTER);
        airline.add(airlineBody, BorderLayout.CENTER);

        modelReservas = new DefaultTableModel(new Object[]{"ID", "Fecha", "Total", "Tickets", "Vuelos" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblReservas.setModel(modelReservas);

        modelPaquetes = new DefaultTableModel(new Object[]{"Id", "Nombre paquete", "Fecha compra", "Precio total" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPaquetes.setModel(modelPaquetes);

        cmbTipo.addActionListener(e -> reloadUsuarios());
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) onUsuarioSeleccionado(); });
        btnDetalleRuta.addActionListener(e -> abrirDetalleRuta());
        btnDetalleReserva.addActionListener(e -> abrirDetalleReservaEnPanel());
        btnDetallePaquete.addActionListener(e -> abrirDetallePaquete());

        buildDetalleReservaPanel();
        panelReservas.add(detalleReservaPanel, BorderLayout.SOUTH);

        reloadUsuarios();
        showCard(CARD_EMPTY);

        tblReservas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && detalleReservaPanel.isVisible()) detalleReservaPanel.setVisible(false);
        });
    }

    // ---------- usuarios ----------
    private void reloadUsuarios() {
        String filtro = Objects.toString(cmbTipo.getSelectedItem(), "Todos");
        modelUsuarios.setRowCount(0);

        try {
            if ("Todos".equalsIgnoreCase(filtro) || "Usuario".equalsIgnoreCase(filtro)) {
                List<BaseCustomerDTO> customers = userController.getAllCustomersSimpleDetails();
                for (BaseCustomerDTO c : customers) {
                    String nick = nz(c.getNickname());
                    if (nick.isBlank()) continue;
                    modelUsuarios.addRow(new Object[]{
                            nz(c.getName()) + " " + nz(c.getSurname()),
                            (enumToString(c.getDocType()) + " " + nz(c.getNumDoc())).trim(),
                            nz(c.getMail()),
                            "Usuario",
                            nick
                    });
                }
            }
            if ("Todos".equalsIgnoreCase(filtro) || "Aerolínea".equalsIgnoreCase(filtro)) {
                List<BaseAirlineDTO> airlines = userController.getAllAirlinesSimpleDetails();
                for (BaseAirlineDTO a : airlines) {
                    String nick = nz(a.getNickname());
                    if (nick.isBlank()) continue;
                    modelUsuarios.addRow(new Object[]{ nz(a.getName()), "", nz(a.getMail()), "Aerolínea", nick });
                }
            }
            adjustDynamicWidthAndHeightToTable(tblUsuarios, modelUsuarios);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        usuarioSeleccionadoNick = usuarioSeleccionadoTipo = null;
        headerDatosReset();
        modelRutas.setRowCount(0);
        modelReservas.setRowCount(0);
        modelPaquetes.setRowCount(0);
        showCard(CARD_EMPTY);
    }

    private void onUsuarioSeleccionado() {
        int v = tblUsuarios.getSelectedRow();
        if (v < 0) return;

        int r = tblUsuarios.convertRowIndexToModel(v);
        usuarioSeleccionadoTipo = Objects.toString(modelUsuarios.getValueAt(r, 3), "");
        usuarioSeleccionadoNick = Objects.toString(modelUsuarios.getValueAt(r, 4), "").trim();

        if (usuarioSeleccionadoNick.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este registro no tiene nickname.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modelRutas.setRowCount(0);
        modelReservas.setRowCount(0);
        modelPaquetes.setRowCount(0);
        if (detalleReservaPanel != null) detalleReservaPanel.setVisible(false);
        if (panelDetalleRuta != null) panelDetalleRuta.setVisible(false);

        try {
            if (usuarioSeleccionadoTipo.equalsIgnoreCase("Aerolínea")) {
                BaseAirlineDTO a = userController.getAirlineSimpleDetailsByNickname(usuarioSeleccionadoNick);
                headerDatosSet(nz(a.getName()), "Aerolínea", nz(a.getMail()), "");
                loadAirlineRoutes(usuarioSeleccionadoNick);
                showCard(CARD_AIRLINE);
            } else {
                String nombre   = Objects.toString(modelUsuarios.getValueAt(r, 0), "");
                String docTxt   = Objects.toString(modelUsuarios.getValueAt(r, 1), "");
                String emailTxt = Objects.toString(modelUsuarios.getValueAt(r, 2), "");
                headerDatosSet(nombre, "Cliente", emailTxt, docTxt);

                loadCustomerReservations(usuarioSeleccionadoNick);
                loadCustomerPackages(usuarioSeleccionadoNick);
                showCard(CARD_CLIENT);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar detalle: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String enumToString(Enum<?> e) { return e == null ? "" : e.name(); }

    // ---------- aerolínea ----------
    private void loadAirlineRoutes(String airlineNickname) {
        modelRutas.setRowCount(0);
        try {
            List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
            for (FlightRouteDTO r : rutas) {
                String origin = callGetterString(r, "getOriginCityName", "getOrigin");
                String dest   = callGetterString(r, "getDestinationCityName", "getDestination");
                String status = callGetterString(r, "getStatus");
                modelRutas.addRow(new Object[]{ nz(r.getName()), origin, dest, status });
            }
            adjustDynamicWidthAndHeightToTable(tblRutas, modelRutas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar rutas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDetalleRuta() {
        int v = tblRutas.getSelectedRow();
        if (v < 0) { JOptionPane.showMessageDialog(this, "Seleccioná una ruta"); return; }
        int r = tblRutas.convertRowIndexToModel(v);
        String routeName = Objects.toString(modelRutas.getValueAt(r, 0), "").trim();
        if (routeName.isEmpty()) { JOptionPane.showMessageDialog(this, "Ruta inválida"); return; }

        FlightRouteDTO dto;
        try {
            dto = flightRouteController.getFlightRouteDetailsByName(routeName);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la ruta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ensureDetalleRutaUI();

        rlTitulo.setText("Detalle de ruta: " + routeName);
        String origen  = callGetterString(dto, "getOriginCityName","getOrigin");
        String destino = callGetterString(dto, "getDestinationCityName","getDestination");
        String estado  = callGetterString(dto, "getStatus");
        String extra   = callGetterNumber(dto, "getPriceExtraUnitBaggage");
        rlRuta.setText("Ruta: " + routeName);
        rlOrigen.setText("Origen: " + (origen.isBlank()? "-" : origen));
        rlDestino.setText("Destino: " + (destino.isBlank()? "-" : destino));
        rlEstado.setText("Estado: " + (estado.isBlank()? "-" : estado));
        rlPrecioExtra.setText("Equipaje extra (unidad): " + (extra.isBlank()? "-" : extra));

        modelDetalleRuta.setRowCount(0);
        try {
            var flights = flightController.getAllFlightsSimpleDetailsByRouteName(routeName);
            if (flights != null) {
                for (var f : flights) {
                    String fname = callGetterString(f, "getName");
                    String fecha = callGetterDate(f,  "getDepartureDate","getDate");
                    String hora  = callGetterString(f, "getDepartureTime","getTime");
                    String est   = callGetterString(f, "getStatus","getState");
                    if (hora != null && hora.contains(":")) {
                        String[] tp = hora.split(":",3);
                        if (tp.length>=2) hora = tp[0]+":"+tp[1];
                    }
                    if (fecha != null && fecha.contains("T")) fecha = fecha.split("T",2)[0];
                    modelDetalleRuta.addRow(new Object[]{ fname==null? "-" : fname,
                            (fecha==null || fecha.isBlank()? "-" : fecha),
                            (hora==null  || hora.isBlank()? "-" : hora),
                            (est==null   || est.isBlank()? "-" : est)});
                }
            }
        } catch (Exception ignored) { }

        panelDetalleRuta.setVisible(true);
        airlineBody.revalidate();
        airlineBody.repaint();
    }

    private void ensureDetalleRutaUI() {
        if (panelDetalleRuta != null) return;

        panelDetalleRuta = new JPanel(new BorderLayout(6,6));
        panelDetalleRuta.setBackground(new Color(0xF7F9FC));
        panelDetalleRuta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1,0,0,0, Color.LIGHT_GRAY),
                new EmptyBorder(8,8,8,8)
        ));
        panelDetalleRuta.setPreferredSize(new Dimension(10, 220));
        panelDetalleRuta.setVisible(false);

        // Top bar
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        rlTitulo = new JLabel("Detalle de ruta");
        rlTitulo.setFont(rlTitulo.getFont().deriveFont(Font.BOLD, 13f));
        top.add(rlTitulo, BorderLayout.WEST);

        JButton btnOcultar = new JButton("Ocultar detalle");
        btnOcultar.addActionListener(e -> {
            panelDetalleRuta.setVisible(false);
            airlineBody.revalidate();
            airlineBody.repaint();
        });
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(btnOcultar);
        top.add(right, BorderLayout.EAST);
        panelDetalleRuta.add(top, BorderLayout.NORTH);

        // Info grid
        JPanel grid = new JPanel(new GridLayout(2,3,8,4));
        grid.setOpaque(false);
        rlRuta        = new JLabel("Ruta: -");
        rlOrigen      = new JLabel("Origen: -");
        rlDestino     = new JLabel("Destino: -");
        rlEstado      = new JLabel("Estado: -");
        rlPrecioExtra = new JLabel("Equipaje extra (unidad): -");
        grid.add(rlRuta); grid.add(rlOrigen); grid.add(rlDestino);
        grid.add(rlEstado); grid.add(rlPrecioExtra); grid.add(new JLabel(""));
        panelDetalleRuta.add(grid, BorderLayout.CENTER);

        // Tabla inferior
        modelDetalleRuta = new DefaultTableModel(new Object[]{"Vuelo","Fecha","Hora","Estado"}, 0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        tblDetalleRuta = new JTable(modelDetalleRuta);
        tblDetalleRuta.setRowHeight(22);
        tblDetalleRuta.setFillsViewportHeight(true);
        tblDetalleRuta.getTableHeader().setReorderingAllowed(false);
        JScrollPane sc = new JScrollPane(tblDetalleRuta);
        panelDetalleRuta.add(sc, BorderLayout.SOUTH);

        // Insertar al final del contenedor
        airlineBody.add(panelDetalleRuta, BorderLayout.SOUTH);
    }

    // ---------- cliente: reservas ----------
    private void loadCustomerReservations(String customerNickname) {
        List<BookFlightDTO> reservas = bookingController.findDTOsByCustomerNickname(customerNickname);

        DefaultTableModel model = (DefaultTableModel) tblReservas.getModel();
        model.setColumnIdentifiers(new Object[] { "ID", "Fecha", "Total", "Tickets", "Vuelos" });
        model.setRowCount(0);

        for (BookFlightDTO reserva : reservas) {
            BookFlight full = null;
            try { full = bookingController.getFullBookingById(reserva.getId()); } catch (Exception ignored) {}

            int ticketsCount = (full != null && full.getTickets() != null) ? full.getTickets().size() : 0;
            String flights = "";
            if (full != null && full.getTickets() != null) {
                flights = full.getTickets().stream()
                        .map(t -> (t.getSeat() != null && t.getSeat().getFlight() != null)
                                ? nz(t.getSeat().getFlight().getName()) : "N/A")
                        .distinct()
                        .reduce((a,b) -> a.isBlank()? b : (b.isBlank()? a : a + ", " + b)).orElse("");
            }

            model.addRow(new Object[]{
                    reserva.getId(),
                    reserva.getCreatedAt(),
                    reserva.getTotalPrice(),
                    ticketsCount,
                    flights
            });
        }
        adjustDynamicWidthAndHeightToTable(tblReservas, modelReservas);
    }

    private void buildDetalleReservaPanel() {
        detalleReservaPanel = new JPanel(new BorderLayout());
        detalleReservaPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        detalleReservaPanel.setBackground(new Color(0xf7f9fc));

        JPanel header = new JPanel(new GridBagLayout());
        header.setBorder(new EmptyBorder(8, 8, 8, 8));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 1; gc.fill = GridBagConstraints.HORIZONTAL;

        lblDetTitulo = new JLabel("Detalle de la reserva");
        lblDetTitulo.setFont(lblDetTitulo.getFont().deriveFont(Font.BOLD, 13f));
        header.add(lblDetTitulo, gc);

        JPanel grid = new JPanel(new GridLayout(2, 2, 8, 4));
        lblDetVuelo = new JLabel("Vuelo: -");
        lblDetRuta  = new JLabel("Ruta: -");
        lblDetFechaHora = new JLabel("Fecha/Hora: -");
        lblDetTotal = new JLabel("Total: -");
        grid.add(lblDetVuelo);
        grid.add(lblDetRuta);
        grid.add(lblDetFechaHora);
        grid.add(lblDetTotal);

        gc.gridy = 1; header.add(grid, gc);

        JButton btnCerrarDetalle = new JButton("Ocultar detalle");
        btnCerrarDetalle.addActionListener(e -> detalleReservaPanel.setVisible(false));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.add(btnCerrarDetalle);

        JPanel headerWrap = new JPanel(new BorderLayout());
        headerWrap.add(header, BorderLayout.CENTER);
        headerWrap.add(right, BorderLayout.EAST);

        modelSeats = new DefaultTableModel(new Object[]{
                "TicketId", "Asiento", "Tipo", "Vuelo", "Ruta", "Origen", "Destino"
        }, 0) { public boolean isCellEditable(int r, int c) { return false; } };
        tblSeats = new JTable(modelSeats);
        tblSeats.setRowHeight(24);
        tblSeats.setShowVerticalLines(false);
        tblSeats.setIntercellSpacing(new Dimension(0, 0));
        JScrollPane spSeats = new JScrollPane(tblSeats);

        detalleReservaPanel.add(headerWrap, BorderLayout.NORTH);
        detalleReservaPanel.add(spSeats, BorderLayout.CENTER);

        detalleReservaPanel.setPreferredSize(new Dimension(10, 220));
        detalleReservaPanel.setVisible(false);
    }

    private void abrirDetalleReservaEnPanel() {
        int v = tblReservas.getSelectedRow();
        if (v < 0) { JOptionPane.showMessageDialog(this, "Seleccioná una reserva"); return; }
        int r = tblReservas.convertRowIndexToModel(v);

        Long bookingId = null;
        try {
            int idCol = findTableColumnIndex(tblReservas, "ID", "Id", "N° Reserva", "Reserva");
            if (idCol < 0) idCol = 0;
            Object idVal = ((DefaultTableModel) tblReservas.getModel()).getValueAt(r, idCol);
            if (idVal instanceof Number n) bookingId = n.longValue();
            else if (idVal != null && !String.valueOf(idVal).isBlank()) bookingId = Long.valueOf(String.valueOf(idVal));
        } catch (Exception ignored) {}

        if (bookingId == null) {
            JOptionPane.showMessageDialog(this, "ID de reserva inválido.");
            return;
        }

        BookFlight booking;
        try { booking = bookingController.getFullBookingById(bookingId); }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la reserva: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (booking == null) { JOptionPane.showMessageDialog(this, "No se encontró la reserva."); return; }

        fillDetalleReserva(booking);
        detalleReservaPanel.setVisible(true);
        adjustDynamicWidthAndHeightToTable(tblSeats, modelSeats);
    }


    private void fillDetalleReserva(BookFlight booking) {
        String totalTxt = String.valueOf(booking.getTotalPrice());
        String fechaTxt = booking.getCreated_at() != null ? String.valueOf(booking.getCreated_at()) : "-";

        String vueloName = "-";
        String rutaName  = "-";
        String origen    = "";
        String destino   = "";

        try {
            if (booking.getTickets() != null && !booking.getTickets().isEmpty()) {
                Ticket t0 = booking.getTickets().get(0);
                if (t0 != null && t0.getSeat() != null && t0.getSeat().getFlight() != null) {
                    var f = t0.getSeat().getFlight();
                    if (f.getName() != null) vueloName = f.getName();
                    if (f.getFlightRoute() != null) {
                        var fr = f.getFlightRoute();
                        if (fr.getName() != null) rutaName = fr.getName();

                        String orgTry = callGetterString(fr, "getOriginCityName","getOriginAirportName","getOriginName","getOriginIata","getOriginCode");
                        String dstTry = callGetterString(fr, "getDestinationCityName","getDestinationAirportName","getDestinationName","getDestinationIata","getDestinationCode");
                        if (!orgTry.isBlank()) origen = orgTry;
                        if (!dstTry.isBlank()) destino = dstTry;
                    }
                }
            }
        } catch (Exception ignored) {}

        if (origen.isBlank() || destino.isBlank()) {
            String[] ends = getRouteEndsByName(rutaName);
            if (origen.isBlank())  origen  = ends[0];
            if (destino.isBlank()) destino = ends[1];
        }

        lblDetTitulo.setText("Detalle de la reserva #" + (booking.getId() != null ? booking.getId() : "-"));
        lblDetVuelo.setText("Vuelo: " + vueloName);
        lblDetRuta.setText("Ruta: " + (rutaName.isBlank() ? "-" : rutaName)
                + "  |  Origen: " + (origen.isBlank() ? "-" : origen)
                + "  |  Destino: " + (destino.isBlank() ? "-" : destino));
        lblDetFechaHora.setText("Fecha/Hora: " + fechaTxt);
        lblDetTotal.setText("Total: " + totalTxt);

        modelSeats.setRowCount(0);
        if (booking.getTickets() != null) {
            for (Ticket t : booking.getTickets()) {
                String ticketId = t.getId() != null ? String.valueOf(t.getId()) : "-";
                String nro = "-";
                String tipo = "-";
                String vName = "-";
                String rName = "-";
                String org = "", dst = "";

                if (t.getSeat() != null) {
                    if (t.getSeat().getNumber() != null) nro = String.valueOf(t.getSeat().getNumber());
                    if (t.getSeat().getType() != null)   tipo = String.valueOf(t.getSeat().getType());
                    if (t.getSeat().getFlight() != null) {
                        var f = t.getSeat().getFlight();
                        if (f.getName() != null) vName = f.getName();
                        if (f.getFlightRoute() != null) {
                            var fr = f.getFlightRoute();
                            if (fr.getName() != null) rName = fr.getName();

                            String orgTry = callGetterString(fr, "getOriginCityName","getOriginAirportName","getOriginName","getOriginIata","getOriginCode");
                            String dstTry = callGetterString(fr, "getDestinationCityName","getDestinationAirportName","getDestinationName","getDestinationIata","getDestinationCode");
                            if (!orgTry.isBlank()) org = orgTry;
                            if (!dstTry.isBlank()) dst = dstTry;

                            if (org.isBlank() || dst.isBlank()) {
                                String[] ends = getRouteEndsByName(rName);
                                if (org.isBlank()) org = ends[0];
                                if (dst.isBlank()) dst = ends[1];
                            }
                        }
                    }
                }
                modelSeats.addRow(new Object[]{ ticketId, nro, tipo, vName, rName, (org.isBlank()? "-" : org), (dst.isBlank()? "-" : dst) });
            }
        }
    }

    private void loadCustomerPackages(String customerNickname) {
        modelPaquetes.setRowCount(0);
        try {
            CustomerDTO c = userController.getCustomerDetailsByNickname(customerNickname);
            if (c == null) { adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes); return; }

            List<Long> boughtPackagesIds = c.getBoughtPackagesIds();
            if (boughtPackagesIds == null || boughtPackagesIds.isEmpty()) { adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes); return; }

            for (Long id : boughtPackagesIds) {
                BuyPackageDTO buyPackageDTO = buyPackageController.getBuyPackageDetailsById(id);

                modelPaquetes.addRow(new Object[] {
                        buyPackageDTO.getId(),
                        buyPackageDTO.getFlightRoutePackageName(),
                        buyPackageDTO.getCreatedAt(),
                        buyPackageDTO.getTotalPrice()
                });
            }

            adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar paquetes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDetallePaquete() {
        int v = tblPaquetes.getSelectedRow();
        if (v < 0) { JOptionPane.showMessageDialog(this, "Seleccioná un paquete"); return; }
        int r = tblPaquetes.convertRowIndexToModel(v);
        String name = Objects.toString(modelPaquetes.getValueAt(r, 1), "");
        try {
            FlightRoutePackageDTO p = flightRoutePackageController.getFlightRoutePackageDetailsByName(name);
            mostrarDialogoDetalle("Detalle de Paquete: " + name, detallePaqueteAsText(p));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el paquete: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /** Construye un panel de texto con el detalle del paquete. */
    private JComponent detallePaqueteAsText(FlightRoutePackageDTO p) {
        if (p == null) {
            JTextArea ta = new JTextArea("No se encontró el paquete.");
            ta.setEditable(false);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            return new JScrollPane(ta);
        }

        // Lecturas seguras usando tus helpers
        String code   = callGetterString(p, "getCode");
        String name   = callGetterString(p, "getName");
        String desc   = callGetterString(p, "getDescription");
        String days   = callGetterNumber(p, "getValidityPeriodDays");
        String disc   = callGetterNumber(p, "getDiscount"); // puede venir 0.15 o 15; lo muestro tal cual
        String rutas  = "";
        try {
            var list = p.getFlightRouteNames();
            if (list != null && !list.isEmpty()) rutas = String.join(", ", list);
        } catch (Throwable ignored) {}

        StringBuilder sb = new StringBuilder();
        sb.append("Código: ").append(code.isBlank() ? "-" : code).append("\n");
        sb.append("Nombre: ").append(name.isBlank() ? "-" : name).append("\n");
        sb.append("Descripción: ").append(desc.isBlank() ? "-" : desc).append("\n");
        sb.append("Validez: ").append(days.isBlank() ? "-" : days + " días").append("\n");
        if (!disc.isBlank()) sb.append("Descuento: ").append(disc).append("\n");
        sb.append("Rutas incluidas: ").append(rutas.isBlank() ? "-" : rutas);

        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        return new JScrollPane(ta);
    }

    // ---------- header ----------
    private void headerDatosSet(String n, String t, String e, String d) {
        name.setText("Nombre: " + nz(n));
        tipo.setText("Tipo: " + nz(t));
        email.setText("Email: " + nz(e));
        doc.setText("Documento: " + nz(d));
    }
    private void headerDatosReset() {
        name.setText("Nombre");
        tipo.setText("Tipo");
        email.setText("Nombre");
        doc.setText("Nombre");
    }

    private void showCard(String key) { ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, key); }

    // ---------- utils tablas ----------
    private void adjustDynamicWidthAndHeightToTable(JTable table, DefaultTableModel tableModel) {
        table.setModel(tableModel);
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = table.getRowCount();

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, col
            );
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width);

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col
                );
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10);
        }
        int minRows = 5;
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(new Dimension(
                table.getPreferredSize().width,
                visibleRows * table.getRowHeight()
        ));
    }

    private int findTableColumnIndex(JTable table, String... candidates) {
        if (table == null || candidates == null) return -1;
        for (int col = 0; col < table.getColumnCount(); col++) {
            Object header = table.getColumnModel().getColumn(col).getHeaderValue();
            String h = header == null ? "" : String.valueOf(header).trim();
            for (String c : candidates) {
                if (c != null && !c.isBlank() && h.equalsIgnoreCase(c.trim())) return col;
            }
        }
        for (int col = 0; col < table.getColumnCount(); col++) {
            Object header = table.getColumnModel().getColumn(col).getHeaderValue();
            String h = header == null ? "" : String.valueOf(header).trim().toLowerCase();
            for (String c : candidates) {
                if (c == null) continue;
                String cand = c.trim().toLowerCase();
                if (h.contains(cand) || cand.contains(h)) return col;
            }
        }
        return -1;
    }

    // ---------- utils reflexión / texto ----------
    private static String nz(String s) { return s == null ? "" : s; }

    private String callGetterString(Object target, String... candidates) {
        Object v = callGetter(target, candidates);
        return v == null ? "" : String.valueOf(v);
    }
    private String callGetterNumber(Object target, String... candidates) {
        Object v = callGetter(target, candidates);
        return v == null ? "" : String.valueOf(v);
    }
    @SuppressWarnings("unchecked")
    private <T> List<T> callGetterList(Object target, Class<T> of, String... candidates) {
        Object v = callGetter(target, candidates);
        if (v instanceof List<?> list) {
            List<T> out = new ArrayList<>();
            for (Object o : list) if (o != null && of.isInstance(o)) out.add((T) o);
            return out;
        }
        return List.of();
    }
    private Object callGetter(Object target, String... candidates) {
        if (target == null) return null;
        Class<?> c = target.getClass();
        for (String name : candidates) {
            try {
                Method m = c.getMethod(name);
                m.setAccessible(true);
                return m.invoke(target);
            } catch (Exception ignored) {}
        }
        return null;
    }
    private String callGetterDate(Object target, String... candidates) {
        Object v = callGetter(target, candidates);
        if (v == null) return "";
        try {
            Method fmt = v.getClass().getMethod("format", java.time.format.DateTimeFormatter.class);
            return String.valueOf(fmt.invoke(v, DTF));
        } catch (Exception ignore) {
            return String.valueOf(v);
        }
    }

    /** 🔧 FALTANTE: invoca por reflexión el primer método que exista. */
    private Object tryInvoke(Object target, String[] methodNames, Class<?>[] paramTypes, Object[] args) {
        if (target == null) return null;
        Class<?> c = target.getClass();
        for (String name : methodNames) {
            try {
                Method m = c.getMethod(name, paramTypes);
                m.setAccessible(true);
                return m.invoke(target, args);
            } catch (NoSuchMethodException ignored) {
                // probamos el siguiente nombre
            } catch (Exception e) {
                System.err.println("Invocación fallida a " + name + ": " + e.getMessage());
            }
        }
        return null;
    }

    private void mostrarDialogoDetalle(String titulo, JComponent contenido) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog d = new JDialog(owner, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.getContentPane().add(contenido);
        d.setSize(520, 420);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
    private void mostrarDialogoDetalle(String titulo, String texto) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(620, 360));
        mostrarDialogoDetalle(titulo, scroll);
    }

    private String[] getRouteEndsByName(String routeName) {
        if (routeName == null || routeName.isBlank()) return new String[]{"", ""};
        String[] cached = routeEndsCache.get(routeName);
        if (cached != null) return cached;
        try {
            FlightRouteDTO dto = flightRouteController.getFlightRouteDetailsByName(routeName);
            String org = callGetterString(dto, "getOriginCityName", "getOrigin", "getOriginName", "getOriginCity");
            String dst = callGetterString(dto, "getDestinationCityName", "getDestination", "getDestinationName", "getDestinationCity");
            String[] out = new String[]{ nz(org), nz(dst) };
            routeEndsCache.put(routeName, out);
            return out;
        } catch (Exception e) {
            routeEndsCache.put(routeName, new String[]{"", ""});
            return new String[]{"", ""};
        }
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Ignacio Suarez
        splitPane1 = new JSplitPane();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        panel13 = new JPanel();
        label5 = new JLabel();
        cmbTipo = new JComboBox();
        scrollPane2 = new JScrollPane();
        tblUsuarios = new JTable();
        panel5 = new JPanel();
        headerDatos = new JPanel();
        name = new JLabel();
        tipo = new JLabel();
        email = new JLabel();
        doc = new JLabel();
        cardsPanel = new JPanel();
        cardEmpty = new JPanel();
        label4 = new JLabel();
        airline = new JPanel();
        panel9 = new JPanel();
        btnDetalleRuta = new JButton();
        scrollPane3 = new JScrollPane();
        tblRutas = new JTable();
        client = new JPanel();
        tabbedPane1 = new JTabbedPane();
        panelReservas = new JPanel();
        panelToolbarReserva = new JPanel();
        btnDetalleReserva = new JButton();
        scrollPaneReservas = new JScrollPane();
        tblReservas = new JTable();
        panelPaquetes = new JPanel();
        panelToolbarPaquete = new JPanel();
        btnDetallePaquete = new JButton();
        scrollPanePaquetes = new JScrollPane();
        tblPaquetes = new JTable();
        flowLayout1 = new FlowLayout();

        //======== splitPane1 ========
        {
            splitPane1.setContinuousLayout(true);
            splitPane1.setResizeWeight(0.45);
            splitPane1.setDividerSize(8);
            splitPane1.setMinimumSize(new Dimension(260, 200));
            splitPane1.setOneTouchExpandable(true);

            //======== panel4 ========
            {
                panel4.setBorder(new EmptyBorder(8, 8, 8, 8));
                panel4.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder
                (0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing.border
                .TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt
                .Color.red),panel4. getBorder()));panel4. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void
                propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException()
                ;}});
                panel4.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {
                    scrollPane1.setBorder(new LineBorder(Color.darkGray));

                    //======== panel13 ========
                    {
                        panel13.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 144, 0};
                        ((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                        ((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                        //---- label5 ----
                        label5.setText("Buscar");
                        label5.setFont(new Font("Inter", Font.PLAIN, 12));
                        label5.setForeground(new Color(0x5f6368));
                        panel13.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));

                        //---- cmbTipo ----
                        cmbTipo.setPreferredSize(new Dimension(110, 28));
                        cmbTipo.setMaximumRowCount(10);
                        panel13.add(cmbTipo, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    scrollPane1.setViewportView(panel13);
                }
                panel4.add(scrollPane1, BorderLayout.NORTH);

                //======== scrollPane2 ========
                {
                    scrollPane2.setForeground(new Color(0x1f1f1f));
                    scrollPane2.setBackground(new Color(0xf7f9fc));

                    //---- tblUsuarios ----
                    tblUsuarios.setRowHeight(26);
                    tblUsuarios.setShowVerticalLines(false);
                    tblUsuarios.setGridColor(new Color(0xe6e6e6));
                    tblUsuarios.setIntercellSpacing(new Dimension(0, 0));
                    tblUsuarios.setAutoCreateRowSorter(true);
                    tblUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tblUsuarios.setSelectionBackground(new Color(0xe8f0fe));
                    tblUsuarios.setSelectionForeground(new Color(0x0b57d0));
                    scrollPane2.setViewportView(tblUsuarios);
                }
                panel4.add(scrollPane2, BorderLayout.CENTER);
            }
            splitPane1.setLeftComponent(panel4);

            //======== panel5 ========
            {
                panel5.setBorder(new EmptyBorder(8, 8, 8, 8));
                panel5.setLayout(new BorderLayout());

                //======== headerDatos ========
                {
                    headerDatos.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
                    headerDatos.setBackground(new Color(0xf7f9fc));
                    headerDatos.setLayout(new GridLayout(2, 2));

                    //---- name ----
                    name.setForeground(new Color(0x1f1f1f));
                    name.setText("Nombre");
                    headerDatos.add(name);

                    //---- tipo ----
                    tipo.setForeground(new Color(0x1f1f1f));
                    tipo.setText("Nombre");
                    headerDatos.add(tipo);

                    //---- email ----
                    email.setForeground(new Color(0x1f1f1f));
                    email.setText("Nombre");
                    headerDatos.add(email);

                    //---- doc ----
                    doc.setForeground(new Color(0x1f1f1f));
                    doc.setText("Nombre");
                    headerDatos.add(doc);
                }
                panel5.add(headerDatos, BorderLayout.NORTH);

                //======== cardsPanel ========
                {
                    cardsPanel.setBorder(new EmptyBorder(6, 8, 8, 8));
                    cardsPanel.setLayout(new CardLayout());

                    //======== cardEmpty ========
                    {
                        cardEmpty.setLayout(new BorderLayout());

                        //---- label4 ----
                        label4.setText("Seleccion\u00e1 un usuario");
                        label4.setHorizontalTextPosition(SwingConstants.CENTER);
                        cardEmpty.add(label4, BorderLayout.CENTER);
                    }
                    cardsPanel.add(cardEmpty, "EMPTY");

                    //======== airline ========
                    {
                        airline.setLayout(new BorderLayout());

                        //======== panel9 ========
                        {
                            panel9.setLayout(new BorderLayout());

                            //---- btnDetalleRuta ----
                            btnDetalleRuta.setText("Ver detalle ruta");
                            panel9.add(btnDetalleRuta, BorderLayout.CENTER);
                        }
                        airline.add(panel9, BorderLayout.NORTH);

                        //======== scrollPane3 ========
                        {

                            //---- tblRutas ----
                            tblRutas.setBorder(new EmptyBorder(8, 8, 8, 8));
                            tblRutas.setFillsViewportHeight(true);
                            tblRutas.setRowHeight(24);
                            scrollPane3.setViewportView(tblRutas);
                        }
                        airline.add(scrollPane3, BorderLayout.CENTER);
                    }
                    cardsPanel.add(airline, "AIRLINE");

                    //======== client ========
                    {
                        client.setLayout(new BorderLayout());

                        //======== tabbedPane1 ========
                        {
                            tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                            tabbedPane1.setForeground(new Color(0x1f1f1f));
                            tabbedPane1.setBackground(new Color(0xf7f9fc));

                            //======== panelReservas ========
                            {
                                panelReservas.setLayout(new BorderLayout());

                                //======== panelToolbarReserva ========
                                {
                                    panelToolbarReserva.setLayout(new FlowLayout());

                                    //---- btnDetalleReserva ----
                                    btnDetalleReserva.setText("Ver Detalle Reserva");
                                    btnDetalleReserva.setMargin(new Insets(8, 14, 8, 14));
                                    btnDetalleReserva.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                    btnDetalleReserva.setToolTipText("Abrir detalle seleccionado");
                                    panelToolbarReserva.add(btnDetalleReserva);
                                }
                                panelReservas.add(panelToolbarReserva, BorderLayout.NORTH);

                                //======== scrollPaneReservas ========
                                {
                                    scrollPaneReservas.setViewportView(tblReservas);
                                }
                                panelReservas.add(scrollPaneReservas, BorderLayout.CENTER);
                            }
                            tabbedPane1.addTab("Reservas", panelReservas);

                            //======== panelPaquetes ========
                            {
                                panelPaquetes.setLayout(new BorderLayout());

                                //======== panelToolbarPaquete ========
                                {
                                    panelToolbarPaquete.setLayout(new FlowLayout());

                                    //---- btnDetallePaquete ----
                                    btnDetallePaquete.setText("Ver Detalle Paquete");
                                    panelToolbarPaquete.add(btnDetallePaquete);
                                }
                                panelPaquetes.add(panelToolbarPaquete, BorderLayout.NORTH);

                                //======== scrollPanePaquetes ========
                                {
                                    scrollPanePaquetes.setViewportView(tblPaquetes);
                                }
                                panelPaquetes.add(scrollPanePaquetes, BorderLayout.CENTER);
                            }
                            tabbedPane1.addTab("Paquetes", panelPaquetes);
                        }
                        client.add(tabbedPane1, BorderLayout.CENTER);
                    }
                    cardsPanel.add(client, "CLIENT");
                }
                panel5.add(cardsPanel, BorderLayout.CENTER);
            }
            splitPane1.setRightComponent(panel5);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Ignacio Suarez
    private JSplitPane splitPane1;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JPanel panel13;
    private JLabel label5;
    private JComboBox cmbTipo;
    private JScrollPane scrollPane2;
    private JTable tblUsuarios;
    private JPanel panel5;
    private JPanel headerDatos;
    private JLabel name;
    private JLabel tipo;
    private JLabel email;
    private JLabel doc;
    private JPanel cardsPanel;
    private JPanel cardEmpty;
    private JLabel label4;
    private JPanel airline;
    private JPanel panel9;
    private JButton btnDetalleRuta;
    private JScrollPane scrollPane3;
    private JTable tblRutas;
    private JPanel client;
    private JTabbedPane tabbedPane1;
    private JPanel panelReservas;
    private JPanel panelToolbarReserva;
    private JButton btnDetalleReserva;
    private JScrollPane scrollPaneReservas;
    private JTable tblReservas;
    private JPanel panelPaquetes;
    private JPanel panelToolbarPaquete;
    private JButton btnDetallePaquete;
    private JScrollPane scrollPanePaquetes;
    private JTable tblPaquetes;
    private FlowLayout flowLayout1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
