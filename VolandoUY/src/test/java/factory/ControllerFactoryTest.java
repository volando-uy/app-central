package factory;

import controllers.user.IUsuarioController;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class ControllerFactoryTest {

    @Test
    void getUsuarioController_deberiaRetornarLaMismaInstancia() {
        IUsuarioController instancia1 = ControllerFactory.getUsuarioController();
        IUsuarioController instancia2 = ControllerFactory.getUsuarioController();

        assertNotNull(instancia1);
        assertSame(instancia1, instancia2, "Las instancias de UsuarioController deberían ser las mismas");
    }

    @Test
    void getModelMapper_deberiaRetornarLaMismaInstancia() {
        ModelMapper mapper1 = ControllerFactory.getModelMapper();
        ModelMapper mapper2 = ControllerFactory.getModelMapper();

        assertNotNull(mapper1);
        assertSame(mapper1, mapper2, "Las instancias de ModelMapper deberían ser las mismas");
    }
}
