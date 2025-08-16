package domain.services.user;

import domain.models.user.Aereolinea;
import domain.models.user.Cliente;
import domain.models.user.Usuario;
import shared.constants.ErrorMessages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioService implements IUsuarioService {
    //Supongamos que tenemos el UserRepository
    LinkedList<Usuario> usuarios = new LinkedList<Usuario>();

    @Override
    public void altaCliente(Cliente cliente) {
        if (existeUsuario(cliente)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_CLIENTE_EXISTE, cliente.getNickname()));
        }
        usuarios.add(cliente);
    }

    @Override
    public void altaAereolinea(Aereolinea aereolinea) {
        usuarios.add(aereolinea);
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarios;
    }

    public List<String> obtenerTodosLosNicknames() {
        return usuarios.stream().map(Usuario::getNickname).collect(Collectors.toList());
    }

    public Usuario obtenerUsuarioPorNickname(String nickname){
        return usuarios.stream().filter(u -> u.getNickname().equalsIgnoreCase(nickname)).findFirst().orElse(null);
    }

    @Override
    public Usuario modificarDatosUsuarioTemporal(Usuario usuario) {

        return null;
    }

    //Func helper para que no hayan 2 usaurios con el mismo nickname
    private boolean existeUsuario(Usuario usuario) {
//        System.out.println("Mi nick"+  usuario.getNickname());
//        usuarios.stream().forEach(u -> System.out.println(u.getNickname()));
        return usuarios.stream().anyMatch(u -> u.getNickname().equalsIgnoreCase(usuario.getNickname()));
    }


}
