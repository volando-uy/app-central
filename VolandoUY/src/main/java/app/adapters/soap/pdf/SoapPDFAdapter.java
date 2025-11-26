package app.adapters.soap.pdf;

import app.adapters.soap.BaseSoapAdapter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import controllers.auth.IAuthController;
import controllers.booking.IBookingController;
import controllers.buypackage.IBuyPackageController;
import controllers.flightroute.IFlightRouteController;
import controllers.flightroutepackage.IFlightRoutePackageController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.models.luggage.EnumEquipajeBasico;
import domain.models.luggage.EnumEquipajeExtra;
import factory.ControllerFactory;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SoapPDFAdapter extends BaseSoapAdapter {
    IBuyPackageController buyPackageController = ControllerFactory.getBuyPackageController();
    IBookingController bookingController = ControllerFactory.getBookingController();
    ITicketController ticketController = ControllerFactory.getTicketController();
    IAuthController authController = ControllerFactory.getAuthController();
    IFlightRoutePackageController flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();
    IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();
    IUserController userController = ControllerFactory.getUserController();


    @Override
    protected String getServiceName() {
        return "pdfService";
    }

    @WebMethod
    public String getPackagePDFBase64(@WebParam(name = "packageId") Long packageId,
                                      @WebParam(name = "token") String token) {
        try {
            // Validar token y obtener nickname
            String nicknameFromToken = authController.getNicknameFromToken(token);
            if (nicknameFromToken == null || nicknameFromToken.isBlank()) {
                throw createSoapFault("Token inválido o expirado.");
            }

            // Obtener detalles del paquete
            BuyPackageDTO buyPackage = buyPackageController.getBuyPackageDetailsById(packageId);
            if (buyPackage == null) {
                throw createSoapFault("No se encontró el paquete con ID: " + packageId);
            }

            // Validar que el paquete pertenezca al usuario autenticado
            if (!nicknameFromToken.equals(buyPackage.getCustomerNickname())) {
                throw createSoapFault("Acceso denegado: el paquete no pertenece al usuario.");
            }

            // Obtener detalles del paquete de rutas
            FlightRoutePackageDTO flightRoutePackage = flightRoutePackageController
                    .getFlightRoutePackageDetailsByName(buyPackage.getFlightRoutePackageName());

            List<FlightRouteDTO> flightRoutes = flightRouteController
                    .getAllFlightRoutesDetailsByPackageName(flightRoutePackage.getName());

            // Obtener aerolíneas relacionadas
            List<AirlineDTO> airlineDTOs = flightRoutes.stream()
                    .map(route -> {
                        try {
                            return userController.getAirlineDetailsByNickname(route.getAirlineNickname());
                        } catch (Exception e) {
                            throw new RuntimeException("No se pudo obtener aerolínea: " + route.getAirlineNickname(), e);
                        }
                    })
                    .toList();

            // Comenzar generación del PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Título
            Paragraph title = new Paragraph("Detalle del Paquete #" + packageId, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaFormateada = buyPackage.getCreatedAt().format(formatter);
            // Datos generales del paquete
            document.add(new Paragraph("Cliente: " + buyPackage.getCustomerNickname(), bodyFont));
            document.add(new Paragraph("Fecha de compra: " + fechaFormateada, bodyFont));
            document.add(new Paragraph("Precio total: $" + String.format("%,2f", buyPackage.getTotalPrice()), bodyFont));
            document.add(new Paragraph("Precio total: $" + String.format(Locale.US, "%.2f", buyPackage.getTotalPrice()), bodyFont));
            document.add(new Paragraph("Descuento aplicado: " + String.format("%.2f", flightRoutePackage.getDiscount()) + "%", bodyFont));
            document.add(Chunk.NEWLINE);

            document.add(new LineSeparator());

            // Detalles de rutas incluidas
            document.add(new Paragraph("Rutas incluidas:", sectionFont));
            for (FlightRouteDTO route : flightRoutes) {
                document.add(new Paragraph("• Ruta: " + route.getName(), bodyFont));
                document.add(new Paragraph("   - Origen: " + route.getOriginAeroCode(), bodyFont));
                document.add(new Paragraph("   - Destino: " + route.getDestinationAeroCode(), bodyFont));
                document.add(new Paragraph("   - Precio turista: $" + String.format("%.2f", route.getPriceTouristClass()), bodyFont));
                document.add(new Paragraph("   - Aerolínea: " + route.getAirlineNickname(), bodyFont));
                document.add(Chunk.NEWLINE);
            }

            document.add(new LineSeparator());

            // Detalles de aerolíneas participantes
            document.add(new Paragraph("Aerolíneas involucradas:", sectionFont));
            for (AirlineDTO airline : airlineDTOs) {
                document.add(new Paragraph("• " + airline.getName() + " (" + airline.getNickname() + ")", bodyFont));
                document.add(new Paragraph("   - Email: " + airline.getMail(), bodyFont));
                document.add(new Paragraph("   - Web: " + airline.getWeb(), bodyFont));
                document.add(Chunk.NEWLINE);
            }

            // Footer
            Paragraph footer = new Paragraph("Gracias por elegir Volando.uy", bodyFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();

            byte[] pdfBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(pdfBytes);

        } catch (SOAPFaultException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw createSoapFault("Error interno al generar el PDF del paquete.");
        }
    }

    @WebMethod
    public String getBookflightPDFBase64(@WebParam(name = "bookingId") Long bookingId, @WebParam(name = "token") String token) {
        try {
            String nicknameFromToken = authController.getNicknameFromToken(token);
            // Obtener la reserva
            BookFlightDTO dto = bookingController.getBookFlightDetailsById(bookingId);

            // Verificar que la reserva pertenezca al usuario
            if (dto == null || !nicknameFromToken.equals(dto.getCustomerNickname())) {
                throw new SecurityException("Acceso denegado a la reserva.");
            }


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();

            PdfWriter.getInstance(document, baos);
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Título
            Paragraph title = new Paragraph("Detalle de Reserva de Vuelo", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Datos de la reserva
            document.add(new Paragraph("Reserva #: " + dto.getId(), bodyFont));
            document.add(new Paragraph("Cliente: " + dto.getCustomerNickname(), bodyFont));
            document.add(new Paragraph("Fecha de reserva: " + dto.getCreatedAt().toString(), bodyFont));
            document.add(new Paragraph("Tipo de asiento: " + dto.getSeatType(), bodyFont));
            document.add(new Paragraph("Precio total: $" + String.format("%.2f", dto.getTotalPrice()), bodyFont));

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());

            // Tickets asociados (si hay)
            if (dto.getTicketIds() != null && !dto.getTicketIds().isEmpty()) {
//                document.add(new Paragraph("Tickets incluidos:", sectionFont));
                for (Long ticketId : dto.getTicketIds()) {
                    // Tabla con los detalles de los tickets
                    PdfPTable table = new PdfPTable(6); // columnas: ID, Pasajero, Documento, Asiento, Tipo Equipaje, Peso
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(10);

                    Stream.of("Ticket ID", "Pasajero", "Documento", "Asiento", "Tipo de Equipaje", "Peso (kg)")
                            .forEach(header -> {
                                PdfPCell cell = new PdfPCell(new Phrase(header, sectionFont));
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                table.addCell(cell);
                            });

                    for (Long tid : dto.getTicketIds()) {
                        TicketDTO ticket = ticketController.getTicketDetailsById(tid);
                        String pasajero = ticket.getName() + " " + ticket.getSurname();
                        String doc = ticket.getDocType() + " - " + ticket.getNumDoc();
                        String asiento = ticket.getSeatNumber() != null ? ticket.getSeatNumber() : "No asignado";

                        boolean addedRow = false;

                        // Equipaje básico
                        if (ticket.getBasicLuggages() != null && !ticket.getBasicLuggages().isEmpty()) {
                            for (BasicLuggageDTO basic : ticket.getBasicLuggages()) {
                                table.addCell(String.valueOf(ticket.getId()));
                                table.addCell(pasajero);
                                table.addCell(doc);
                                table.addCell(asiento);
                                table.addCell(translateBasic(basic.getCategory()));
                                table.addCell(String.format("%.2f", basic.getWeight()));
                                addedRow = true;
                            }
                        }

                        // Equipaje extra
                        if (ticket.getExtraLuggages() != null && !ticket.getExtraLuggages().isEmpty()) {
                            for (ExtraLuggageDTO extra : ticket.getExtraLuggages()) {
                                table.addCell(String.valueOf(ticket.getId()));
                                table.addCell(pasajero);
                                table.addCell(doc);
                                table.addCell(asiento);
                                table.addCell(translateExtra(extra.getCategory()));
                                table.addCell(String.format("%.2f", extra.getWeight()));
                                addedRow = true;
                            }
                        }

                        // Sin equipaje
                        if (!addedRow) {
                            table.addCell(String.valueOf(ticket.getId()));
                            table.addCell(pasajero);
                            table.addCell(doc);
                            table.addCell(asiento);
                            table.addCell("Sin equipaje");
                            table.addCell("-");
                        }
                    }

// Agregamos tabla al documento
                    document.add(new Paragraph("Tickets incluidos:", sectionFont));
                    document.add(table);


                }
            }

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());

            // Footer
            Paragraph footer = new Paragraph("Gracias por elegir Volando.uy", bodyFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();

            byte[] pdfBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(pdfBytes);

        }catch (SOAPFaultException e) {
            throw e; // importante: relanzar, no ocultar
        } catch (Exception e) {
            e.printStackTrace();
            throw createSoapFault("Error interno al generar el PDF.");
        }
    }
    private SOAPFaultException createSoapFault(String message) {
        try {
            SOAPFactory factory = SOAPFactory.newInstance();
            SOAPFault fault = factory.createFault(message, new QName("Server"));
            return new SOAPFaultException(fault);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear SOAPFaultException", e);
        }
    }
    private String translateBasic(EnumEquipajeBasico tipo) {
        if (tipo == null) return "Sin equipaje";
        return switch (tipo) {
            case BOLSO -> "Bolso";
            case MOCHILA -> "Mochila";
            case CARRY_ON -> "Carry On";
        };
    }

    private String translateExtra(EnumEquipajeExtra tipo) {
        if (tipo == null) return "Sin equipaje";
        return switch (tipo) {
            case MALETA -> "Maleta";
            case ANIMAL -> "Animal";
        };
    }

}
