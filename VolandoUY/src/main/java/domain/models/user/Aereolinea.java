package domain.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aereolinea extends Usuario{
    private String descripcion;
    private String web;

    @Override
    public void actualizarDatosDesde(Usuario nuevosDatos) {
        if (!(nuevosDatos instanceof Aereolinea nuevo)) return;

        this.setNombre(nuevo.getNombre());
        this.setDescripcion(nuevo.getDescripcion());
        this.setWeb(nuevo.getWeb());

    }
}
