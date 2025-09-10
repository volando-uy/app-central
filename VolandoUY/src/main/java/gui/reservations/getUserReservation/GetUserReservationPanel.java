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
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.dtos.user.CustomerDTO;
import java.util.List;

/**
 * @author AparicioQuian
 */
public class GetUserReservationPanel extends JPanel {

    private final IUserController userController;
    private final IFlightRouteController flightRouteController;
    private final IFlightController flightController;
    private final IFlightRoutePackageController flightRoutePackageController;
    private final IBookingController bookingController;

    private static final String CARD_EMPTY   = "EMPTY";
    private static final String CARD_AIRLINE = "AIRLINE";
    private static final String CARD_CLIENT  = "CLIENT";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --------- Modelos de tabla -----
    private DefaultTableModel modelUsuarios;
    private DefaultTableModel modelRutas;
    private DefaultTableModel modelReservas;
    private DefaultTableModel modelPaquetes;

    private String usuarioSeleccionadoTipo = null;    // "Usuario" | "Aerolínea"
    private String usuarioSeleccionadoNick = null;

    public GetUserReservationPanel(
            IUserController userController,
            IFlightRouteController flightRouteController,
            IFlightController flightController,
            IFlightRoutePackageController flightRoutePackageController,
            IBookingController bookingController
    ) {
        this.userController = userController;
        this.flightRouteController = flightRouteController;
        this.flightController = flightController;
        this.flightRoutePackageController = flightRoutePackageController;
        this.bookingController = bookingController;

        initComponents();                 // 1) primero: JFD crea splitPane1 y demás

        setLayout(new BorderLayout());    // 2) layout del panel raíz
        add(splitPane1, BorderLayout.CENTER); // 3) ahora sí: splitPane1 ya NO es null

        // Si estás usando la versión de evaluación de JFD, no cambies bordes aquí.
        // (Evita setBorder(...) para no disparar la trampa de evaluación.)

        postInitWireUp();
        try { setBorder(new EtchedBorder(EtchedBorder.LOWERED)); } catch (Exception ignored) {}
    }

