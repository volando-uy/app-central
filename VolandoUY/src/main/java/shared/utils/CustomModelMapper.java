package shared.utils;

import domain.dtos.airport.AirportDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.seat.SeatDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.airport.Airport;
import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class CustomModelMapper extends ModelMapper {

    public CustomerDTO mapCustomer(Customer customer) {
        CustomerDTO customerDTO = this.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    public AirlineDTO mapAirline(Airline airline) {
        AirlineDTO airlineDTO = this.map(airline, AirlineDTO.class);
        airlineDTO.setFlightRoutesNames(airline.getFlightRoutes()
                .stream()
                .map(FlightRoute::getName)
                .toList());
        airlineDTO.setFlightsNames(airline.getFlights()
                .stream()
                .map(Flight::getName)
                .toList());
        return airlineDTO;
    }

    public FlightDTO mapFlight(Flight flight) {
        FlightDTO flightDTO = this.map(flight, FlightDTO.class);
        flightDTO.setAirlineNickname(flight.getAirline().getName());
        flightDTO.setFlightRouteName(flight.getFlightRoute().getName());
        return flightDTO;
    }

    public FlightRouteDTO mapFlightRoute(FlightRoute flightRoute) {
        FlightRouteDTO flightRouteDTO = this.map(flightRoute, FlightRouteDTO.class);
        flightRouteDTO.setOriginCityName(flightRoute.getOriginCity().getName());
        flightRouteDTO.setDestinationCityName(flightRoute.getDestinationCity().getName());
        flightRouteDTO.setAirlineNickname(flightRoute.getAirline().getNickname());
        flightRouteDTO.setCategories(flightRoute.getCategories().stream().map(Category::getName).toList());
        flightRouteDTO.setFlightsNames(flightRoute.getFlights().stream().map(Flight::getName).toList());
        return flightRouteDTO;
    }

    public CategoryDTO mapCategory(Category category) {
        CategoryDTO categoryDTO = this.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    public BuyPackageDTO mapBuyPackage(BuyPackage buyPackage) {
        BuyPackageDTO buyPackageDTO = this.map(buyPackage, BuyPackageDTO.class);
        buyPackageDTO.setCustomerNickname(buyPackage.getCustomer().getNickname());
        buyPackageDTO.setFlightRoutePackageName(buyPackage.getFlightRoutePackage().getName());
        buyPackageDTO.setBookFlightsIds(buyPackage.getBookFlights().stream().map(BookFlight::getId).toList());
        return buyPackageDTO;
    }

    public BookFlightDTO mapBookFlight(BookFlight bookFlight) {
        BookFlightDTO bookFlightDTO = this.map(bookFlight, BookFlightDTO.class);
        bookFlightDTO.setCustomerNickname(bookFlight.getCustomer().getNickname());
        return bookFlightDTO;
    }

    public BasicLuggageDTO mapBasicLuggage(BasicLuggage basicLuggage) {
        BasicLuggageDTO basicLuggageDTO = this.map(basicLuggage, BasicLuggageDTO.class);
        return basicLuggageDTO;
    }

    public ExtraLuggageDTO mapExtraLuggage(ExtraLuggage extraLuggage) {
        ExtraLuggageDTO extraLuggageDTO = this.map(extraLuggage, ExtraLuggageDTO.class);
        return extraLuggageDTO;
    }

    public TicketDTO mapTicket(Ticket ticket) {
        TicketDTO ticketDTO = this.map(ticket, TicketDTO.class);
        if (ticket.getBasicLuggages() != null) {
            ticketDTO.setBasicLuggages(ticket.getBasicLuggages().stream().map(this::mapBasicLuggage).collect(toList()));
        }
        if (ticket.getExtraLuggages() != null) {
            ticketDTO.setExtraLuggages(ticket.getExtraLuggages().stream().map(this::mapExtraLuggage).collect(toList()));
        }
        ticketDTO.setSeatNumber(ticket.getSeat().getNumber());
        ticketDTO.setBookFlightId(ticket.getBookFlight().getId());
        return ticketDTO;
    }

    public SeatDTO mapSeat(Seat seat) {
        SeatDTO seatDTO = this.map(seat, SeatDTO.class);
        seatDTO.setFlightName(seat.getFlight().getName());
        seatDTO.setTicketId(seat.getTicket().getId());
        return seatDTO;
    }

    public AirportDTO mapAirport(Airport airport) {
        AirportDTO airportDTO = this.map(airport, AirportDTO.class);
        airportDTO.setCityName(airport.getCity().getName());
        return airportDTO;
    }

    public CityDTO mapCity(City city) {
        CityDTO cityDTO = this.map(city, CityDTO.class);
        cityDTO.setAirportNames(city.getAirports().stream().map(Airport::getName).toList());
        return cityDTO;
    }

    public FlightRoutePackageDTO mapFlightRoutePackage(FlightRoutePackage flightRoutePackage) {
        FlightRoutePackageDTO flightRoutePackageDTO = this.map(flightRoutePackage, FlightRoutePackageDTO.class);
        flightRoutePackageDTO.setFlightRouteNames(flightRoutePackage.getFlightRoutes().stream().map(FlightRoute::getName).toList());
        return flightRoutePackageDTO;
    }


    public UserDTO mapUser(User user) {
        if (user instanceof Customer) {
            return this.mapCustomer((Customer) user);
        } else if (user instanceof Airline) {
            return this.mapAirline((Airline) user);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
        }
    }

    public User mapUserDTO(UserDTO updatedUserDTO) {
        if (updatedUserDTO instanceof CustomerDTO customerDTO) {
            return this.map(customerDTO, Customer.class);
        } else if (updatedUserDTO instanceof AirlineDTO airlineDTO) {
            return this.map(airlineDTO, Airline.class);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
        }
    }
}
