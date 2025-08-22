package factory;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.flight.IFlightService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class FactoryControllerTest {

    @Test
    void getUserController_shouldReturnSingletonInstance() {
        // GIVEN
        IUserController instance1 = FactoryController.getUserController();
        IUserController instance2 = FactoryController.getUserController();

        // THEN
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    @Test
    void getUserService_shouldReturnSingletonInstance() {
        // GIVEN
        IUserService service1 = FactoryController.getUserService();
        IUserService service2 = FactoryController.getUserService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getModelMapper_shouldReturnSingletonInstance() {
        // GIVEN
        ModelMapper mapper1 = FactoryController.getModelMapper();
        ModelMapper mapper2 = FactoryController.getModelMapper();

        // THEN
        assertNotNull(mapper1);
        assertSame(mapper1, mapper2);
    }

    @Test
    void getFlightRouteController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRouteController controller1 = FactoryController.getFlightRouteController();
        IFlightRouteController controller2 = FactoryController.getFlightRouteController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getFlightRouteService_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRouteService service1 = FactoryController.getFlightRouteService();
        IFlightRouteService service2 = FactoryController.getFlightRouteService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getFlightController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightController controller1 = FactoryController.getFlightController();
        IFlightController controller2 = FactoryController.getFlightController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getCategoryService_shouldReturnSingletonInstance() {
        // GIVEN
        ICategoryService service1 = FactoryController.getCategoryService();
        ICategoryService service2 = FactoryController.getCategoryService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getCategoryController_shouldReturnSingletonInstance() {
        // GIVEN
        ICategoryController controller1 = FactoryController.getCategoryController();
        ICategoryController controller2 = FactoryController.getCategoryController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getFlightRoutePackageService_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRoutePackageService service1 = FactoryController.getFlightRoutePackageService();
        IFlightRoutePackageService service2 = FactoryController.getFlightRoutePackageService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getFlightRoutePackageController_shouldReturnSingletonInstance() {
        // GIVEN
        IFlightRoutePackageController controller1 = FactoryController.getFlightRoutePackageController();
        IFlightRoutePackageController controller2 = FactoryController.getFlightRoutePackageController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

    @Test
    void getCityService_shouldReturnSingletonInstance() {
        // GIVEN
        ICityService service1 = FactoryController.getCityService();
        ICityService service2 = FactoryController.getCityService();

        // THEN
        assertNotNull(service1);
        assertSame(service1, service2);
    }

    @Test
    void getCityController_shouldReturnSingletonInstance() {
        // GIVEN
        ICityController controller1 = FactoryController.getCityController();
        ICityController controller2 = FactoryController.getCityController();

        // THEN
        assertNotNull(controller1);
        assertSame(controller1, controller2);
    }

//    @Test
//    void sharedModelMapper_shouldBeSameAcrossAllServices() {
//        // GIVEN
//        ModelMapper mapperFromUser = FactoryController.getUserService().getModelMapper();
//        ModelMapper mapperFromFlight = FactoryController.getFlightController().getFlightService().getModelMapper();
//        ModelMapper mapperFromCity = FactoryController.getCityService().getModelMapper();
//
//        // THEN
//        assertSame(mapperFromUser, mapperFromFlight);
//        assertSame(mapperFromUser, mapperFromCity);
//    }
}