    // ---------- Wiring UI/Model ----------
    private void postInitWireUp() {
        // Filtro
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{"Todos", "Usuario", "Aerolínea"}));
        cmbTipo.setSelectedIndex(0);

        // Modelos
        modelUsuarios = new DefaultTableModel(new Object[]{"Nombre", "Documento", "Email", "Tipo", "Nickname"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsuarios.setModel(modelUsuarios);

        modelRutas = new DefaultTableModel(new Object[]{"Ruta", "Origen", "Destino" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblRutas.setModel(modelRutas);

        modelReservas = new DefaultTableModel(new Object[]{"N° Reserva", "Fecha", "Vuelo" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblReservas.setModel(modelReservas);

        modelPaquetes = new DefaultTableModel(new Object[]{"Código", "Nombre", "Validez (días)" }, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPaquetes.setModel(modelPaquetes);

        // Listeners
        cmbTipo.addActionListener(e -> reloadUsuarios());
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onUsuarioSeleccionado();
        });
        btnDetalleRuta.addActionListener(e -> abrirDetalleRuta());
        btnDetalleReserva.addActionListener(e -> abrirDetalleReserva());
        btnDetallePaquete.addActionListener(e -> abrirDetallePaquete());

        // Arranque: todos
        reloadUsuarios();
        showCard(CARD_EMPTY);
    }

    // ---------- Carga de usuarios ----------
    private void reloadUsuarios() {
        String filtro = Objects.toString(cmbTipo.getSelectedItem(), "Todos");
        modelUsuarios.setRowCount(0);

        try {
            if ("Todos".equalsIgnoreCase(filtro) || "Usuario".equalsIgnoreCase(filtro)) {
                List<BaseCustomerDTO> customers = userController.getAllCustomersSimpleDetails();
                for (BaseCustomerDTO c : customers) {
                    String nick = nz(c.getNickname());
                    if (nick.isBlank()) continue; // <<< evitar filas inválidas

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
                    if (nick.isBlank()) continue; // <<< idem aerolíneas

                    modelUsuarios.addRow(new Object[]{
                            nz(a.getName()),
                            "",
                            nz(a.getMail()),
                            "Aerolínea",
                            nick
                    });
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
            JOptionPane.showMessageDialog(this,
                    "Este registro no tiene nickname. No se puede cargar el detalle.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // LIMPIEZA
        modelRutas.setRowCount(0);
        modelReservas.setRowCount(0);
        modelPaquetes.setRowCount(0);

        try {
            if (usuarioSeleccionadoTipo.equalsIgnoreCase("Aerolínea")) {
                // Para aerolínea sí pedimos el DTO simple (no toca bookedFlights)
                BaseAirlineDTO a = userController.getAirlineSimpleDetailsByNickname(usuarioSeleccionadoNick);
                headerDatosSet(nz(a.getName()), "Aerolínea", nz(a.getMail()), "");
                loadAirlineRoutes(usuarioSeleccionadoNick);
                showCard(CARD_AIRLINE);
            } else {
                // ⬇️ NO pedimos Customer; usamos lo que ya está en la tabla
                String nombre   = Objects.toString(modelUsuarios.getValueAt(r, 0), "");
                String docTxt   = Objects.toString(modelUsuarios.getValueAt(r, 1), "");
                String emailTxt = Objects.toString(modelUsuarios.getValueAt(r, 2), "");
                headerDatosSet(nombre, "Usuario", emailTxt, docTxt);

                // Datos derivados: reservas + paquetes (consultas planas)
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

    // ---------- Aerolínea ----------
    private void loadAirlineRoutes(String airlineNickname) {
        modelRutas.setRowCount(0);
        try {
            List<FlightRouteDTO> rutas =
                    flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname);
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
        String routeName = Objects.toString(modelRutas.getValueAt(r, 0), "");
        try {
            FlightRouteDTO dto = flightRouteController.getFlightRouteDetailsByName(routeName);
            mostrarDialogoDetalle("Detalle de Ruta: " + routeName, detalleRutaAsText(dto));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la ruta: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComponent detalleRutaAsText(FlightRouteDTO r) {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        String cats = "";
        try { if (r.getCategories() != null) cats = String.join(", ", r.getCategories()); } catch (Throwable ignored) {}

        t.setText(
                "Nombre: " + nz(r.getName()) + "\n" +
                        "Origen: " + callGetterString(r, "getOriginCityName", "getOrigin") + "\n" +
                        "Destino: " + callGetterString(r, "getDestinationCityName", "getDestination") + "\n" +

                        "Categorías: " + cats
        );
        return new JScrollPane(t);
    }

    private void loadCustomerReservations(String customerNickname) {
        modelReservas.setRowCount(0);
        try {
            // 1) Traigo TODO y filtro en UI
            List<BookFlightDTO> todas = bookingController.findAllBookFlightDetails();

            for (BookFlightDTO b : todas) {
                String nick = extractBookUserNickname(b);
                if (!customerNickname.equalsIgnoreCase(nick)) continue;

                String resNum = (b.getId() == null) ? "" : String.valueOf(b.getId());
                String fecha  = callGetterDate(b, "getCreated_at", "getCreatedAt", "getCreationDate");
                String vuelo  = extractBookFlightName(b); // usa campo plano si existe);

                modelReservas.addRow(new Object[]{ resNum, fecha, vuelo  });
            }

            adjustDynamicWidthAndHeightToTable(tblReservas, modelReservas);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar reservas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void abrirDetalleReserva() {
        int v = tblReservas.getSelectedRow();
        if (v < 0) { JOptionPane.showMessageDialog(this, "Seleccioná una reserva"); return; }
        int r = tblReservas.convertRowIndexToModel(v);

        String flightName = String.valueOf(modelReservas.getValueAt(r, 2));
        if (flightName == null || flightName.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Esta reserva no expone el nombre de vuelo.\n" +
                            "Podemos agregarlo al DTO más adelante.",
                    "Sin datos de vuelo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            FlightDTO f = flightController.getFlightDetailsByName(flightName);
            mostrarDialogoDetalle("Detalle de Vuelo: " + flightName, detalleVueloAsText(f));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el vuelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComponent detalleVueloAsText(FlightDTO f) {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        String fecha = callGetterDate(f, "getDepartureDate");
        t.setText(
                "Nombre: " + nz(f.getName()) + "\n" +
                        "Ruta: "   + callGetterString(f, "getFlightRouteName", "getRouteName") + "\n" +
                        "Fecha: "  + fecha + "\n" +
                        "Hora: "   + callGetterString(f, "getDepartureTime", "getTime") + "\n" +
                        "Estado: " + callGetterString(f, "getStatus")
        );
        return new JScrollPane(t);
    }

    // ---------- Cliente: PAQUETES (sin tocar services) ----------
    private void loadCustomerPackages(String customerNickname) {
        modelPaquetes.setRowCount(0);
        try {
            // 1) Traigo el DTO completo del cliente (no entidad)
            CustomerDTO c = userController.getCustomerDetailsByNickname(customerNickname);
            if (c == null) {
                adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes);
                return;
            }

            // 2) Leemos los IDs de paquetes comprados (lista de Long en el DTO)
            List<Long> ids = callGetterList(c, Long.class, "getBoughtPackagesIds");
            if (ids == null || ids.isEmpty()) {
                adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes);
                return;
            }

            for (Long id : ids) {
                BaseFlightRoutePackageDTO p = null;

                // 3) Intentamos resolver por ID con métodos comunes (si existen en tu controller)
                //    Probamos varias firmas por REFLEXIÓN, sin cambiar controllers.
                Object candidate = tryInvoke(
                        flightRoutePackageController,
                        new String[]{
                                "getFlightRoutePackageSimpleDetailsById",
                                "getFlightRoutePackageDetailsById",
                                "findFlightRoutePackageSimpleById",
                                "findFlightRoutePackageById"
                        },
                        new Class<?>[]{ Long.class },
                        new Object[]{ id }
                );

                if (candidate instanceof FlightRoutePackageDTO dtoFull) {
                    // Lo convertimos a base por getters (sólo lo que muestra la tabla)
                    modelPaquetes.addRow(new Object[]{
                            callGetterString(dtoFull, "getCode"),
                            callGetterString(dtoFull, "getName"),
                            callGetterNumber(dtoFull, "getValidityPeriodDays"),
                            ""
                    });
                    continue;
                } else if (candidate instanceof BaseFlightRoutePackageDTO dtoBase) {
                    p = dtoBase;
                }

                if (p != null) {
                    modelPaquetes.addRow(new Object[]{
                            callGetterString(p, "getCode"),
                            callGetterString(p, "getName"),
                            callGetterNumber(p, "getValidityPeriodDays"),
                            ""
                    });
                } else {
                    // 4) Si tu controller no expone búsquedas por ID,
                    //    mostramos el ID y dejamos el resto vacío (no rompe la UI).
                    modelPaquetes.addRow(new Object[]{
                            String.valueOf(id), // usamos el id como "código" visible
                            "",                  // nombre desconocido
                            "",                  // validez desconocida
                            ""
                    });
                }
            }

            adjustDynamicWidthAndHeightToTable(tblPaquetes, modelPaquetes);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar paquetes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object tryInvoke(Object target, String[] methodNames, Class<?>[] paramTypes, Object[] args) {
        if (target == null) return null;
        Class<?> c = target.getClass();
        for (String name : methodNames) {
            try {
                var m = c.getMethod(name, paramTypes);
                m.setAccessible(true);
                return m.invoke(target, args);
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                // Si el método existe pero falló, mostramos aviso amigable y seguimos con el próximo
                System.err.println("Invocación fallida a " + name + ": " + e.getMessage());
            }
        }
        return null;
    }
    private void abrirDetallePaquete() {
        int v = tblPaquetes.getSelectedRow();
        if (v < 0) { JOptionPane.showMessageDialog(this, "Seleccioná un paquete"); return; }
        int r = tblPaquetes.convertRowIndexToModel(v);
        String name = Objects.toString(modelPaquetes.getValueAt(r, 1), "");
        try {
            FlightRoutePackageDTO p =
                    flightRoutePackageController.getFlightRoutePackageDetailsByName(name);
            mostrarDialogoDetalle("Detalle de Paquete: " + name, detallePaqueteAsText(p));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el paquete: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JComponent detallePaqueteAsText(FlightRoutePackageDTO p) {
        JTextArea t = new JTextArea();
        t.setEditable(false);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        String rutas = "";
        try { if (p.getFlightRouteNames() != null) rutas = String.join(", ", p.getFlightRouteNames()); } catch (Throwable ignored) {}

        String desc = "";
        try { Double d = p.getDiscount(); if (d != null) desc = (d <= 1 ? d * 100 : d) + "%"; } catch (Throwable ignored) {}

        String dias = callGetterNumber(p, "getValidityPeriodDays");
        t.setText(
                "Código: "     + callGetterString(p, "getCode") + "\n" +
                        "Nombre: "     + nz(p.getName()) + "\n" +
                        "Descripción: "+ callGetterString(p, "getDescription") + "\n" +
                        "Validez: "    + (dias.isBlank() ? "" : dias + " días") + "\n" +
                        "Descuento: "  + desc + "\n" +
                        "Rutas: "      + rutas
        );
        return new JScrollPane(t);
    }

    // ---------- Header ----------
    private void headerDatosSet(String n, String t, String e, String d) {
        name.setText("Nombre: " + nz(n));
        tipo.setText("Tipo: " + nz(t));
        email.setText("Email: " + nz(e));
        doc.setText("Documento: " + nz(d));
    }
    private void headerDatosReset() {
        name.setText("Nombre");
        tipo.setText("Tipo");
        email.setText("Email");
        doc.setText("Documento");
    }

    private void showCard(String key) {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, key);
    }

    // ---------- Utils (tablas) ----------
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
    private String extractBookUserNickname(BookFlightDTO b) {
        String nick = callGetterString(b, "getUserNickname");   // directo en el DTO
        if (!nick.isBlank()) return nick;

        Object user = callGetter(b, "getUser");                 // objeto anidado
        if (user != null) {
            String n = callGetterString(user, "getNickname", "getNick");
            if (!n.isBlank()) return n;
        }
        return "";
    }

    /** Devuelve el nombre del vuelo de la reserva.
     *  Intenta getFlightName(), si no usa getFlight().getName(). */
    private String extractBookFlightName(BookFlightDTO b) {
        // SOLO campo directo si existe en el DTO; no navegues getFlight()
        String direct = callGetterString(b, "getFlightName"); // si tu DTO lo trae
        return direct; // si no existe, quedará "", y está bien
    }
    // ---------- Utils (texto/reflexión) ----------
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
    private List<?> callGetterListRaw(Object target, String... candidates) {
        Object v = callGetter(target, candidates);
        return (v instanceof List<?> list) ? list : List.of();
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
    private List<String> safeListStrings(List<?> in) {
        List<String> out = new ArrayList<>();
        for (Object o : in) if (o != null) out.add(String.valueOf(o));
        return out;
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

    private void mostrarDialogoDetalle(String titulo, JComponent contenido) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        JDialog d = new JDialog(owner, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        d.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        d.getContentPane().add(contenido);
        d.setSize(520, 420);
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
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
                        panel13.setLayout(new FlowLayout());

                        //---- label5 ----
                        label5.setText("Buscar");
                        label5.setFont(new Font("Inter", Font.PLAIN, 12));
                        label5.setForeground(new Color(0x5f6368));
                        panel13.add(label5);

                        //---- cmbTipo ----
                        cmbTipo.setPreferredSize(new Dimension(110, 28));
                        cmbTipo.setMaximumRowCount(10);
                        panel13.add(cmbTipo);
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
                            panel9.setLayout(new FlowLayout());

                            //---- btnDetalleRuta ----
                            btnDetalleRuta.setText("Ver detalle ruta");
                            panel9.add(btnDetalleRuta);
                        }
                        airline.add(panel9, BorderLayout.NORTH);

                        //======== scrollPane3 ========
                        {
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
    // Generated using JFormDesigner Evaluation license - Juan Aparicio Quián Rodríguez
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
