package shared.constants;

import java.util.Locale;

public class ErrorMessages {
    // ================================General errors==============================
    public static final String ERR_CAMPO_OBLIGATORIO = "El campo %s es obligatorio";
    public static final String ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO = "La cantidad debe ser mayor a cero";
    public static final String ERR_ACCEDER_CAMPO = "Error al acceder al campo %s";
    // ================================Fin General errors==============================


    // ================================User errors==============================
    public static final String ERR_USER_EXISTS = "El usuario %s ya existe";
    public static final String ERR_USER_MAIL_ALREADY_IN_USE = "Ya existe un usuario con el correo %s";
    public static final String ERR_USER_NOT_FOUND = "El usuario %s no fue encontrado";
    public static final String ERR_USER_NOT_SUPPORTED = "El tipo de usuario no es soportado";
    public static final String ERR_CUSTOMER_NOT_FOUND = "El cliente %s no fue encontrado";
    public static final String ERR_WEB_FORMAT = "El formato de la web no es válido";
    public static final String ERR_FORMATO_EMAIL_INVALIDO = "Formato de email invalido";
    public static final String ERR_AIRLINE_NOT_FOUND = "La aerolínea %s no fue encontrada";
    public static final String ERR_BIRTHDAY_IN_PAST = "La fecha de nacimiento debe ser en el pasado";


    // ================================Fin User errors==============================


    // ================================Category errors==============================
    public static final String ERR_CATEGORY_EXISTS = "La categoría %s ya existe";
    public static final String ERR_CATEGORY_NOT_FOUND = "La categoría %s no fue encontrada";
    // ================================Fin Category errors==============================


    // ================================City errors==============================
    public static final String ERR_CITY_NOT_FOUND = "La ciudad %s no fue encontrada";
    public static final String ERR_CITY_ALREADY_EXISTS = "La ciudad %s ya existe";
    public static final String ERROR_MIN_LATITUDE = "Latitud mínima -90";
    public static final String ERROR_MAX_LATITUDE = "Latitud máxima 90";
    public static final String ERROR_MIN_LONGITUDE = "Longitud mínima -180";
    public static final String ERROR_MAX_LONGITUDE = "Longitud máxima 180";
    public static final String CITY_NOT_FOUND = "No se puede agregar un aeropuerto a una ciudad nula";

    // ================================Fin City errors==============================


    // ================================Airport errors==============================
    public static final String ERR_AIRPORT_NOT_FOUND = "El aeropuerto con código %s no fue encontrado";
    public static final String ERR_AIRPORT_NAME_MANDATORY = "El nombre del aeropuerto es obligatorio";
    public static final String ERR_AIRPORT_CODE_ALREADY_EXISTS = "Ya existe un aeropuerto con el código: %s";
    public static final String ERR_AIRPORT_CODE_FORMAT = "El código IATA debe ser 3 letras mayúsculas";
    public static final String ERR_AIRPORT_CODE_MANDATORY = "El código IATA es obligatorio";
    // ================================Fin Airport errors==============================


    // ================================Flight errors==============================
    public static final String ERR_FLIGHT_NOT_FOUND = "El vuelo con nombre %s no fue encontrado";
    public static final String ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND = "El paquete de ruta del vuelo con nombre %s no fue encontrado";
    public static final String ERR_FLIGHT_ROUTE_NOT_FOUND = "La ruta del vuelo con nombre %s no fue encontrada";
    // ================================Fin Flight errors==============================



    // ================================Vuelo errors==============================
    public static final String ERR_FLIGHT_ALREADY_EXISTS = "El vuelo %s ya existe";
    public static final String ERR_FLIGHT_DEPARTURE_FUTURE = "La salida debe ser en el futuro";
    public static final String ERR_FLIGHT_DURATION_POSITIVE = "La duración (min) debe ser mayor a 0";
    public static final String ERR_FLIGHT_MAX_BUSINESS_SEATS = "El número máximo de asientos ejecutivos debe estar en el rango 0 - 500";
    public static final String ERR_FLIGHT_MAX_ECONOMY_SEATS = "El número máximo de asientos turista debe estar en el rango 0 - 500";
    // ================================Fin Vuelo errors==============================


    // ================================Ruta Vuelo errors==============================
    public static final String ERR_FLIGHT_ROUTE_ALREADY_EXISTS = "Ya existe una ruta con el nombre: %s";
    // ================================Fin Ruta Vuelo errors==============================


    // ================================Paquete Ruta Vuelo errors==============================
    public static final String ERR_PACKAGE_ALREADY_EXISTS = "El paquete %s ya existe";
    public static final String ERR_INVALID_SEAT_TYPE = "El tipo de asiento %s no es válido";
    public static final String ERR_FLIGHT_ROUTE_NOT_BELONGS_TO_AIRLINE = "La ruta de vuelo %s no pertenece a la aerolínea %s";
    public static final String ERR_NO_FLIGHT_ROUTE_PACKAGES_FOUND = "No se encontraron paquetes de ruta de vuelo para este paquete";
    // ================================Fin Paquete Ruta Vuelo errors==============================



    // ================================Inicio Tickets==============================
    public static final String ERROR_TICKET_NOT_FOUND = "Ticket no encontrado";
    // ================================Fin Tickets==============================



}
