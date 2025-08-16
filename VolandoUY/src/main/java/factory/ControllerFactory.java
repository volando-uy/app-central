package factory;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.services.user.IUsuarioService;
import domain.services.user.UsuarioService;
import org.modelmapper.ModelMapper;


public class ControllerFactory {

    private static IUsuarioController usuarioController;
    private static ModelMapper modelMapper;
    public static IUsuarioController getUsuarioController() {
        if (usuarioController == null) {
            usuarioController = crearUsuarioController();
        }
        return usuarioController;
    }
    public static ModelMapper getModelMapper() {
        if(modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    private static IUsuarioController crearUsuarioController() {
        IUsuarioService usuarioService = new UsuarioService(); // por ahora en memoria
        return new UsuarioController(usuarioService, getModelMapper());
    }
}