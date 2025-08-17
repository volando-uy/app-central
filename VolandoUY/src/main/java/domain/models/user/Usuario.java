package domain.models.user;

import domain.dtos.user.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Usuario {
    private String nombre;
    private String nickname;
    private String mail;

    public abstract void actualizarDatosDesde(UsuarioDTO nuevosDatos);

}
