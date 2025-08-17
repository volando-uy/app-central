package domain.models.user;

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
    public void actualizarDatosDesde(Usuario nuevosDatos) {
        if (!(nuevosDatos instanceof Aerolinea nuevo)) return;

        this.setNombre(nuevo.getNombre());
        this.setDescripcion(nuevo.getDescripcion());
        this.setWeb(nuevo.getWeb());

    }
}
