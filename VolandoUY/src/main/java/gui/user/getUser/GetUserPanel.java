/*
 * Created by JFormDesigner on Sun Sep 07 16:17:39 UYT 2025
 */

package gui.user.getUser;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
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
import controllers.seat.ISeatController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.dtos.user.CustomerDTO;
import gui.flightRoute.details.FlightRouteDetailWindow;
import gui.flightRoutePackage.details.FlightRoutePackageDetailWindow;
import shared.constants.Images;
import shared.utils.ImageProcessor;

/**
 * @author AparicioQuian
 */
public class GetUserPanel extends JPanel {
    private final IUserController userController;
    private final IFlightRouteController flightRouteController;
    private final IFlightController flightController;
    private final IFlightRoutePackageController flightRoutePackageController;
    private final IBookingController bookingController;
    private final IBuyPackageController buyPackageController;
    private final ITicketController ticketController;
    private final ISeatController seatController;

    // --------- constantes ----------
    private static final String CARD_EMPTY   = "EMPTY";
    private static final String CARD_AIRLINE = "AIRLINE";
    private static final String CARD_CLIENT  = "CLIENT";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --------- modelos de tablas ----------
    private DefaultTableModel modelUsuarios;
    private DefaultTableModel modelRutas;
    private DefaultTableModel modelReservas;
    private DefaultTableModel modelPaquetes;

    // --------- estado selección ----------
    private String usuarioSeleccionadoTipo = null;
    private String usuarioSeleccionadoNick = null;

    // --------- header dinámico (manual, NO JFD) ----------
    private JPanel headerCards;        // contenedor con CardLayout (reusa headerDatos)
    private JPanel headerClientePanel; // card cliente
    private JPanel headerAirlinePanel; // card aerolínea

    // labels aerolínea (manuales; los de cliente son los de JFD: name, tipo, email, doc)
    private JLabel aName;
    private JLabel aNickname;
    private JLabel aImage;
    private JLabel aEmail;
    private JLabel aWeb;
    private JLabel aDesc;

    // --------- detalle reserva (manual) ----------
    private JPanel detalleReservaPanel;
    private JLabel lblDetTitulo, lblDetVuelo, lblDetRuta, lblDetFechaHora, lblDetTotal;
    private JTable tblSeats;
    private DefaultTableModel modelSeats;

    // --------- detalle ruta (manual) ----------
    private JPanel panelDetalleRuta, airlineBody;


    // --------- ctor ----------
    public GetUserPanel(
            IUserController userController,
            IFlightRouteController flightRouteController,
            IFlightController flightController,
            IFlightRoutePackageController flightRoutePackageController,
            IBookingController bookingController,
            IBuyPackageController buyPackageController,
            ITicketController ticketController,
            ISeatController seatController
    ) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.flightController = flightController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.bookingController = bookingController;
        this.buyPackageController = buyPackageController;
        this.ticketController = ticketController;
        this.seatController = seatController;

        initComponents();

        setLayout(new BorderLayout());
        add(splitPane1, BorderLayout.CENTER);

        // construye el header dinámico sobre el panel generado por JFD
        buildHeaderCards();

