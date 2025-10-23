package factory;

import controllers.airport.IAirportController;
import controllers.auth.IAuthController;
import controllers.booking.IBookingController;
import controllers.buypackage.IBuyPackageController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightroute.IFlightRouteController;
import controllers.flightroutepackage.IFlightRoutePackageController;
import controllers.seat.ISeatController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flightroute.IFlightRouteService;
import domain.services.flightroutepackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class ControllerFactoryTest {

    @Test
    void getUserController_shouldReturnSingletonInstance() {
        // GIVEN
        IUserController instance1 = ControllerFactory.getUserController();
        IUserController instance2 = ControllerFactory.getUserController();

        // THEN
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

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
    void getFlightRouteController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRouteController controller1 = ControllerFactory.getFlightRouteController();
        IFlightRouteController controller2 = ControllerFactory.getFlightRouteController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
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
    void getFlightController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightController controller1 = ControllerFactory.getFlightController();
        IFlightController controller2 = ControllerFactory.getFlightController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
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
    void getCategoryController_shouldReturnSingletonInstance() {
        // GIVEN
        ICategoryController controller1 = ControllerFactory.getCategoryController();
        ICategoryController controller2 = ControllerFactory.getCategoryController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
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
    void getFlightRoutePackageController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRoutePackageController controller1 = ControllerFactory.getFlightRoutePackageController();
        IFlightRoutePackageController controller2 = ControllerFactory.getFlightRoutePackageController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
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
    void getCityController_shouldReturnSingletonInstance() {
        // GIVEN
        ICityController controller1 = ControllerFactory.getCityController();
        ICityController controller2 = ControllerFactory.getCityController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getAirportController_shouldReturnSingletonInstance() {
        // GIVEN
        IAirportController controller1 = ControllerFactory.getAirportController();
        IAirportController controller2 = ControllerFactory.getAirportController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }


    @Test
    void getBookingController_shouldReturnSingletonInstance() {
        IBookingController controller1 = ControllerFactory.getBookingController();
        IBookingController controller2 = ControllerFactory.getBookingController();

        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getBuyPackageController_shouldReturnSingletonInstance() {
        IBuyPackageController controller1 = ControllerFactory.getBuyPackageController();
        IBuyPackageController controller2 = ControllerFactory.getBuyPackageController();

        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getTicketController_shouldReturnSingletonInstance() {
        ITicketController controller1 = ControllerFactory.getTicketController();
        ITicketController controller2 = ControllerFactory.getTicketController();

        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getSeatController_shouldReturnSingletonInstance() {
        ISeatController controller1 = ControllerFactory.getSeatController();
        ISeatController controller2 = ControllerFactory.getSeatController();

        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getAuthController_shouldReturnSingletonInstance() {
        IAuthController controller1 = ControllerFactory.getAuthController();
        IAuthController controller2 = ControllerFactory.getAuthController();

        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }
    @Test
    void sharedModelMapper_shouldBeSameAcrossAllServices() {
        // GIVEN
        ModelMapper mapperFromUser = ControllerFactory.getCustomModelMapper();
        ModelMapper mapperFromFlight = ControllerFactory.getCustomModelMapper();
        ModelMapper mapperFromCity = ControllerFactory.getCustomModelMapper();

        // THEN
        assertSame(mapperFromUser, mapperFromFlight);
        assertSame(mapperFromUser, mapperFromCity);
        assertSame(mapperFromFlight, mapperFromCity);
    }
}
