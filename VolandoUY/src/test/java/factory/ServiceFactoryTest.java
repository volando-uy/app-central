package factory;

import domain.services.airport.IAirportService;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flight.IFlightService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {

    @Test
    void getUserService_shouldReturnSingletonInstance() {
        // GIVEN
        IUserService service1 = ServiceFactory.getUserService();
        IUserService service2 = ServiceFactory.getUserService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getFlightRouteService_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRouteService service1 = ServiceFactory.getFlightRouteService();
        IFlightRouteService service2 = ServiceFactory.getFlightRouteService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getFlightService_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightService service1 = ServiceFactory.getFlightService();
        IFlightService service2 = ServiceFactory.getFlightService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getCategoryService_shouldReturnSingletonInstance() {
        // GIVEN
        ICategoryService service1 = ServiceFactory.getCategoryService();
        ICategoryService service2 = ServiceFactory.getCategoryService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getCityService_shouldReturnSingletonInstance() {
        // GIVEN
        ICityService service1 = ServiceFactory.getCityService();
        ICityService service2 = ServiceFactory.getCityService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getFlightRoutePackageService_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRoutePackageService service1 = ServiceFactory.getFlightRoutePackageService();
        IFlightRoutePackageService service2 = ServiceFactory.getFlightRoutePackageService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getAirportService_shouldReturnSingletonInstance() {
        // GIVEN
        IAirportService service1 = ServiceFactory.getAirportService();
        IAirportService service2 = ServiceFactory.getAirportService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }
}
