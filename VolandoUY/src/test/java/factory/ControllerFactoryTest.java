package factory;

import controllers.user.IUserController;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class ControllerFactoryTest {

    @Test
    void getUserController_shouldReturnTheSameInstance() {
        IUserController instance1 = ControllerFactory.getUserController();
        IUserController instance2 = ControllerFactory.getUserController();

        assertNotNull(instance1);
        assertSame(instance1, instance2, "Las instancias de UserController deberían ser las mismas");
    }

    @Test
    void getModelMapper_shouldReturnTheSameInstance() {
        ModelMapper mapper1 = ControllerFactory.getModelMapper();
        ModelMapper mapper2 = ControllerFactory.getModelMapper();

        assertNotNull(mapper1);
        assertSame(mapper1, mapper2, "Las instancias de ModelMapper deberían ser las mismas");
    }
}
