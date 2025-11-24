package app.adapters.soap;

import app.adapters.soap.airport.AirportSoapAdapter;
import app.adapters.soap.auth.AuthSoapAdapter;
import app.adapters.soap.booking.BookingSoapAdapter;
import app.adapters.soap.buypackage.BuyPackageSoapAdapter;
import app.adapters.soap.category.CategorySoapAdapter;
import app.adapters.soap.city.CitySoapAdapter;
import app.adapters.soap.constants.ConstantsSoapAdapter;
import app.adapters.soap.countries.SoapCountriesAdapter;
import app.adapters.soap.flight.FlightSoapAdapter;
import app.adapters.soap.flightroute.FlightRouteSoapAdapter;
import app.adapters.soap.flightroutepackage.FlightRoutePackageSoapAdapter;
import app.adapters.soap.images.ImagesSoapAdapter;
import app.adapters.soap.seat.SeatSoapAdapter;
import app.adapters.soap.ticket.TicketSoapAdapter;
import app.adapters.soap.user.UserSoapAdapter;
import factory.ControllerFactory;
import shared.constants.Images;

import java.util.List;

public class SoapServicePublisher {
    public static void publicarTodos() {
        List<BaseSoapAdapter> adapters = List.of(
                new AirportSoapAdapter(ControllerFactory.getAirportController()),
                new AuthSoapAdapter(ControllerFactory.getAuthController()),
                new BookingSoapAdapter(ControllerFactory.getBookingController()),
                new BuyPackageSoapAdapter(ControllerFactory.getBuyPackageController()),
                new CategorySoapAdapter(ControllerFactory.getCategoryController()),
                new CitySoapAdapter(ControllerFactory.getCityController()),
                new FlightSoapAdapter(ControllerFactory.getFlightController()),
                new FlightRouteSoapAdapter(ControllerFactory.getFlightRouteController()),
                new FlightRoutePackageSoapAdapter(ControllerFactory.getFlightRoutePackageController()),
                new SeatSoapAdapter(ControllerFactory.getSeatController()),
                new TicketSoapAdapter(ControllerFactory.getTicketController()),
                new UserSoapAdapter(ControllerFactory.getUserController()),
                new ImagesSoapAdapter(),
                new ConstantsSoapAdapter(),
                new SoapCountriesAdapter()
        );

        adapters.forEach(BaseSoapAdapter::publish);
    }
}
