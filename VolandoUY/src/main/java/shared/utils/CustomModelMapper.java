package shared.utils;

import domain.dtos.airport.AirportDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.seat.SeatDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.*;
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
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import shared.constants.ErrorMessages;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class CustomModelMapper extends ModelMapper {

    public CustomModelMapper() {
        Converter<PersistentBag, List<?>> persistentBagToListConverter = new Converter<>() {
            @Override
            public java.util.List<?> convert(MappingContext<PersistentBag, java.util.List<?>> context) {
                return new java.util.ArrayList<>(context.getSource());
            }
        };
        this.addConverter(persistentBagToListConverter);
    }

    public CustomerDTO mapFullCustomer(Customer customer) {
        CustomerDTO customerDTO = this.map(customer, CustomerDTO.class);
        if (customer.getBookedFlights() != null) {
            customerDTO.setBookFlightsIds(customer.getBookedFlights()
                    .stream()
                    .map(BookFlight::getId)
                    .toList());
        }
        if (customer.getBoughtPackages() != null) {
            customerDTO.setBoughtPackagesIds(customer.getBoughtPackages()
                    .stream()
                    .map(BuyPackage::getId)
                    .toList());
        }
        return customerDTO;
    }

    public BaseCustomerDTO mapBaseCustomer(Customer customer) {
        return this.map(customer, BaseCustomerDTO.class);
    }

    public AirlineDTO mapFullAirline(Airline airline) {
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


    public BaseAirlineDTO mapBaseAirline(Airline airline) {
        return this.map(airline, BaseAirlineDTO.class);
    }

    public FlightDTO mapFullFlight(Flight flight) {
        FlightDTO flightDTO = this.map(flight, FlightDTO.class);
        flightDTO.setAirlineNickname(flight.getAirline() != null ? flight.getAirline().getNickname() : null);
        flightDTO.setFlightRouteName(flight.getFlightRoute() != null ? flight.getFlightRoute().getName() : null);
        return flightDTO;
    }

    public FlightRouteDTO mapFullFlightRoute(FlightRoute flightRoute) {
        FlightRouteDTO flightRouteDTO = this.map(flightRoute, FlightRouteDTO.class);
        flightRouteDTO.setOriginCityName(flightRoute.getOriginCity() != null ? flightRoute.getOriginCity().getName() : null);
        flightRouteDTO.setDestinationCityName(flightRoute.getDestinationCity() != null ? flightRoute.getDestinationCity().getName() : null);
        flightRouteDTO.setAirlineNickname(flightRoute.getAirline() != null ? flightRoute.getAirline().getNickname() : null);
        flightRouteDTO.setCategories(
                new ArrayList<>(flightRoute.getCategories()).stream().map(Category::getName).toList()
        );
        flightRouteDTO.setFlightsNames(
                new ArrayList<>(flightRoute.getFlights()).stream().map(Flight::getName).toList()
        );
        return flightRouteDTO;
    }

    public FlightRouteDTO mapBaseFlightRoute(FlightRoute flightRoute) {
        BaseFlightRouteDTO bfrdto = this.map(flightRoute, BaseFlightRouteDTO.class);
        return this.map(bfrdto, FlightRouteDTO.class);
    }

    public BuyPackageDTO mapFullBuyPackage(BuyPackage buyPackage) {
        BuyPackageDTO buyPackageDTO = this.map(buyPackage, BuyPackageDTO.class);
        buyPackageDTO.setCustomerNickname(buyPackage.getCustomer() != null ? buyPackage.getCustomer().getNickname() : null);
        buyPackageDTO.setFlightRoutePackageName(buyPackage.getFlightRoutePackage() != null ? buyPackage.getFlightRoutePackage().getName() : null);
        buyPackageDTO.setBookFlightsIds(buyPackage.getBookFlights().stream().map(BookFlight::getId).toList());
        return buyPackageDTO;
    }

    public BookFlightDTO mapFullBookFlight(BookFlight bookFlight) {
        BookFlightDTO bookFlightDTO = this.map(bookFlight, BookFlightDTO.class);
        bookFlightDTO.setCustomerNickname(bookFlight.getCustomer() != null ? bookFlight.getCustomer().getNickname() : null);
        bookFlightDTO.setTicketIds(bookFlight.getTickets().stream().map(Ticket::getId).toList());
        return bookFlightDTO;
    }

    public BasicLuggageDTO mapFullBasicLuggage(BasicLuggage basicLuggage) {
        BasicLuggageDTO basicLuggageDTO = this.map(basicLuggage, BasicLuggageDTO.class);
        return basicLuggageDTO;
    }

    public ExtraLuggageDTO mapFullExtraLuggage(ExtraLuggage extraLuggage) {
        ExtraLuggageDTO extraLuggageDTO = this.map(extraLuggage, ExtraLuggageDTO.class);
        return extraLuggageDTO;
    }

    public TicketDTO mapFullTicket(Ticket ticket) {
        TicketDTO ticketDTO = this.map(ticket, TicketDTO.class);
        if (ticket.getBasicLuggages() != null) {
            ticketDTO.setBasicLuggages(ticket.getBasicLuggages().stream().map(this::mapFullBasicLuggage).collect(toList()));
        }
        if (ticket.getExtraLuggages() != null) {
            ticketDTO.setExtraLuggages(ticket.getExtraLuggages().stream().map(this::mapFullExtraLuggage).collect(toList()));
        }
        ticketDTO.setSeatNumber(ticket.getSeat().getNumber());
        ticketDTO.setBookFlightId(ticket.getBookFlight() != null ? ticket.getBookFlight().getId() : null);
        return ticketDTO;
    }

    public SeatDTO mapFullSeat(Seat seat) {
        SeatDTO seatDTO = this.map(seat, SeatDTO.class);
        seatDTO.setFlightName(seat.getFlight() != null ? seat.getFlight().getName() : null);
        seatDTO.setTicketId(seat.getTicket() != null ? seat.getTicket().getId() : null);
        return seatDTO;
    }

    public AirportDTO mapFullAirport(Airport airport) {
        AirportDTO airportDTO = this.map(airport, AirportDTO.class);
        airportDTO.setCityName(airport.getCity() != null ? airport.getCity().getName() : null);
        return airportDTO;
    }

    public CityDTO mapFullCity(City city) {
        CityDTO cityDTO = this.map(city, CityDTO.class);
        cityDTO.setAirportNames(city.getAirports().stream().map(Airport::getName).toList());
        return cityDTO;
    }

    public FlightRoutePackageDTO mapFullFlightRoutePackage(FlightRoutePackage flightRoutePackage) {
        FlightRoutePackageDTO flightRoutePackageDTO = this.map(flightRoutePackage, FlightRoutePackageDTO.class);
        flightRoutePackageDTO.setFlightRouteNames(flightRoutePackage.getFlightRoutes().stream().map(FlightRoute::getName).toList());
        flightRoutePackageDTO.setBuyPackages(flightRoutePackage.getBuyPackages().stream().map(BuyPackage::getId).toList());
        return flightRoutePackageDTO;
    }


    public UserDTO mapFullUser(User user) {
        if (user instanceof Customer) {
            return this.mapFullCustomer((Customer) user);
        } else if (user instanceof Airline) {
            return this.mapFullAirline((Airline) user);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
        }
    }

    public UserDTO mapUser(User user) {
        if (user instanceof Customer) {
            return this.map(user, BaseCustomerDTO.class);
        } else if (user instanceof Airline) {
            return this.map(user, BaseAirlineDTO.class);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
        }
    }

    public User mapUserDTO(UserDTO updatedUserDTO) {
        if (updatedUserDTO instanceof BaseCustomerDTO customerDTO) {
            return this.map(customerDTO, Customer.class);
        } else if (updatedUserDTO instanceof BaseAirlineDTO airlineDTO) {
            return this.map(airlineDTO, Airline.class);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
        }
    }


}
