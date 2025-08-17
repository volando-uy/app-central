package domain.models.user;

import domain.dtos.user.AerolineaDTO;
import domain.dtos.user.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aerolinea extends Usuario{
    private String descripcion;
    private String web;

    @Override
    public void actualizarDatosDesde(UsuarioDTO nuevosDatos) {
        if (!(nuevosDatos instanceof AerolineaDTO nuevo)) return;

        this.setNombre(nuevo.getNombre());
        this.setDescripcion(nuevo.getDescripcion());
        this.setWeb(nuevo.getWeb());

    }
}
