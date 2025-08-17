package factory;

import controllers.user.IUsuarioController;
import controllers.user.UsuarioController;
import domain.models.user.mapper.UsuarioMapper;
import domain.services.user.IUsuarioService;
import domain.services.user.UsuarioService;
import org.modelmapper.ModelMapper;


public class ControllerFactory {

    private static IUsuarioController usuarioController;
    private static ModelMapper modelMapper;
    private static UsuarioMapper usuarioMapper;
    private static UsuarioFactoryMapper usuarioFactoryMapper;
    private static IUsuarioService usuarioService;

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
    public static UsuarioMapper getusuarioMapper() {
        if(usuarioMapper == null){
            return new UsuarioMapper(getModelMapper());
        }
        return usuarioMapper;
    }
    public static IUsuarioService getUsuarioService() {
        if(usuarioService == null){
            return new UsuarioService();
        }
        return usuarioService;
    }
    public static UsuarioFactoryMapper getUsuarioFactoryMapper() {
        if(usuarioFactoryMapper == null){
            return new UsuarioFactoryMapper(getModelMapper());
        }
        return usuarioFactoryMapper;
    }

    public static IUsuarioController crearUsuarioController() {
        return new UsuarioController(getUsuarioService(), getModelMapper(),getusuarioMapper(),getUsuarioFactoryMapper());
    }

    public static IUsuarioController crearUsuarioController(IUsuarioService usuarioService, ModelMapper modelMapper, UsuarioMapper usuarioMapper, UsuarioFactoryMapper usuarioFactoryMapper) {
        return new UsuarioController(usuarioService, modelMapper, usuarioMapper, usuarioFactoryMapper);
    }
}