        postInitWireUp();

        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
        try { panel4.setBorder(null); } catch (Exception ignored) {}
    }

    // ---------- wiring inicial ----------
    private void postInitWireUp() {
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"Todos", "Usuario", "Aerolínea"}));
        cmbTipo.setSelectedIndex(0);

        modelUsuarios = new DefaultTableModel(new Object[]{"Tipo", "Nombre", "Nickname"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsuarios.setModel(modelUsuarios);
        tblUsuarios.setAutoCreateRowSorter(true);
        tblUsuarios.getTableHeader().setReorderingAllowed(false);

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
        tblPaquetes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modelPaquetes = new DefaultTableModel(new Object[]{"Id", "Nombre paquete", "Fecha compra", "Precio total" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPaquetes.setModel(modelPaquetes);
        tblPaquetes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cmbTipo.addActionListener(e -> reloadUsuarios());
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) onUsuarioSeleccionado(); });

        reloadUserTableLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { reloadUsuarios(); }
        });
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

    // ---------- header dinámico ----------
    private void buildHeaderCards() {
        // usamos headerDatos (creado por JFD) como contenedor de tarjetas
        headerDatos.removeAll();
        headerDatos.setLayout(new CardLayout());
        headerCards = headerDatos;

        // Card CLIENTE: reutiliza labels existentes
        headerClientePanel = new JPanel(new GridLayout(2, 3));
        headerClientePanel.add(name);
        headerClientePanel.add(nickname);
        headerClientePanel.add(image);
        headerClientePanel.add(doc);
        headerClientePanel.add(email);

        headerClientePanel.add(new JLabel("")); // espacio vacío
        // Card AEROLÍNEA: labels nuevos
        headerAirlinePanel = new JPanel(new GridLayout(2, 3));
        headerAirlinePanel.setOpaque(false);
        aName = new JLabel("Nombre");
        aNickname = new JLabel("Nickname");
        aImage = new JLabel(ImageProcessor.makeRoundedIcon(Images.USER_DEFAULT, 100));
        aWeb  = new JLabel("Web");
        aEmail = new JLabel("Email");
        aDesc = new JLabel("Descripción");


        headerAirlinePanel.add(aName);
        headerAirlinePanel.add(aNickname);
        headerAirlinePanel.add(aImage);
        headerAirlinePanel.add(aWeb);
        headerAirlinePanel.add(aEmail);
        headerAirlinePanel.add(aDesc);

        headerCards.add(headerClientePanel, CARD_CLIENT);
        headerCards.add(headerAirlinePanel, CARD_AIRLINE);

        showHeaderCard(CARD_CLIENT);
    }

    private void showHeaderCard(String key) {
        ((CardLayout) headerCards.getLayout()).show(headerCards, key);
        headerCards.revalidate();
        headerCards.repaint();
    }

    private void headerClienteSet(String n, String nick, ImageIcon i, String e, String d) {
        name.setText(nz(n));
        nickname.setText(nick);
        image.setIcon(i);
        email.setText(nz(e));
        doc.setText(nz(d));
        showHeaderCard(CARD_CLIENT);
    }

    private void headerAirlineSet(String n, String nick,ImageIcon i, String e, String w, String d) {
        aName.setText(nz(n));
        aNickname.setText(nick);
        aImage.setIcon(i);
        aEmail.setText(nz(e));
        aWeb.setText((w.isBlank() ? "-Sin web-" : w));
        aDesc.setText((d.isBlank() ? "-Sin descripción-" : d));
        showHeaderCard(CARD_AIRLINE);
    }

    private void headerDatosReset() {
        name.setText("Nombre");
        nickname.setText("Nickname");
        email.setText("Email");
        doc.setText("Documento");
        showHeaderCard(CARD_CLIENT);
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
                    String nombre = (nz(c.getName()) + " " + nz(c.getSurname())).trim();
                    modelUsuarios.addRow(new Object[]{ "Usuario", nombre, nick });
                }
            }
            if ("Todos".equalsIgnoreCase(filtro) || "Aerolínea".equalsIgnoreCase(filtro)) {
                List<BaseAirlineDTO> airlines = userController.getAllAirlinesSimpleDetails();
                for (BaseAirlineDTO a : airlines) {
                    String nick = nz(a.getNickname());
                    if (nick.isBlank()) continue;
                    modelUsuarios.addRow(new Object[]{ "Aerolínea", nz(a.getName()), nick });
                }
            }
            adjustDynamicWidthAndHeightToTable(tblUsuarios);
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
        // Nueva disposición de columnas: 0=Tipo, 1=Nombre, 2=Nickname
        usuarioSeleccionadoTipo = Objects.toString(modelUsuarios.getValueAt(r, 0), "");
        usuarioSeleccionadoNick = Objects.toString(modelUsuarios.getValueAt(r, 2), "").trim();

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
                if (a == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró la aerolínea seleccionada.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ImageIcon image = ImageProcessor.makeRoundedIcon(!a.getImage().equals(Images.USER_DEFAULT) ? a.getImage() : Images.USER_DEFAULT, 100);
                headerAirlineSet(a.getName(), a.getNickname(), image, a.getMail(), a.getWeb(), a.getDescription());                       // usa tus labels de aerolínea (web/desc/email)
                loadAirlineRoutes(usuarioSeleccionadoNick);
                showCard(CARD_AIRLINE);
            } else {
                // Cliente: ahora levantamos todos los datos vía controller (no desde la tabla)
                CustomerDTO c = userController.getCustomerDetailsByNickname(usuarioSeleccionadoNick);
                if (c == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró el cliente seleccionado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombre   = (nz(c.getName()) + " " + nz(c.getSurname())).trim();
                String docTxt   = (enumToString(c.getDocType()) + " " + nz(c.getNumDoc())).trim();
                String emailTxt = nz(c.getMail());
                ImageIcon image = ImageProcessor.makeRoundedIcon(!c.getImage().equals(Images.USER_DEFAULT) ? c.getImage() : Images.USER_DEFAULT, 100);
                System.out.println(c.getImage());

                headerClienteSet(nombre, c.getNickname(), image, emailTxt, docTxt);

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
            adjustDynamicWidthAndHeightToTable(tblRutas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar rutas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirDetalleRuta() {
        int v = tblRutas.getSelectedRow();
        if (v < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná una ruta");
            return;
        }
        int r = tblRutas.convertRowIndexToModel(v);
        String routeName = Objects.toString(modelRutas.getValueAt(r, 0), "").trim();
        if (routeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ruta inválida");
            return;
        }

        try {
            // Traemos el DTO de la ruta
            FlightRouteDTO dto = flightRouteController.getFlightRouteDetailsByName(routeName);

            // Abrimos la ventana de detalle de RUTA
            SwingUtilities.invokeLater(() -> {
                JFrame win = new FlightRouteDetailWindow(dto, flightController);
                win.setVisible(true);
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar la ruta: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------- cliente: reservas ----------
    private void loadCustomerReservations(String customerNickname) {
        List<BookFlightDTO> reservas = bookingController.getBookFlightsDetailsByCustomerNickname(customerNickname);

        DefaultTableModel model = (DefaultTableModel) tblReservas.getModel();
        model.setColumnIdentifiers(new Object[] { "ID", "Fecha", "Total", "Tickets", "Vuelos" });
        model.setRowCount(0);

        for (BookFlightDTO reserva : reservas) {
            int ticketsCount = (reserva != null && reserva.getTicketIds() != null) ? reserva.getTicketIds().size() : 0;
            String flights = "";
            if (reserva != null && reserva.getTicketIds() != null) {
                flights = reserva.getTicketIds().stream()
                        .map(ticketId -> {
                            TicketDTO t = ticketController.getTicketDetailsById(ticketId);
                            SeatDTO s = seatController.getSeatDetailsById(t.getSeatId());
                            return (s != null && s.getFlightName() != null)
                                    ? nz(s.getFlightName()) : "N/A";
                        })
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
        adjustDynamicWidthAndHeightToTable(tblReservas);
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

        BookFlightDTO bookingDTO;
        try { bookingDTO = bookingController.getBookFlightDetailsById(bookingId); }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la reserva: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (bookingDTO == null) { JOptionPane.showMessageDialog(this, "No se encontró la reserva."); return; }

        fillDetalleReserva(bookingDTO);
        detalleReservaPanel.setVisible(true);
        adjustDynamicWidthAndHeightToTable(tblSeats);
    }

    private void fillDetalleReserva(BookFlightDTO booking) {
        String totalTxt = String.valueOf(booking.getTotalPrice());
        String fechaTxt = booking.getCreatedAt() != null ? String.valueOf(booking.getCreatedAt()) : "-";

        // Un ticket cualquiera (mismo vuelo)
        TicketDTO t = ticketController.getTicketDetailsById(booking.getTicketIds().get(0));
        if (t == null) return;
        SeatDTO s = seatController.getSeatDetailsById(t.getSeatId());
        if (s == null) return;
        FlightDTO f = flightController.getFlightDetailsByName(s.getFlightName());
        if (f == null) return;
        FlightRouteDTO fr = flightRouteController.getFlightRouteDetailsByName(f.getFlightRouteName());
        if (fr == null) return;

        String vueloName = f.getName();
        String rutaName  = fr.getName();
        String origen    = fr.getOriginCityName();
        String destino   = fr.getDestinationCityName();

        lblDetTitulo.setText("Detalle de la reserva #" + (booking.getId() != null ? booking.getId() : "-"));
        lblDetVuelo.setText("Vuelo: " + vueloName);
        lblDetRuta.setText("Ruta: " + (rutaName.isBlank() ? "-" : rutaName)
                + "  |  Origen: " + (origen.isBlank() ? "-" : origen)
                + "  |  Destino: " + (destino.isBlank() ? "-" : destino));
        lblDetFechaHora.setText("Fecha/Hora: " + fechaTxt);
        lblDetTotal.setText("Total: " + totalTxt);

        modelSeats.setRowCount(0);
        if (booking.getTicketIds() != null) {
            for (Long ticketId : booking.getTicketIds()) {
                BaseSeatDTO seatDTO = seatController.getSeatSimpleDetailsByTicketId(ticketId);
                String numeroAsiento = seatDTO.getNumber();
                String tipo = seatDTO.getType().name();

                modelSeats.addRow(new Object[]{
                        ticketId, numeroAsiento, tipo, vueloName, rutaName, origen, destino  });
            }
        }
    }

    private void loadCustomerPackages(String customerNickname) {
        modelPaquetes.setRowCount(0);
        try {
            CustomerDTO c = userController.getCustomerDetailsByNickname(customerNickname);
            if (c == null) { adjustDynamicWidthAndHeightToTable(tblPaquetes); return; }

            List<Long> boughtPackagesIds = c.getBoughtPackagesIds();
            if (boughtPackagesIds == null || boughtPackagesIds.isEmpty()) { adjustDynamicWidthAndHeightToTable(tblPaquetes); return; }

            for (Long id : boughtPackagesIds) {
                BuyPackageDTO buyPackageDTO = buyPackageController.getBuyPackageDetailsById(id);

                modelPaquetes.addRow(new Object[] {
                        buyPackageDTO.getId(),
                        buyPackageDTO.getFlightRoutePackageName(),
                        buyPackageDTO.getCreatedAt(),
                        buyPackageDTO.getTotalPrice()
                });
            }

            adjustDynamicWidthAndHeightToTable(tblPaquetes);

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
            SwingUtilities.invokeLater(() -> {
                JFrame win = new FlightRoutePackageDetailWindow(name, flightRoutePackageController, flightRouteController);
                win.setVisible(true);
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el paquete: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showCard(String key) { ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, key); }

    // ---------- utils tablas ----------
    private void adjustDynamicWidthAndHeightToTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int tableWidth = 0;

        // ancho
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 0;
            int maxRows = table.getRowCount();

            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, column.getHeaderValue(), false, false, 0, col
            );
            preferredWidth = Math.max(preferredWidth, headerComp.getPreferredSize().width) + 50;

            for (int row = 0; row < maxRows; row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col
                );
                preferredWidth = Math.max(preferredWidth, c.getPreferredSize().width);
            }
            column.setPreferredWidth(preferredWidth + 10);
            tableWidth += preferredWidth;
        }

        // alto
        int minRows = 5;
        int visibleRows = Math.max(table.getRowCount(), minRows);
        table.setPreferredSize(new Dimension(
                tableWidth,
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - asd
        splitPane1 = new JSplitPane();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        panel13 = new JPanel();
        reloadUserTableLabel = new JLabel();
        cmbTipo = new JComboBox();
        scrollPane2 = new JScrollPane();
        tblUsuarios = new JTable();
        panel5 = new JPanel();
        headerDatos = new JPanel();
        name = new JLabel();
        nickname = new JLabel();
        email = new JLabel();
        doc = new JLabel();
        image = new JLabel();
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
                panel4.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
                swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn", javax. swing. border
                . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog"
                ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel4. getBorder
                ( )) ); panel4. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
                .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () )) throw new RuntimeException
                ( ); }} );
                panel4.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {
                    scrollPane1.setBorder(new LineBorder(Color.darkGray));

                    //======== panel13 ========
                    {
                        panel13.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {28, 144, 0};
                        ((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0};
                        ((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                        ((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                        //---- reloadUserTableLabel ----
                        reloadUserTableLabel.setText("\ud83d\udd04");
                        reloadUserTableLabel.setFont(new Font("Inter", Font.PLAIN, 12));
                        reloadUserTableLabel.setForeground(new Color(0x5f6368));
                        reloadUserTableLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
                        reloadUserTableLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        reloadUserTableLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        panel13.add(reloadUserTableLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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

                    //---- nickname ----
                    nickname.setForeground(new Color(0x1f1f1f));
                    nickname.setText("Nombre");
                    headerDatos.add(nickname);

                    //---- email ----
                    email.setForeground(new Color(0x1f1f1f));
                    email.setText("Nombre");
                    headerDatos.add(email);

                    //---- doc ----
                    doc.setForeground(new Color(0x1f1f1f));
                    doc.setText("Nombre");
                    headerDatos.add(doc);

                    //---- image ----
                    image.setForeground(new Color(0x1f1f1f));
                    headerDatos.add(image);
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
    // Generated using JFormDesigner Evaluation license - asd
    private JSplitPane splitPane1;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JPanel panel13;
    private JLabel reloadUserTableLabel;
    private JComboBox cmbTipo;
    private JScrollPane scrollPane2;
    private JTable tblUsuarios;
    private JPanel panel5;
    private JPanel headerDatos;
    private JLabel name;
    private JLabel nickname;
    private JLabel email;
    private JLabel doc;
    private JLabel image;
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
/*
private JLabel reloadUserTableLabel;
private JLabel imagen;
 */