package factory;

import controllers.user.IUserController;
import controllers.user.UserController;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import org.modelmapper.ModelMapper;


public class ControllerFactory {

    private static IUserController usuarioController;
    private static ModelMapper modelMapper;
    private static UserMapper userMapper;
    private static UserFactoryMapper userFactoryMapper;
    private static IUserService usuarioService;

    public static IUserController getUsuarioController() {
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
    public static UserMapper getusuarioMapper() {
        if(userMapper == null){
            return new UserMapper(getModelMapper());
        }
        return userMapper;
    }
    public static IUserService getUsuarioService() {
        if(usuarioService == null){
            return new UserService();
        }
        return usuarioService;
    }
    public static UserFactoryMapper getUsuarioFactoryMapper() {
        if(userFactoryMapper == null){
            return new UserFactoryMapper(getModelMapper());
        }
        return userFactoryMapper;
    }

    public static IUserController crearUsuarioController() {
        return new UserController(getUsuarioService(), getModelMapper(),getusuarioMapper(),getUsuarioFactoryMapper());
    }

    public static IUserController crearUsuarioController(IUserService usuarioService, ModelMapper modelMapper, UserMapper userMapper, UserFactoryMapper userFactoryMapper) {
        return new UserController(usuarioService, modelMapper, userMapper, userFactoryMapper);
    }
}