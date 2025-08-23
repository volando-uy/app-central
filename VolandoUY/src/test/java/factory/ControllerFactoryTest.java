package factory;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
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
    void getModelMapper_shouldReturnSingletonInstance() {
        // GIVEN
        ModelMapper mapper1 = ControllerFactory.getModelMapper();
        ModelMapper mapper2 = ControllerFactory.getModelMapper();

        // THEN
        assertNotNull(mapper1);
        assertSame(mapper1, mapper2);
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

//    @Test
//    void sharedModelMapper_shouldBeSameAcrossAllServices() {
//        // GIVEN
//        ModelMapper mapperFromUser = ControllerFactory.getUserService().getModelMapper();
//        ModelMapper mapperFromFlight = ControllerFactory.getFlightController().getFlightService().getModelMapper();
//        ModelMapper mapperFromCity = ControllerFactory.getCityService().getModelMapper();
//
//        // THEN
//        assertSame(mapperFromUser, mapperFromFlight);
//        assertSame(mapperFromUser, mapperFromCity);
//    }
}
